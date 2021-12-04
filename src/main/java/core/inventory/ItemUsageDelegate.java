package core.inventory;

import core.levels.LevelManager;

import java.util.UUID;

public abstract class ItemUsageDelegate<T extends Item> {
    public abstract void use(LevelManager levelManager, UUID user_id, T item);

    //TODO have an inventory manager class for use case of all items (items manager) (by ID)
    // Do same for Dagger
    // Make evertything clean
    // Add defender (spawn as for circle)

}
