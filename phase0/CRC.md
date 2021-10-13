---
marp: true
title: Marp CLI example
description: Hosting Marp slide deck on the web
theme: gaia
---

<!-- _class: lead -->
# Entity Classes

---

# `Interface WorldEntity`

### Responsibilities
* Position: Attribute, double[x,y], continuous coordinates for where the entity is located
* Shape: Attribute, the outline of the entity


### Collaborators
Player, Enemy, Defender

---

# `abstract class GameCharacter`

### Responsibilities
* Health: Attribute, int, denotes the total amount of health the individual has
* Position: Attribute, array with x, y, where on the map the IC is currently located
* Inventory: Attribute, ArrayList, items currently being held
* Level(?): Attribute, the level of the enemy
* Range(?): Attribute, how far the character can attack
* Hitbox(?): Attribute, from how far the character can be hit from

### Collaborators
Player, Enemy, Defender

---

# `class Player`

### Responsibilities
* inventory: Attribute, ArrayList of items currently in player's inventory. can be defender, weapon, map item (like a wall)
* itemDictionary: Attribute, Dictionary of items and their information


### Collaborators
Player, Enemy, Defender, Collidable, GameCharacter

---

# `class Enemy`

### Responsibilities

### Collaborators
GameCharacter, Player, Defender, Map, DamageableCollidable

---

# `class Defender`

### Responsibilities

### Collaborators
GameCharacter, Player, Enemy, Map, DamageableCollidable

---

# `class Tower`

### Responsibilities

### Collaborators
Enemy, Map, Collidable

---

# `class Tile implements WorldEntity`

### Responsibilities
* spawn_point: boolean, is this a spawn point for the enemies
* collides: boolean, can other entities collide with this tile
* texture: what the tile looks like (i.e. grass, road, water, etc.) – could be path to image file


### Collaborators
World, Map

---

# `class Map`

### Responsibilities
* Tiles_layout: Attribute, 2d array of tiles


### Collaborators
Enemy, World, Tiles

---

# `class World`

### Responsibilities
* All_Positions: holds the positions of all WorldEntities


### Collaborators
Map, WorldEntity

---

# `interface Collidable`

### Responsibilities
* Ensures that no two objects implementing Collidable are at the same place at the same time.
* Collidable objects have an onCollision method which defines their behaviour upon collision.
* Collidable objects have a hitbox, their shape in the world of Collidables.
* (If necessary) label system to ignore collisions with certain objects



### Collaborators
Player, Enemy, Defender, Tower

---

# `interface DamagingCollidable extends Collidable`

### Responsibilities
* Has information about how much damage to inflict when it hits something, who caused the damage


### Collaborators
Player, Enemy, Defender, Tower


---

# `interface DamageableCollidable extends Collidable`

### Responsibilities
* When a DamageableCollidable collides with a DamagingCollidable, the DamageableCollidable (or manager's) takeDamage method is called.


### Collaborators
Player, Enemy, Defender, Tower

---

# `abstract class Weapon`

### Responsibilities
* is an inventory item
* spawns in a DamagingCollidable which actually inflicts the damage
* level: Attribute, upgrade level of weapon
* static dict with attributes for each level
  * damage
  * range
  * attack speed (?)

### Collaborators
Player, Enemy, Defender, DamagingCollidable

---

# `class ControlsState`

### Responsibilities
* is the common language representing states of a GameCharacter's input device (keyboard, controller, etc.)
* uses `double` attributes for Up/Down, Left/Right, and other inputs

### Collaborators
KeyboardInputHandler, GameCharacter

---

<!-- _class: lead --># Use Case Classes

---

# `class CharacterManager`

### Responsibilities
* Animate the character based on inputs
  * Inputs from some `InputManager` stored as a `ControlsState`
* Responsible for what happens upon certain inputs
  * moveCharacter: update character's position
  * useItem: method, activates an inventory item
  * addInventory(item): add item to inventory
  * removeInventory(item): remove item from inventory
  * openInventory: method, returns inventory contents (use presenter to display)

### Collaborators
Player, Enemy, Defender, Collidable 

---

# `class GameManager`

### Responsibilities
* beginGame: trigger process to begin a game
* deleteCharacter: removes a character from the map when health = 0


### Collaborators
Player, Enemy, Defender, Map

---

<!-- _class: lead -->
# Controller Classes

---

# `abstract class InputHandler`
### Responsibilities
* Get input from any source and return a `ControlsState` representing the input.

### Collaborators
ControlsState, GameCharacter

# `class KeyboardInputHandler extends inputHandler`

### Responsibilities
* Translate keyboard inputs into a `ControlsState`
  + `keyLeft` move player left
  + `keyRight` move player right
  + `keyDown` move player down
  + `keyUp` move player up
  + `keyOpenInventory`, browse inventory
  + `keyChooseInventoryItem`, pick inventory item
  + `keyUseItem` place inventory item
  + `keyLevelUpDefender` level up the defender
  + `keyAttack` attack with weapon

### Collaborators
Player, CharacterManager

<!-- How does key input for main menu work? Would that be in a main menu class? -->
<!-- Should there be a keyEscape for pause? Is that also in some main class instead? -->

---

# `class SpawnController`

### Responsibilities
* spawnLocation, attribute, (x,y) coordinate, different for player and enemy
* spawn(spawnLocation), method, spawn player/enemy at spawn location

### Collaborators
Player, Enemy, Map

---

<!-- _class: lead -->
# Other Classes

---

# `abstract class MenuScreen`

### Responsibilities
* handle menus with clickable buttons, text fields, etc. 
* position of elements, what happens when clicked, etc.

### Collaborators
TBD

---

# `class MainMenu extends MenuScreen`

### Responsibilities
* buttons for
  * Start
  * Help
  * Quit


### Collaborators
MenuScreen

---

# `class PauseMenu extends MenuScreen`

### Responsibilities
* buttons for
  * Resume Game
  * Help
  * Quit


### Collaborators
MenuScreen

<style>
:root {
  font-size: 24px;
}
</style>
