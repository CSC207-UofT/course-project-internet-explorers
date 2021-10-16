package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.screens.MainMenuScreen;
import game.sprites.CharacterSprite;
import game_characters.*;

import java.util.ArrayList;

public class GdxGame extends Game {
    public SpriteBatch batch;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    // The in-game character sprites
    CharacterSprite sprites = new CharacterSprite();

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));


        sprites.createCharacter();

    }

    @Override
    public void render() {
        super.render();
        sprites.renderCharacter();
    }

    @Override
    public void dispose() {

    }


}
