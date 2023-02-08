package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.Site;
import searchengine.parsing.LemmaFinder;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

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

    @Override
    public Map<String, String> search(String query, String siteUrl, int offset, int limit) {
        Set<String> searchLemmas = new HashSet<>();
        Map<String, Integer> searchQuery = lemmaFinder.collectLemmas(query);
        for(Map.Entry<String, Integer> entry : searchQuery.entrySet()){
            searchLemmas.add(entry.getKey());
        }

        List<Site> sites = siteRepository.findAll();
        for(Site site : sites){
           // lemmaRepository.getRelevantLemmas(searchLemmas.)
        }



        return null;
    }
}
