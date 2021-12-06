package presenters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import core.config.Config;
import core.presenters.levels.LevelGameplayController;
import desktop.DesktopLauncher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestGraphicsToggle {

    static final String graphicsSettingName = "render-graphics";

    @BeforeAll
    static void ensureCanSetGraphicsFalse() {
        Assertions.assertDoesNotThrow(() -> Config.get(graphicsSettingName), "Graphics rendering setting not found");

        Assertions.assertEquals(true, Config.get(graphicsSettingName), "Couldn't get rendering setting value");

        Assertions.assertDoesNotThrow(() -> Config.set(graphicsSettingName, "false"), "couldn't set rendering setting to 'false'");

        Assertions.assertEquals(false, Config.get(graphicsSettingName), "Rendering setting not actually set to false");
    }

    @Test
    void testRunDesktopLauncherWithoutGraphics() {
        Assertions.assertDoesNotThrow(() -> DesktopLauncher.main(new String[] {}), "Error thrown when app launched from DesktopController");
    }

    @Test
    void testRunGameScreenWithoutGraphics() {
        Assertions.assertDoesNotThrow(
            () -> {
                new LwjglApplication(
                    new Game() {
                        @Override
                        public void create() {
                            this.setScreen(new LevelGameplayController());
                        }
                    }
                );
            },
            "Error thrown when app launched from DesktopController"
        );
    }
}
