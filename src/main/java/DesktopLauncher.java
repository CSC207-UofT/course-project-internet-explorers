import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.ScreenManager;

class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 60;
        config.width = ScreenManager.WIDTH;
        config.height = ScreenManager.HEIGHT;
        config.resizable = false;
        new LwjglApplication(new ScreenManager(), config);
    }
}