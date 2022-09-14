public class InfoWeight {

    private final Dimensions dimension;
    private final int mass;
    private final String address;
    private final boolean property;
    private final String number;
    private final boolean fragileOrNot;

    public InfoWeight(Dimensions dimension, int mass, String address, boolean property, String number, boolean fragileOrNot) {
        this.dimension = dimension;
        this.mass = mass;
        this.address = address;
        this.property = property;
        this.number = number;
        this.fragileOrNot = fragileOrNot;
    }

    public InfoWeight setAddress(String address){
        return new InfoWeight(dimension, mass, address, property, number, fragileOrNot);
    }

    public InfoWeight setDimension(Dimensions dimension){
        return new InfoWeight(dimension, mass, address, property, number, fragileOrNot);
    }

    public InfoWeight setMass(int mass){
        return new InfoWeight(dimension, mass, address, property, number, fragileOrNot);
    }

    public Dimensions getDimension() {
        return dimension;
    }

    public int getMass() {
        return mass;
    }

    public String getAddress() {
        return address;
    }

    public boolean isProperty() {
        return property;
    }

    public String getNumber() {
        return number;
    }

    public boolean isFragileOrNot() {
        return fragileOrNot;
    }

    @Override
    public String toString() {
        return "InfoWeight{" +
                "dimension=" + dimension +
                ", mass=" + mass +
                ", address='" + address + '\'' +
                ", property=" + property +
                ", number='" + number + '\'' +
                ", fragileOrNot=" + fragileOrNot +
                '}';
    }
}
