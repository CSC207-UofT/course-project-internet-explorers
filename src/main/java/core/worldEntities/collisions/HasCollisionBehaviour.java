package core.worldEntities.collisions;

import core.worldEntities.WorldEntity;
import java.util.List;

public interface HasCollisionBehaviour<T extends WorldEntity & HasCollisionBehaviour<T>> {
    List<CollisionBehaviour<T, ?>> getCollisionBehaviour();
}
