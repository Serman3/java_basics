package searchengine.dto.statistics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveAction;

public class SiteIndexingAction extends RecursiveAction {

    private URL url;
    private static CopyOnWriteArraySet<String> links = new CopyOnWriteArraySet<>();

    public SiteIndexingAction(URL url){
        this.url = url;
    }

    @Override
    protected void compute() {
        getChildren();
    }

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
            SiteIndexingAction task = new SiteIndexingAction(child);
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
