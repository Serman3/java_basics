import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class Main {
    private static final String URL = "https://skillbox-java.github.io/";
    private static final String folderPath = "src/data";

    static Map<String,Line> lines = new HashMap<>();
    static List<Station> stations = new ArrayList<>();
    static List<List<String>> connections = new ArrayList<>();



    public static void main(String[] args) {

        List<String> line = linesAndNumber();
        System.out.println(line);
        List<String> station = stationsAndLine();
        System.out.println(station);

        Parse parse = new Parse();

        try {
        Map<String, Station> stringStationMapJson = parse.readFile(folderPath);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

    }

    public static List<String> linesAndNumber(){
        List<String> metroLine = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URL).get();
            Elements lines = document.select(".js-metro-line");
            lines.stream().forEach(el -> metroLine.add("\n" + el.attr("data-line") + " " + el.text()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metroLine;
    }

    public static List<String> stationsAndLine(){
        List<String> metroStation = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements line = doc.select(".js-metro-stations");
            line.stream().forEach(el -> metroStation.add("\n" + el.attr("data-line") + " " + el.text()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metroStation;
    }
}

