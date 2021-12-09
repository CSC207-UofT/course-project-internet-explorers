package core.presenters.levels;

import com.badlogic.gdx.Screen;
import core.config.Config;
import core.input.InputController;
import core.input.InputManager;
import core.input.InputMapping;
import core.inventory.ItemManager;
import core.inventory.items.Dagger;
import core.inventory.items.Defender;
import core.inventory.items.Sword;
import core.levels.LevelLoader;
import core.levels.LevelManager;
import core.levels.SavedLevel;
import core.presenters.HUD.HudPresenter;
import core.worldEntities.SpawnerFactory;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import core.worldEntities.types.damageDealers.DefenderDamage;

import java.util.ArrayList;
import java.util.UUID;

public class LevelGameplayController implements Screen {

    private static final LevelManager levelManager = new LevelManager();

    private LevelGameplayPresenter levelGameplayPresenter;
    private HudPresenter hudPresenter;
    private InputManager inputManager;
    private CameraManager cameraManager;
    private CharacterManager characterManager;
    private WorldEntityManager entityManager;
    private UUID playerId;
    private ItemManager itemManager;
    private InputController inputController;
    private final SpawnerFactory demoSpawnerFactory;

    public LevelGameplayController() {
        this.demoSpawnerFactory = new SpawnerFactory();
    }

    public LevelGameplayController(SpawnerFactory demoSpawnerFactory) {
        this.demoSpawnerFactory = demoSpawnerFactory;
    }

    @Override
    public void show() {
        this.inputManager = new InputManager();

        SavedLevel chosenLevel = LevelLoader.loadState(LevelManager.selectedLevel.get());
        levelManager.initializeLevel(chosenLevel);
        this.itemManager = new ItemManager(levelManager);
        this.entityManager = levelManager.getEntityManager();
        this.cameraManager = new CameraManager(levelManager.getUnitScale(), entityManager);
        this.characterManager = new CharacterManager(levelManager, itemManager);
        levelManager.addGameCharacterRegistrationCallbacks(characterManager, inputManager);

        createSpawners(chosenLevel);

        if ((boolean) Config.get("render-graphics")) {
            this.levelGameplayPresenter = new LevelGameplayPresenter(this);
            this.hudPresenter = new HudPresenter(characterManager, levelManager, playerId);
            inputManager.addInputMapping(
                new InputMapping<>(InputController.keyboardInputDevice().hudInputInputProvider(), hudPresenter::handleInput)
            );
        }

        this.inputController = new InputController();
    }

    @Override
    public void render(float dt) {
        inputManager.handleInputs();
        levelManager.step(dt);
        cameraManager.update(dt);

        if (levelGameplayPresenter != null) {
            levelGameplayPresenter.render();
        }
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void createSpawners(SavedLevel level) {
        Spawner<Character> playerSpawner = demoSpawnerFactory.createPlayerSpawner(level.getPlayerPosition());
        playerSpawner.setEntityManager(entityManager);
        playerSpawner.addSpawnCallback(player -> {
            characterManager.addCharacterInputMapping(
                inputManager,
                player.getId(),
                InputController.keyboardInputDevice().characterInputProvider()
            );
            player.setTeam("player");
            characterManager.addInventoryItem(player.getId(), itemManager.createItem(Dagger.class).getId());
            characterManager.addInventoryItem(player.getId(), itemManager.createItem(Sword.class).getId());
            characterManager.addInventoryItem(player.getId(), itemManager.createItem(Defender.class).getId());

            cameraManager.setSubjectID(player.getId());
        });
        this.playerId = playerSpawner.spawn().getId();


        for (ArrayList<Float> position : level.getEnemyPositions()) {
            Spawner<Character> enemySpawner = demoSpawnerFactory.loadEnemySpawner(position);
            enemySpawner.setEntityManager(entityManager);
            enemySpawner.addSpawnCallback(enemy -> {
                characterManager.addCharacterInputMapping(
                    inputManager,
                    enemy.getId(),
                    InputController.aiInputDevice().characterInputProvider()
                );
                enemy.setTeam("enemy");
            });
            enemySpawner.spawn();
        }

        for (ArrayList<Float> position : level.getDefenderPositions()) {
            Spawner<DefenderDamage> defenderSpawner = SpawnerFactory.createDefenseSpawner(position);
            defenderSpawner.setEntityManager(entityManager);
            defenderSpawner.addSpawnCallback(defender -> defender.setTeam("defense"));
            defenderSpawner.spawn();
        }

        Spawner<?> mapBorderSpawner = SpawnerFactory.createMapBorderSpawner();
        mapBorderSpawner.setEntityManager(entityManager);
        mapBorderSpawner.spawn();
    }

    @Override
    public void resize(int width, int height) {
        if (levelGameplayPresenter != null) {
            levelGameplayPresenter.resize();
        }
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
        if (hudPresenter != null) {
            hudPresenter.dispose();
        }
    }

    public HudPresenter getHudPresenter() {
        return hudPresenter;
    }

    public CharacterManager getCharacterManager() {
        return characterManager;
    }
}
