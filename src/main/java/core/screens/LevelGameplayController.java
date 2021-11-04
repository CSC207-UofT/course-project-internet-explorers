package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import core.camera.CameraManager;
import core.levels.LevelManager;
import core.world.WorldEntity;
import core.world.WorldManager;

/**
 * Runs the gameplay of a Level.
 */
public class LevelGameplayController implements Screen {

    private final CameraManager cameraManager;
    private ShapeRenderer shapeRenderer;
    private final LevelManager levelManager;
    private final WorldEntity player;

    public LevelGameplayController(LevelManager levelManager) {
        this.levelManager = levelManager;
        cameraManager = new CameraManager(levelManager.getUnitScale());
        WorldManager wm = levelManager.getWorldManager();

        // TODO handle spawning the player via the spawn system once implemented (line 35, not fully figured out yet)
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(new Vector2(2, 2));
        // levelManager.spawnEntity(playerDef, some-spawn-controller);

        player = wm.createEntity(playerDef);

        player.setSize(new Vector2(2, 2));
        player.setOffset(player.getSize().cpy().scl(-.5f));
        player.setSprite(new TextureAtlas("characters/sprites.txt").createSprite("demo_player"));

        // not the proper way to control stuff on-screeen, this is just for debugging
        // TODO remove following line – player position should be mutated by the PlayerManager
        //      should pass in the Player WorldEntity's ID and the WorldEntity Manager once ID-based system is implemented
        cameraManager.enterDebugFreecamMode();
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraManager.update(dt);
        // TODO change to proper system, currently snap player position to freecam subject pos (allowing arrow key control)
        player.getBody().setTransform(cameraManager.getSubjectPosition(), 0);

        levelManager.step(dt);
        levelManager.renderMap(cameraManager.getCamera());
        levelManager.renderWorld(cameraManager.getCamera());

        // TODO move following code to the CameraManager
        // draw a red dot which marks the spot tracked by the camera (for debugging)
        shapeRenderer.setProjectionMatrix(cameraManager.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(cameraManager.getSubjectPosition().x, cameraManager.getSubjectPosition().y, 0.1f, 16);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        cameraManager.syncCameraViewportToWindow();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        levelManager.dispose();
    }
}
