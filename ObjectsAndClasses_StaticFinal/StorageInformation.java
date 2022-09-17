public class StorageInformation {
    private final String type;
    private final int volumeMemory;
    private final int weight;

    public StorageInformation(String type, int volumeMemory, int weight) {
        this.type = type;
        this.volumeMemory = volumeMemory;
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public int getVolumeMemory() {
        return volumeMemory;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "StorageInformation{" +
                "type='" + type + '\'' +
                ", volumeMemory='" + volumeMemory + '\'' +
                ", weight=" + weight +
                '}';
    }
}
