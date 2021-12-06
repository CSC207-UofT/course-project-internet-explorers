package core.worldEntities.types.damageDealers;

import com.badlogic.gdx.physics.box2d.Body;
import core.worldEntities.WorldEntityWithSprite;
import core.worldEntities.health.Damage;
import core.worldEntities.health.DealsDamage;

public class Spike extends WorldEntityWithSprite implements DealsDamage {

    public Spike(Body body) {
        super(body);
    }

    @Override
    public Damage dealDamage() {
        return new Damage(1f, null);
    }
}
