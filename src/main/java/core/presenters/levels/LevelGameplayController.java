package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import com.badlogic.gdx.Screen;
import core.config.Config;
import core.input.InputController;
import core.input.InputManager;
import core.input.InputMapping;
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

    @Override
    public void show() {
        this.inputManager = new InputManager();

        SavedLevel chosenLevel = LevelLoader.loadState(LevelManager.selectedLevel.get());
        levelManager.initializeLevel(chosenLevel);
        this.entityManager = levelManager.getEntityManager();
        this.cameraManager = new CameraManager(levelManager.getUnitScale(), entityManager);
        this.characterManager = new CharacterManager(entityManager);
        levelManager.addGameCharacterRegistrationCallbacks(characterManager, inputManager);

        createSpawners(chosenLevel);

        if ((boolean) Config.get("render-graphics")) {
            this.levelGameplayPresenter = new LevelGameplayPresenter(this);
            this.hudPresenter = new HudPresenter(characterManager, levelManager, playerId);
            inputManager.addInputMapping(
                new InputMapping<>(InputController.keyboardInputDevice().hudInputInputProvider(), hudPresenter::handleInput)
            );
        }
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
        Spawner<Character> playerSpawner = createPlayerSpawner(level.getPlayerPosition());
        playerSpawner.setEntityManager(entityManager);
        playerSpawner.addSpawnCallback(player -> {
            characterManager.addCharacterInputMapping(
                inputManager,
                player.getId(),
                InputController.keyboardInputDevice().characterInputProvider()
            );
            player.setTeam("player");
            characterManager.addInventoryItem(player.getId(), new Dagger());
            characterManager.addInventoryItem(player.getId(), new Sword());

            cameraManager.setSubjectID(player.getId());
        });
        this.playerId = playerSpawner.spawn().getId();

        Spawner<Spike> spikeSpawner = createSpikeSpawner();
        spikeSpawner.setEntityManager(entityManager);
        spikeSpawner.spawn();

        for (ArrayList<Float> position : level.getEnemyPositions()) {
            Spawner<Character> enemySpawner = loadEnemySpawner(position);
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
            Spawner<Character> defenderSpawner = createDefenderSpawner(position);
            defenderSpawner.setEntityManager(entityManager);
            defenderSpawner.addSpawnCallback(defender -> defender.setTeam("defense"));
            defenderSpawner.spawn();
        }

        Spawner<?> mapBorderSpawner = createMapBorderSpawner();
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
