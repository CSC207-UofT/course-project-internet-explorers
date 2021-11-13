package core.screens.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import core.characters.CharacterManager;
import core.characters.GameCharacter;

/**
 * Use case class that manages all heads-up-displays
 */

public class HudManager implements Disposable {

    private Stage stage;
    private Viewport viewport;

    //score && time tracking variables
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private boolean timeUp;

    //Labels for displaying game info on the overlay
    private Label countdownLabel, timeLabel, linkLabel;
    private static Label scoreLabel;

    private boolean inventoryIsOpen;
    private InventoryWindow playerInventory;

    private SpriteBatch sb;

    // TODO: add param for player id
    public HudManager() {
        //define tracking variables
        sb = new SpriteBatch();
        // TODO: Get this data from LevelManager
        worldTimer = 250;
        timeCount = 0;
        score = 0;

        // TODO: Get inventory info from CharacterManager instead of instantiating it here
        playerInventory = new InventoryWindow();

        //setup the HUD viewport using a new camera seperate from gamecam
        //define stage using that viewport and games spritebatch

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("LEFTOVER TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        linkLabel = new Label("POINTS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //define a table used to organize hud's labels
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //add labels to table, padding the top, and giving them all equal width with expandX
        table.add(linkLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();


        //add table to the stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    /***
     * Draws the overlay
     */
    public void draw(){
        sb.setProjectionMatrix(getCamera().combined);
        stage.draw();
    }

    /***
     * TODO: use the camera from cameraManager
     * @return the camera used for the HUD
     */
    public Camera getCamera(){
        return stage.getCamera();
    }

    /***
     * Disposes all HUD related elements (e.g. used when you move to a different screen)
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /***
     * Toggles the inventory window by adding or removing it to/from the stage
     */
    public void toggleInventory(){
        if (inventoryIsOpen){
            playerInventory.remove();
        } else {
            stage.addActor(playerInventory);
        }
        inventoryIsOpen = !inventoryIsOpen;
    }

    // These methods are unused at the moment, but may be useful later
//    public boolean isTimeUp() {
//        return timeUp;
//    }
//
//    public static Label getScoreLabel() {
//        return scoreLabel;
//    }
//
//    public static Integer getScore() {
//        return score;
//    }
}
