package core.inventory;

import com.badlogic.gdx.graphics.Texture;
import java.util.UUID;

public interface Item {
    Texture getSelectedTexture();
    Texture getUnselectedTexture();
    UUID getId();
}
