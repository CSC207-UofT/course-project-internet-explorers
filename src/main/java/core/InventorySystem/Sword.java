package core.InventorySystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.*;

public class Sword extends Weapon {
    private String name;
    private Texture texture = new Texture(Gdx.files.internal("java/resources/weapons/sword.png"));
    private String meta;
    private int size = 2;
    private String shape = "Rectangle";
    private int level;
    private int range = 2;
    private int damage;
    private boolean held = false;

    public Sword(String name, String meta, int level, int damage) {
        this.name = name;
        this.meta = meta;
        //how many times weapon can be used
        this.level = level;
        this.damage = damage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public String getMeta() {
        return meta;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getShape() {
        return shape;
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
    public int getDamage() {
        return damage;
    }
}
