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
import core.characters.CharacterSprite;

public class GameScreen implements Screen {

    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private CharacterSprite sprites;
    private float x;
    private float y;

    public GameScreen() {}

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        sprites = new CharacterSprite();
        sprites.createCharacter();

        renderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("basic.tmx"), 0.5f);
        x = 0;
        y = 0;
        camera.position.set(x, y, 1f);
        camera.update();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprites.renderCharacter();
        handleUserInput();
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    private void handleUserInput() {
        float inc = 10 * camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= inc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += inc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= inc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
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
    public void dispose() {}
}
