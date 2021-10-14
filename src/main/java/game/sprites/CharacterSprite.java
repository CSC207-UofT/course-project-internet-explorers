package game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import game_characters.*;

import java.util.ArrayList;

public class CharacterSprite {

    SpriteBatch batch;
    TextureAtlas playerTextureAtlas;
    TextureAtlas defenderTextureAtlas;
    TextureAtlas enemyTextureAtlas;
    Sprite playerSprite;
    Sprite defenderSprite;
    Sprite enemySprite;

    public void create() {


        // Creates instances of the 3 main characters on the screen
        ArrayList<String> playerItems = new ArrayList<String>();
        Player protagonist = new Player("characters/demo_player.jpg", 100, 1,
                playerItems);
        Defender defender = new Defender("characters/demo_defense.jpg", 100, 1);
        Enemy enemy = new Enemy("characters/demo_enemy.jpg", 100, 1);

        // Creates the instances of the texture atlas
        playerTextureAtlas = new TextureAtlas(protagonist.imgFile);
        defenderTextureAtlas = new TextureAtlas(defender.imgFile);
        enemyTextureAtlas = new TextureAtlas(enemy.imgFile);

        // Creates the sprite instances
        playerSprite = playerTextureAtlas.createSprite("player");
        defenderSprite = defenderTextureAtlas.createSprite("defender");
        enemySprite = enemyTextureAtlas.createSprite("enemy");



    }

    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        playerSprite.draw(batch);
        defenderSprite.draw(batch);
        enemySprite.draw(batch);
        batch.end();
    }

    public void dispose() {
        playerTextureAtlas.dispose();
        defenderTextureAtlas.dispose();
        enemyTextureAtlas.dispose();
    }

}
