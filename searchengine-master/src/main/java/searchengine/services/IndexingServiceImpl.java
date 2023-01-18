package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.MyThreadParser;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.util.*;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService{

    private final SitesList sites;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Map<String,String> startedIndexing() {

        List<Site> listSites = sites.getSites();
        List<Thread> threadList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        if(MyThreadParser.getPoolSize() > 0){
            Map<String, String> map1 = new HashMap<>();
            map1.put("result", "false");
            map1.put("error", "Индексация уже запущена");
            return map1;
        }

        if(!MyThreadParser.fjpIsActive()){
            for(Site site : listSites){
                siteRepository.deleteAll();
                pageRepository.deleteAll();
                MyThreadParser myThreadParser = new MyThreadParser(pageRepository, siteRepository, site);
                Thread myThread = new Thread(myThreadParser);
                threadList.add(myThread);
                myThread.start();
            }
        }
        map.put("result", "true");
        return map;
    }

}
