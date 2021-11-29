package core.worldEntities.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import core.worldEntities.WorldEntity;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof HasCollisionBehaviour<?> participantA) {
            (participantA.getCollisionBehaviour()).forEach(collisionBehaviour ->
                    collisionBehaviour.doCollisionBehaviourIfNecessary((WorldEntity) a, (WorldEntity) b)
                );
        }

        if (b instanceof HasCollisionBehaviour<?> participantB) {
            participantB
                .getCollisionBehaviour()
                .forEach(collisionBehaviour -> collisionBehaviour.doCollisionBehaviourIfNecessary((WorldEntity) b, (WorldEntity) a));
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
