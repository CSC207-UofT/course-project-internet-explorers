package core.world;

import com.badlogic.gdx.math.Vector2;

public class WorldEntity {

    protected Vector2 position;

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }
}
