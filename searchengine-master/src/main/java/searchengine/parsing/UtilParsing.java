package searchengine.parsing;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.beans.factory.annotation.Autowired;
import searchengine.model.*;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public class UtilParsing {

    private volatile boolean doStop = false;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private IndexRepository indexRepository;
    private LemmaFinder lemmaFinder;
    {
        try {
            lemmaFinder = new LemmaFinder(new RussianLuceneMorphology());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startIndexing(String path, String name){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        searchengine.model.Site newSite = new searchengine.model.Site(Status.INDEXING, LocalDateTime.now(), "NULL", path, name);
        siteRepository.save(newSite);
        SiteIndexingAction siteIndexingAction = new SiteIndexingAction(path, newSite, pageRepository, siteRepository, UtilParsing.this);
        forkJoinPool.invoke(siteIndexingAction);
        System.out.println("FINISHED");
        forkJoinPool.shutdown();
        if(forkJoinPool.isShutdown()){
            newSite.setStatus(Status.INDEXED);
            newSite.setStatusTime(LocalDateTime.now());
            siteRepository.save(newSite);
        }
        if(isStopped()){
            closePool(forkJoinPool);
            setStatusAllSitesFailed();
            doStop(false);
        }
    }

    public void transformationLemmasAndIndex(Page page) {
        String text = lemmaFinder.clearHTMLTags(page);
        Map<String,Integer> lemmaInfo = lemmaFinder.collectLemmas(text);
        for (Map.Entry<String,Integer> entry : lemmaInfo.entrySet()){
            if(isStopped()){
                break;
            }
            Lemma lemmaEntity = null;
            String lemma = entry.getKey();
            float rank = (float) entry.getValue();
            if(!lemmaRepository.findFirstByLemma(lemma).isPresent()){
                lemmaEntity = new Lemma(page.getSite(), lemma, 1);
                if(isStopped()){
                    break;
                }
                lemmaRepository.save(lemmaEntity);
            }else{
                lemmaEntity = lemmaRepository.findFirstByLemma(lemma).get();
                lemmaEntity.setFrequency(lemmaEntity.getFrequency() + 1);
                lemmaRepository.save(lemmaEntity);
            }
            if(isStopped()){
                break;
            }
            indexRepository.save(new Index(page, lemmaEntity, rank));
        }
    }

    public void setStatusAllSitesFailed(){
        List<searchengine.model.Site> siteList = siteRepository.findAll();
        for (searchengine.model.Site site : siteList) {
            site.setLastError("Индексация остановлена пользователем");
            site.setStatus(Status.FAILED);
            siteRepository.save(site);
        }
    }

    public synchronized boolean isIndexing(Status status){
        List<Site> listSites = siteRepository.findByStatus(status);
        return !listSites.isEmpty();
    }

    public void doStop(boolean flag) {
        this.doStop = flag;
    }

    public boolean isStopped(){
        return doStop;
    }

    public void closePool(ForkJoinPool forkJoinPool){
        System.out.println("pool close");
        while (!forkJoinPool.isTerminated()){
            forkJoinPool.shutdownNow();
        }
    }

}
