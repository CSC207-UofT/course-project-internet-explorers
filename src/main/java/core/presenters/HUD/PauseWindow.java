package core.presenters.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.IOException;

public class PauseWindow extends Window {

    HudPresenter hudPresenter;
    public PauseWindow(HudPresenter hudPresenter) {
        super("Pause", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 300);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 300);
        this.add(exitGameButton());
        this.hudPresenter = hudPresenter;
    }

    private TextButton exitGameButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.WHITE;
        TextButton button = new TextButton("Exit Game", style);

        button.addListener(
            new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        hudPresenter.saveState();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gdx.app.exit();
                }
            }
        );
        return button;
    }
}
