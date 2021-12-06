package core.presenters;

import com.badlogic.gdx.Game;
import core.presenters.menus.MainMenuScreen;

public class ScreenController extends Game {

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
