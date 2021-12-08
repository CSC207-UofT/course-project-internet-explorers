package core.worldEntities.types.damageDealers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.levels.LevelManager;
import core.worldEntities.WorldEntity;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.health.Damage;
import core.worldEntities.health.DealsDamage;

/**
 * Entity that is a circle where damage is dealt.
 */
public class CircularDamageRegion extends WorldEntity implements DealsDamage {

    private Damage damage;

    public CircularDamageRegion(Body body) {
        super(body);
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    @Override
    public Damage dealDamage() {
        return this.damage;
    }
}
