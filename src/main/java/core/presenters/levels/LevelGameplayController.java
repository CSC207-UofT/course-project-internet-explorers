package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import com.badlogic.gdx.Screen;
import core.config.Config;
import core.config.ConfigurableSetting;
import core.input.InputController;
import core.input.InputManager;
import core.input.InputMapping;
import core.inventory.items.Dagger;
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
    private InputManager inputManager;
    private CharacterManager characterManager;
    private WorldEntityManager entityManager;
    private UUID playerId;

    @Override
    public void show() {
        this.inputManager = new InputManager();
        levelManager.initializeLevel(selectedLevel.get());

        this.entityManager = levelManager.getEntityManager();
        this.characterManager = new CharacterManager(entityManager);
        levelManager.addGameCharacterRegistrationCallbacks(characterManager, inputManager);

        createSpawners();

        this.levelGameplayPresenter = new LevelGameplayPresenter(this);
        this.hudPresenter = new HudPresenter(characterManager, levelManager, playerId);
        inputManager.addInputMapping(new InputMapping<>(InputController.keyboardInputDevice().hudInputInputProvider(), hudPresenter));
    }

    @Override
    public void render(float dt) {
        inputManager.handleInputs();
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
            characterManager.registerInputMapping(
                inputManager,
                player.getId(),
                InputController.keyboardInputDevice().characterInputProvider()
            );
            characterManager.addInventoryItem(player.getId(), new Dagger());
            characterManager.addInventoryItem(player.getId(), new Sword());
            this.playerId = player.getId();
        });
        playerSpawner.spawn();

        Spawner<Spike> spikeSpawner = createSpikeSpawner();
        spikeSpawner.setEntityManager(entityManager);
        spikeSpawner.spawn();

        Spawner<?> enemySpawner = createEnemySpawner();
        enemySpawner.setEntityManager(entityManager);
        enemySpawner.addSpawnCallback(enemy ->
            characterManager.registerInputMapping(inputManager, enemy.getId(), InputController.aiInputDevice().characterInputProvider())
        );
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
