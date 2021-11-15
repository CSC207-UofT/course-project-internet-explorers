package core.InventorySystem.ItemTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.Texture;
import core.InventorySystem.Weapon;
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

    private final String texturePath = "weapons/dagger.png";
    private int size;
    private int level;
    private int range;
    private int damage;
    public UUID id;

    public Dagger(int level) {
        this.level = level;
        this.size = this.level * 1;
        this.range = this.level * 1;
        this.damage = this.level * 2;
        this.id = UUID.randomUUID();
    }

    @Override
    public Texture getTexture() {
        return new Texture(texturePath);
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
}
