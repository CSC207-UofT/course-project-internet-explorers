package core.presenters;

import com.badlogic.gdx.Game;
import core.presenters.menus.MainMenuScreen;

public class ScreenManager extends Game {

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