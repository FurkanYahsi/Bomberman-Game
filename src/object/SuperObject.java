package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    protected BufferedImage image;
    public String name;
    public boolean playerCollision = false, NPCCollision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2D, GamePanel gp) {

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

        if(screenX + gp.tileSize > 0 && screenX <= gp.screenWidth &&
                worldX + gp.tileSize > gp.player.getWorldX() - gp.player.screenX && worldX - gp.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.getWorldY() - gp.player.screenY && worldY - gp.tileSize< gp.player.getWorldY() + gp.player.screenY) {
            g2D.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }else if (gp.player.screenX > gp.player.getWorldX() || gp.player.screenY > gp.player.getWorldY() ||
                rightOffset > gp.worldWidth - gp.player.getWorldX() || bottomOffset > gp.worldHeight - gp.player.getWorldY()) {
            g2D.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
