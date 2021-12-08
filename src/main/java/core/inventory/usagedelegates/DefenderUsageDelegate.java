package core.inventory.usagedelegates;

import core.inventory.ItemManager;
import core.inventory.items.Defender;
import core.levels.LevelManager;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import java.util.UUID;
import core.worldEntities.DemoSpawners;

public class DefenderUsageDelegate extends ItemManager.ItemUsageDelegate<Defender> {

    /*
     * Provides the useDefender function as a usageDelegate to ItemManager
     * @param levelManager: The relevant levelManager
     * */

    private static LevelManager levelManager;

    public DefenderUsageDelegate(LevelManager levelManager) {
        super(Defender.class, DefenderUsageDelegate::useDefender);
        this.levelManager = levelManager;
    }

    private static void useDefender(UUID user_id, Defender defender) {
        WorldEntityManager entityManager = levelManager.getEntityManager();
        Spawner<?> DefenseSpawner = DemoSpawners.createDefenseSpawner();
        DefenseSpawner.setEntityManager(entityManager);
        DefenseSpawner.spawn();
    }

}
