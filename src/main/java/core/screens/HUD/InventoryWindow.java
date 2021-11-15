package core.screens.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class InventoryWindow extends Window {

    public InventoryWindow() {
        super("Inventory", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 300);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 300);
        this.add(createSpriteDemoButton());
    }

    private TextButton createSpriteDemoButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.BLACK;
        TextButton button = new TextButton("Sprites Demo", style);

        button.setPosition(500, 600);

        button.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("testing inv button");
                }
            }
        );
        return button;
    }
}
