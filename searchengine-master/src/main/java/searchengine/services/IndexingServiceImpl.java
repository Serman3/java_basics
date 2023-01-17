package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.MyThreadParser;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.time.LocalDateTime;
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

        Map<String, String> map = new HashMap<>();
        List<Site> listSites = sites.getSites();
        String[] result = { "true", "false" };
        String error = "Индексация уже запущена";

        siteRepository.deleteAll();
        pageRepository.deleteAll();

        for(Site site : listSites){
            MyThreadParser myThreadParser = new MyThreadParser(pageRepository, siteRepository, site);
            Thread myThread = new Thread(myThreadParser);
            myThread.start();
        }

        map.put("result", result[0]);
        return map;
    }

}
