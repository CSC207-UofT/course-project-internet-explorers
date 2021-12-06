package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import core.input.AIInputDevice;
import core.input.KeyboardInputDevice;
import core.inventory.Item;
import core.inventory.items.Dagger;
import core.inventory.items.Sword;
import core.levels.LevelManager;
import core.levels.LevelState;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import core.worldEntities.types.damageDealers.Spike;
import java.util.UUID;
import java.util.function.Supplier;

public class LevelGameplayController {

    private final LevelManager levelManager;
    private final WorldEntityManager entityManager;
    private final CharacterManager characterManager;
    private UUID playerId;

    public LevelGameplayController(Supplier<LevelState> levelSupplier) {
        this.levelManager = new LevelManager(levelSupplier.get());
        this.entityManager = levelManager.getEntityManager();
        this.characterManager = new CharacterManager(entityManager);
        levelManager.addGameCharacterRegistrationCallbacks(characterManager);

        createSpawners();
        initiatePlayerInventory();
    }

    public CharacterManager getCharacterManager() {
        return characterManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
    }

    public void createSpawners() {
        Spawner<Character> playerSpawner = createPlayerSpawner();
        playerSpawner.setEntityManager(entityManager);
        playerSpawner.addSpawnCallback(player -> characterManager.setInputDeviceType(player.getId(), KeyboardInputDevice.class));
        this.playerId = playerSpawner.spawn().getId();

        Spawner<Spike> spikeSpawner = createSpikeSpawner();
        spikeSpawner.setEntityManager(entityManager);
        spikeSpawner.spawn();

        Spawner<?> enemySpawner = createEnemySpawner();
        enemySpawner.setEntityManager(entityManager);
        enemySpawner.addSpawnCallback(enemy -> characterManager.setInputDeviceType(enemy.getId(), AIInputDevice.class));
        enemySpawner.spawn();

        Spawner<?> defenderSpawner = createDefenderSpawner();
        defenderSpawner.setEntityManager(entityManager);
        defenderSpawner.spawn();

        Spawner<?> mapBorderSpawner = createMapBorderSpawner();
        mapBorderSpawner.setEntityManager(entityManager);
        mapBorderSpawner.spawn();
    }

    public void initiatePlayerInventory() {
        Item sword = new Sword(1);
        Item dagger = new Dagger(1);

        characterManager.addInventoryItem(playerId, dagger);
        characterManager.addInventoryItem(playerId, sword);
    }

    public UUID getPlayerId() {
        return this.playerId;
    }
}
