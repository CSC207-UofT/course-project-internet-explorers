package core.InventorySystem;

import com.badlogic.gdx.graphics.Texture;

public abstract interface Item {
    String getName();
    Texture getTexture();
    String getMeta();
    int getSize();
    String getShape();
    Boolean getHeld();
    void setHeld();
}
