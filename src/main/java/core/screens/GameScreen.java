package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import core.camera.CameraManager;
import core.levels.LevelState;

/**
 * Runs the gameplay of a Level.
 */
public class GameScreen implements Screen {

    private TiledMapRenderer renderer;
    private CameraManager cameraManager;
    private ShapeRenderer shapeRenderer;
    private LevelState level;

    public GameScreen(LevelState level) {
        this.level = level;
    }

    @Override
    public void show() {
        // unitScale measured in m/px
        // represents in-game size of map tiles
        // current conventions
        //  * 1 map tile is 1m by 1m
        //  * tiles are 32px by 32px
        float unitScale = 1 / 32f;

        cameraManager = new CameraManager(unitScale);
        cameraManager.enterDebugFreecamMode();

        shapeRenderer = new ShapeRenderer();

        renderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load(level.getMapPath()), unitScale);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraManager.update(dt);
        renderer.setView(cameraManager.getCamera());

        renderer.render();

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
    public void dispose() {}
}
