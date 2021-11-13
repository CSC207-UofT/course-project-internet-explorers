package core.screens.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Presenter/Controller class that displays inventory and allows the user to choose items in the inventory
 */

public class InventoryWindow extends Window {

    public InventoryWindow() {
        super("Inventory", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 200);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 300);

//        Container<Table> tableContainer = new Container<Table>();
//        tableContainer.setSize(400, 150);
//        tableContainer.fillX();

//        ArrayList<String> playerInventory = characterManager.openInventory();
//
//        if (playerInventory != null){
//            for (String item : playerInventory){
//                this.add(chooseItemButton(item));
//            }
//        }

        Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        Label label0 = new Label("Selected", skin);
        Label label1 = new Label("1", skin);

        Table table = new Table();

        table.pad(10);

        table.add(label0);
        table.add(label1);

        table.row();
        table.add(swordButton("sword", 0)).width(48).height(123).pad(10);
        table.add(swordButton("sword", 1)).width(48).height(123).pad(10);

        this.add(table);
    }

    //TODO: change to item type once we have it
    private ImageButton swordButton(String item, int index) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        if (index == 0){
            style.up = new TextureRegionDrawable(new Texture("items/sword_highlight.png"));
            style.over = new TextureRegionDrawable(new Texture("items/sword_highlight.png"));
        } else {
            style.up = new TextureRegionDrawable(new Texture("items/sword_not_highlight.png"));
            style.over = new TextureRegionDrawable(new Texture("items/sword_not_highlight.png"));
        }
        ImageButton button = new ImageButton(style);

//        button.setPosition(500, 600);

        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        System.out.println("testing button" + index);
                        // TODO: CharacterManager update item to current index
                    }
                }
        );
        return button;
    }
}
