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
            120,
            () -> lwjglConfig.foregroundFPS,
            value -> lwjglConfig.foregroundFPS = value,
            Integer::parseUnsignedInt
        );

        public static final ConfigurableSetting<Boolean> resizable = new ConfigurableSetting<>(
            Boolean.class,
            "resizable",
            // TODO change note to (Requires restart) once saving/loading configs is implemented
            "Whether the game window is resizable (Cannot be changed on the fly).",
            false,
            () -> lwjglConfig.resizable,
            value -> lwjglConfig.resizable = value,
            Boolean::parseBoolean
        );

        public static final ConfigurableSetting<Integer> width = new ConfigurableSetting<>(
            Integer.class,
            "width",
            "Initial width of the game window.",
            1024,
            () -> lwjglConfig.width,
            value -> lwjglConfig.width = value,
            Integer::parseUnsignedInt
        );

        public static final ConfigurableSetting<Integer> height = new ConfigurableSetting<>(
            Integer.class,
            "height",
            "Initial height of the game window.",
            768,
            () -> lwjglConfig.height,
            value -> lwjglConfig.height = value,
            Integer::parseUnsignedInt
        );
    }

    public static void main(String[] arg) {
        // must be set to avoid error on close
        DesktopConfig.lwjglConfig.forceExit = false;

        // Launch game window
        new LwjglApplication(new ScreenManager(), DesktopConfig.lwjglConfig);

        // Start debug terminal
        Terminal debugTerminal = new Terminal();
        ConfigController.useTerminal(debugTerminal);
        new Thread(debugTerminal).start();
    }
}
