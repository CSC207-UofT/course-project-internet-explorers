package desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import core.config.ConfigController;
import core.presenters.ScreenController;
import core.presenters.debug.Terminal;

public class DesktopLauncher {

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
