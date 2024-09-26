package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import controls.keyboard;
import graphics.screen;

public class game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    private static final int width = 800;
    private static final int height = 600;
    private static JFrame window;

    private static volatile boolean onPlaying = false;

    private static final String name = "Game";
    private static Thread thread;

    private static keyboard keyboard;

    private static int ups = 0;
    private static int fps = 0;

    private static int x = 0;
    private static int y = 0;
    private static screen screen;

    private static BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private static final ImageIcon icon = new ImageIcon(game.class.getResource("/resources/icon.png"));

    private game() {
        setPreferredSize(new Dimension(width, height));

        screen = new screen(width, height);

        keyboard = new keyboard();
        addKeyListener(keyboard);

        window = new JFrame(name);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.setIconImage(icon.getImage());
        window.add(this, BorderLayout.CENTER);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        game game = new game();
        game.start();
    }

    private synchronized void start() {
        onPlaying = true;

        thread = new Thread(this, "Graphics");
        thread.start();
    }

    private synchronized void stop() {
        onPlaying = false;

        try{
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        keyboard.update();

        if (keyboard.up) {
            y += 3;
        }
        if (keyboard.down) {
            y -= 3;
        }
        if (keyboard.left) {
            x += 3;
        }
        if (keyboard.right) {
            x -= 3;
        }

        ups++;
    }

    private void showGame() {
        BufferStrategy strategy = getBufferStrategy();
        if (strategy == null) {
            createBufferStrategy(2);
            return;
        }

        screen.clean();
        screen.showGameScreen(x, y);

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics g = strategy.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();

        strategy.show();

        fps++;
    }

    public void run() {
        final int ns_per_seconds = 1000000000;
        final byte ups_objective = 60;
        final double ns_per_update = ns_per_seconds / ups_objective;

        long updateReference = System.nanoTime();
        long counterReference = System.nanoTime();

        double passedTime;
        double delta = 0;

        requestFocus();

        while(onPlaying) {
            final long bucleInit = System.nanoTime();

            passedTime = bucleInit - updateReference;
            updateReference = bucleInit;

            delta += passedTime / ns_per_update;

            while (delta >= 1) {
                update();
                delta--;
            }

            showGame();

            if (System.nanoTime() - counterReference > ns_per_seconds) {
                window.setTitle(name + " || UPS: " + ups + " || FPS: " + fps);
                ups = 0;
                fps = 0;
                counterReference = System.nanoTime();
            }
        }

    }
}