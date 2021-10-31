package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class WorldEntity {

    protected Vector2 position;
    protected Vector2 size;

    public WorldEntity() {
        this.position = new Vector2();
        this.size = new Vector2();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
    }
}
