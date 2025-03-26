package entity;

import main.AssetSetter;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    KeyHandler keyH;
    public int screenX, screenY;
    public int defeatedMonsters;

    public Player (GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(8, 16, gp.tileSize - 8 * 2, gp.tileSize - 16);
        //solidArea = new Rectangle(0, 0, 48, 48);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        setDefaultValues();
        getImage();


    }

    public void setDefaultValues() {

        setWorldX(gp.tileSize * 1);
        setWorldY(gp.tileSize * 1);
        setSpeed(4);
        direction = "down";
    }

    @Override
    public void getImage() {

        up1 = setUp("/playerSprites/boy_up_1");
        up2 = setUp("/playerSprites/boy_up_2");
        down1 = setUp("/playerSprites/boy_down_1");
        down2 = setUp("/playerSprites/boy_down_2");
        left1 = setUp("/playerSprites/boy_left_1");
        left2 = setUp("/playerSprites/boy_left_2");
        right1 = setUp("/playerSprites/boy_right_1");
        right2 = setUp("/playerSprites/boy_right_2");

    }


    public void update() {

        if (keyH.rightPressed == true || keyH.leftPressed == true || keyH.upPressed == true || keyH.downPressed == true) {
            if (keyH.rightPressed == true) {
                direction = "right";
                movementPermission();
            }
            if (keyH.leftPressed == true) {
                direction = "left";
                movementPermission();
            }
            if (keyH.upPressed == true) {
                direction = "up";
                movementPermission();
            }
            if (keyH.downPressed == true) {
                direction = "down";
                movementPermission();
            }

            spriteCounter++;
            if (spriteCounter > 10) {

                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;

                spriteCounter = 0;
            }
        }
    }
    public void movementPermission() {
        //CHECK TILE COLLISION
        collisionOn = false;
        gp.collisionChecker.checkTile(this);

        //CHECK OBJECT COLLISION
        int objIndex = gp.collisionChecker.checkObject(this);
        pickUpObject(objIndex);

        //CHECK ENTITY COLLISION
        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        if (!collisionOn) {
            switch (direction) {
                case "up": setWorldY(getWorldY() - getSpeed()); break;
                case "down": setWorldY(getWorldY() + getSpeed()); break;
                case "left": setWorldX(getWorldX() - getSpeed()); break;
                case "right": setWorldX(getWorldX() + getSpeed()); break;
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {

           String objectName = gp.obj[i].name;

           switch (objectName) {
               case "Door":
                   if (defeatedMonsters == 6)
                      gp.gameState = gp.finishState; // Tüm canavarlar öldüyse kapıyı aç................
                   break;
               case "Wall":
                   break;
               case "SpeedUp":
                   this.setSpeed(getSpeed() *5/4);
                   gp.obj[i] = null;
                   break;
               case "IncreaseRadius":
                   gp.obj[i] = null;
                   break;
           }
        }
    }

    public void interactNPC(int i) {

        if (i != 999) {
            System.out.println("npc");
        }
    }

    public void draw(Graphics2D g2D) {

    //    g2D.setColor(Color.pink);
    //    g2D.fillRect(getX(), getY(), gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        if (spriteNum == 1) {
            switch (direction) {
                case "right": image = right1; break;
                case "left": image = left1; break;
                case "up": image = up1; break;
                case "down": image = down1; break;
            }
        } else if (spriteNum == 2) {
            switch (direction) {
                case "right": image = right2; break;
                case "left": image = left2; break;
                case "up": image = up2; break;
                case "down": image = down2; break;
            }
        }
        int x = screenX, y = screenY;

        if (screenX > getWorldX()) {
            x = getWorldX();
        }
        if (screenY > getWorldY()) {
            y = getWorldY();
        }

        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > gp.worldWidth - getWorldX()) {
            x = gp.screenWidth - (gp.worldWidth - getWorldX());
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > gp.worldHeight - getWorldY()) {
            y = gp.screenHeight - (gp.worldHeight - getWorldY());
        }

        g2D.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        // PLAYER HITBOX
        g2D.setColor(Color.MAGENTA);
        g2D.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);

    }


}
