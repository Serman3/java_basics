package searchengine.services;

import searchengine.dto.statistics.StatisticsResponse;

import java.util.Map;

public interface IndexingService {
    Map<String,String> startIndexing();
}
