package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.characters.CharacterSprite;
import core.characters.GameCharacter;
import core.screens.menus.MainMenuScreen;

import java.util.ArrayList;

public class ScreenManager extends Game {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public SpriteBatch batch;

    @Override
    public void create() {

        this.batch = new SpriteBatch();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {}
}
