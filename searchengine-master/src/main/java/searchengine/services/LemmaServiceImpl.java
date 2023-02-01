package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.LemmaFinder;
import searchengine.dto.statistics.UtilParsing;
import searchengine.model.*;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LemmaServiceImpl extends UtilParsing implements LemmaService{

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Map<String, String> indexPage(String url) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        LemmaFinder lemmaFinder = new LemmaFinder(new RussianLuceneMorphology());

        if(!siteRepository.findByUrl(url).isPresent()){
            HashMap<String,String> map = new HashMap<>();
            map.put("result","false");
            map.put("error", "Данная страница находится за пределами сайтов, указанных в конфигурационном файле");
            return map;
        }
        startReIndexing(url);

        List<Page> pages = pageRepository.findByPathStartingWith(url);
        for(Page page : pages){
            String text = lemmaFinder.clearHTMLTags(page);
            Lemma lemmaEntity = null;
            Map<String,Integer> lemmaInfo = lemmaFinder.collectLemmas(text);
            for (Map.Entry<String,Integer> entry : lemmaInfo.entrySet()){
                String lemma = entry.getKey();
                float rank = (float) entry.getValue();
                if(!lemmaRepository.findByLemma(lemma).isPresent()){
                    lemmaRepository.save(new Lemma(page.getSite(), lemma, 1));
                }else{
                    lemmaEntity = lemmaRepository.findByLemma(lemma).get();
                    lemmaEntity.setFrequency(lemmaEntity.getFrequency() + 1);
                    lemmaRepository.save(lemmaEntity);
                }
                indexRepository.save(new Index(page, lemmaEntity, rank));
            }

        }
        response.put("result","true");
        return response;
    }

    public void startReIndexing(String path){
       if (siteRepository.findByUrl(path).isPresent()){
           Site siteEntity = siteRepository.findByUrl(path).get();
           siteRepository.delete(siteRepository.getByUrl(path));
           startIndexing(path, siteEntity.getName());
       }else {
           System.out.println("Такой страницы в базе нет");
       }
    }

}










/* ForkJoinPool forkJoinPool = new ForkJoinPool(CORE);
           LocalDateTime statusTime = LocalDateTime.now();
           searchengine.model.Site newSite = new searchengine.model.Site(Status.INDEXING, statusTime, "NULL", path, siteEntity.getName());
           siteRepository.save(newSite);
           SiteIndexingAction siteIndexingAction = new SiteIndexingAction(new URL(path), newSite, pageRepository, siteRepository);
           forkJoinPool.invoke(siteIndexingAction);
           System.out.println("FINISHED");
           forkJoinPool.shutdown();
           if(forkJoinPool.isShutdown()){
               LocalDateTime newTime = LocalDateTime.now();
               newSite.setStatus(Status.INDEXED);
               newSite.setStatusTime(newTime);
               siteRepository.save(newSite);
           }*/
