package core.InventorySystem;

import com.badlogic.gdx.graphics.Texture;

import java.util.UUID;

public abstract interface Item {
    Texture getTexture();
    int getSize();
    Boolean getHeld();
    void setHeld();
    UUID getID();
}
