import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class XMLHandler extends DefaultHandler {

    private Voter voter;
    private final Map<Voter, Byte> voterCounts;
    private static StringBuilder insertQuery;
    private static final int limit = 150000;
    //private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");

    public XMLHandler(){
        voterCounts = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (voterCounts.size() >= limit) {
                try {
                    writeToDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                voterCounts.clear();
            }
            if (qName.equals("voter") && voter == null) {
                //Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), attributes.getValue("birthDay"));
            }
            else if(qName.equals("visit") && voter != null){
                byte count = voterCounts.getOrDefault(voter,(byte)0);
                count++;
                voterCounts.put(voter, count);
            }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("voter")){
            voter = null;
        }
    }

    public void printDuplicatedVoters(){
        for(Voter voter : voterCounts.keySet()){
            byte count = voterCounts.get(voter);
            if(count > 1){
                System.out.println(voter.toString() + " - " + count);
            }
        }
    }

    public void writeToDataBase() throws SQLException {
            insertQuery = new StringBuilder();
            for (Voter voter : voterCounts.keySet()) {
                String name = voter.getName();
                String birthDate = voter.getBirthDay();
                byte count = voterCounts.get(voter);
                insertQuery.append(insertQuery.length() == 0 ? "" : ",")
                        .append("('").append(name).append("','").append(birthDate)
                        .append("',").append(count).append(")");
            }
            DBConnection.executeMultiInsert(insertQuery);
    }

}
