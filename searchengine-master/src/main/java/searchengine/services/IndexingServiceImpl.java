package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.SiteConfig;
import searchengine.config.SitesList;
import searchengine.parsing.UtilParsing;
import searchengine.model.Status;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.InterfacesServices.IndexingService;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl extends UtilParsing implements IndexingService {

    private final SitesList sites;
    @Autowired
    private final SiteRepository siteRepository;
    @Autowired
    private final PageRepository pageRepository;
    @Autowired
    private final LemmaRepository lemmaRepository;
    @Autowired
    private final IndexRepository indexRepository;
    private static final int CORE = Runtime.getRuntime().availableProcessors();

    @Override
    public Map<String,String> startedIndexing() {
        List<SiteConfig> listSites = sites.getSites();
        Map<String, String> response = new HashMap<>();
        if(isIndexing(Status.INDEXING)){
            Map<String, String> map = new HashMap<>();
            map.put("result", "false");
            map.put("error", "Индексация уже запущена");
            return map;
        }
       /* for(SiteConfig site : listSites){
            siteRepository.findByUrl(site.getUrl()).ifPresent(siteRepository.delete());
        }*/
        siteRepository.deleteAll();
        pageRepository.deleteAll();
        ExecutorService executorService = Executors.newFixedThreadPool(CORE);
        try {
            for(SiteConfig site : listSites) {
                executorService.submit(
                        new Runnable() {
                    @Override
                    public void run() {
                       startIndexing(site.getUrl(), site.getName());
                    }
                });
            }
        } catch (RejectedExecutionException ree) {
            ree.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        response.put("result", "true");
        return response;
    }

    public Map<String,String> stopIndexing(){
        Map<String,String> response = new HashMap<>();
        if(!isIndexing(Status.INDEXING)){
            Map<String, String> map = new HashMap<>();
            map.put("result", "false");
            map.put("error", "Индексация не запущена");
            return map;
        }
        doStop(true);
        response.put("result", "true");
        return response;
    }
}

