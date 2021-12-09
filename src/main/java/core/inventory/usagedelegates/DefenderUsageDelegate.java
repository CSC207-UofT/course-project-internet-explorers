package core.inventory.usagedelegates;

import core.inventory.ItemManager;
import core.inventory.items.Defender;
import core.levels.LevelManager;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import java.util.UUID;
import core.worldEntities.SpawnerFactory;
import core.worldEntities.types.characters.Character;

public class DefenderUsageDelegate extends ItemManager.ItemUsageDelegate<Defender> {

    /*
     * Provides the useDefender function as a usageDelegate to ItemManager
     * @param levelManager: The relevant levelManager
     * */

    private static LevelManager levelManager;

    public DefenderUsageDelegate(LevelManager levelManager) {
        super(Defender.class, DefenderUsageDelegate::useDefender);
        DefenderUsageDelegate.levelManager = levelManager;
    }

    private static void useDefender(UUID user_id, Defender defender) {
        WorldEntityManager entityManager = levelManager.getEntityManager();
        Spawner<?> DefenseSpawner = SpawnerFactory.createDefenseSpawner(levelManager.getEntityManager().getPlayerPosition());
        DefenseSpawner.setEntityManager(entityManager);
        Character entity = (Character) DefenseSpawner.spawn();
        entity.setTeam("defense");

    }

}
