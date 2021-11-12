package core.InventorySystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.UUID;

public class Sword extends Weapon { //TODO make Dagger and Sword in sub-package for item_types
    private Texture texture = new Texture(Gdx.files.internal("java/resources/weapons/sword.png"));
    private int size;
    private int level;
    private int range;
    private int damage;
    public UUID id;
    private boolean held = false;

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
    public Boolean getHeld() {return held;}

    @Override
    public void setHeld() {held = !held;}

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
