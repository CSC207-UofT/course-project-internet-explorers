package core.worldEntities.collisions;

import core.worldEntities.WorldEntity;
import java.util.function.BiConsumer;

/**
 * Defines what happens when an object of type A collides with a subclass of type B.
 * @param <A>
 * @param <B>
 */
public class CollisionBehaviour<A, B> {

    public final Class<A> typeA;
    public final Class<B> typeB;
    private final BiConsumer<A, B> collisionHandler;

    public CollisionBehaviour(Class<A> typeA, Class<B> typeB, BiConsumer<A, B> collisionHandler) {
        this.typeA = typeA;
        this.typeB = typeB;
        this.collisionHandler = collisionHandler;
    }

    /**
     * Invoke the behaviour handler if the passed WorldEntities satisfy this CollisionBehaviour's types.
     */
    protected void doCollisionBehaviourIfNecessary(WorldEntity a, WorldEntity b) {
        if (typeA.isInstance(a) && typeB.isInstance(b)) {
            collisionHandler.accept(typeA.cast(a), typeB.cast(b));
        }
    }
}
