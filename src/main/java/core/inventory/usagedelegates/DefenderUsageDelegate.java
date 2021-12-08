package core.inventory.usagedelegates;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.inventory.ItemManager;
import core.inventory.Weapon;
import core.inventory.items.Defender;
import core.levels.LevelEvent;
import core.levels.LevelManager;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.health.Damage;
import core.worldEntities.types.damageDealers.CircularDamageRegion;
import java.util.UUID;
import core.worldEntities.DemoSpawners;

public class DefenderUsageDelegate extends ItemManager.ItemUsageDelegate<Defender> {

    private static LevelManager levelManager;

    public DefenderUsageDelegate(LevelManager levelManager) {
        super(Defender.class, DefenderUsageDelegate::useDefender);
        DefenderUsageDelegate.levelManager = levelManager;
    }

    private static void useDefender(UUID user_id, Defender defender) {
        WorldEntityManager entityManager = levelManager.getEntityManager();
        Spawner<?> DefenseSpawner = DemoSpawners.createDefenseSpawner();
        DefenseSpawner.setEntityManager(entityManager);
        DefenseSpawner.spawn();
    }

}
