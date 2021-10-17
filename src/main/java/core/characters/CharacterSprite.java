package core.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

// Following documentation from: https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics

public class CharacterSprite {

    SpriteBatch batch;
    TextureAtlas textureAtlas;
    Sprite playerSprite;
    Sprite defenderSprite;
    Sprite enemySprite;
    int player_x;
    int player_y;

    public void createCharacter() {
        // Creates instances of the 3 main characters on the screen
        // ArrayList<String> playerItems = new ArrayList<String>();
        //        Player protagonist = new Player(100, 1,
        //                playerItems, "Square");
        //        NPC defender = new NPC("Defender", 100, 1, "Circle");
        //        NPC enemy = new NPC("Enemy", 100, 1, "Circle");

        // Creates the instances of the texture atlas
        textureAtlas = new TextureAtlas("characters/sprites.txt");

        // Creates the sprite instances
        playerSprite = textureAtlas.createSprite("demo_player");
        defenderSprite = textureAtlas.createSprite("demo_defense");
        enemySprite = textureAtlas.createSprite("demo_enemy");
        player_x = 0;
        player_y = 0;
        playerSprite.setSize(100, 100);
        playerSprite.setPosition(player_x, player_y);
        defenderSprite.setSize(100, 100);
        defenderSprite.setPosition(100, 100);
        enemySprite.setSize(100, 100);
        enemySprite.setPosition(200, 200);

        batch = new SpriteBatch();
    }

    public void renderCharacter() {
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) this.player_x -= 10;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) this.player_x += 10;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) this.player_y += 10;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) this.player_y -= 10;
        this.playerSprite.setPosition(player_x, player_y);
        batch.begin();
        playerSprite.draw(batch);
        defenderSprite.draw(batch);
        enemySprite.draw(batch);
        batch.end();
    }

    public void dispose() {
        textureAtlas.dispose();
        batch.dispose();
    }
}
