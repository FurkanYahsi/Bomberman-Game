package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectPurpleWall extends SuperObject{

    public ObjectPurpleWall() {
        name = "PurpleWall";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/purplewall.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        playerCollision = true;
        NPCCollision = true;
    }
}
