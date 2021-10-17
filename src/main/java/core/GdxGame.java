package core;

import com.badlogic.gdx.Game;
import core.characters.CharacterSprite;
import core.characters.GameCharacter;
import core.screens.menus.MainMenuScreen;

import java.util.ArrayList;

public class GdxGame extends Game {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    @Override
    public void create() {
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {}
}
