import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.ScreenManager;
import core.config.Config;
import core.config.ConfigurableSetting;
import core.debug.Terminal;

class DesktopLauncher {

    private static class DesktopConfig {

        private static final LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();

        public final ConfigurableSetting<Integer> fps = new ConfigurableSetting<>(
            Integer.class,
            "fps",
            "FPS limit when game window is active.",
            () -> lwjglConfig.foregroundFPS,
            value -> lwjglConfig.foregroundFPS = value,
            Integer::parseUnsignedInt
        );

        public final ConfigurableSetting<Boolean> resizable = new ConfigurableSetting<>(
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
        Terminal debugTerminal = new Terminal();

        DesktopConfig config = new DesktopConfig();
        config.fps.setValue(120);
        config.resizable.setValue(false);
        DesktopConfig.lwjglConfig.width = ScreenManager.WIDTH;
        DesktopConfig.lwjglConfig.height = ScreenManager.HEIGHT;

        Config.useTerminal(debugTerminal);

        // Launch game window
        new LwjglApplication(new ScreenManager(), DesktopConfig.lwjglConfig);

        // Start debug terminal
        new Thread(debugTerminal).start();
    }
}
