package core.InventorySystem;

import com.badlogic.gdx.graphics.Texture;

public abstract class Weapon implements Item, DamagingCollidable {
    private String name;
    private String texture;
    private String meta;
    private int size;
    private String shape;
    private int level;
    private int damage;
    private int range;
    public abstract Boolean getHeld();
    public abstract void setHeld();
    public abstract int getDamage();
    public abstract String getName();
    public abstract Texture getTexture();
    public abstract String getMeta();
    public abstract int getSize();
    public abstract String getShape();
    public abstract int getLevel();
    public abstract void setLevel(int new_level);
    public abstract int getRange();
}
