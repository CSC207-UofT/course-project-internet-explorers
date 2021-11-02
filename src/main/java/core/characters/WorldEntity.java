package core.characters;

public abstract class WorldEntity {

    /*
     * Abstract class that defines the main attributes of the entities in the game.
     * @param position: the x, y coordinates of the entity on the map
     * @param shape: the shape of the object, used to define movement and hit box.
     * */

    public float[] position;
    public String shape;

    public WorldEntity(String shape) {

        this.position = new float[]{0, 0};
        this.shape = shape;
    }
}
