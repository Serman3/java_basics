import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsoupParser {

    private static final String URL = "https://skillbox-java.github.io/";
    private List<Line> lines = new ArrayList<>();
    private Map<String, String> stationsAndLines = new HashMap<>();
    private Map<String, String> connections = new HashMap<>();

    public List<Line> linesAndNumber(){
        try {
            Document document = Jsoup.connect(URL).get();
            Elements line = document.select(".js-metro-line");
            for(Element element : line){
                lines.add(new Line(element.attr("data-line"), element.text()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public Map<String, String> stationsAndLine(){
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements elementsLine = doc.getElementsByClass("js-metro-stations t-metrostation-list-table");
            for(Element element : elementsLine){
                Elements stationList = element.getElementsByClass("name");
                for(Element stationElement : stationList){
                    stationsAndLines.put(stationElement.text(), element.attr("data-line"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stationsAndLines;
    }

    public Map<String, String> parseConnection(){
        try {
            Document document = Jsoup.connect(URL).get();
            Elements elementsConnection = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
            for(Element el : elementsConnection){
                Elements stations = el.select("p:has(span[title])");
                for(Element station : stations){
                    String stationText = station.text();
                    int index = stationText.lastIndexOf(".");
                    String stationName = stationText.substring(index + 1).trim();
                    String connectionsText = station.getElementsByClass("t-icon-metroln").attr("title");
                    connections.put(stationName, connectionsText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connections;
    }
}
