package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectBomb extends SuperObject{

    public ObjectBomb() {
        name = "Bomb";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/shield_wood.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        playerCollision = false;
        NPCCollision = true;
    }
}
