package core;

import com.badlogic.gdx.Game;
import core.screens.menus.MainMenuScreen;

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
