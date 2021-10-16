package core.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.GdxGame;
import core.screens.GameScreen;

public class MainMenuScreen extends Menu {

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_X = GdxGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_X = GdxGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
    private static final int PLAY_BUTTON_Y = 300;

    public MainMenuScreen(GdxGame game) {
        super(game);
        stage.addActor(createPlayButton());
        stage.addActor(createExitButton());
    }

    private ImageButton createPlayButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/play_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/play_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(PLAY_BUTTON_X, PLAY_BUTTON_Y);
        button.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        button.addListener(
            new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new GameScreen());
                }
            }
        );

        return button;
    }

    private ImageButton createExitButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/exit_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/exit_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(EXIT_BUTTON_X, EXIT_BUTTON_Y);
        button.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        button.addListener(
            new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.exit();
                }
            }
        );

        return button;
    }
}
