package nl.gremmee.evolve;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import nl.gremmee.evolve.objects.Meeple;

public class Evolve extends Canvas implements Runnable {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = WIDTH / 12 * 9;

    private boolean running = false;
    private Handler handler;
    private Thread thread;

    public Evolve() {
        handler = new Handler();
        // this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH, HEIGHT, "Evolve", this);

        // handler.addObject(new Player(100, 100, ID.Player));
        // handler.addObject(new BasicEnemy(200, 200, ID.Enemy));

        Random random = new Random();

        int tileHeight = (Evolve.HEIGHT - 20) / 7;

        int tileWidth = (int) Math.round(tileHeight * 0.6);
        System.out.println(tileWidth);
        System.out.println(tileHeight);
        // top tiles

        for (int i = 0; i < 30; i++) {
            handler.addObject(new Meeple(random.nextInt(Evolve.WIDTH) - 8, random.nextInt(Evolve.HEIGHT) - 8, ID.Tile));
        }
    }

    public static void main(String[] args) {
        new Evolve();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " meeples " + handler.object.size());
                frames = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    private void tick() {
        handler.removeDead();
        handler.tick();
    }
}
