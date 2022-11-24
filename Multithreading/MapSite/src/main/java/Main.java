import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool= new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new SiteMap(URL));
        System.out.println("FINISH");

        Set<String> links = SiteMap.getLinksSet();
        try {
            writeFile(links);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void writeFile(Set<String> linksSet) throws FileNotFoundException {
        String filePath = "src/main/resources/siteMap.txt";
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        String result = "";
        for(String url : linksSet) {

            int indPat;
            int curInd = 0;
            int counter = 0;
            while ((indPat = url.indexOf("/", curInd)) != -1) {
                counter++;
                curInd = indPat + 1;
           // }
            if (counter == 3) {
                result = url + "\n";
            } else if (counter == 4) {
                result = "\t" + url + "\n";
            } else if (counter == 5) {
                result = "\t" + "\t" + url + "\n";
            } else if (counter == 6) {
                result = "\t" + "\t" + "\t" + url + "\n";
            } else if (counter ==7){
                result = "\t" + "\t" + "\t" + "\t" + url + "\n" + "\n";
            }
        }
            writer.write(result);
        }
    }
}
