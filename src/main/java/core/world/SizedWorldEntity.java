package core.world;

import com.badlogic.gdx.math.Vector2;

public class SizedWorldEntity extends WorldEntity {

    protected Vector2 size;

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
    }
}
