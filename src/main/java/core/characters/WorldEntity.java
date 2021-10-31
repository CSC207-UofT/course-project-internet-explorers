package core.characters;

public abstract class WorldEntity {

    /*
     * Abstract class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param position: the x, y coordinates of the entity on the map
     * @param shape: the shape of the object, used to define movement and hit box.
     * */

    public float[] position;
    public String shape;

    public WorldEntity(float[] position, String shape) {

        this.position = position;
        this.shape = shape;
    }
}
