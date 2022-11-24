import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.RecursiveAction;

public class SiteMap extends RecursiveAction {


    private String url;
    private static Set<String> linksSet = Collections.synchronizedSet(new TreeSet<>());

    public SiteMap(){}

    public SiteMap(String url) {
        this.url = url.trim();
    }

    public static Set<String> getLinksSet() {
        return linksSet;
    }

    public boolean regexAndContains(String str){
        if(!str.isEmpty() && str.startsWith(url) && !linksSet.contains(str) && !str
                .contains("#")){
            return true;
        }
        return false;
    }

    public void getLinks(Set<SiteMap> subTask) {
        try {
            Thread.sleep(200);
            Document document = Jsoup.connect(url).get();
            Elements line = document.select("a");
            for (Element element : line) {
                String str = element.absUrl("href");
                if (regexAndContains(str)) {
                    SiteMap siteMap = new SiteMap(str);
                    siteMap.fork();
                    subTask.add(siteMap);
                    linksSet.add(str);
                    linksSet.forEach(System.out::println);
                }
            }
        } catch (InterruptedException | IOException ignored){
        }
    }


    @Override
    protected void compute() {
        StringBuffer sb = new StringBuffer(url + "\n");
        Set<SiteMap> task = new HashSet<>();

        getLinks(task);

        for (SiteMap link : task) {
            sb.append(link.join());
        }

    }
}
