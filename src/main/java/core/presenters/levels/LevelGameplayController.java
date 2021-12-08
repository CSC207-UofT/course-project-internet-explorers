package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import com.badlogic.gdx.Screen;
import core.config.Config;
import core.config.ConfigurableSetting;
import core.input.AIInputDevice;
import core.input.InputController;
import core.input.KeyboardInputDevice;
import core.inventory.ItemManager;
import core.inventory.items.Dagger;
import core.inventory.items.Defender;
import core.inventory.items.Sword;
import core.levels.LevelManager;
import core.presenters.HUD.HudPresenter;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import core.worldEntities.types.damageDealers.Spike;
import java.util.UUID;

public class LevelGameplayController implements Screen {

    private static final ConfigurableSetting<String> selectedLevel = Config.add(
        String.class,
        "selected-level",
        "Name of the level to load & play when the Play button is clicked.",
        "demo",
        s -> s
    );

    private static final LevelManager levelManager = new LevelManager();
    private LevelGameplayPresenter levelGameplayPresenter;
    private HudPresenter hudPresenter;
    private InputController inputController;
    private CharacterManager characterManager;
    private WorldEntityManager entityManager;
    private UUID playerId;
    private ItemManager itemManager;

    @Override
    public void show() {
        levelManager.initializeLevel(selectedLevel.get());
        // add to LevelManager.initializeLevel

        this.itemManager = new ItemManager(levelManager);
        this.entityManager = levelManager.getEntityManager();
        this.characterManager = new CharacterManager(levelManager, itemManager);

        levelManager.addGameCharacterRegistrationCallbacks(characterManager);

        createSpawners();


        this.levelGameplayPresenter = new LevelGameplayPresenter(this);
        this.hudPresenter = new HudPresenter(characterManager, levelManager, playerId);

        this.inputController = new InputController(entityManager, characterManager, hudPresenter, levelManager);
    }

    @Override
    public void render(float dt) {
        inputController.handleInputs(dt);
        levelManager.step(dt);

        levelGameplayPresenter.render(dt);
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
        playerSpawner.addSpawnCallback(player -> {
            characterManager.setInputDeviceType(player.getId(), KeyboardInputDevice.class);
            characterManager.addInventoryItem(player.getId(), itemManager.createItem(Dagger.class));
            characterManager.addInventoryItem(player.getId(), itemManager.createItem(Sword.class));;
            characterManager.addInventoryItem(player.getId(), itemManager.createItem(Defender.class));
            this.playerId = player.getId();
        });
        playerSpawner.spawn();

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

    public UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public void resize(int width, int height) {
        levelGameplayPresenter.resize();
    }

    @Override
    public void pause() {
        levelManager.pause();
    }

    @Override
    public void resume() {
        levelManager.resume();
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        levelManager.dispose();
        hudPresenter.dispose();
    }

    public HudPresenter getHudPresenter() {
        return hudPresenter;
    }
}
