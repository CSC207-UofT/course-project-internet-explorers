package core.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.ScreenManager;
import core.screens.LevelGameplayController;
import core.screens.SpritesDemoScreen;

public class MainMenuScreen extends Menu {

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_X = ScreenManager.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_X = ScreenManager.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
    private static final int PLAY_BUTTON_Y = 300;
    private final ScreenManager game;

    public MainMenuScreen(ScreenManager game) {
        super(game);
        this.game = game;
        stage.addActor(createSpriteDemoButton());
        stage.addActor(createPlayButton());
        stage.addActor(createExitButton());
    }

    private TextButton createSpriteDemoButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.BLACK;
        TextButton button = new TextButton("Sprites Demo", style);

        button.setPosition(500, 600);

        button.addListener(
            new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new SpritesDemoScreen());
                    MainMenuScreen.super.dispose();
                }
            }
        );
        return button;
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
                    game.setScreen(new LevelGameplayController(game));
                    MainMenuScreen.super.dispose();
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
