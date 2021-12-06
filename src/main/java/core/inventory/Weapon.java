package core.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.levels.LevelManager;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.health.Damage;
import core.worldEntities.types.damageDealers.CircularDamageRegion;
import java.util.UUID;

public abstract class Weapon implements Item {

    public static int DEFAULT_LEVEL = 1;

    private String texturePath;
    private int level;
    private Damage damage;
    private int range;
    public UUID id;

    public static ItemUsageDelegate<Weapon> usageDelegate = new ItemUsageDelegate<>() {
        /**
         * Creates a CircularDamageRegion around the Character who used the specified Weapon.
         *
         * Region radius is set as the Weapon's range.
         *
         * @param levelManager : The manager of the level in which the CircularDamageRegion should be spawned
         * @param user_id : The ID of the Character who used the item
         * @param weapon : The Weapon item used.
         */
        @Override
        public void use(LevelManager levelManager, UUID user_id, Weapon weapon) {
            WorldEntityManager entityManager = levelManager.getEntityManager();

            CircularDamageRegion damageRegion = entityManager.createEntity(
                CircularDamageRegion.class,
                createBodyDef(entityManager.getEntity(user_id).getPosition()),
                createFixtureDef((float) weapon.getRange())
            );
            damageRegion.setDeletionTime(levelManager.getTime() + 500);
            damageRegion.setDamage(new Damage(weapon.getDamage().amount(), user_id));
        }

        private static BodyDef createBodyDef(Vector2 position) {
            BodyDef def = new BodyDef();
            def.position.set(position);
            return def;
        }

        private static FixtureDef createFixtureDef(float radius) {
            CircleShape shape = new CircleShape();
            shape.setRadius(radius);

            FixtureDef def = new FixtureDef();
            // lets other objects pass through this fixture
            def.isSensor = true;
            def.shape = shape;
            return def;
        }
    };

    @Override
    public <T extends Item> ItemUsageDelegate<T> getUsageDelegate() {
        return (ItemUsageDelegate<T>) Weapon.usageDelegate;
    }

    public abstract Damage getDamage();

    public abstract int getLevel();

    public abstract void setLevel(int new_level);

    public abstract int getRange();

    public abstract UUID getID();
}
