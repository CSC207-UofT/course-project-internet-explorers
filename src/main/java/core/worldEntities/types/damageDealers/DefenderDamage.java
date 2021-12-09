package core.worldEntities.types.damageDealers;

import com.badlogic.gdx.physics.box2d.Body;
import core.worldEntities.WorldEntityWithSprite;
import core.worldEntities.health.Damage;
import core.worldEntities.health.DealsDamage;

public class DefenderDamage extends WorldEntityWithSprite implements DealsDamage {

    /**
     * Entity that is a defender where damage is dealt.
     */

    public DefenderDamage(Body body) {
        super(body);
    }

    @Override
    public Damage dealDamage() {
        return new Damage(1f, null);
    }
}
