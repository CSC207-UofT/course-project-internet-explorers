package core.presenters.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import core.config.Config;
import core.levels.LevelLoader;

public class LevelSelectWindow extends Window {

    public LevelSelectWindow() {
        super("Level_Select", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 300);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 100);
        Table table = new Table();
        table.pad(10);
        table.add(levelButton(1)).pad(10);
        table.add(levelButton(2)).pad(10);
        table.add(levelButton(3)).pad(10);

        table.row();

        table.add(deleteButton(1)).pad(10);
        table.add(deleteButton(2)).pad(10);
        table.add(deleteButton(3)).pad(10);

        this.add(table);
    }

    /**
     * Creates a button that sets the level you want to play.
     * @param level the level that the button corresponds to.
     * @return the button that was created
     */
    private TextButton levelButton(int level) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.WHITE;
        TextButton button = new TextButton("Level " + level, style);

        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed (ChangeEvent event, Actor actor) {
                    Config.set("selected-level", "Level " + level );
                    close();

                }
            }
        );
        return button;
    }

    /**
     * Calls the delete level method when pressed
     * @param level this level's save file is deleted
     * @return the button
     */
    private TextButton deleteButton(int level) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.WHITE;
        TextButton button = new TextButton("Clear Save", style);

        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed (ChangeEvent event, Actor actor) {
                        LevelLoader.DeleteLevel("Level " + level);
                        System.out.println("Level " + level + " save has been cleared.");
                    }
                }
        );
        return button;
    }

    private void close(){
        super.remove();
    }
}
