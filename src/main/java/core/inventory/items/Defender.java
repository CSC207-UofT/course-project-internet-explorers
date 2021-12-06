package core.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import core.inventory.Weapon;
import core.worldEntities.health.Damage;
import java.util.UUID;

// TODO extend Item, add own UsageDelegate and fix Weapon usage delegate
public class Defender extends Weapon {

    /*
     * Defender weapon object stored in inventory for use
     * @param texture: texture of Defender
     * @param level: level of Defender
     * @param range: range of Defender
     * @param damage: damage of Defender
     * */

    private int level;
    private int range;
    private Damage damage;
    public UUID id;
    static final int DAMAGE_PER_LEVEL = 4;
    static final int RANGE_PER_LEVEL = 2;

    public Defender() {
        this(Weapon.DEFAULT_LEVEL);
    }

    public Defender(int level) {
        setLevel(level);
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
    public void setLevel(int level) {
        this.level = level;
        this.range = level * RANGE_PER_LEVEL;
        this.damage = new Damage(level * DAMAGE_PER_LEVEL, null);
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
    public Damage getDamage() {
        return damage;
    }
}
