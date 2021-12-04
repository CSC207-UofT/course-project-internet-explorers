package core.worldEntities.health;

import core.worldEntities.collisions.CollisionBehaviour;

public interface TakesDamage extends HasHealth {
    default void takeDamage(Damage damage) {
        setHealth(getHealth() - damage.amount());
    }

    // TODO move somewhere less confusing
    CollisionBehaviour<TakesDamage, DealsDamage> takeDamageOnCollision = new CollisionBehaviour<>(
        TakesDamage.class,
        DealsDamage.class,
        (takesDamage, dealsDamage) -> {
            takesDamage.takeDamage(dealsDamage.dealDamage());
            // TODO remove once we have a proper way of displaying health
            System.out.println(takesDamage.getHealth());
        }
    );
}
