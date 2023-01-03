import java.util.Objects;

public class PathAndSize implements Comparable<PathAndSize>{

    private String path;
    private double size;
    private String stringSize;

    public PathAndSize(String path, double size, String stringSize) {
        this.path = path;
        this.size = size;
        this.stringSize = stringSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getStringSize() {
        return stringSize;
    }

    public void setStringSize(String stringSize) {
        this.stringSize = stringSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathAndSize that = (PathAndSize) o;
        return Double.compare(that.size, size) == 0 && Objects.equals(path, that.path) && Objects.equals(stringSize, that.stringSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, size, stringSize);
    }

    @Override
    public String toString() {
        return "PathAndSize{" +
                "path='" + path + '\'' +
                ", size=" + size +
                ", stringSize='" + stringSize + '\'' +
                '}';
    }

    @Override
    public int compareTo(PathAndSize o) {
        if(this.getSize() == o.getSize()){
            return 0;
        }else if(this.getSize() < o.getSize()){
            return 1;
        }else{
            return -1;
        }
    }
}
