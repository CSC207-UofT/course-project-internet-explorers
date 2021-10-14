package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.GdxGame;

class DesktopLauncher {



    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 60;
        config.width = GdxGame.WIDTH;
        config.height = GdxGame.HEIGHT;
        config.resizable = false;
        new LwjglApplication(new GdxGame(), config);
    }
}
