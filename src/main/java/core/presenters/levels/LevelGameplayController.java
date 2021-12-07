package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import com.badlogic.gdx.Screen;
import core.config.Config;
import core.config.ConfigurableSetting;
import core.input.AIInputDevice;
import core.input.InputController;
import core.input.KeyboardInputDevice;
import core.inventory.Item;
import core.inventory.items.Dagger;
import core.inventory.items.Sword;
import core.levels.LevelLoader;
import core.levels.LevelManager;
import core.levels.SavedLevel;
import core.presenters.HUD.HudPresenter;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import core.worldEntities.types.damageDealers.Spike;

import java.io.IOException;
import java.util.UUID;

public class LevelGameplayController implements Screen {

    private static LevelManager levelManager;
    private static final ConfigurableSetting<String> selectedLevel = Config.add(
        String.class,
        "selected-level",
        "Name of the level to load & play when the Play button is clicked.",
        "demo",
        s -> s
    );
    private LevelGameplayPresenter levelGameplayPresenter;
    private HudPresenter hudPresenter;
    private InputController inputController;
    private WorldEntityManager entityManager;
    private CharacterManager characterManager;
    private UUID playerId;

    public LevelGameplayController() {}

    @Override
    public void show() {
        try {
            SavedLevel chosenLevel = LevelLoader.loadState("LevelOne");
            levelManager = new LevelManager(chosenLevel);

//            levelManager.initializeLevel(selectedLevel.get());
            // add to LevelManager.initializeLevel

            this.entityManager = levelManager.getEntityManager();
            this.characterManager = new CharacterManager(entityManager);
            levelManager.addGameCharacterRegistrationCallbacks(characterManager);

            createSpawners();
            initiatePlayerInventory();

            this.levelGameplayPresenter = new LevelGameplayPresenter(this);
            this.hudPresenter = new HudPresenter(characterManager, levelManager, playerId);

            this.inputController = new InputController(entityManager, characterManager, hudPresenter, levelManager);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
