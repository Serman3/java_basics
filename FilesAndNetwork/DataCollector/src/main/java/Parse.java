import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse {

    Map<String, Station> listStations = new HashMap<>();
    private String FILE_PATH = "";

    public Map<String, Station> readFile(String path) throws FileNotFoundException, org.json.simple.parser.ParseException {
        File doc = new File(path);
        if (doc.isFile()) {
            FILE_PATH = doc.getAbsolutePath();
            if (doc.getName().endsWith(".json")) {
                parseDatesFromJson(doc);
            }

            if (doc.getName().endsWith(".csv")) {
                parseDatesFromCsv(doc);
            }
        }
        else {
            File[] files = doc.listFiles();
            for (File file : files) {
                readFile(file.getAbsolutePath());
            }
        }
        return listStations;
    }

    public void parseDatesFromJson(File doc) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(getJsonFile());
        for(Object ob : jsonArray){
            JSONObject jsonObject = (JSONObject) ob;
            String nameStation = (String) jsonObject.get("name");
            String nameStation2 = (String) jsonObject.get("station_name");

            if(!listStations.containsKey(nameStation)){
                listStations.put(nameStation, new Station(nameStation));
            }
            if (!listStations.containsKey(nameStation2)){
                listStations.put(nameStation2, new Station(nameStation2));
            }
            if(doc.getName().startsWith("dates")){
                String dates = (String) jsonObject.get("date");
                listStations.get(nameStation).setDate(dates);
            }
            if(doc.getName().startsWith("depths-1.json")){
                String depth = String.valueOf(jsonObject.get("depth"));
                if(depth.indexOf(",") != -1){
                    depth = depth.replaceAll(",","\\.");
                }
                double dephDouble = Double.parseDouble(depth);
                listStations.get(nameStation).setDepth(dephDouble);
            } if(doc.getName().startsWith("depths-3.json")) {
                String depth2 = String.valueOf(jsonObject.get("depth_meters"));
                if(depth2.indexOf(",") != -1){
                    depth2 = depth2.replaceAll(",","\\.");
                }
                double dephDouble2 = Double.parseDouble(depth2);
                listStations.get(nameStation2).setDepth(dephDouble2);
            }
        }

    }

    public void parseDatesFromCsv(File doc)throws FileNotFoundException{
        String filePath = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String line = "";
            String l = reader.readLine(); // Считываем из файла первую не нужную строку
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(",", 2);
                for (int i = 0; i < lines.length; i++) {
                        String stationName = lines[0];
                        if (!listStations.containsKey(stationName)) {
                            listStations.put(stationName, new Station(stationName));
                        }
                        if (doc.getName().startsWith("dates")) {
                            listStations.get(stationName).setDate(lines[1]);
                        } else if (doc.getName().startsWith("depth")) {
                            String depth = lines[1];
                            if(depth.indexOf(",") != -1){
                                depth = depth.replaceAll(",","\\.");
                            }
                            double depthDouble = Double.parseDouble(depth);
                            listStations.get(stationName).setDepth(depthDouble);
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
