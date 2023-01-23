package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.SiteIndexingAction;
import searchengine.dto.statistics.URL;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService{

    private final SitesList sites;
    private SiteIndexingAction siteIndexingAction;
    private static final int CORE = Runtime.getRuntime().availableProcessors();
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    private ExecutorService executorService = new ThreadPoolExecutor(CORE, 10, 1, TimeUnit.HOURS,
            new LinkedBlockingQueue<Runnable>(10));
    private volatile boolean isStarted;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Map<String,String> startedIndexing() {
        isStarted = true;
        List<Site> listSites = sites.getSites();
        Map<String, String> map = new HashMap<>();

        if(forkJoinPool.getPoolSize() > 0){
            Map<String, String> map1 = new HashMap<>();
            map1.put("result", "false");
            map1.put("error", "Индексация уже запущена");
            return map1;
        }

        siteRepository.deleteAll();
        pageRepository.deleteAll();

        try {
            for(Site site : listSites) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        LocalDateTime statusTime = LocalDateTime.now();
                        searchengine.model.Site newSite = new searchengine.model.Site(Status.INDEXING, statusTime, "NULL", site.getUrl(), site.getName());
                        siteRepository.save(newSite);
                        URL url = new URL(site.getUrl());
                        siteIndexingAction = new SiteIndexingAction(url, newSite, pageRepository, siteRepository);
                        forkJoinPool.invoke(siteIndexingAction);
                        System.out.println("FINISHED");
                        forkJoinPool.shutdown();
                        if(forkJoinPool.isShutdown()){
                            LocalDateTime newTime = LocalDateTime.now();
                            newSite.setStatus(Status.INDEXED);
                            newSite.setStatusTime(newTime);
                            siteRepository.save(newSite);
                        }
                    }
                });
            }
        } catch (RejectedExecutionException ree) {
            ree.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        map.put("result", "true");
        return map;
    }

    public Map<String,String> stopIndexing(){
        isStarted = false;
        Map<String,String> map = new HashMap<>();

        if(forkJoinPool.getPoolSize() == 0){
            Map<String, String> map1 = new HashMap<>();
            map1.put("result", "false");
            map1.put("error", "Индексация не запущена");
            return map1;
        }

        shutdownAndAwaitTermination();

        map.put("result", "true");
        return map;
    }

    public void shutdownAndAwaitTermination() {
        forkJoinPool.shutdown();
        try {
            if (!forkJoinPool.awaitTermination(120, TimeUnit.SECONDS)) {
                forkJoinPool.shutdownNow();
                if (!forkJoinPool.awaitTermination(120, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            forkJoinPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void stop(){
        try {
            forkJoinPool.shutdownNow();
            try {
                forkJoinPool.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            List<searchengine.model.Site> siteList = siteRepository.findAll();
            for (searchengine.model.Site site : siteList) {
                site.setStatus(Status.FAILED);
                siteRepository.save(site);
            }
        }
    }

}





        /*siteRepository.deleteAll();
        pageRepository.deleteAll();

        for(Site site : listSites){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    LocalDateTime statusTime = LocalDateTime.now();
                    searchengine.model.Site newSite = new searchengine.model.Site(Status.INDEXING, statusTime, "NULL", site.getUrl(), site.getName());
                    siteRepository.save(newSite);
                    URL url = new URL(site.getUrl());
                    forkJoinPool.invoke(new SiteIndexingAction(url, newSite, pageRepository, siteRepository));
                    System.out.println("FINISHED");
                    forkJoinPool.shutdown();
                    if(forkJoinPool.isShutdown()){
                        LocalDateTime newTime = LocalDateTime.now();
                        newSite.setStatus(Status.INDEXED);
                        newSite.setStatusTime(newTime);
                        siteRepository.save(newSite);
                    }
                }
            });
        }
        executorService.shutdown();*/






       /* ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        siteRepository.deleteAll();
        pageRepository.deleteAll();
        for(Site site : listSites){
            MyThreadParser myThreadParser = new MyThreadParser(pageRepository, siteRepository, site);
            executorService.submit(myThreadParser);
            threadList.add(myThreadParser);
        }
        executorService.shutdown();*/
