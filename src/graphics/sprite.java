package graphics;

public final class sprite {
    private final int side;

    private int x;
    private int y;

    public int[] pixels;
    private final spriteSheet sheet;

    // SPRITES
    public static sprite prueba2 = new sprite(64, 0, 0, spriteSheet.prueba);

    // SPRITES END

    public sprite(final int side, final int column, final int row, final spriteSheet sheet) {
        this.side = side;
        pixels = new int[side * side];
        this.x = column * side;
        this.y = row * side;
        this.sheet = sheet;

        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                pixels[x + y * side] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getWidth()];
            }
        }
    }
}