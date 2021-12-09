package core.inventory;

import core.worldEntities.health.Damage;

public abstract class Weapon implements Item {

    public static final int DEFAULT_LEVEL = 1;
    private int level;

    public abstract Damage getDamage();

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public abstract int getRange();
}
