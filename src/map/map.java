package map;

import graphics.screen;

public abstract class map {
    private int width;
    private int height;

    private int[] tiles;

    public map(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new int[width * height];
        generateMap();
    }

    public map(String path) {
        loadMap(path);
    }

    private void generateMap() {

    }

    private void loadMap(String path) {

    }

    public void update() {

    }

    public void show(int compensationX, int compensationY, screen screen) {

    }
}
