package core.InventorySystem;

import java.util.ArrayList;

public class Sword extends Weapon {
    private String name;
    private String texture;
    private String meta;
    private int size;
    private String shape;
    private int level;
    private int range;
    private int damage;

    public Sword(String name, String texture, String meta, int size, String shape, int level, int range, int damage) {
        this.name = name;
        this.texture = texture;
        this.meta = meta;
        this.size = size;
        this.shape = shape;
        this.level = level;
        this.range = range;
        this.damage = damage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTexture() {
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
    public int getLevel() {
        return level;
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
