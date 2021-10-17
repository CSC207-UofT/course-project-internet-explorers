# Project Specification

When the game is first launched, players are greeted with the main menu which has 4 options: 
start game, which allows them to begin a new game, load game, which allows them to continue
from where they left off during a previous session, help, which directs them to a screen with
fixes for common issues and finally the ability to quit the game. 

When players starts or loads a game, an instance of player will be spawned on a map which can be controlled 
using the keyboard. Additionally, a tower will be spawned which the player needs to defend.
To defend the tower, the player will be able to move around the map and build defenses. Enemies 
will then spawn in waves of varying lengths and difficulty with the goal of destroying the tower.
Ultimately, the goal of the game is to survive for as long as possible. When the player is defeated,
an end game menu will be shown with the option to restart or return to the main menu.

### Entity Classes

<code>GameCharacter</code>: Abstract class that holds attributes common to main game entities:
Player, Enemy and Defense.

<code>Player</code>: Controllable character placed on the map when game is first
started; ability to alter the map by placing defenses.

<code>Defense</code>: Non player character that is able to be placed on the map by 
<code>Player</code> to defend <code>Tower</code>.

<code>Tower</code>: The central objective of the game, must survive for the player to continue the
game.

<code>Enemy</code>: Spawns in waves and follows a set path along the map towards the <code>Tower</code>, 
taking damage from <code>Defense</code>.

### Use Case Classes

<code>CharacterManager</code>: Allows instances of <code>GameCharacter</code> to move around the map,
attack objects within their range and take damage if attacked.

<code>GameManager</code>: Handles the ability to begin and end a game.
### Controller Classes

<code>KeyboardInputHandler</code>: Gets inputs from the keyboard.

## Additional details and specifics

### Minimum Viable Prototype & More
* Main menus.Menu
  * Selectable Buttons
  * Start Game
  * Load game (if we have time to do it)
  * Help
  * Quit game


* Start Game
  * Load Game Scene
  * Generate a specific mapSystem.Map (if there’s time, we could consider add maps)
  * Play a quick comic scene
  * Spawn A Player


* In-game
  * Player that can controlled by keyboard
    * Moves around and build defender
    * Collide with castle and walls (map) but go through enemies
    * Ability to attack enemies (if there’s time)
  * Enemies
    * Follow set path
    * Has health point
    * Has movement speed
    * Make types (if we have time)
      * Fly vs. Ground
      * Armor vs non-army etc
  * Defenders
    * Static (for now)
    * Attack type (single vs range vs penetrate)
    * Level (corresponds to the following)
      * Attack/damage
      * Attack speed
      * Maybe range
    * Different kind of towers (dragons :D)
    * Option to remove (if there’s time)
  * In game menus.Menu (pause menu)
    * Pause the game
    * Help
    * Quit the game
  * Tracker
    * Time until next wave/stage
    * Score
    * Currency
      * Get currency every wave and enemy you beat


* End game menu
  * WASTED screen (+ the comic scene)
  * Restart button
  * Main menus.Menu button

* Maps
  * Tiles
  * Grass (background)
  * Path (background)
  * Wall (collidable)
  * mapSystem.Map Edge (collidable)
  * Spawn points (data)


* Camera
  * Centered on player
  * Can look around map (if has time)

* TODOs
  * Main menu
    * Start Game
    * Quit
  * Ingame
    * Game Loading
      * Load the map
      * Spawn player


### Structure of program
* Class to handle inputs
* Class to handle drawing
* Class to handle AI
* Main game class



### Classes
* at least three entity classes (store info, getters, setters)
  * Player
  * Enemy
  * Defender
* at least two use case classes (manipulates entity)
  * PlayerManager
* at least one controller (send commands to use case classes)
  * InputHandler
    * KeyboardInputHandler
    * ControllerInputHandler
  * SpawnController
  * DisplayController
* and at least a basic command line interface
  * Highscores viewer if necessary


### Look of game
* Comic book image intro
* **2D style game, real time moves**
  * **Top down camera**
* Clash of clans style
* Bloons style
* Age of war style

* Feature ideas
  * You walk around to place defenses
    * Walking defender guy
    * Pour oil from castle walls
  * Need to figure out
    * Levelling system
