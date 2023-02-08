package searchengine.parsing;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public class SiteIndexingAction extends RecursiveAction {

    private final String url;
    private final Site site;
    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;
    private final UtilParsing utilParsing;
    private static final Set<String> links = Collections.synchronizedSet(new HashSet<>());

    public SiteIndexingAction(String url, Site site, PageRepository pageRepository, SiteRepository siteRepository, UtilParsing utilParsing) {
        this.url = url;
        this.site = site;
        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
        this.utilParsing = utilParsing;
    }

    @Override
    protected void compute() {
        List<SiteIndexingAction> tasks = new ArrayList<>();
        Document document = null;
        try {
            Thread.sleep(150);
            Connection connection = getConnection(url);
            Connection.Response response = connection.execute();
            int statusCode = response.statusCode();
            if (statusCode == 200/* && response.contentType().startsWith("text/html")*/) {
                document = connection.get();
                Elements elements = document.select("a");
                String content = document.html();
                for (Element element : elements) {
                     String path = element.attr("abs:href");
                    if (isCorrectUrl(path) && !pageRepository.findByPathAndSite_id(path.replaceFirst(url,"/"), site.getId()).isPresent()) {
                        System.out.println(path);
                        if(stopped()){
                            links.clear();
                            System.out.println("STOPPED FAILED");
                            break;
                        }
                        Page page = new Page(site, path.replaceFirst(url,"/"), statusCode, content);
                        pageRepository.save(page);
                        site.setStatusTime(LocalDateTime.now());
                        siteRepository.save(site);
                        links.add(path);
                        SiteIndexingAction task = new SiteIndexingAction(path, site, pageRepository, siteRepository, utilParsing);
                        tasks.add(task);
                        if(stopped()){
                            break;
                        }
                    }
                }
                for(SiteIndexingAction task1 : tasks){
                    task1.fork();
                    task1.join();
                }
                links.clear();
            }
        }catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isCorrectUrl(String path){
        if(!path.isEmpty() && path.startsWith(url) && !links.contains(path) && !path.contains("#")){
            return true;
        }
        return false;
    }

    public Connection getConnection(String url){
        Connection connection = Jsoup.connect(url)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .followRedirects(false)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .timeout(30000);
        return connection;
    }

    public void forkTasks(Set<String> childUrl){
        for (String url : childUrl) {
            SiteIndexingAction task = new SiteIndexingAction(url, site, pageRepository, siteRepository, utilParsing);
            if(stopped()){
                break;
            }
            task.fork();
            task.join();
        }
    }

    public boolean stopped(){
        return utilParsing.isStopped();
    }

    public synchronized void savePage(Site site, String path, int code, String html){
        if(!pageRepository.findByPathAndSite_id(path.replaceFirst(url,"/"), site.getId()).isPresent()) {
            Page page = new Page(site, path, code, html);
            pageRepository.save(page);
        }
        //utilParsing.transformationLemmasAndIndex(page);
        /*pagesSet.add(page);
        if(pagesSet.size() == 30){
            pageRepository.saveAll(pagesSet);
            utilParsing.transformationLemmasAndIndex(pagesSet);
            pagesSet.clear();
        }*/
    }
}
