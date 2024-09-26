package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class spriteSheet {
    private final int width;
    private final int height;
    public final int[] pixels;

    // SPRITES
    public static spriteSheet prueba = new spriteSheet("/textures/prueba.png", 64, 64);

    // SPRITES END

    public spriteSheet(final String path, final int width, final int height) {
        this.width = width;
        this.height = height;

        pixels = new int[width * height];
        BufferedImage image;

        try {
            image = ImageIO.read(spriteSheet.class.getResource(path));
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }
}