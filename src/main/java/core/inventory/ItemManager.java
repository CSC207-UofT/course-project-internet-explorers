package core.inventory;

import core.inventory.items.Dagger;
import core.inventory.items.Defender;
import core.inventory.items.Sword;
import core.inventory.usagedelegates.DefenderUsageDelegate;
import core.inventory.usagedelegates.WeaponUsageDelegate;
import core.levels.LevelManager;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ItemManager {

    /*
     * Provides an interface to create and use items
     * @param itemUsageDelegates: A hashmap containing usageDelegates for different types of items
     * @param items: A hashmap containing the instantiated items
     * */

    public static class ItemUsageDelegate<T extends Item> {

        public final Class<T> type;
        /**
         * usageHandler takes the ID of the Character using the item, and the item in question.
         */
        private final BiConsumer<UUID, T> usageHandler;

        public ItemUsageDelegate(Class<T> type, BiConsumer<UUID, T> usageHandler) {
            this.type = type;
            this.usageHandler = usageHandler;
        }

        public void use(UUID userId, Item item) {
            if (type.isInstance(item)) {
                usageHandler.accept(userId, type.cast(item));
            }
        }
    }

    private final HashMap<Class<? extends Item>, ItemUsageDelegate<?>> itemUsageDelegates = new HashMap<>();
    private final HashMap<UUID, Item> items = new HashMap<>();

    public ItemManager(LevelManager levelManager) {
        this.itemUsageDelegates.put(Dagger.class, new WeaponUsageDelegate(levelManager));
        this.itemUsageDelegates.put(Sword.class, new WeaponUsageDelegate(levelManager));
        this.itemUsageDelegates.put(Defender.class, new DefenderUsageDelegate(levelManager));
    }

    /**
     * Workaround for instantiating items without coupling between entity and use case classes
     */
    public <T extends Item> T createItem(Class<T> type, Object... args) {
        try {
            Class<?>[] argTypes = new Class[args.length];
            T item = type.getConstructor(argTypes).newInstance(args);
            this.items.put(item.getId(), item);
            return item;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public Item get(UUID itemId) {
        return this.items.get(itemId);
    }

    public void use(UUID userId, UUID itemId) {
        Item item = this.get(itemId);
        itemUsageDelegates.get(item.getClass()).use(userId, item);
    }
}
