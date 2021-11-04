package core.characters;

public class AIHandler {
    /*
    * Handles the movement of the defenders and enemies in the game
    * @param cm: the CharacterManager allowing AIHandler to pass inputs to entities
    * @param
    * @param
    * TODO: Add extends InputHandler
    * */

    public CharacterManager cm;

    public AIHandler(CharacterManager cm) {
        /*
        * Instantiates with a character manager to allow direct access to pre-loaded set
        * of character entities
        * */
        this.cm = cm;
    }

    /*
    * Need to make it so that it calls methods from the Character Manager
    * Need to make sure it updates the position of the character at entity level and on screen
    *   - Need characterSprite.setPosition in whatever triggers the movement of the character
    * Thinking an event will trigger movement, item etc.
    * Need to figure out how to know which character to move
    * May need to add a getter to the use case to handle events that trigger on collisiion
    *   - Ex. attacking the character, tower, defence
    * */




}
