package searchengine.dto.statistics;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveAction;

public class SiteIndexingAction extends RecursiveAction {

    private URL url;
    private Site site;
    private PageRepository pageRepository;

    private static CopyOnWriteArraySet<String> links = new CopyOnWriteArraySet<>();

    public SiteIndexingAction(URL url, Site site, PageRepository pageRepository) {
        this.url = url;
        this.site = site;
        this.pageRepository = pageRepository;
    }

    @Override
    protected void compute() {
        try {
            Connection connection = Jsoup.connect(url.getUrl())
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .timeout(30000);
            Connection.Response response = connection.execute();
            Document document = null;
            if (response.contentType().startsWith("text/html" )) {
                Thread.sleep(200);
                document = connection.get();
                Elements line = document.select("body").select("a");
                String html = document.outerHtml();
                int code = document.connection().response().statusCode();
                for (Element element : line) {
                    String path = element.attr("abs:href");
                    if (isCorrectUrl(path) && pageRepository.countAllByPath(path) < 1) {
                        System.out.println(path);
                        url.addChildUrl(new URL(path));
                        Page page = new Page(site, path, code, html);
                        pageRepository.save(page);
                        links.add(path);
                    }
                }
                for (URL childUrl : url.getChildUrl()) {
                    SiteIndexingAction task = new SiteIndexingAction(childUrl, site, pageRepository);
                    task.fork();
                    task.join();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isCorrectUrl(String str){
        if(!str.isEmpty() && str.startsWith(url.getUrl()) && !links.contains(str) && !str
                .contains("#")){
            return true;
        }
        return false;
    }
}
