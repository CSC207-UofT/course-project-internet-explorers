package core.world;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents an entity within a level's World.
 * <p>
 * A WorldEntity has a position and a rectangular bounding box,
 * expressed as its dimensions and that box's offset relative
 * to the position.
 */
public class WorldEntity {

    /**
     * The position of this WorldEntity's origin.
     */
    protected Vector2 position;
    /**
     * The x and y dimensions of this WorldEntity.
     */
    protected Vector2 size;
    /**
     * The offset between this WorldEntity's origin and one of its corners.
     * <p>
     * Two opposing corners of this WorldEntity are
     * position + offset
     * position + offset + size
     */
    protected Vector2 offset;

    public WorldEntity() {
        this.position = new Vector2();
        this.size = new Vector2();
        this.offset = new Vector2();
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getSize() {
        return size.cpy();
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
    }

    public Vector2 getOffset() {
        return offset.cpy();
    }

    public void setOffset(Vector2 offset) {
        this.offset.set(offset);
    }
}
