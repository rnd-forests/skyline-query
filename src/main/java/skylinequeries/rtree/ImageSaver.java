package skylinequeries.rtree;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

final class ImageSaver {

    static void save(BufferedImage image, File file, String imageFormat) {
        try {
            ImageIO.write(image, imageFormat, file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
