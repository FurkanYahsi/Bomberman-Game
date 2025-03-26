package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public int mapTileNumPixels[][]; ////////////////////////////////////

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapTileNumPixels = new int[gp.worldWidth][gp.worldHeight]; /////////////////////////////////

        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/035.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Background.png"));
            tile[1].collision = true;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;

                }
                 if (col == gp.maxWorldCol) {
                     col = 0;
                     row ++;
                 }
            }
            br.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2D) {

        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
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
                worldX + gp.tileSize > gp.player.getWorldX() - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.getWorldY() - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.getWorldY() + gp.player.screenY) {

                    g2D.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }else if (gp.player.screenX > gp.player.getWorldX() || gp.player.screenY > gp.player.getWorldY() ||
            rightOffset > gp.worldWidth - gp.player.getWorldX() || bottomOffset > gp.worldHeight - gp.player.getWorldY()) {
                g2D.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
