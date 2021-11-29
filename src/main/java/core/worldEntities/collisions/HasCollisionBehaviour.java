package core.worldEntities.collisions;

import core.worldEntities.WorldEntity;

public interface HasCollisionBehaviour<T extends WorldEntity & HasCollisionBehaviour<T>> {
    CollisionBehaviour<T, ?>[] getCollisionBehaviour();
}
