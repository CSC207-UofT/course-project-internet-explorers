package core.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class WorldEntityManager {

    private WorldEntity entity;

    public void addToWorld(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(entity.getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        addToWorld(world, bodyDef);
    }

    public void addToWorld(World world, BodyDef bodyDef) {
        Body body = world.createBody(bodyDef);
        body.setUserData(entity);
        entity.position = body.getPosition();
    }
}
