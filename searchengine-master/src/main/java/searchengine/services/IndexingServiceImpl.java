package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.SiteIndexingAction;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.URL;
import searchengine.repository.IndexingRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService{

    private SitesList sitesList;

    @Autowired
    private IndexingRepository indexingRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;


    @Override
    public Map<String,String> startIndexing() {

        Map<String, String> map = new HashMap<>();

        for(Site site : sitesList.getSites()){
            String siteLink = site.getUrl();
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            URL url = new URL(siteLink);
            forkJoinPool.invoke(new SiteIndexingAction(url));
        }
        System.out.println("FINISH");
        map.put("result", "true");
        return map;
    }
}
