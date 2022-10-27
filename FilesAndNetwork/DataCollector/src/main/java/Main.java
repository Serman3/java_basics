import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class Main {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String folderPath = "src/data";

    public static void main(String[] args) {
        Map<String, Station> metroMap = new HashMap<>();
        JsoupParser jsoupParser = new JsoupParser();
        Parse parse = new Parse();

        List<Line> listLines = jsoupParser.linesAndNumber(); // номер линии, имя линии
      //  System.out.println(listLines);
        Map<String, String> mapStationLine = jsoupParser.stationsAndLine(); // имя станции, номер линии
       // System.out.println(mapStationLine);
        Map<String, String> hasConnections = jsoupParser.parseConnection(); // имя станции, имя станции на которую переходим
      //  System.out.println(hasConnections);

        try {
        metroMap = parse.readFile(folderPath); // имя станции, дата, глубина , переход
            metroMap.remove(null);
            for(Map.Entry<String, Station> entry : metroMap.entrySet()){
                for(Map.Entry<String, String> connections : hasConnections.entrySet()) {
                    for (Line line : listLines) {
                        for (Map.Entry<String, String> lineStation : mapStationLine.entrySet()) {
                            if (entry.getValue().getName().equals(lineStation.getKey())) {
                                entry.getValue().setNumberLine(lineStation.getValue());
                            }
                            if (entry.getValue().getNumberLine() != null && entry.getValue().getNumberLine().equals(line.getNumber())) {
                                entry.getValue().setLineName(line.getName());
                            }
                            if (entry.getKey().equals(connections.getKey())){
                                entry.getValue().setHasConnection(true);
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        removeNull(metroMap);
        String str = GSON.toJson(metroMap);
        System.out.println(str);
       /* Map<String, Map<Line, Set<Station>>> metro = metroMSC(metroMap, listLines, mapStationLine);
        String str = GSON.toJson(metro);
        System.out.println(str);*/
    }

    public static void removeNull(Map<String,Station> metroMap){
        Iterator<Map.Entry<String, Station>> iterator = metroMap.entrySet().iterator();
        while (iterator.hasNext()) {
                Map.Entry<String, Station> entry = iterator.next();
                if(entry.getValue().getName() == null || entry.getValue().getNumberLine() == null || entry.getValue().getLineName() == null || entry.getValue().getDate() == null || Double.isNaN(entry.getValue().getDepth()) || Double.isInfinite(entry.getValue().getDepth())){
                  iterator.remove();
                }
        }
    }

    /*public static Map<String, Map<Line, Set<Station>>> metroMSC(Map<String,Station> metroMap, List<Line> listlines, Map<String, String> mapStationLine){
        Set<Station> setStation = new HashSet<>();
        Map<Line, Set<Station>> map = new HashMap<>();
        Map<String, Map<Line, Set<Station>>> metro = new HashMap<>();

        for(Map.Entry<String, Station> entry : metroMap.entrySet()){
            for(Map.Entry<String, String> lineStation : mapStationLine.entrySet()) {
                if(entry.getValue().getNumberLine().equals(lineStation.getValue()) && entry.getValue().getName().equals(lineStation.getKey())){
                    setStation.add(new Station(entry.getValue().getName(), entry.getValue().getLineName(), entry.getValue().getDate(), entry.getValue().getDepth(), entry.getValue().isHasConnection()));
                }
                for (Line line : listlines) {
                    if (entry.getValue().getLineName().equals(line.getName())) {
                        map.put(new Line(entry.getValue().getNumberLine()), setStation);
                        metro.put("Stations", map);
                    }
                }
            }
        }
        return metro;
    }*/
}



