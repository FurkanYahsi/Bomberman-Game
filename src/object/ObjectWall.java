package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectWall extends SuperObject {

    public ObjectWall() {
        name = "Wall";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/wall.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        playerCollision = true;
        NPCCollision = true;
    }
}
