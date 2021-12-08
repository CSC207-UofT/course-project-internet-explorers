package core.inventory;

import core.worldEntities.health.Damage;
import java.util.UUID;

public abstract class Weapon implements Item {

    public static int DEFAULT_LEVEL = 1;

    private String texturePath;
    private int level;
    private Damage damage;
    private int range;
    public UUID id;

    public abstract Damage getDamage();

    public abstract int getLevel();

    public abstract void setLevel(int new_level);

    public abstract int getRange();

    public abstract UUID getId();
}
