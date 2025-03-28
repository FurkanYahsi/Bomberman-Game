package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean rightPressed, leftPressed, upPressed, downPressed, bombDropped;
    public int speciality;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {

            if (gp.ui.titleScreenState == 0) {
                if(code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 1;
                    }
                }
                if(code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 1) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    }
                    if (gp.ui.commandNum == 1) {
                        System.exit(0);
                    }
                }
            }
            else if (gp.ui.titleScreenState == 1) {
                if(code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 4;
                    }
                }
                if(code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 4) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        speciality = 0;
                        System.out.println("Speed up!!");
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.commandNum == 1) {
                        System.out.println("Extra bomb!!");
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.commandNum == 2) {
                        System.out.println("Increase radius!!");
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.commandNum == 3) {
                        System.out.println("Remote control!!");
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.commandNum == 4) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                    }
                }
            }

        }

        if(code == KeyEvent.VK_RIGHT)
            rightPressed = true;

        if(code == KeyEvent.VK_LEFT)
            leftPressed = true;

        if(code == KeyEvent.VK_UP)
            upPressed = true;

        if(code == KeyEvent.VK_DOWN)
            downPressed = true;

        if(code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        if (code == KeyEvent.VK_Z) {
            bombDropped = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_RIGHT)
            rightPressed = false;

        if(code == KeyEvent.VK_LEFT)
            leftPressed = false;

        if(code == KeyEvent.VK_UP)
            upPressed = false;

        if(code == KeyEvent.VK_DOWN)
            downPressed = false;

        if(code == KeyEvent.VK_Z)
            bombDropped = false;
    }
}
