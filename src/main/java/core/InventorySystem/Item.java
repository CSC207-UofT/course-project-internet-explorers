package core.InventorySystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.UUID;

public abstract interface Item {
    Texture getSelectedTexture();
    Texture getUnselectedTexture();
    int getSize();
    UUID getID();
    ImageButton createInventorySlot(int i);
}
