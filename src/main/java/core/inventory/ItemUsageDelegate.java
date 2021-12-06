package core.inventory;

import core.levels.LevelManager;

import java.util.UUID;

public interface ItemUsageDelegate<T extends Item> {
    void use(LevelManager levelManager, UUID user_id, T item);

    //TODO Make evertything clean: have an inventory manager class for use case of all items (items manager that implements this interface) (by ID)
    // Add defender (spawn as for circle)

}
