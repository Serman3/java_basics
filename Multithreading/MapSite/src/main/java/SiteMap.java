import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class SiteMap extends RecursiveAction {

    private URL url;

    public SiteMap(URL url){
        this.url = url;
    }

    @Override
    protected void compute() {
        try {
            Thread.sleep(200);
            Document document = Jsoup.connect(url.getUrl()).get();
            Elements line = document.select("body").select("a");
            for (Element element : line) {
                String str = element.absUrl("href");
                if (isCorrectUrl(str)) {
                    url.addChildUrl(new URL(str));
                }
            }
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
        for (URL child : url.getChildUrl()) {
            SiteMap task = new SiteMap(child);
            task.compute();
        }
    }

    public boolean isCorrectUrl(String str){
        if(!str.isEmpty() && str.startsWith(url.getUrl()) && !url.getChildUrl().contains(str) && !str
                .contains("#")){
            return true;
        }
        return false;
    }
}
