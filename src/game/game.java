package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

import controls.keyboard;

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

    private game() {
        setPreferredSize(new Dimension(width, height));

        keyboard = new keyboard();
        addKeyListener(keyboard);

        window = new JFrame(name);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
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

    private void update() {
        keyboard.update();

        if (keyboard.up) {

        }
        if (keyboard.down) {
            
        }
        if (keyboard.left) {
            
        }
        if (keyboard.right) {
            
        }

        ups++;
    }

    private void showGame() {
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

            delta += passedTime / ns_per_seconds;

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