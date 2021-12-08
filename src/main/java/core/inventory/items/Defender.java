package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import core.inventory.Item;
import core.inventory.Weapon;
import core.worldEntities.health.Damage;
import java.util.UUID;

// TODO extend Item, add own UsageDelegate and fix Weapon usage delegate
public class Defender implements Item {

    /*
     * Defender item object stored in inventory for use
     * */

    public UUID id;

    @Override
    public Texture getSelectedTexture() {
        String texturePathSelected = "items/defender_highlight.png";
        return new Texture(texturePathSelected);
    }

    @Override
    public Texture getUnselectedTexture() {
        String texturePathNotSelected = "items/defender_not_highlight.png";
        return new Texture(texturePathNotSelected);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

}
