package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import core.inventory.Weapon;
import core.worldEntities.health.Damage;
import java.util.UUID;

public class Sword extends Weapon {

    /*
     * Sword weapon object stored in inventory for use
     * @param texture: texture of Sword
     * @param level: level of Sword
     * @param range: range of Sword
     * @param damage: damage of Sword
     * */

    private int range;
    private Damage damage;
    public final UUID id;
    static final int DAMAGE_PER_LEVEL = 3;
    static final int RANGE_PER_LEVEL = 2;

    public Sword(int level) {
        setLevel(level);
        this.id = UUID.randomUUID();
    }

    @SuppressWarnings("unused") // used dynamically
    public Sword() {
        this(Weapon.DEFAULT_LEVEL);
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
        super.setLevel(level);
        this.range = getLevel() * RANGE_PER_LEVEL;
        this.damage = new Damage(getLevel() * DAMAGE_PER_LEVEL, null);
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Damage getDamage() {
        return damage;
    }
}
