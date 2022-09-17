public class Screen {
    private final int diagonal;
    private final String type;
    private final int weight;

    public Screen(int diagonal, String type, int weight) {
        this.diagonal = diagonal;
        this.type = type;
        this.weight = weight;
    }

    public int getDiagonal() {
        return diagonal;
    }

    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "Screen{" +
                "diagonal='" + diagonal + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                '}';
    }
}
