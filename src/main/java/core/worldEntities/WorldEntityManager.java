package core.worldEntities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import core.input.CharacterInputDevice;
import core.worldEntities.types.characters.GameCharacter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Use-case class for WorldEntities.
 */
public class WorldEntityManager {

    private final Map<UUID, WorldEntity> entities;
    private final World world;

    public WorldEntityManager(World world) {
        this.world = world;
        this.entities = new HashMap<>();
    }

    /**
     * Creates a WorldEntity's representation in the World,
     * and adds it to the collection of managed entities.
     *
     * @param entity      The entity to register
     * @param bodyDef     Box2D representation details
     * @param fixtureDefs Box2D representation details
     */
    public void register(WorldEntity entity, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        Body body = world.createBody(bodyDef);
        createFixtures(body, fixtureDefs);
        entity.setBody(body);
        this.entities.put(entity.id, entity);
    }

    /**
    * Creates a GameCharacter
     * If a game character is being created, an input device is passed to handle the inputs
    * @param entity      The entity to register
    * @param bodyDef     Box2D representation details
    * @param inputDeviceType type of inputs the entity will receive
    * @param fixtureDefs Box2D representation details
    */
    public void register(WorldEntity entity, BodyDef bodyDef, Class<? extends CharacterInputDevice> inputDeviceType,
                            FixtureDef... fixtureDefs) {
        Body body = world.createBody(bodyDef);
        createFixtures(body, fixtureDefs);
        entity.setBody(body);

        if (entity instanceof GameCharacter) {
            ((GameCharacter) entity).setInputDeviceType(inputDeviceType);
        } else {
            throw new RuntimeException(entity.id + "does not represent a GameCharacter");
        }
        this.entities.put(entity.id, entity);
    }

    /**
     * Adds Box2D fixtures to the specified WorldEntity's Box2D Body.
     *
     * @param entityId The target entity's id
     * @param defs     details of fixtures to add
     */
    public void createFixtures(UUID entityId, FixtureDef... defs) {
        createFixtures(getEntity(entityId).getBody(), defs);
    }

    private void createFixtures(Body body, FixtureDef... defs) {
        for (FixtureDef def : defs) {
            body.createFixture(def);
        }
    }

    /**
     * Draw all the entities on the screen.
     *
     * @param batch a SpriteBatch which is already drawing (batch.begin() has been called)
     */
    public void draw(SpriteBatch batch) {
        entities.forEach((id, e) -> {
            if (e instanceof WorldEntityWithSprite entity) {
                Sprite sprite;
                if ((sprite = entity.getSprite()) != null) {
                    sprite.draw(batch);
                }
            }
        });
    }

    public WorldEntity getEntity(UUID id) {
        return entities.get(id);
    }

    public void teleport(UUID id, Vector2 target) {
        Body body = entities.get(id).getBody();
        body.setTransform(target, body.getAngle());
    }

    public int getNumEntities() {
        return this.entities.size();
    }
}
