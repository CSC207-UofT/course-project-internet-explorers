package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import com.badlogic.gdx.Screen;
import core.config.Config;
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
import java.util.ArrayList;
import java.util.UUID;

public class LevelGameplayController implements Screen {

    private static final LevelManager levelManager = new LevelManager();

    private LevelGameplayPresenter levelGameplayPresenter;
    private HudPresenter hudPresenter;
    private InputController inputController;
    private WorldEntityManager entityManager;
    private CharacterManager characterManager;
    private UUID playerId;

    public LevelGameplayController() {}

    @Override
    public void show() {

        SavedLevel chosenLevel = LevelLoader.loadState((String) Config.get("selected-level"));
        levelManager.initializeLevel(chosenLevel);
        this.entityManager = levelManager.getEntityManager();
        this.characterManager = new CharacterManager(entityManager);
        levelManager.addGameCharacterRegistrationCallbacks(characterManager);

        createSpawners(chosenLevel);
        initiatePlayerInventory();

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

    public void createSpawners(SavedLevel level) {
        Spawner<Character> playerSpawner = createPlayerSpawner(level.getPlayerPosition());
        playerSpawner.setEntityManager(entityManager);
        playerSpawner.addSpawnCallback(player -> characterManager.setInputDeviceType(player.getId(), KeyboardInputDevice.class));
        Character player = (Character) playerSpawner.spawn();
        player.setTeam("player");
        this.playerId = player.getId();

        Spawner<Spike> spikeSpawner = createSpikeSpawner();
        spikeSpawner.setEntityManager(entityManager);
        spikeSpawner.spawn();


        for (ArrayList<Float> position : level.getEnemyPositions()) {
            Spawner<?> enemySpawner = loadEnemySpawner(position);
            enemySpawner.setEntityManager(entityManager);
            enemySpawner.addSpawnCallback(enemy -> characterManager.setInputDeviceType(enemy.getId(),
                                                                                       AIInputDevice.class
            ));
            Character enemy = (Character) enemySpawner.spawn();
            enemy.setTeam("enemy");
        }


        for (ArrayList<Float> position : level.getDefenderPositions()) {
            Spawner<?> defenderSpawner = createDefenderSpawner(position);
            defenderSpawner.setEntityManager(entityManager);
            Character defender = (Character) defenderSpawner.spawn();
            defender.setTeam("defense");
        }

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
