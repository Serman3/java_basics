public class Processor {
    private final int frequenci;
    private final int numberOfCores;
    private final String vendor;
    private final int weight;

    public Processor(int fercuenci, int numberOfCores, String vendor, int weight){
        this.frequenci = fercuenci;
        this.numberOfCores = numberOfCores;
        this.vendor = vendor;
        this.weight = weight;
    }

    public int getFrequenci() {
        return frequenci;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    public String getVendor() {
        return vendor;
    }

    public int getWeight() {
        return weight;
    }

    public String toString(){
        return "Processor{" +
                "frequenci='" + frequenci + '\'' +
                ", numberOfCores='" + numberOfCores + '\'' +
                ", vendor=" + vendor +
                ", weight=" + weight +
                '}';

    }
}

