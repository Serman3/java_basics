import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        writeJson(str, "src/main/resources/stations.json");

        Map<String, Map<String, Set<String>>> metro = metroMSC(metroMap, mapStationLine);

        Map<String, List<Line>> map = new TreeMap<>();
        map.put("lines", listLines);

        List<Map> finishList = new ArrayList<>();
        finishList.add(metro);
        finishList.add(map);

        String str2 = GSON.toJson(finishList);
        writeJson(str2, "src/main/resources/map.json");

        readJson();
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

    public static Map<String, Map<String, Set<String>>> metroMSC(Map<String,Station> metroMap, Map<String, String> mapStationLine){
        Map<String, Set<String>> map = new TreeMap<>();
        Map<String, Map<String, Set<String>>> metro = new TreeMap<>();
        for(Map.Entry<String, Station> entry : metroMap.entrySet()){
            for (Map.Entry<String, String> stationLine : mapStationLine.entrySet()){
                if(entry.getValue().getNumberLine().equals(stationLine.getValue())){
                    Set<String> setStation = new HashSet<>();
                    for (Map.Entry<String, String> statLine : mapStationLine.entrySet()){
                        if(entry.getValue().getNumberLine().equals(statLine.getValue())){
                            setStation.add(statLine.getKey());
                        }
                    }
                    map.put(entry.getValue().getNumberLine(), setStation);
                    metro.put("Stations", map);
                }

            }
        }
        return metro;

    }

    public static void writeJson(String str, String path){
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void readJson() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/map.json"));
            lines.forEach(line -> builder.append(line));
            String s = builder.toString();
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(s);
            for(Object ob : jsonArray) {
                JSONObject jsonObject = (JSONObject) ob;
                JSONObject stations = (JSONObject) jsonObject.get("Stations");
                for (Object key : stations.keySet()) {
                    JSONArray line = (JSONArray) stations.get(key);
                    int size = line.size();
                    System.out.println("Линия " + key.toString() + ", количество станций равно - "  + size);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



