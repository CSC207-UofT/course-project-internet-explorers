package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import core.characters.CharacterSprite;

public class SpritesDemoScreen implements Screen {

    private CharacterSprite sprites;

    @Override
    public void show() {
        // The in-game character sprites
        sprites = new CharacterSprite();
        sprites.createCharacter();
    }


    @Override
    public void render(float delta) {
        sprites.renderCharacter();
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
        sprites.dispose();
    }
}
