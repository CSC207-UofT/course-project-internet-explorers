package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class DemoSpawners {

    public static Spawner<WorldEntityWithSprite> createPlayerSpawner() {
        Vector2 position = new Vector2(2, 2);
        Sprite sprite = new TextureAtlas("characters/sprites.txt").createSprite("demo_player");
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        return Spawner.createSpriteBasedEntitySpawner(position, sprite);
    }

    public static Spawner<WorldEntityWithSprite> createEnemySpawner() {
        Vector2 position = new Vector2(20, 8);
        Sprite sprite = new TextureAtlas("characters/sprites.txt").createSprite("demo_enemy");
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        return Spawner.createSpriteBasedEntitySpawner(position, sprite);
    }

    public static Spawner<WorldEntityWithSprite> createDefenderSpawner() {
        Sprite sprite = new TextureAtlas("characters/sprites.txt").createSprite("demo_defense");
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(16, 3));
        return Spawner.createSpriteBasedEntitySpawner(bodyDef, sprite);
    }

    public static Spawner<WorldEntity> createMapBorderSpawner() {
        Spawner<WorldEntity> spawner = new Spawner<>(WorldEntity.class);

        spawner.setBodyDefSupplier(() -> {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            return def;
        });

        // map vertices
        Vector2[] mapVertices = new Vector2[] { new Vector2(0, 0), new Vector2(0, 10), new Vector2(30, 10), new Vector2(30, 0) };

        int vertices = mapVertices.length;
        FixtureDef[] fixtureDefs = new FixtureDef[vertices];

        for (int i = 0; i < vertices; i++) {
            fixtureDefs[i] = new FixtureDef();

            EdgeShape edge = new EdgeShape();
            edge.setVertex0(mapVertices[i]);
            edge.set(mapVertices[(i + 1) % vertices], mapVertices[(i + 2) % vertices]);
            edge.setVertex3(mapVertices[(i + 3) % vertices]);

            fixtureDefs[i].shape = edge;
            fixtureDefs[i].restitution = 0.2f;
        }

        spawner.setFixtureDefsSupplier(() -> fixtureDefs);

        // dispose of shapes used for fixtures *after* entity has been spawned
        spawner.setSpawnCallback(e -> {
            for (FixtureDef def : fixtureDefs) {
                def.shape.dispose();
            }
        });

        return spawner;
    }
}
