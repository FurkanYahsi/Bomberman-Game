package main;

import entity.Player;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2D;
    Font arial_40I, arial_80B;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0");
    public int commandNum = 0;
    public int titleScreenState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40I = new Font("Arial", Font.ITALIC, 30);
    }

    public void draw(Graphics2D g2D) {

        this.g2D = g2D;

        g2D.setFont(arial_40I);
        g2D.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayScreen();
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        //FINISH STATE
        if (gp.gameState == gp.finishState) {
           drawFinishScreen();
        }

    }


    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            g2D.setColor(new Color(100, 20, 50));
            g2D.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2D.setFont(g2D.getFont().deriveFont(Font.ROMAN_BASELINE, 96F));
            String text = "Bomberman";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //SHADOW
            g2D.setColor(Color.black);
            g2D.drawString(text, x+5, y+5);

            //TEXT COLOR
            g2D.setColor(Color.white);
            g2D.drawString(text, x, y);

            //CHARACTER IMAGE
            x = gp.screenWidth/2 - gp.tileSize;
            y += gp.tileSize * 2;
            g2D.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //MENU
            g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2D.drawString(text, x, y);
            if (commandNum == 0) {
                g2D.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 1) {
                g2D.drawString(">", x-gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {

            //BUFF SELECTION SCREEN
            g2D.setColor(Color.white);
            g2D.setFont(g2D.getFont().deriveFont(42F));

            String text = "Select your speciality!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;;
            g2D.drawString(text, x, y);

            text = "SpeedUp";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2D.drawString(text, x, y);
            if (commandNum == 0) {
                g2D.drawString(">", x - gp.tileSize, y);
            }

            text = "Extra Bomb";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 1) {
                g2D.drawString(">", x - gp.tileSize, y);
            }

            text = "Increase Radius";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 2) {
                g2D.drawString(">", x - gp.tileSize, y);
            }

            text = "Remote Control";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 3) {
                g2D.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2D.drawString(text, x, y);
            if (commandNum == 4) {
                g2D.drawString(">", x - gp.tileSize, y);
            }
        }
    }
    public void drawPlayScreen() {

        //ALIVE MONSTERS
        int aliveMonsters = 0;
        for(int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null) {
                aliveMonsters++;
            }
        }
        if (aliveMonsters == 0) {
            gp.player.defeatedMonsters = 6;
            //gp.obj[40].playerCollision = false;
        }
        g2D.setFont(arial_40I);
        g2D.setColor(Color.WHITE);
        g2D.drawString("Alive monsters: " + aliveMonsters, 30, 32);

        // TIME
        playTime += (double) 1/60;
        g2D.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 13 + 10, 32);
        if (playTime > 200) {
            gp.gameState = gp.gameOverState;
        }
    }
    public void drawPauseScreen() {

        //ALIVE MONSTERS
        g2D.setFont(arial_40I);
        g2D.setColor(Color.WHITE);
        g2D.drawString("Alive monsters: ", 30, 32);

        //TIME
        g2D.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 13 + 10, 32);

        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2D.drawString(text, x, y);
    }
    public void drawGameOverScreen() {
        g2D.setFont(arial_40I);
        g2D.setColor(Color.white);

        String text;
        int textLength;
        int x;
        int y;

        text = "GAME OVER!";
        textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2D.drawString(text, x, y);

        gp.gameThread = null;
    }
    public void drawFinishScreen() {

        g2D.setColor(Color.black);
        g2D.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2D.setFont(arial_40I);
        g2D.setColor(Color.white);

        String text;
        int textLength;
        int x;
        int y;

        text = "You are a survivor!";
        textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2D.drawString(text, x, y);



        text = "Your Time is: " + decimalFormat.format(playTime) + "!";
        textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + (gp.tileSize * 4);
        g2D.drawString(text, x, y);



        /*
        g2D.setFont(arial_80B);
        g2D.setColor(Color.yellow);
        text = "Congratulations!";
        textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2D.drawString(text, x, y);


         */
        gp.gameThread = null;
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
