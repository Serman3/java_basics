package searchengine.services;

import java.util.Map;

public interface SearchService {
    Map<String,String> search(String query, String url, int offset, int limit);
}
