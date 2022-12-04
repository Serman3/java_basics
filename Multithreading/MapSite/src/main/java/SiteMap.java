import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveAction;

public class SiteMap extends RecursiveAction {

    private URL url;
    private static CopyOnWriteArraySet<String> links = new CopyOnWriteArraySet<>();

    public SiteMap(URL url){
        this.url = url;
    }

    @Override
    protected void compute() {
            getChildren();
    }

/*    @Override
    protected void compute() {
        try {
            Thread.sleep(200);
            Document document = Jsoup.connect(url.getUrl()).get();
            Elements line = document.select("body").select("a");
            for (Element element : line) {
                String str = element.absUrl("href");
                if (isCorrectUrl(str)) {
                    url.addChildUrl(new URL(str));
                    links.add(str);
                }
            }
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
        for (URL child : url.getChildUrl()) {
            SiteMap task = new SiteMap(child);
            task.compute();
        }
    }*/

    private void getChildren() {
        try {
            Thread.sleep(200);
            Document document = Jsoup.connect(url.getUrl()).get();
            Elements line = document.select("body").select("a");
            for (Element element : line) {
                String str = element.absUrl("href");
                if (isCorrectUrl(str)) {
                    url.addChildUrl(new URL(str));
                    links.add(str);
                }
            }
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
        for (URL child : url.getChildUrl()) {
            SiteMap task = new SiteMap(child);
            task.fork();
            task.getChildren();
            task.join();
        }
    }

    public boolean isCorrectUrl(String str){
        if(!str.isEmpty() && str.startsWith(url.getUrl()) && !links.contains(str) /*&& !url.getChildUrl().contains(str)*/ && !str
                .contains("#")){
            return true;
        }
        return false;
    }
}
