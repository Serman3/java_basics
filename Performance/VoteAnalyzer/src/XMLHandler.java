import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class XMLHandler extends DefaultHandler {

    private Voter voter;
    //private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final Map<Voter, Byte> voterCounts;

    public XMLHandler(){
        voterCounts = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            if (qName.equals("voter") && voter == null) {
                //Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), attributes.getValue("birthDay"));
            }
            else if(qName.equals("visit") && voter != null){
                byte count = voterCounts.getOrDefault(voter,(byte)0);
                count++;
                voterCounts.put(voter, count);
            }
            //System.out.println(qName + " - " + "started");

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("voter")){
            voter = null;
        }
        //System.out.println(qName + " - " + "ended");
    }

    public void printDuplicatedVoters(){
        for(Voter voter : voterCounts.keySet()){
            byte count = voterCounts.get(voter);
            if(count > 1){
                System.out.println(voter.toString() + " - " + count);
            }
        }
    }
}
