# Project Specification

### Minimum Viable Prototype & More
* Main Menu
  * Selectable Buttons
  * Start Game
  * Load game (if we have time to do it)
  * Help
  * Quit game


* Start Game
  * Load Game Scene
  * Generate a specific Map (if there’s time, we could consider add maps)
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
  * In game Menu (pause menu)
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
  * Main Menu button

* Maps
  * Tiles
  * Grass (background)
  * Path (background)
  * Wall (collidable)
  * Map Edge (collidable)
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
