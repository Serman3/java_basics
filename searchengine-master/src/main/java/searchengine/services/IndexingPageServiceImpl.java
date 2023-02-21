package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.parsing.UtilParsing;
import searchengine.model.*;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.InterfacesServices.IndexingPageService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IndexingPageServiceImpl extends UtilParsing implements IndexingPageService {

    @Autowired
    private final SiteRepository siteRepository;
    @Autowired
    private final LemmaRepository lemmaRepository;
    @Autowired
    private final IndexRepository indexRepository;
    @Autowired
    private final PageRepository pageRepository;

    @Override
    public Map<String, String> indexPage(String url) throws IOException {
        HashMap<String, String> response = new HashMap<>();

        if(!siteRepository.findByUrl(url).isPresent()){
            HashMap<String,String> map = new HashMap<>();
            map.put("result","false");
            map.put("error", "Данная страница находится за пределами сайтов, указанных в конфигурационном файле");
            return map;
        }
        startReIndexing(url);
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
