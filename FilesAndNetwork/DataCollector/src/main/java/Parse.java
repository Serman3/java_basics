import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse {

    Map<String, Station> listStations = new HashMap<>();

    private String FILE_PATH = "";


    public Map<String, Station> readFile(String path) throws ParseException, FileNotFoundException, org.json.simple.parser.ParseException {
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
            if(doc.getName().startsWith("depth")){
                String depth = String.valueOf(jsonObject.get("depth"));
                listStations.get(nameStation).setDepth(depth);
            }
            if  (doc.getName().startsWith("depths-3")){
                String depth2 = String.valueOf(jsonObject.get("depth_meters"));
                listStations.get(nameStation2).setDepth(depth2);
            }
        }
        for(Map.Entry<String, Station> entry : listStations.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        System.out.println(listStations);
    }

    public void parseDatesFromCsv(File doc){
        String filePath = doc.getAbsolutePath();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            for(;;){
                String line  = bufferedReader.readLine();
                if(line == null){
                    break;
                }
                String [] result = line.split(",");
                for(int i = 0; i < result.length; i++){
                    if (i % 2 == 0) {
                        String stationName = result[i];
                        if (!listStations.containsKey(stationName)) {
                            listStations.put(stationName, new Station(stationName));
                        }
                        if (doc.getName().startsWith("dates")) {
                            listStations.get(stationName).setDate(result[++i]);
                        }
                        if (doc.getName().startsWith("depth")) {
                            listStations.get(stationName).setDepth(result[++i]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(listStations);
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
