package core.presenters.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import core.inventory.Item;
import core.worldEntities.types.characters.CharacterManager;
import java.util.ArrayList;
import java.util.UUID;

public class InventoryWindow extends Window {

    private final ArrayList<Item> playerInventory;
    private final UUID playerId;
    private final CharacterManager characterManager;

    public InventoryWindow(CharacterManager characterManager, UUID playerId) {
        super("Inventory", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 200);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 100);

        playerInventory = characterManager.openInventory(playerId);
        this.playerId = playerId;
        this.characterManager = characterManager;
        updateInventoryWindow();
    }

    private void updateInventoryWindow() {
        this.clear();

        Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        Label label0 = new Label("Selected", skin);
        Table table = new Table();
        table.pad(10);
        table.add(label0);

        if (playerInventory != null) {
            for (int i = 1; i < playerInventory.size(); i++) {
                Label label = new Label(String.valueOf(i), skin);
                table.add(label);
            }

            table.row();

            for (int i = 0; i < playerInventory.size(); i++) {
                Item item = playerInventory.get(i);

                ImageButton button = item.createInventorySlot(i);

                button.addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            characterManager.selectItem(playerId, item);
                            updateInventoryWindow();
                        }
                    }
                );

                table.add(button).width(48).height(123).pad(10);
            }
        }
        this.add(table);
    }
}
