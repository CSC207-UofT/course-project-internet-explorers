import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.config.ConfigController;
import core.config.ConfigurableSetting;
import core.presenters.ScreenManager;
import core.presenters.debug.Terminal;

class DesktopLauncher {

    private static class DesktopConfig {

        private static final LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();

        public static final ConfigurableSetting<Integer> fps = new ConfigurableSetting<>(
            Integer.class,
            "fps",
            "FPS limit when game window is active.",
            () -> lwjglConfig.foregroundFPS,
            value -> lwjglConfig.foregroundFPS = value,
            Integer::parseUnsignedInt
        );

        public static final ConfigurableSetting<Boolean> resizable = new ConfigurableSetting<>(
            Boolean.class,
            "resizable",
            // TODO change note to (Requires restart) once saving/loading configs is implemented
            "Whether the game window is resizable (Cannot be changed on the fly).",
            () -> lwjglConfig.resizable,
            value -> lwjglConfig.resizable = value,
            Boolean::parseBoolean
        );
    }

    public static void main(String[] arg) {
        // Configure app
        DesktopConfig.fps.set(120);
        DesktopConfig.resizable.set(false);
        DesktopConfig.lwjglConfig.width = ScreenManager.WIDTH;
        DesktopConfig.lwjglConfig.height = ScreenManager.HEIGHT;
        DesktopConfig.lwjglConfig.forceExit = false;

        // Launch game window
        new LwjglApplication(new ScreenManager(), DesktopConfig.lwjglConfig);

        // Start debug terminal
        Terminal debugTerminal = new Terminal();
        ConfigController.useTerminal(debugTerminal);
        new Thread(debugTerminal).start();
    }
}
