import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

    class ImageLoader{
        
        public static BufferedImage loadImage(String path){
            try{
                return ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

