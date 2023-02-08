package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.SitesList;
import searchengine.dto.statistics.*;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.parsing.UtilParsing;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl extends UtilParsing implements StatisticsService {

    private final Random random = new Random();
    private final SitesList sites;
    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public StatisticsResponse getStatistics() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int pagesCount = pageRepository.findAll().size();
        int lemmasCount = lemmaRepository.findAll().size();

        String[] errors = {
                "Ошибка индексации: главная страница сайта не доступна",
                "Ошибка индексации: сайт не доступен",
                ""
        };

        TotalStatistics total = new TotalStatistics();
        total.setSites(sites.getSites().size());
        if(isIndexing(Status.INDEXING)) {
            total.setIndexing(true);
        }else {
            total.setIndexing(false);
        }

        List<DetailedStatisticsItem> detailed = new ArrayList<>();
        List<Site> sitesList = siteRepository.findAll();
        for(Site site : sitesList) {
            DetailedStatisticsItem item = new DetailedStatisticsItem();
            item.setName(site.getName());
            item.setUrl(site.getUrl());
            item.setPages(pageRepository.countPagesBySiteId(site.getId()));
            item.setLemmas(lemmaRepository.countLemmasBySiteId(site.getId()));
            item.setStatus(site.getStatus().toString());
            item.setStatusTime(site.getStatusTime().format(formatter));
            total.setPages(pagesCount);
            total.setLemmas(lemmasCount);
            detailed.add(item);
        }

        StatisticsResponse response = new StatisticsResponse();
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }
}
