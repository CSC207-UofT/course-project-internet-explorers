package core.InventorySystem;

public abstract interface Item {
    String getName();
    String getTexture();
    String getMeta();
    int getSize();
    String getShape();
    Boolean held();
}
