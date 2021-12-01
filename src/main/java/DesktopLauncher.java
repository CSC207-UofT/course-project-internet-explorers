import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.presenters.ScreenController;

class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 120;
        config.width = ScreenController.WIDTH;
        config.height = ScreenController.HEIGHT;
        config.resizable = false;
        config.forceExit = false;
        new LwjglApplication(new ScreenController(), config);
    }
}
