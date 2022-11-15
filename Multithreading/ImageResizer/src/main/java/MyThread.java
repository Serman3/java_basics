import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;

public class MyThread extends Thread{

    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;

    public MyThread(File[] files, int newWidth, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));
                BufferedImage newImage = MyThread.resize(image, newWidth, newHeight);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
                System.out.println("finish " + Thread.currentThread().getName() + " - " + (System.currentTimeMillis() - start) + " ms" + "\n");
                            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static BufferedImage resize(BufferedImage src, int targetWidth, int targetHeight, BufferedImageOp ... ops){
        return Scalr.resize(src ,Scalr.Method.AUTOMATIC,
                Scalr.Mode.FIT_EXACT,
                targetWidth,
                targetHeight,
                Scalr.OP_ANTIALIAS);
    }
}
