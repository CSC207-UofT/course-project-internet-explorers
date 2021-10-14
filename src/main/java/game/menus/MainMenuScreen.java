package game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import game.GdxGame;

public class MainMenuScreen implements Screen{

     GdxGame game;

     Texture exitButtonActive;
     Texture exitButtonInActive;
     Texture playButtonActive;
     Texture playButtonInActive;

    public MainMenuScreen (GdxGame game){
        this.game = game;
        playButtonActive = new Texture("grass.png");
        playButtonInActive = new Texture("path.png");
        exitButtonActive = new Texture("grass.png");
        exitButtonInActive = new Texture("wall.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(exitButtonActive, 100, 100);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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