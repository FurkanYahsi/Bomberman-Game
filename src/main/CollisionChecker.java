package main;

import entity.Entity;
import entity.NormalMonster;
import object.ObjectSpeedUp;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.getWorldX() + entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width - gp.scale;
        int entityTopWorldY = entity.getWorldY() + entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height - gp.scale;

        int entityLeftCol = (entityLeftWorldX) / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;


        switch (entity.direction) {
            case "up" :
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
         }
        /*
        if (entity.direction.equals("up")) {
            entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("down")) {
            entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("left")) {
            entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("right")) {
            entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
        }


         */
    }

    public int checkObject(Entity entity) {

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) {

            //PLAYER COLLISION
            if (gp.obj[i] != null) {

                // Get the entity's solid area position
                gp.player.solidArea.x = gp.player.getWorldX() + gp.player.solidArea.x;
                gp.player.solidArea.y = gp.player.getWorldY() + gp.player.solidArea.y;

                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (gp.player.direction) {
                    case "up":
                        gp.player.solidArea.y -= gp.player.getSpeed();
                        if (gp.player.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].playerCollision) {
                                gp.player.collisionOn = true;
                                index = i;
                            }
                        }

                        break;
                    case "down":
                        gp.player.solidArea.y += gp.player.getSpeed();
                        if (gp.player.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].playerCollision) {
                                gp.player.collisionOn = true;
                                index = i;
                            }

                        }
                        break;
                    case "left":
                        gp.player.solidArea.x -= gp.player.getSpeed();
                        if (gp.player.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].playerCollision) {
                                gp.player.collisionOn = true;
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        gp.player.solidArea.x += gp.player.getSpeed();
                        if (gp.player.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].playerCollision) {
                                gp.player.collisionOn = true;
                                index = i;
                            }
                        }
                        break;
                }

                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
                gp.player.solidArea.y = gp.player.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;


            }


            for (int j = 0; j < gp.npc.length; j++) {


                //NPC COLLISION
                if (gp.obj[i] != null && gp.npc[j] != null) {

                    // Get the entity's solid area position
                    gp.npc[j].solidArea.x = gp.npc[j].getWorldX() + gp.npc[j].solidArea.x;
                    gp.npc[j].solidArea.y = gp.npc[j].getWorldY() + gp.npc[j].solidArea.y;

                    // Get the object's solid area position
                    gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                    gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                    switch (gp.npc[j].direction) {
                        case "up":
                            gp.npc[j].solidArea.y -= gp.npc[j].getSpeed();
                            if (gp.npc[j].solidArea.intersects(gp.obj[i].solidArea)) {
                                if (gp.obj[i].NPCCollision) {
                                    gp.npc[j].collisionOn = true;
                                }
                            }
                            break;
                        case "down":
                            gp.npc[j].solidArea.y += gp.npc[j].getSpeed();
                            if (gp.npc[j].solidArea.intersects(gp.obj[i].solidArea)) {
                                if (gp.obj[i].NPCCollision) {
                                    gp.npc[j].collisionOn = true;
                                }

                            }
                            break;
                        case "left":
                            gp.npc[j].solidArea.x -= gp.npc[j].getSpeed();
                            if (gp.npc[j].solidArea.intersects(gp.obj[i].solidArea)) {
                                if (gp.obj[i].NPCCollision) {
                                    gp.npc[j].collisionOn = true;
                                }

                            }
                            break;
                        case "right":
                            gp.npc[j].solidArea.x += gp.npc[j].getSpeed();
                            if (gp.npc[j].solidArea.intersects(gp.obj[i].solidArea)) {

                                if (gp.obj[i].NPCCollision) {
                                    gp.npc[j].collisionOn = true;
                                }
                            }
                            break;
                    }
                    gp.npc[j].solidArea.x = gp.npc[j].solidAreaDefaultX;
                    gp.npc[j].solidArea.y = gp.npc[j].solidAreaDefaultY;
                    gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                    gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

                }
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for(int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                // Get the entity's solid area position
                entity.solidArea.x = entity.getWorldX() + entity.solidArea.x;
                entity.solidArea.y = entity.getWorldY() + entity.solidArea.y;

                // Get the npc's solid area position
                target[i].solidArea.x = target[i].getWorldX() + target[i].solidArea.x;
                target[i].solidArea.y = target[i].getWorldY() + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.getSpeed();
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            gp.gameState = gp.gameOverState;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.getSpeed();
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            gp.gameState = gp.gameOverState;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.getSpeed();
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            gp.gameState = gp.gameOverState;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.getSpeed();
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            gp.gameState = gp.gameOverState;
                            index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;


            }
        }
        return index;
    }

    public void checkPlayer(Entity entity) {

        // Get the entity's solid area position
        entity.solidArea.x = entity.getWorldX() + entity.solidArea.x;
        entity.solidArea.y = entity.getWorldY() + entity.solidArea.y;

        // Get the player's solid area position
        gp.player.solidArea.x = gp.player.getWorldX() + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.getWorldY() + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.getSpeed();
                if (entity.solidArea.intersects(gp.player.solidArea) && entity instanceof NormalMonster) {
                    entity.collisionOn = true;
                    gp.gameState = gp.gameOverState;
                }
                break;
            case "down":
                entity.solidArea.y += entity.getSpeed();
                if (entity.solidArea.intersects(gp.player.solidArea) && entity instanceof NormalMonster) {
                    entity.collisionOn = true;
                    gp.gameState = gp.gameOverState;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.getSpeed();
                if (entity.solidArea.intersects(gp.player.solidArea) && entity instanceof NormalMonster) {
                    entity.collisionOn = true;
                    gp.gameState = gp.gameOverState;
                }
                break;
            case "right":
                entity.solidArea.x += entity.getSpeed();
                if (entity.solidArea.intersects(gp.player.solidArea) && entity instanceof NormalMonster) {
                    entity.collisionOn = true;
                    gp.gameState = gp.gameOverState;
                }
                break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

    }

}

