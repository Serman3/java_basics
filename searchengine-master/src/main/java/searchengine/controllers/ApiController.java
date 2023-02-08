package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.IndexingService;
import searchengine.services.LemmaService;
import searchengine.services.SearchService;
import searchengine.services.StatisticsService;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StatisticsService statisticsService;
    private final IndexingService indexingService;
    private final LemmaService lemmaService;
    private final SearchService searchService;

    public ApiController(StatisticsService statisticsService, IndexingService indexingService, LemmaService lemmaService, SearchService searchService) {
        this.statisticsService = statisticsService;
        this.indexingService = indexingService;
        this.lemmaService = lemmaService;
        this.searchService = searchService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<Map<String,String>> startIndexing(){
        Map<String, String> response = indexingService.startedIndexing();
        if(response.get("result").equals("false")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<Map<String,String>> stopIndexing(){
        Map<String, String> response = indexingService.stopIndexing();
        if(response.get("result").equals("false")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("indexPage")
    public ResponseEntity<Map<String,String>> indexPage(@RequestParam String url){
        Map<String, String> response = null;
        try {
            response = lemmaService.indexPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.get("result").equals("false")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String,String>> search(@RequestParam(value = "query") String query,
                                                     @RequestParam(value = "site", defaultValue = "") String siteUrl,
                                                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(value = "limit", defaultValue = "20") int limit){
        Map<String, String> response = searchService.search(query, siteUrl, offset, limit);
        if(response.get("result").equals("false")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

}
