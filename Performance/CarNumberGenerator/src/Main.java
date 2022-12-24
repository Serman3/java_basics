import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int сoreCount = ((Runtime.getRuntime().availableProcessors()));
    private static final int regionCount = 20;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(сoreCount);

        for (int i = 1; i <= regionCount; i++){

            executorService.submit(new Loader(i));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println((System.currentTimeMillis() - startTime) + " ms");
    }
}