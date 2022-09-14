public class Dimensions {
    private final int  width;
    private final int  height;
    private final int  length;


    public Dimensions(int width, int height, int length){
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Dimensions setWidth(int width){
        return new Dimensions(width, height, length);
    }

    public Dimensions setHeight(int height){
        return new Dimensions(width, height, length);
    }

    public Dimensions setLength(int length){
        return new Dimensions(width, height, length);
    }

    public void volumeOfCargo(){
        int a = width * height * length;
        System.out.println("Объем равен " + a);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                '}';
    }
}
