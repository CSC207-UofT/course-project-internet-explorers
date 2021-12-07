package presenters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
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
        Assertions.assertNotNull(DesktopLauncher.DesktopConfig.renderGraphics, "renderGraphics setting does not exist");

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
        LevelGameplayController levelGameplayController = new LevelGameplayController();

        new HeadlessApplication(
            new Game() {
                @Override
                public void create() {
                    this.setScreen(levelGameplayController);
                }
            }
        );

        levelGameplayController.show();
        levelGameplayController.hide();
        levelGameplayController.dispose();
    }
}
