package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.SiteConfig;
import searchengine.config.SitesList;
import searchengine.dto.statistics.UtilParsing;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl extends UtilParsing implements IndexingService{

    private final SitesList sites;
    private static final int CORE = Runtime.getRuntime().availableProcessors();
    //private ForkJoinPool forkJoinPool;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Map<String,String> startedIndexing() {

        List<SiteConfig> listSites = sites.getSites();
        Map<String, String> response = new HashMap<>();

        if(isIndexing(Status.INDEXING)/*forkJoinPool.getPoolSize() > 0*/){
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
                executorService.submit(new Runnable() {
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

        if(!isIndexing(Status.INDEXING)/*forkJoinPool.getPoolSize() == 0*/){
            Map<String, String> map = new HashMap<>();
            map.put("result", "false");
            map.put("error", "Индексация не запущена");
            return map;
        }

        doStop();

        response.put("result", "true");
        return response;
    }






    /*public void closePool(ExecutorService pool){
        while(!pool.isTerminated()){
            pool.shutdownNow();
        }
        System.out.println("pool close");
    }

    public boolean isIndexing(Status status){
        List<Site> listSites = siteRepository.findByStatus(status);
        return !listSites.isEmpty();
    }*/




    /*LocalDateTime statusTime = LocalDateTime.now();
                        searchengine.model.Site newSite = new searchengine.model.Site(Status.INDEXING, statusTime, "NULL", site.getUrl(), site.getName());
                        siteRepository.save(newSite);
                        SiteIndexingAction siteIndexingAction = new SiteIndexingAction(new URL(site.getUrl()), newSite, pageRepository, siteRepository);
                        forkJoinPool = new ForkJoinPool();
                        forkJoinPool.invoke(siteIndexingAction);
                        System.out.println("FINISHED");
                        forkJoinPool.shutdown();
                        if(forkJoinPool.isShutdown()){
                            LocalDateTime newTime = LocalDateTime.now();
                            newSite.setStatus(Status.INDEXED);
                            newSite.setStatusTime(newTime);
                            siteRepository.save(newSite);
                        }*/






    /*closePool(forkJoinPool);

        if (forkJoinPool.isTerminated()){
        if(isTerminated()){
            List<searchengine.model.Site> siteList = siteRepository.findAll();
            for (searchengine.model.Site site : siteList) {
                site.setLastError("Индексация остановлена пользователем");
                site.setStatus(Status.FAILED);
                siteRepository.save(site);
            }
        }

        }*/

}

