package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import core.inventory.Item;
import java.util.UUID;

public class Defender implements Item {

    /*
     * Defender item object stored in inventory for use
     * */

    private final UUID id;

    public Defender() {
        this.id = UUID.randomUUID();
    }

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
