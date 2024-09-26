package graphics;

public final class screen {
    private final int width;
    private final int height;

    public final int[] pixels;

    // TEMP
    private final static int sprite_side = 64;
    private final static int sprite_mask = sprite_side - 1;
    // TEMP END

    public screen(final int width, final int height) {
        this.width = width;
        this.height = height;

        pixels = new int[width * height];
    }

    public void clean() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void showGameScreen(final int compensationX, final int compensationY) {
        for (int y = 0; y < height; y++) {
            int positionY = y + compensationY;
            if (positionY < 0 || positionY >= height) {
                continue;
            }

            for (int x = 0; x < width; x++) {
                int positionX = x + compensationX;
                if (positionX < 0 || positionX >= width) {
                    continue;
                }

                // TEMP
                pixels[positionX + positionY * width] = sprite.prueba2.pixels[(x & sprite_mask) + (y & sprite_mask) * sprite_side];
            }
        }
    }
}
