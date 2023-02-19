package searchengine.services.InterfacesServices;

import java.io.IOException;
import java.util.Map;

public interface LemmaService {
    Map<String,String> indexPage(String url) throws IOException;
}
