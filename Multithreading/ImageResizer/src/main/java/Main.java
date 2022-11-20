import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static int newWidth = 300;

    public static void main(String[] args) {

        String srcFolder = "D:\\ФОТО СВАДЬБА\\ФОТО СВАДЬБА\\Дубли";
        String dstFolder = "C:\\Users\\User\\Desktop\\dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

           /* int size = files.length / Runtime.getRuntime().availableProcessors();

            File[] files1 = new File[size];
            System.arraycopy(files, 0, files1, 0, files1.length);
            MyThread thread1 = new MyThread(files1, newWidth, dstFolder, start);
            thread1.start();

            File[] files2 = new File[(files.length / 2) - files1.length];
            System.arraycopy(files, size, files2, 0, files2.length);
            MyThread thread2 = new MyThread(files2, newWidth, dstFolder, start);
            thread2.start();

            File[] files3 = new File[(files.length / 2) - files2.length];
            System.arraycopy(files, files2.length, files3, 0, files3.length);
            MyThread thread3 = new MyThread(files3, newWidth, dstFolder, start);
            thread3.start();


            File[] files4 = new File[(files.length / 2) - files3.length];
            System.arraycopy(files, files3.length, files4, 0, files4.length);
            MyThread thread4 = new MyThread(files4, newWidth, dstFolder, start);
            thread4.start();*/

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.submit(new MyThread(files, newWidth, dstFolder, start));
        executorService.shutdown();

            System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }
}
