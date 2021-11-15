package core.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class DemoSpawners {

    public static EntitySpawner createPlayerSpawner() {
        EntitySpawner spawner = new EntitySpawner();

        spawner.setBodyDefSupplier(() -> {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            return def;
        });

        EntitySpawner.WorldEntityGeometry geometry = new EntitySpawner.WorldEntityGeometry();
        geometry.position = new Vector2(2, 10);
        geometry.size = new Vector2(1, 1);
        geometry.offset = geometry.size.cpy().scl(-.5f);

        spawner.geometrySupplier(() -> geometry);

        spawner.setFixtureDefsSupplier(EntitySpawner.createRectangularFixtureDefSupplier(geometry.size));

        spawner.setSpriteSupplier(() -> new TextureAtlas("characters/sprites.txt").createSprite("demo_player"));

        return spawner;
    }

    public static EntitySpawner createEnemySpawner() {
        EntitySpawner spawner = new EntitySpawner();

        spawner.setBodyDefSupplier(() -> {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            return def;
        });

        EntitySpawner.WorldEntityGeometry geometry = new EntitySpawner.WorldEntityGeometry();
        geometry.position = new Vector2(20, 8);
        geometry.size = new Vector2(1, 1);
        geometry.offset = geometry.size.cpy().scl(-.5f);

        spawner.geometrySupplier(() -> geometry);

        spawner.setFixtureDefsSupplier(EntitySpawner.createRectangularFixtureDefSupplier(geometry.size));

        spawner.setSpriteSupplier(() -> new TextureAtlas("characters/sprites.txt").createSprite("demo_enemy"));

        return spawner;
    }

    public static EntitySpawner createDefenderSpawner() {
        EntitySpawner spawner = new EntitySpawner();

        spawner.setBodyDefSupplier(() -> {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            return def;
        });

        EntitySpawner.WorldEntityGeometry geometry = new EntitySpawner.WorldEntityGeometry();
        geometry.position = new Vector2(16, 3);
        geometry.size = new Vector2(1, 1);
        geometry.offset = geometry.size.cpy().scl(-.5f);

        spawner.geometrySupplier(() -> geometry);

        spawner.setFixtureDefsSupplier(EntitySpawner.createRectangularFixtureDefSupplier(geometry.size));

        spawner.setSpriteSupplier(() -> new TextureAtlas("characters/sprites.txt").createSprite("demo_defense"));

        return spawner;
    }

    public static EntitySpawner createMapBorderSpawner() {
        EntitySpawner spawner = new EntitySpawner();

        spawner.setBodyDefSupplier(() -> {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            return def;
        });

        // map vertices
        Vector2[] mapVertices = new Vector2[] { new Vector2(0, 0), new Vector2(0, 10), new Vector2(30, 10), new Vector2(30, 0) };

        spawner.setFixtureDefsSupplier(() -> {
            int vertices = mapVertices.length;
            FixtureDef[] edges = new FixtureDef[vertices];

            for (int i = 0; i < vertices; i++) {
                edges[i] = new FixtureDef();

                EdgeShape edge = new EdgeShape();
                edge.setVertex0(mapVertices[i]);
                edge.set(mapVertices[(i + 1) % vertices], mapVertices[(i + 2) % vertices]);
                edge.setVertex3(mapVertices[(i + 3) % vertices]);

                edges[i].shape = edge;
                edges[i].restitution = 0.2f;
            }

            return edges;
        });

        spawner.setSpriteSupplier(() -> new TextureAtlas("characters/sprites.txt").createSprite("demo_defense"));

        return spawner;
    }
}
