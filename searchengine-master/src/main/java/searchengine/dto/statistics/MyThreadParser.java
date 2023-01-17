package searchengine.dto.statistics;

import searchengine.config.Site;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;

public class MyThreadParser implements Runnable{

    private PageRepository pageRepository;
    private SiteRepository siteRepository;
    private Site site;

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
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new SiteIndexingAction(url, newSite, pageRepository));
        System.out.println("FINISHED");
        forkJoinPool.shutdown();
        if(forkJoinPool.isShutdown()){
            LocalDateTime newTime = LocalDateTime.now();
            newSite.setStatus(Status.INDEXED);
            newSite.setStatusTime(newTime);
            siteRepository.save(newSite);
        }
    }

}
