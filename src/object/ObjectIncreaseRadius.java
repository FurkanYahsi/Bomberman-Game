package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectIncreaseRadius extends SuperObject{

    public ObjectIncreaseRadius() {
        name = "IncreaseRadius";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/greenicon.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        playerCollision = true;
        NPCCollision = true;
    }
}
