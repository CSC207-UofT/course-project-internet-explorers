package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.inventory.Weapon;
import java.util.UUID;

public class Dagger extends Weapon {

    /*
     * Dagger weapon object stored in inventory for use
     * @param texture: texture of Dagger
     * @param size: size of Dagger
     * @param level: level of Dagger
     * @param range: range of Dagger
     * @param damage: damage of Dagger
     * */

    private final int size;
    private int level;
    private final int range;
    private final int damage;
    public UUID id;
    static final int DAGGER_DAMAGE = 2;
    static final int DAGGER_RANGE = 1;

    public Dagger(int level) {
        this.level = level;
        this.size = this.level;
        this.range = this.level * DAGGER_RANGE;
        this.damage = this.level * DAGGER_DAMAGE;
        this.id = UUID.randomUUID();
    }

    @Override
    public Texture getSelectedTexture() {
        String texturePathSelected = "items/dagger_highlight.png";
        return new Texture(texturePathSelected);
    }

    @Override
    public Texture getUnselectedTexture() {
        String texturePathNotSelected = "items/dagger_not_highlight.png";
        return new Texture(texturePathNotSelected);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int new_level) {
        this.level = new_level;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public ImageButton createInventorySlot(int index) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        if (index == 0) {
            style.up = new TextureRegionDrawable(this.getSelectedTexture());
        } else {
            style.up = new TextureRegionDrawable(this.getUnselectedTexture());
        }

        return new ImageButton(style);
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
