package core.InventorySystem.ItemTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.InventorySystem.Weapon;
import java.util.UUID;

public class Sword extends Weapon {

    /*
     * Sword weapon object stored in inventory for use
     * @param texture: texture of Sword
     * @param size: size of Sword
     * @param level: level of Sword
     * @param range: range of Sword
     * @param damage: damage of Sword
     * */

    private final String texturePathSelected = "items/sword_highlight.png";
    private final String texturePathNotSelected = "items/sword_not_highlight.png";

    private int size;
    private int level;
    private int range;
    private int damage;
    public UUID id;

    public Sword(int level) {
        this.level = level;
        this.size = this.level * 1;
        this.range = this.level * 2;
        this.damage = this.level * 3;
        this.id = UUID.randomUUID();
    }

    @Override
    public Texture getSelectedTexture() { return new Texture(texturePathSelected); }

    @Override
    public Texture getUnselectedTexture() {
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
    public int getDamage() {
        return damage;
    }

    public ImageButton createInventorySlot(int index) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        if (index == 0) {
            style.up = new TextureRegionDrawable(this.getSelectedTexture());
        } else {
            style.up = new TextureRegionDrawable(this.getUnselectedTexture());
        }
        return new ImageButton(style);
    }
}
