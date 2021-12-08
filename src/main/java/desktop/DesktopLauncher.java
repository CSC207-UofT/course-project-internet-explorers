package desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.config.Config;
import core.config.ConfigController;
import core.config.ConfigurableSetting;
import core.presenters.ScreenController;
import core.presenters.debug.Terminal;

public class DesktopLauncher {

    public static class DesktopConfig {

        private static final LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();

        public static final ConfigurableSetting<Boolean> renderGraphics = Config.add(
            Boolean.class,
            "render-graphics",
            "Whether graphics should be rendered.",
            true,
            Boolean::parseBoolean
        );

        public static final ConfigurableSetting<Integer> fps = Config.add(
            Integer.class,
            "fps",
            "FPS limit when game window is active.",
            120,
            () -> lwjglConfig.foregroundFPS,
            value -> lwjglConfig.foregroundFPS = value,
            Integer::parseUnsignedInt
        );

        public static final ConfigurableSetting<Boolean> resizable = Config.add(
            Boolean.class,
            "resizable",
            // TODO change note to (Requires restart) once saving/loading configs is implemented
            "Whether the game window is resizable (Cannot be changed on the fly).",
            false,
            () -> lwjglConfig.resizable,
            value -> lwjglConfig.resizable = value,
            Boolean::parseBoolean
        );

        public static final ConfigurableSetting<Integer> width = Config.add(
            Integer.class,
            "width",
            "Initial width of the game window.",
            1024,
            () -> lwjglConfig.width,
            value -> lwjglConfig.width = value,
            Integer::parseUnsignedInt
        );

        public static final ConfigurableSetting<Integer> height = Config.add(
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
        new LwjglApplication(new ScreenController(), DesktopConfig.lwjglConfig);

        // Start debug terminal
        Terminal debugTerminal = new Terminal();
        ConfigController.useTerminal(debugTerminal);
        new Thread(debugTerminal).start();
    }
}