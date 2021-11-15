package core.screens;

import static core.world.DemoSpawners.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import core.camera.CameraManager;
import core.characters.CharacterManager;
import core.characters.GameCharacter;
import core.input.KeyboardInputDevice;
import core.levels.LevelManager;
import core.levels.LevelState;
import core.screens.HUD.HudManager;
import core.world.Spawner;
import core.world.WorldEntityManager;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Runs the gameplay of a Level.
 */
public class LevelGameplayController implements Screen {

    private final Supplier<LevelState> levelSupplier;
    private CameraManager cameraManager;
    private ShapeRenderer shapeRenderer;
    private LevelManager levelManager;
    private CharacterManager characterManager;
    private WorldEntityManager entityManager;
    private Box2DDebugRenderer box2DDebugRenderer;
    private HudManager hud;

    public LevelGameplayController(Supplier<LevelState> levelSupplier) {
        this.levelSupplier = levelSupplier;
    }

    @Override
    public void show() {
        this.levelManager = new LevelManager(levelSupplier.get());
        this.entityManager = levelManager.getEntityManager();
        this.characterManager = new CharacterManager(entityManager);
        this.cameraManager = new CameraManager(levelManager.getUnitScale(), entityManager);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.hud = new HudManager();

        Spawner<GameCharacter> playerSpawner = createPlayerSpawner();
        //TODO: ^This should be a GameCharacter, but GameCharacter currently extends the wrong world entity
        playerSpawner.setEntityManager(entityManager);
        UUID playerId = playerSpawner.spawn().id;
        characterManager.addCharacter(playerId, new KeyboardInputDevice());

        Spawner<?> enemySpawner = createEnemySpawner();
        enemySpawner.setEntityManager(entityManager);
        enemySpawner.spawn();

        Spawner<?> defenderSpawner = createDefenderSpawner();
        defenderSpawner.setEntityManager(entityManager);
        defenderSpawner.spawn();

        Spawner<?> mapBorderSpawner = createMapBorderSpawner();
        mapBorderSpawner.setEntityManager(entityManager);
        mapBorderSpawner.spawn();

        cameraManager.setSubjectID(playerId);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        characterManager.processInputs(dt);
        levelManager.step(dt);

        cameraManager.update(dt);

        levelManager.renderMap(cameraManager.getCamera());
        levelManager.renderWorld(cameraManager.getCamera());

        // TODO move following code to the CameraManager
        // draw a red dot which marks the spot tracked by the camera (for debugging)
        shapeRenderer.setProjectionMatrix(cameraManager.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(cameraManager.getSubjectPosition().x, cameraManager.getSubjectPosition().y, 0.1f, 16);
        shapeRenderer.end();

        // TODO only do this in debug mode
        levelManager.renderPhysics(box2DDebugRenderer, cameraManager.getCamera());

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            hud.toggleInventory();
        }

        hud.draw();
    }

    @Override
    public void resize(int width, int height) {
        cameraManager.syncCameraViewportToWindow();
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
        hud.dispose();
    }
}
