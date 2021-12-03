package core.presenters.levels;

import static core.worldEntities.DemoSpawners.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import core.input.AIInputDevice;
import core.input.InputController;
import core.input.KeyboardInputDevice;
import core.inventory.Item;
import core.inventory.items.Dagger;
import core.inventory.items.Sword;
import core.levels.LevelManager;
import core.levels.LevelState;
import core.presenters.HUD.HudPresenter;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Runs the gameplay of a Level.
 */
public class LevelGameplayPresenter implements Screen {

    private final Supplier<LevelState> levelSupplier;
    private CameraManager cameraManager;
    private ShapeRenderer shapeRenderer;
    private LevelManager levelManager;
    private CharacterManager characterManager;
    private WorldEntityManager entityManager;
    private Box2DDebugRenderer box2DDebugRenderer;
    private HudPresenter hud;
    private InputController inputController;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SpriteBatch batch;

    public LevelGameplayPresenter(Supplier<LevelState> levelSupplier) {
        this.levelSupplier = levelSupplier;
    }

    @Override
    public void show() {
        this.levelManager = new LevelManager(levelSupplier.get());
        this.entityManager = levelManager.getEntityManager();
        this.characterManager = new CharacterManager(entityManager);

        levelManager.addGameCharacterRegistrationCallbacks(characterManager);

        this.cameraManager = new CameraManager(levelManager.getUnitScale(), entityManager);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.mapRenderer = new OrthogonalTiledMapRenderer(levelManager.getMap(), levelManager.getLevelUnitScale());
        this.batch = new SpriteBatch();

        Spawner<Character> playerSpawner = createPlayerSpawner();
        playerSpawner.setEntityManager(entityManager);
        UUID playerId = playerSpawner.spawn().id;
        characterManager.addCharacter(playerId, KeyboardInputDevice.class);

        Item sword = new Sword(1);
        Item dagger = new Dagger(1);

        characterManager.addInventory(playerId, sword);
        characterManager.addInventory(playerId, dagger);

        this.hud = new HudPresenter(this.characterManager, this.levelManager, playerId);
        this.inputController = new InputController(characterManager, hud, levelManager);

        Spawner<?> enemySpawner = createEnemySpawner();
        enemySpawner.setEntityManager(entityManager);
        UUID enemyId = enemySpawner.spawn().id;
        characterManager.addCharacter(enemyId, AIInputDevice.class);

        Spawner<?> defenderSpawner = createDefenderSpawner();
        defenderSpawner.setEntityManager(entityManager);
        defenderSpawner.spawn();

        Spawner<?> mapBorderSpawner = createMapBorderSpawner();
        mapBorderSpawner.setEntityManager(entityManager);
        mapBorderSpawner.spawn();

        cameraManager.setSubjectID(playerId);
    }

    public void renderMap() {
        mapRenderer.setView(cameraManager.getCamera());
        mapRenderer.render();
    }

    public void renderWorld() {
        batch.setProjectionMatrix(cameraManager.getCamera().combined);
        batch.begin();
        entityManager.draw(batch);
        batch.end();
    }

    /**
     * Invoke `render` on a Box2DDebugRenderer to draw the physics going on in this world.
     * Used for debugging.
     */
    public void renderPhysics() {
        box2DDebugRenderer.render(levelManager.getWorld(), cameraManager.getCamera().combined);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        inputController.handleInputs(dt);
        levelManager.step(dt);

        cameraManager.update(dt);

        renderMap();
        renderWorld();

        // TODO move following code to the CameraManager
        // draw a red dot which marks the spot tracked by the camera (for debugging)
        shapeRenderer.setProjectionMatrix(cameraManager.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(cameraManager.getSubjectPosition().x, cameraManager.getSubjectPosition().y, 0.1f, 16);
        shapeRenderer.end();

        // TODO only do this in debug mode
        renderPhysics();

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
