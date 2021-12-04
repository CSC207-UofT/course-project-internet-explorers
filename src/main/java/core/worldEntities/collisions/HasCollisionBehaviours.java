package core.worldEntities.collisions;

import java.util.List;

public interface HasCollisionBehaviours {
    List<CollisionBehaviour<?, ?>> getCollisionBehaviours();
}
