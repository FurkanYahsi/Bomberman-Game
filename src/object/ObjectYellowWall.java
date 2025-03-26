package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectYellowWall extends SuperObject{

    public ObjectYellowWall() {
        name = "YellowWall";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/yellowwall.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        playerCollision = true;
        NPCCollision = true;
    }
}
