package core.InventorySystem.ItemTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    private Texture texture = new Texture(Gdx.files.internal("java/resources/weapons/sword.png"));
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
    public Texture getTexture() {
        return texture;
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
