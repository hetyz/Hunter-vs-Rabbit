package hu.elte.fi.progtech.tron.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public final class ResourceLoader {

    public static BufferedImage readImage(String path) {
        try(final InputStream inputStream = ResourceLoader.class.getResourceAsStream(path)) {
            final BufferedImage image = ImageIO.read(inputStream);
            if(image == null) {
                throw new IOException("No registered ImageReader claims to be able to read the resulting stream." +
                        "(ImageIO.read(...) returns null)");
            }
            return image;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private ResourceLoader() {

    }

}
