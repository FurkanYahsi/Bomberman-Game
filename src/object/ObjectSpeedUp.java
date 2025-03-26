package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectSpeedUp extends SuperObject{

    public ObjectSpeedUp() {
        name = "SpeedUp";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        playerCollision = true;
        NPCCollision = true;
    }
}
