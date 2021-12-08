package presenters;

import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import core.config.Config;
import core.presenters.levels.LevelGameplayController;
import desktop.DesktopConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGraphicsToggle {

    static final String graphicsSettingName = "render-graphics";

    private void testRenderGraphicsSetting() {
        Assertions.assertNotNull(DesktopConfig.renderGraphics, "renderGraphics setting does not exist");

        Assertions.assertDoesNotThrow(() -> Config.get(graphicsSettingName), "Graphics rendering setting not found");

        Assertions.assertEquals(true, Config.get(graphicsSettingName), "Couldn't get rendering setting value");

        Assertions.assertDoesNotThrow(() -> Config.set(graphicsSettingName, "false"), "couldn't set rendering setting to 'false'");

        Assertions.assertEquals(false, Config.get(graphicsSettingName), "Rendering setting not actually set to false");
    }

    @Test
    void testRunGameScreenWithoutGraphics() {
        Gdx.gl20 = mock(com.badlogic.gdx.graphics.GL20.class);
        Gdx.gl = Gdx.gl20;

        testRenderGraphicsSetting();

        LevelGameplayController levelGameplayController = new LevelGameplayController();

        Gdx.app =
            new HeadlessApplication(
                new Game() {
                    @Override
                    public void create() {}
                }
            );

        levelGameplayController.show();
        levelGameplayController.render(0.1f);
        levelGameplayController.hide();
        levelGameplayController.dispose();

        Gdx.app.exit();
    }
}
