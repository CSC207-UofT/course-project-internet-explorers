package core.inventory;

import java.util.UUID;

public abstract class Weapon implements Item {

    private String texturePath;
    private int size;
    private int level;
    private int damage;
    private int range;
    public UUID id;

    public abstract int getDamage();

    public abstract int getSize();

    public abstract int getLevel();

    public abstract void setLevel(int new_level);

    public abstract int getRange();

    public abstract UUID getID();
}