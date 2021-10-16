package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import core.GdxGame;

public class GameScreen implements Screen {

    private float zoom;
    private float physicsStep;
    private float cameraX;
    private float cameraY;

    Texture img;
    AssetManager assetManager;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private float x;
    private float y;

    GdxGame game;

    Texture exitButtonActive;
    Texture exitButtonInActive;
    Texture playButtonActive;
    Texture playButtonInActive;

    public GameScreen(GdxGame game) {
        this.game = game;
        playButtonActive = new Texture("tiles/grass.png");
        playButtonInActive = new Texture("tiles/path.png");
        exitButtonActive = new Texture("tiles/grass.png");
        exitButtonInActive = new Texture("tiles/wall.png");
    }

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        img = new Texture("tiles/grass.png");
        renderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("basic.tmx"), 0.5f);
        x = 0;
        y = 0;
        camera.position.set(x, y, 1f);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleUserInput();
        camera.update();

        renderer.setView(camera);
        renderer.render();
    }

    private void handleUserInput() {
        float inc = 10 * camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= inc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += inc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= inc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += inc;
        }

        camera.translate((x * 2f - camera.position.x) * 0.1f, (y * 2f - camera.position.y) * 0.1f);

        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom > 0.2f) {
            camera.zoom *= 0.95f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.L) && camera.zoom < 3f) {
            camera.zoom /= 0.95f;
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.batch.dispose();
        img.dispose();
    }
}
