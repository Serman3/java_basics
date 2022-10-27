public class Station {

    private String numberLine;
    private String name;
    private String lineName;
    private String date;
    private double depth;
    private boolean hasConnection;


    public Station(String name) {
        this.name = name;
    }

    public Station(String name, String lineName, String date, double depth, boolean hasConnection) {
        this.name = name;
        this.lineName = lineName;
        this.date = date;
        this.depth = depth;
        this.hasConnection = hasConnection;
    }

    public String getLineName() {
        return lineName;
    }

    public String getDate() {
        return date;
    }

    public double getDepth() {
        return depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public String getName() {
        return name;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString() {
        return "Station{" +
                "numberLine='" + numberLine + '\'' +
                ", name='" + name + '\'' +
                ", lineName='" + lineName + '\'' +
                ", date='" + date + '\'' +
                ", depth='" + depth + '\'' +
                ", hasConnection=" + hasConnection +
                '}';
    }
}

