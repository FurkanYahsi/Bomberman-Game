package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NormalMonster extends Entity{

    public NormalMonster(GamePanel gp) {
        super(gp);

        direction = "down";
        setSpeed(3);

        //solidArea = new Rectangle(30, 0, 40, gp.tileSize);

        getImage();
    }

    @Override
    public void getImage() {

        up1 = setUp("/monsters/greenslime_down_1");
        up2 = setUp("/monsters/greenslime_down_1");
        down1 = setUp("/monsters/greenslime_down_1");
        down2 = setUp("/monsters/greenslime_down_1");
        left1 = setUp("/monsters/greenslime_down_1");
        left2 = setUp("/monsters/greenslime_down_1");
        right1 = setUp("/monsters/greenslime_down_1");
        right2 = setUp("/monsters/greenslime_down_1");

    }

    @Override
    public void setAction() {


        if (collisionOn) {

            Random random = new Random();
            int i = random.nextInt(4) + 1;
            switch (i) {
                case 1: direction = "up"; break;
                case 2: direction = "down"; break;
                case 3: direction = "left"; break;
                case 4: direction = "right"; break;
            }
        }
    }
}
