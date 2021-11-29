package core.worldEntities.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import core.worldEntities.WorldEntity;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        WorldEntity a = (WorldEntity) contact.getFixtureA().getBody().getUserData();
        WorldEntity b = (WorldEntity) contact.getFixtureB().getBody().getUserData();

        if (a instanceof HasCollisionBehaviour participantA) {
            for (CollisionBehaviour<?, ?> collisionBehaviour : participantA.getCollisionBehaviour()) {
                collisionBehaviour.doCollisionBehaviourIfNecessary(a, b);
            }
        }

        if (b instanceof HasCollisionBehaviour participantB) {
            for (CollisionBehaviour<?, ?> collisionBehaviour : participantB.getCollisionBehaviour()) {
                collisionBehaviour.doCollisionBehaviourIfNecessary(b, a);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
