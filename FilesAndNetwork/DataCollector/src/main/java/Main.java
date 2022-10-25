import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    //private static final String URL = "https://skillbox-java.github.io/";
    private static final String folderPath = "src/data";

    public static void main(String[] args) {
        JsoupParser jsoupParser = new JsoupParser();
        Parse parse = new Parse();

        List<Line> listLines = jsoupParser.linesAndNumber(); // номер линии, имя линии
        System.out.println(listLines);
        Map<String, String> mapStationLine = jsoupParser.stationsAndLine(); // имя станции, номер линии
        System.out.println(mapStationLine);
        Map<String, String> hasConnections = jsoupParser.parseConnection(); // имя станции, имя станции на которую переходим
        System.out.println(hasConnections);

        try {
        Map<String, Station> metroMap = parse.readFile(folderPath); // название станции, дата, глубина
            metroMap.remove(null);
            //Map<String, String> newMapStationLine = quanityChek(mapStationLine, metroMap);
            for(Map.Entry<String, Station> entry : metroMap.entrySet()){
                for(Line line : listLines){
                    for(Map.Entry<String, String> lineStation : mapStationLine.entrySet()){
                       if(entry.getValue().getName().equals(lineStation.getKey())){
                           entry.getValue().setNumberLine(lineStation.getValue());
                       }
                       if(entry.getValue().getNumberLine() != null && entry.getValue().getNumberLine().equals(line.getNumber())){
                           entry.getValue().setLineName(line.getName());
                       }
                    }
                }
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

   /* public static List<String> linesAndNumber(){
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
    }*/

    /*public static Map<String, String> quanityChek(Map<String,String> mapStationLine, Map<String,Station> metroMap){

        Iterator<Map.Entry<String, String>> iterator = mapStationLine.entrySet().iterator();
        while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (!metroMap.containsKey(entry.getKey())) {
                    iterator.remove();
                }
                if(mapStationLine.size() == metroMap.size()){
                    break;
                }
        }
        Map<String, String> map = new HashMap<>(mapStationLine);
        *//*for(Map.Entry<String, String> lineStation : mapStationLine.entrySet()){
            for(Map.Entry<String, Station> entry : metroMap.entrySet()){
               if(mapStationLine.containsKey(entry.getValue().getName())){
                 //  String line = mapStationLine.get(entry.getValue().getName());
                   map.put(lineStation.getKey(), lineStation.getValue());
               }
            }
        }*//*
        return map;
    }*/
}

