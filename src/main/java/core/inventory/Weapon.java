package core.inventory;
//TODO MAKE CLEAN - remove non-clean imports, create inventory manager, move use method
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.inventory.items.Defender;
import core.levels.LevelEvent;
import core.levels.LevelManager;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.health.Damage;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.damageDealers.CircularDamageRegion;
import java.util.UUID;
import static core.worldEntities.DemoSpawners.createDefenseSpawner;

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

            if (weapon instanceof Defender){

                Spawner<?> DefenseSpawner = createDefenseSpawner();
                DefenseSpawner.setEntityManager(entityManager);
                DefenseSpawner.spawn();

            }

            CircularDamageRegion damageRegion = entityManager.createEntity(
                CircularDamageRegion.class,
                createBodyDef(entityManager.getEntity(user_id).getPosition()),
                createFixtureDef((float) weapon.getRange())
            );
            damageRegion.setDamage(new Damage(weapon.getDamage().amount(), user_id));

            levelManager.addLevelEvent(
                new LevelEvent(
                    levelManager.getTime() + 0.1f,
                    levelManager_ -> {
                        levelManager.getEntityManager().deleteEntity(damageRegion.getId());
                    }
                )
            );
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
