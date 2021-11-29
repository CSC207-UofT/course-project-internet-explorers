package core.worldEntities.health;

public interface TakesDamage extends HasHealth {
    default void takeDamage(Damage damage) {
        setHealth(getHealth() - damage.amount());
    }
}
