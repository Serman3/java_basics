package searchengine.parsing;

import com.sun.istack.NotNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import static java.lang.Thread.sleep;

public class SiteIndexingAction extends RecursiveTask<Set<String>> {

    private String url;
    private String rootUrl;
    private Site site;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;
    private UtilParsing utilParsing;
    private static volatile Set<String> allLinks = Collections.synchronizedSet(new HashSet<>());
    //private static final String URL_IS_FILE = "http[s]?:/(?:/[^/]+){1,}/[А-Яа-яёЁ\\w ]+\\.[a-z]{3,5}(?![/]|[\\wА-Яа-яёЁ])";
    private static final String URL_IS_FILE = "(\\S+(\\.(?i)(jpg|jpeg|JPG|png|gif|bmp|pdf|xml))$)";

    public SiteIndexingAction(String rootUrl, Site site, PageRepository pageRepository, SiteRepository siteRepository, UtilParsing utilParsing){
        this.url = rootUrl;
        this.rootUrl = rootUrl;
        this.site = site;
        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
        this.utilParsing = utilParsing;
        allLinks.clear();
    }

    public SiteIndexingAction(String url, Site site, String rootUrl, PageRepository pageRepository, SiteRepository siteRepository, UtilParsing utilParsing) {
        this.url = url;
        this.site = site;
        this.rootUrl = rootUrl;
        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
        this.utilParsing = utilParsing;
    }

    @Override
    protected Set<String> compute() {
        Set<String> links = getLinks(url);
        ArrayList<SiteIndexingAction> taskList = new ArrayList<>();
        for (String link : links) {
            SiteIndexingAction task = new SiteIndexingAction(link, site, rootUrl, pageRepository, siteRepository, utilParsing);
            task.fork();
            taskList.add(task);
        }
        for (SiteIndexingAction task : taskList){
            links.addAll(task.join());
        }
        return links;
    }

    public Set<String> getLinks(String url){
        Set<String> resultList = new HashSet<>();
        Document document = null;
        Connection.Response response = null;
        Connection connection = getConnection(url);
        try {
            sleep(150);
            try {
                response = connection.execute();
            } catch (Exception exception){
                exception.printStackTrace();
            }
            if (response != null) {
                document = response.parse();
                Elements elements = document.select("a[href]");
                for (Element element : elements) {
                    String absUrl = element.absUrl("href");
                    if (isCorrectUrl(absUrl)) {
                        System.out.println("-- " + Thread.currentThread().getName() + " got correct abs:href " + absUrl);
                        if (stopped()) {
                            System.out.println("STOPPED");
                            break;
                        }
                        Page page = savePage(site, absUrl.replaceFirst(rootUrl, "/"), response.statusCode(), document.outerHtml());
                        if(page != null){
                            utilParsing.transformationLemmasAndIndex(page);
                        }
                        site.setStatusTime(LocalDateTime.now());
                        siteRepository.save(site);
                        System.out.println("---- add to set" + site.getUrl() + " " + site.getName() + " " + site.getStatus());
                        allLinks.add(absUrl);
                        resultList.add(absUrl);
                        if (stopped()) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public boolean isCorrectUrl(String url){
        return !url.isEmpty()
                && !linkIsFile(url)
                && url.startsWith(rootUrl)
                && !allLinks.contains(url)
                && !url.contains("#")
                && !url.contains("?method=")
                && !url.contains("?");
    }

    public synchronized Page savePage(Site site, String path, int statusCode, String content){
        Page page = null;
        if(!pageRepository.findByPathAndSiteId(path.replaceFirst(rootUrl, "/"), site.getId()).isPresent()) {
            page = new Page(site, path, statusCode, content);
            pageRepository.save(page);
        }
        return page;
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

    public boolean linkIsFile(@NotNull String link) {
        return link.matches(URL_IS_FILE);
    }

    public boolean stopped(){
        return utilParsing.isStopped();
    }
}
