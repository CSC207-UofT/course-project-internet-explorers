package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.inventory.ItemUsageDelegate;
import core.inventory.Weapon;
import core.worldEntities.health.Damage;

import java.util.UUID;

public class Dagger extends Weapon {

    /*
     * Dagger weapon object stored in inventory for use
     * @param texture: texture of Dagger
     * @param size: size of Dagger
     * @param level: level of Dagger
     * @param range: range of Dagger
     * @param damage: damage of Dagger
     * @param
     * */

    private final int size;
    private int level;
    private final int range;
    private final Damage damage;
    public UUID id;

    public Dagger(int level) {
        this.level = level;
        this.size = this.level;
        this.range = this.level;
        this.damage = new Damage(this.level * 2, null);
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
    public ItemUsageDelegate getUsageDelegate() {
        return null;
    }

    @Override
    public Damage getDamage() {
        return damage;
    }
}
