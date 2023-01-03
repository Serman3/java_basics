import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {

    private String path;
    private long sizeInByte;

    public Task(String path, long sizeInByte) {
        this.path = path;
        this.sizeInByte = sizeInByte;
    }

    private static Set<PathAndSize> set = new TreeSet<>();

    public synchronized void processFilesFromFolder(File folder, long sizeFile){
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                Task task = new Task(file.getPath(), sizeFile);
                task.fork();
                //processFilesFromFolder(file, sizeFile);
                task.join();
               // continue;
            }else{
                if(file.length() > sizeFile){
                    String size = getReadableSize(file.length());
                    String[] arr = size.split(" ");
                    String replace = arr[0].replaceAll(",","\\.");
                    double doubleSize = Double.parseDouble(replace);
                    if(size.contains("GB")){
                        doubleSize = doubleSize * 1024;
                    }                                           // Приводим все к мегабайтам
                    if(size.contains("KB")){
                        doubleSize = doubleSize / 1024;
                    }
                    set.add(new PathAndSize(file.getPath(), doubleSize, size));
                }
            }
        }
    }

    public static String getReadableSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static void print(){
        for(PathAndSize pathAndSize : set){
            System.out.println(pathAndSize.getStringSize() + "  " + pathAndSize.getPath());
        }
    }

    @Override
    protected void compute() {
        processFilesFromFolder(new File(path), sizeInByte);
    }
}
