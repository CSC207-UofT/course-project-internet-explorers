package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.inventory.Weapon;
import java.util.UUID;

public class Sword extends Weapon {

    /*
     * Sword weapon object stored in inventory for use
     * @param texture: texture of Sword
     * @param level: level of Sword
     * @param range: range of Sword
     * @param damage: damage of Sword
     * */

    private int level;
    private int range;
    private int damage;
    public UUID id;
    static final int DAMAGE_PER_LEVEL = 3;
    static final int RANGE_PER_LEVEL = 2;

    public Sword() {
        this(Weapon.DEFAULT_LEVEL);
    }

    public Sword(int level) {
        setLevel(level);
        this.id = UUID.randomUUID();
    }

    @Override
    public Texture getSelectedTexture() {
        String texturePathSelected = "items/sword_highlight.png";
        return new Texture(texturePathSelected);
    }

    @Override
    public Texture getUnselectedTexture() {
        String texturePathNotSelected = "items/sword_not_highlight.png";
        return new Texture(texturePathNotSelected);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        this.range = level * RANGE_PER_LEVEL;
        this.damage = level * DAMAGE_PER_LEVEL;
    }

    @Override
    public int getLevel() {
        return level;
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
