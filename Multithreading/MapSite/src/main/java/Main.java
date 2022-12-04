import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final String siteLink = "https://skillbox.ru/";
    //private static final String siteLink = "https://nikoartgallery.com/";

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        URL url = new URL(siteLink);
        forkJoinPool.invoke(new SiteMap(url));
        System.out.println("FINISH");

       /* Set<URL> links = url.getChildUrl();
          try {
            writeFile(links);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        try {
            FileOutputStream stream = new FileOutputStream("src/main/resources/siteMap.txt");
            String result = createSitemapString(url, 0);
            stream.write(result.getBytes());
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createSitemapString(URL url, int level) {
        String tabs = String.join("", Collections.nCopies(level, "\t"));
        StringBuilder result = new StringBuilder(tabs + url.getUrl());
        url.getChildUrl().forEach(child -> {
            result.append("\n").append(createSitemapString(child, level + 1));
        });
        return result.toString();
    }

   /* public static void writeFile(Set<URL> links) throws FileNotFoundException {
        String filePath = "src/main/resources/siteMap.txt";
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        String result = "";
        for(URL url : links){
           Set<URL> link = url.getChildUrl();
            String parent = url.getUrl() + "\n";
           for(URL u : link){
               result += "\t " + u.getUrl() + "\n";
               writer.write(parent + result);
           }
        }
    }*/
}
