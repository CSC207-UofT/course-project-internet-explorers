package core.worldEntities.types.damageDealers;

import com.badlogic.gdx.math.Vector2;
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
 *
 * TODO add system for scheduling entity deletions
 *
 *
 */
public class CircularDamageRegion extends WorldEntity implements DealsDamage {
    private final float deletionTime;
    private final WorldEntityManager entityManager;
    private final Damage damage;

    /**
     *
     * @param levelManager : The manager of the level in which the CircularDamageRegion should be spawned
     * @param position : The position of our CircularDamageRegion
     * @param radius : The range of our CircularDamageRegion
     * @param damage: The Damage object to be dealt when a takesDamage is in the radius
     */
    public CircularDamageRegion(LevelManager levelManager, Vector2 position, float radius, Damage damage) {
        super(
                levelManager.getEntityManager(),
                createBodyDef(position),
                createFixtureDef(radius)
        );
        deletionTime = levelManager.getTime() + 500;
        entityManager = levelManager.getEntityManager();
        this.damage = damage;
    }

    private static BodyDef createBodyDef(Vector2 position) {
        BodyDef def = new BodyDef();

        def.position.set(position);

        return def;
    }

    private static FixtureDef createFixtureDef(float radius) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef def = new FixtureDef();
        // lets other objects pass through this fixture
        def.isSensor = true;
        def.shape = shape;
        return def;
    }

    @Override
    public Damage dealDamage() {
        return this.damage;
    }
}



