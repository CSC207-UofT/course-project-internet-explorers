package core.presenters.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.presenters.ScreenController;
import core.presenters.levels.LevelGameplayController;

public class MainMenuScreen extends Menu {

    private static final int EXIT_BUTTON_WIDTH = 160;
    private static final int EXIT_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_WIDTH = 160;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int LEVEL_BUTTON_WIDTH = 400;
    private static final int LEVEL_BUTTON_HEIGHT = 100;
    private static final int HOW_TO_PLAY_BUTTON_WIDTH = 450;
    private static final int HOW_TO_PLAY_BUTTON_HEIGHT = 100;
    private static final int EXIT_BUTTON_X = Gdx.graphics.getWidth() / 2 - EXIT_BUTTON_WIDTH / 2;
    private static final int EXIT_BUTTON_Y = 80;
    private static final int PLAY_BUTTON_X = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
    private static final int PLAY_BUTTON_Y = 440;
    private static final int LEVEL_BUTTON_X = Gdx.graphics.getWidth() / 2 - LEVEL_BUTTON_WIDTH / 2;
    private static final int LEVEL_BUTTON_Y = 320;
    private static final int HOW_TO_PLAY_BUTTON_X = Gdx.graphics.getWidth() / 2 - HOW_TO_PLAY_BUTTON_WIDTH / 2;
    private static final int HOW_TO_PLAY_BUTTON_Y = 200;
    private LevelSelectWindow levelselectwindow;

    public MainMenuScreen(ScreenController screenController) {
        super(screenController);
    }

    @Override
    public void show() {
        //        stage.addActor(createSpriteDemoButton());
        super.show();
        stage.addActor(createPlayButton());

        // todo try/catch in loader
        stage.addActor(createLevelButton());
        stage.addActor(createHowToPlayButton());
        stage.addActor(createExitButton());
        levelselectwindow = new LevelSelectWindow();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    private ImageButton createPlayButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/play_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/play_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(PLAY_BUTTON_X, PLAY_BUTTON_Y);
        button.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        button.addListener(createExitButtonListener(new LevelGameplayController()));

        return button;
    }

    private ImageButton createLevelButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/level_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/level_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(LEVEL_BUTTON_X, LEVEL_BUTTON_Y);
        button.setSize(LEVEL_BUTTON_WIDTH, LEVEL_BUTTON_HEIGHT);

        button.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stage.addActor(levelselectwindow);
                }
            }
        );

        return button;
    }

    private ImageButton createHowToPlayButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/how_to_play_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/how_to_play_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(HOW_TO_PLAY_BUTTON_X, HOW_TO_PLAY_BUTTON_Y);
        button.setSize(HOW_TO_PLAY_BUTTON_WIDTH, HOW_TO_PLAY_BUTTON_HEIGHT);

        button.addListener(createExitButtonListener(new HowToPlayScreen(screenController, new MainMenuScreen(screenController))));

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
