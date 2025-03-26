package main;

import entity.NormalMonster;
import object.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetSetter {

    GamePanel gp;
    int [][] map;
    KeyHandler keyH;
    int bombCooldown = 0;
    boolean isBombPlaced = false;
    public int explosionRadius;

    public AssetSetter(GamePanel gp, KeyHandler keyH) {

        this.map = new int[gp.maxWorldCol][gp.maxWorldRow];
        this.gp = gp;

        this.keyH = keyH;
    }

    public void setObject() {

        randomWalls();

    }

    //public void setNPC() { }

    public void randomWalls() {

        try {
            InputStream is = getClass().getResourceAsStream("/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    map[col][row] = num;
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

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                System.out.print(map[i][j]);
            }
            System.out.println();
        }


        int numberOfTotalWalls = (int)(Math.random() * 10) + 50, numberOfDrawnWalls = 1;
        int x, y;
        int k = 40;
        boolean isDoorDrawn = false, isIconDrawn = false;

        while (numberOfDrawnWalls <= numberOfTotalWalls) {
            do {
                x = (int) (Math.random() * map[0].length);
                y = (int) (Math.random() * map.length);

            } while ((x >= map[0].length || y >= map.length || map[y][x] != 0) || (x < 3 && y < 3));

            if(!isDoorDrawn) {

                gp.obj[40] = new ObjectDoor();
                gp.obj[40].worldX = y * gp.tileSize;
                gp.obj[40].worldY = x * gp.tileSize;
                isDoorDrawn = true;

                gp.obj[++k] = new ObjectPurpleWall();
                gp.obj[k].worldX = y * gp.tileSize;
                gp.obj[k].worldY = x * gp.tileSize;
                map[y][x] = 1;
            }
            if(!isIconDrawn && map[y][x] == 0) {

                isIconDrawn = true;

                gp.obj[39] = new ObjectYellowWall();
                gp.obj[39].worldX = y * gp.tileSize;
                gp.obj[39].worldY = x * gp.tileSize;
                map[y][x] = 1;
            }


            if (map[y][x] == 0) {
                gp.obj[++k] = new ObjectWall();
                gp.obj[k].worldX = y * gp.tileSize;
                gp.obj[k].worldY = x * gp.tileSize;
                map[y][x] = 1;
            }

            numberOfDrawnWalls++;
        }
        int drawnMonsters = 1;
        k = -1;

        while (drawnMonsters <= 6) {
            do {
                x = (int) (Math.random() * map[0].length);
                y = (int) (Math.random() * map.length);

            } while ((x >= map[0].length || y >= map.length || map[y][x] != 0) || (x < 3 && y < 3));

            gp.npc[++k] = new NormalMonster(gp);
            gp.npc[k].setWorldX(gp.tileSize * y);
            gp.npc[k].setWorldY(gp.tileSize * x);
            map[y][x] = 1;

            drawnMonsters++;

        }

    }

    public void update() {

        if (keyH.bombDropped == true && !isBombPlaced) {
            isBombPlaced = true;
            bombCooldown = 0;
            gp.obj[1] = new ObjectBomb();
            gp.obj[1].worldX = ((gp.player.getWorldX() + 20) / gp.tileSize) * gp.tileSize;
            gp.obj[1].worldY = ((gp.player.getWorldY() + 20) / gp.tileSize) * gp.tileSize;
        }
        if (isBombPlaced) {
            bombCooldown++;
        }

        if (bombCooldown > 180) {
            bombExplosion(1);
            gp.obj[1] = null;

            bombCooldown = 0;
            isBombPlaced = false;
        }
    }

    public void bombExplosion(int i) {
        int bombX = gp.obj[i].worldX;
        int bombY = gp.obj[i].worldY;
        explosionRadius = gp.tileSize;
        if (gp.ui.commandNum == 2 && gp.obj[39] == null) {
            explosionRadius = gp.tileSize * 2;
        }
        int tolerance = gp.tileSize - gp.scale; // Tolerance gap

        int iconX;
        int iconY;

        // Right
        for (int x = bombX; x <= bombX + explosionRadius; x += gp.tileSize) {
            for (int j = 0; j < gp.obj.length; j++) {
                if ((gp.obj[j] instanceof ObjectWall || gp.obj[j] instanceof ObjectPurpleWall || gp.obj[j] instanceof ObjectYellowWall)
                        && Math.abs(gp.obj[j].worldX - x) <= tolerance && Math.abs(gp.obj[j].worldY - bombY) <= tolerance) {
                    iconX = ((gp.obj[j].worldX) / gp.tileSize);
                    iconY = ((gp.obj[j].worldY) / gp.tileSize);
                    if (gp.obj[j] instanceof ObjectYellowWall) {
                        gp.obj[j] = null; // Dispose the wall
                        chosenSpeciality(20, iconX, iconY);
                    }else {
                        gp.obj[j] = null; // Dispose the wall
                    }
                }
            }
            for (int j = 0; j < gp.npc.length; j++) {
                if (gp.npc[j] instanceof NormalMonster && Math.abs(gp.npc[j].getWorldX() - x) <= tolerance && Math.abs(gp.npc[j].getWorldY() - bombY) <= tolerance) {
                    gp.npc[j] = null; // Eliminate the monster
                }
            }
            if (Math.abs(gp.player.getWorldX() - x) <= tolerance && Math.abs(gp.player.getWorldY() - bombY) <= tolerance){
                gp.gameState = gp.gameOverState;
            }
        }

        // Left
        for (int x = bombX; x >= bombX - explosionRadius; x -= gp.tileSize) {
            for (int j = 0; j < gp.obj.length; j++) {
                if ((gp.obj[j] instanceof ObjectWall || gp.obj[j] instanceof ObjectPurpleWall || gp.obj[j] instanceof ObjectYellowWall)
                        && Math.abs(gp.obj[j].worldX - x) <= tolerance && Math.abs(gp.obj[j].worldY - bombY) <= tolerance) {
                    iconX = ((gp.obj[j].worldX) / gp.tileSize);
                    iconY = ((gp.obj[j].worldY) / gp.tileSize);

                    if (gp.obj[j] instanceof ObjectYellowWall) {
                        gp.obj[j] = null; // Dispose the wall
                        chosenSpeciality(20, iconX, iconY);
                    }else {
                        gp.obj[j] = null; // Dispose the wall
                    }
                }
            }
            for (int j = 0; j < gp.npc.length; j++) {
                if (gp.npc[j] instanceof NormalMonster && Math.abs(gp.npc[j].getWorldX() - x) <= tolerance && Math.abs(gp.npc[j].getWorldY() - bombY) <= tolerance) {
                    gp.npc[j] = null; // Eliminate the monster
                }
            }
            if (Math.abs(gp.player.getWorldX() - x) <= tolerance && Math.abs(gp.player.getWorldY() - bombY) <= tolerance){
                gp.gameState = gp.gameOverState;
            }
        }

        // Up
        for (int y = bombY; y >= bombY - explosionRadius; y -= gp.tileSize) {
            for (int j = 0; j < gp.obj.length; j++) {
                if ((gp.obj[j] instanceof ObjectWall || gp.obj[j] instanceof ObjectPurpleWall || gp.obj[j] instanceof ObjectYellowWall)
                        && Math.abs(gp.obj[j].worldX - bombX) <= tolerance && Math.abs(gp.obj[j].worldY - y) <= tolerance) {
                    iconX = ((gp.obj[j].worldX) / gp.tileSize);
                    iconY = ((gp.obj[j].worldY) / gp.tileSize);

                    if (gp.obj[j] instanceof ObjectYellowWall) {
                        gp.obj[j] = null; // Dispose the wall
                        chosenSpeciality(20, iconX, iconY);
                    }else {
                        gp.obj[j] = null; // Dispose the wall
                    }
                }
            }
            for (int j = 0; j < gp.npc.length; j++) {
                if (gp.npc[j] instanceof NormalMonster && Math.abs(gp.npc[j].getWorldX() - bombX) <= tolerance && Math.abs(gp.npc[j].getWorldY() - y) <= tolerance) {
                    gp.npc[j] = null; // Eliminate the monster
                }
            }
            if (Math.abs(gp.player.getWorldX() - bombX) <= tolerance && Math.abs(gp.player.getWorldY() - y) <= tolerance){
                gp.gameState = gp.gameOverState;
            }
        }

        // Down
        for (int y = bombY; y <= bombY + explosionRadius; y += gp.tileSize) {
            for (int j = 0; j < gp.obj.length; j++) {
                if ((gp.obj[j] instanceof ObjectWall || gp.obj[j] instanceof ObjectPurpleWall || gp.obj[j] instanceof ObjectYellowWall)
                        && Math.abs(gp.obj[j].worldX - bombX) <= tolerance && Math.abs(gp.obj[j].worldY - y) <= tolerance) {
                    iconX = ((gp.obj[j].worldX ) / gp.tileSize);
                    iconY = ((gp.obj[j].worldY ) / gp.tileSize);

                    if (gp.obj[j] instanceof ObjectYellowWall) {
                        gp.obj[j] = null; // Dispose the wall
                        chosenSpeciality(20, iconX, iconY);
                    }else {
                        gp.obj[j] = null; // Dispose the wall
                    }
                }
            }
            for (int j = 0; j < gp.npc.length; j++) {
                if (gp.npc[j] instanceof NormalMonster && Math.abs(gp.npc[j].getWorldX() - bombX) <= tolerance && Math.abs(gp.npc[j].getWorldY() - y) <= tolerance) {
                    gp.npc[j] = null; // Eliminate the monster
                }
            }
            if (Math.abs(gp.player.getWorldX() - bombX) <= tolerance && Math.abs(gp.player.getWorldY() - y) <= tolerance){
                gp.gameState = gp.gameOverState;
            }
        }
    }

    public void chosenSpeciality(int i, int x, int y) {

            if (gp.ui.commandNum == 0) {
                gp.obj[i] = new ObjectSpeedUp();
                gp.obj[i].worldX = x * gp.tileSize;
                gp.obj[i].worldY = y * gp.tileSize;
            }if (gp.ui.commandNum == 2) {
                gp.obj[i] = new ObjectIncreaseRadius();
                gp.obj[i].worldX = x * gp.tileSize;
                gp.obj[i].worldY = y * gp.tileSize;
            }

    }
}
