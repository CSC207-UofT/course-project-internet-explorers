package core.InventorySystem.ItemTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.*;
import core.InventorySystem.Weapon;

import java.util.UUID;

public class Dagger extends Weapon {
    /*
     * Dagger weapon object stored in inventory for use
     * @param name: name of dagger
     * @param texture: Either Player or AI so the input handler can distinguish between usages
     * @param size: Either Player or AI so the input handler can distinguish between usages
     * @param level: Either Player or AI so the input handler can distinguish between usages
     * @param range: Either Player or AI so the input handler can distinguish between usages
     * @param damage: Either Player or AI so the input handler can distinguish between usages
     * @param held: Either Player or AI so the input handler can distinguish between usages
     * */
    private Texture texture = new Texture(Gdx.files.internal("java/resources/weapons/dagger.png"));
    private int size;
    private int level;
    private int range;
    private int damage;
    public UUID id;

    public Dagger(int level) {
        //how many times weapon can be used
        this.level = level;
        this.size = this.level * 1;
        this.range = this.level * 1;
        this.damage = this.level * 2;
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
