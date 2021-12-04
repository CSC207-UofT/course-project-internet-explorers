package core.inventory;

import core.levels.LevelManager;
import core.worldEntities.health.Damage;
import core.worldEntities.types.damageDealers.CircularDamageRegion;

import java.util.UUID;

public abstract class Weapon implements Item {

    private String texturePath;
    private int size;
    private int level;
    private Damage damage;
    private int range;
    public UUID id;

    public static ItemUsageDelegate<Weapon> usageDelegate = new ItemUsageDelegate<Weapon>() {
        @Override
        public void use(LevelManager levelManager, UUID user_id, Weapon weapon) {
            Damage damage = new Damage(weapon.getDamage().amount(), user_id);
             new CircularDamageRegion(levelManager, levelManager.getEntityManager().getEntity(user_id).getPosition(),
                                                      (float) weapon.getRange(),damage);

        }
    };

    public abstract Damage getDamage();

    public abstract int getSize();

    public abstract int getLevel();

    public abstract void setLevel(int new_level);

    public abstract int getRange();

    public abstract UUID getID();

    @Override
    public ItemUsageDelegate getUsageDelegate() {
        return Weapon.usageDelegate;
    }
}
