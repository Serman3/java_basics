public class Keyboard {
    private final String type;
    private final boolean sunswer;
    private final int weight;

    public Keyboard(String type, boolean sunswer, int weight) {
        this.type = type;
        this.sunswer = sunswer;
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public boolean isSunswer() {
        return sunswer;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "Keyboard{" +
                "type='" + type + '\'' +
                ", sunswer='" + sunswer + '\'' +
                ", weight=" + weight +
                '}';
    }
}
