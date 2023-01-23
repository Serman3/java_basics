package searchengine.dto.statistics;

import searchengine.config.Site;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class MyThreadParser implements Runnable{

    private PageRepository pageRepository;
    private SiteRepository siteRepository;
    private Site site;
    private static ForkJoinPool forkJoinPool = new ForkJoinPool();

    public MyThreadParser(PageRepository pageRepository, SiteRepository siteRepository, Site site) {
        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
        this.site = site;

    }

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

    public static boolean IsActiveFJP(){
        return forkJoinPool.hasQueuedSubmissions();
    }

    public static int getFJPSize(){
        return forkJoinPool.getPoolSize();
    }

    public void stopFJP(SiteRepository siteRepository){
        forkJoinPool.shutdownNow();
        if (forkJoinPool.isTerminating()){
            List<searchengine.model.Site> siteList = siteRepository.findAll();
            for(searchengine.model.Site site : siteList){
                site.setStatus(Status.FAILED);
                siteRepository.save(site);
           }
        }
    }

    public void shutdownAndAwaitTermination() {
        forkJoinPool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!forkJoinPool.awaitTermination(60, TimeUnit.SECONDS)) {
                forkJoinPool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!forkJoinPool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            forkJoinPool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public void stop() throws InterruptedException {
        try {
            forkJoinPool.shutdownNow();
            forkJoinPool.awaitTermination(60, TimeUnit.SECONDS);
        } finally {
            List<searchengine.model.Site> siteList = siteRepository.findAll();
            for (searchengine.model.Site site : siteList) {
                site.setStatus(Status.FAILED);
                siteRepository.save(site);
            }
        }
    }

}
