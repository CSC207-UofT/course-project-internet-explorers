import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.ScreenManager;
import core.debug.Terminal;

class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 120;
        config.width = ScreenManager.WIDTH;
        config.height = ScreenManager.HEIGHT;
        config.resizable = false;

        // Launch game window
        new LwjglApplication(new ScreenManager(), config);

        // Start debug terminal
        new Thread(new Terminal()).start();
    }
}
