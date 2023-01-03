import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {

    //private static final String path = " C:\\Users\\User\\Desktop 2 ";
    private static String path;
    private static long sizeFileInByte;
    private static final int mbToByte = 1048576;

    public static void main(String[] args) {

        System.out.println("Введите путь директории и через пробел размер файла в мегабайтах");
        Scanner scanner = new Scanner(System.in);
        String inputPath = scanner.nextLine();
        String[] arg = inputPath.split(" ");
        path = arg[0];
        sizeFileInByte = Long.parseLong(arg[1]) * mbToByte;
        if (arg.length < 2){
            System.out.println("Не полный формат ввода");
        }else{
            long start = System.currentTimeMillis();
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            Task task = new Task(path, sizeFileInByte);
            forkJoinPool.invoke(task);
            //task.processFilesFromFolder(new File(path), sizeFileInByte);
            Task.print();
            System.out.println("Parsing duration " + (System.currentTimeMillis() - start) + " ms");
        }
    }
}
