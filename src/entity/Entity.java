package entity;

import main.GamePanel;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    private int worldX, worldY, speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0, spriteNum = 1;
    public Rectangle solidArea =  new Rectangle(8, 16, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() { }
    public void update() {
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this);
        gp.collisionChecker.checkPlayer(this);

        if (!collisionOn) {
            switch (direction) {
                case "up": setWorldY(getWorldY() - getSpeed()); break;
                case "down": setWorldY(getWorldY() + getSpeed()); break;
                case "left": setWorldX(getWorldX() - getSpeed()); break;
                case "right": setWorldX(getWorldX() + getSpeed()); break;
            }
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

    public BufferedImage setUp(String imagePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getImage() { }

    public void draw (Graphics2D g2D) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
        int screenY = worldY- gp.player.getWorldY() + gp.player.screenY;

        // Stop moving the camera at the edge
        if (gp.player.screenX > gp.player.getWorldX()){
            screenX = worldX;
        }
        if (gp.player.screenY > gp.player.getWorldY()) {
            screenY = worldY;
        }
        int rightOffset = gp.screenWidth - gp.player.screenX;
        if (rightOffset > gp.worldWidth - gp.player.getWorldX()) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - gp.player.screenY;
        if (bottomOffset > gp.worldHeight - gp.player.getWorldY()) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if(screenX + gp.tileSize > 0 && screenX <= gp.screenWidth ) {

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
            g2D.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            //HITBOXES
            g2D.setColor(Color.RED);
            g2D.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        }else if (gp.player.screenX > gp.player.getWorldX() || gp.player.screenY > gp.player.getWorldY() ||
                rightOffset > gp.worldWidth - gp.player.getWorldX() || bottomOffset > gp.worldHeight - gp.player.getWorldY()) {

            g2D.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            //HITBOXES
            g2D.setColor(Color.RED);
            g2D.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
