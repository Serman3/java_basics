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
    private LemmaFinder lemmaFinder;
    {
        try {
            lemmaFinder = new LemmaFinder(new RussianLuceneMorphology());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private PageRepository pageRepository;

    public void startIndexing(String path, String name){
        searchengine.model.Site newSite = new searchengine.model.Site(Status.INDEXING, LocalDateTime.now(), "NULL", path, name);
        siteRepository.save(newSite);
        SiteIndexingAction siteIndexingAction = new SiteIndexingAction(path, newSite, pageRepository, siteRepository, UtilParsing.this);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(siteIndexingAction);
        System.out.println("FINISHED");
        forkJoinPool.shutdown();
        if(forkJoinPool.isShutdown()){
            newSite.setStatus(Status.INDEXED);
            newSite.setStatusTime(LocalDateTime.now());
            siteRepository.save(newSite);
        }
        if(isStopped()){
            List<searchengine.model.Site> siteList = siteRepository.findAll();
            for (searchengine.model.Site site : siteList) {
                site.setLastError("Индексация остановлена пользователем");
                site.setStatus(Status.FAILED);
                siteRepository.save(site);
            }
            doStop(false);
        }
    }

    public void transformationLemmasAndIndex(Page page) {
       /* for (Page page : pageSet){
            String text = lemmaFinder.clearHTMLTags(page);
            Lemma lemmaEntity = null;
            Map<String,Integer> lemmaInfo = lemmaFinder.collectLemmas(text);
            for (Map.Entry<String,Integer> entry : lemmaInfo.entrySet()){
                String lemma = entry.getKey();
                float rank = (float) entry.getValue();
                if(!lemmaRepository.findFirstByLemma(lemma).isPresent()){
                    lemmaRepository.save(new Lemma(page.getSite(), lemma, 1));
                }else{
                    lemmaEntity = lemmaRepository.findFirstByLemma(lemma).get();
                    lemmaEntity.setFrequency(lemmaEntity.getFrequency() + 1);
                    lemmaRepository.save(lemmaEntity);
                }
                indexRepository.save(new Index(page, lemmaEntity, rank));
            }
        }*/
        String text = lemmaFinder.clearHTMLTags(page);
        Lemma lemmaEntity = null;
        Map<String,Integer> lemmaInfo = lemmaFinder.collectLemmas(text);
        for (Map.Entry<String,Integer> entry : lemmaInfo.entrySet()){
            if(isStopped()){
                break;
            }
            String lemma = entry.getKey();
            float rank = (float) entry.getValue();
            if(!lemmaRepository.findFirstByLemma(lemma).isPresent()){
                lemmaRepository.save(new Lemma(page.getSite(), lemma, 1));
            }else{
                lemmaEntity = lemmaRepository.findFirstByLemma(lemma).get();
                lemmaEntity.setFrequency(lemmaEntity.getFrequency() + 1);
                lemmaRepository.save(lemmaEntity);
            }
            indexRepository.save(new Index(page, lemmaEntity, rank));
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

    /*public void closePool(){
        System.out.println("pool close");
        while(!forkJoinPool.isTerminated()){
            forkJoinPool.shutdownNow();
        }
    }*/
}
