package core.inventory;

import static core.worldEntities.DemoSpawners.createDefenseSpawner;

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
import core.worldEntities.types.damageDealers.CircularDamageRegion;
import java.util.UUID;
import java.util.function.BiConsumer;

//        if (weapon instanceof Defender) {
//                Spawner<?> DefenseSpawner = createDefenseSpawner();
//        DefenseSpawner.setEntityManager(entityManager);
//        DefenseSpawner.spawn();
//        return;
//        }

public class WeaponUsageDelegate extends ItemManager.ItemUsageDelegate<Weapon> {

    private static LevelManager levelManager;

    public WeaponUsageDelegate(LevelManager levelManager) {
        super(Weapon.class, WeaponUsageDelegate::useWeapon);
        WeaponUsageDelegate.levelManager = levelManager;
    }

    private static void useWeapon(UUID user_id, Weapon weapon) {
        WorldEntityManager entityManager = levelManager.getEntityManager();

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
}
