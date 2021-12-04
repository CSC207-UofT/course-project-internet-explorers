package core.worldEntities.types.damageDealers;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.WorldEntityWithSprite;
import core.worldEntities.health.Damage;
import core.worldEntities.health.DealsDamage;

public class Spike extends WorldEntityWithSprite implements DealsDamage {

    //DamageCarrier - each DealsDamage

    public Spike(WorldEntityManager entityManager, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        super(entityManager, bodyDef, fixtureDefs);
    }

    @Override
    public Damage dealDamage() {
        return new Damage(1f, null);
    }
}
