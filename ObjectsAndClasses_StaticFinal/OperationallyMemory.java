public class OperationallyMemory {
    private final String type;
    private final int volume;
    private final int weight;

    public OperationallyMemory(String type, int volume, int weight) {
        this.type = type;
        this.volume = volume;
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "OperationallyMemory{" +
                "type='" + type + '\'' +
                ", volume='" + volume + '\'' +
                ", weight=" + weight +
                '}';
    }
}
