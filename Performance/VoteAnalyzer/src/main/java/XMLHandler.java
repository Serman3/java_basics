import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class XMLHandler extends DefaultHandler {

    private Voter voter;
    private final Map<Voter, Byte> voterCounts;
    private static StringBuilder insertQuery = new StringBuilder();
    private static final int limit = 7000000;
    //private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");

    public XMLHandler(){
        voterCounts = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            if (qName.equals("voter") && voter == null) {
                //Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), attributes.getValue("birthDay"));
            } else if (qName.equals("visit") && voter != null) {
                byte count = voterCounts.getOrDefault(voter, (byte) 0);
                count++;
                //voterCounts.put(voter, count);
                insertQuery.append(insertQuery.length() == 0 ? "" : ",")
                        .append("('").append(voter.getName()).append("','").append(voter.getBirthDay())
                        .append("',").append(count).append(")");
                if(insertQuery.length() >= limit) {
                    try {
                        DBConnection.executeMultiInsert(insertQuery);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    insertQuery.setLength(0);
                }
            }
            System.out.println(insertQuery.length());
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

    public void writeToDatabase(){
        try {
            for (Voter voter : voterCounts.keySet()) {
                String name = voter.getName();
                String birthDate = voter.getBirthDay();
                byte count = voterCounts.get(voter);
                insertQuery.append(insertQuery.length() == 0 ? "" : ",")
                        .append("('").append(name).append("','").append(birthDate)
                        .append("',").append(count).append(")");
            }
            DBConnection.executeMultiInsert(insertQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
