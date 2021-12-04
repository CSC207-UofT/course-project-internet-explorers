package core.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import java.util.UUID;

public interface Item {
    Texture getSelectedTexture();
    Texture getUnselectedTexture();
    int getSize();
    UUID getID();
    ImageButton createInventorySlot(int i);
    ItemUsageDelegate getUsageDelegate();
}
