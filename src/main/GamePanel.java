package main;

import entity.Entity;
import entity.NormalMonster;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16, maxScreenRow = 13;

    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 33, maxWorldRow = 13;

    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    //FPS
    public final int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    protected AssetSetter assetSetter = new AssetSetter(this, keyH);
    public UI ui = new UI(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[100];
    public Entity npc[] = new Entity[30];

    //GAME STATE
    public int gameState, titleState = 0, playState = 1, pauseState = 2, finishState = 3, gameOverState = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {
        assetSetter.setObject();
        //assetSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                double remainingTimeMillis = remainingTime / 1000000;

                if (remainingTimeMillis < 0)
                    remainingTimeMillis = 0;

                Thread.sleep((long) remainingTimeMillis); // nanosecond/10^-6 = millis
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void update() {

        if (gameState == playState) {
            //PLAYER
            player.update();
            assetSetter.update();



            //NPC
            for (int i = 0; i < npc.length; i++) {

                if (npc[i] != null) {
                    npc[i].update();
                }
            }

        }
        if (gameState == pauseState) {
            //nothing
        }
        if (gameState == finishState) {

        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2D);
        }else {
            // TILE
            tileM.draw(g2D);

            // OBJECT
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].draw(g2D, this);
                }
            }

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2D);
                }
            }

            // PLAYER
            player.draw(g2D);

            // UI
            ui.draw(g2D);

        }


        g2D.dispose();
    }
}
