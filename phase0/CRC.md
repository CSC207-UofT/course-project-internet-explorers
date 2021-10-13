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
* Spawnable_defenders: Attribute, dictionary of level of defenders and their corresponding integer of number of defenders the player can spawn.  
* Build_wall(position): Method to use inventory to build wall at a certain position on the map
* Spawn_defender(position): Method to spawn defender at a certain position on the map

### Collaborators
Player, Enemy, Defender, Weaponable, Collidable, GameCharacter

---

# `class Enemy`

### Responsibilities
* Path: Attribute, ArrayList, the path the enemy is meant to follow
* Attack: Method, attacks defense on the map

### Collaborators
GameCharacter, Player, Defender, Map, Weaponable, Collidable

---

# `class Defender`

### Responsibilities
* Attack: Method, attacks enemies on the map

### Collaborators
GameCharacter, Player, Enemy, Map, Weaponable, Collidable

---

# `class Tower (?)`

### Responsibilities
* Position: Attribute, array with x, y, where on the map the Tower is currently located
* Health: Attribute, total health of the tower
* updateHealth: method, updates the health of the tower to reflect damage taken

### Collaborators
Enemy, Map, Collidable

---

# `class Tile`

### Responsibilities
* spawn_point: boolean, is this a spawn point for the enemies
* collides: boolean, can other entities collide with this tile
* texture: what the tile looks like (i.e. grass, road, water, etc.)


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

# `interface Weaponable`

### Responsibilities
* H


### Collaborators
Player, Enemy, Defender

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

<!-- _class: lead --># Use Case Classes

---

# `class CharacterManager`

### Responsibilities
* moveCharacter: method, takes directional commands from keyboard input and moves character
* attackObject: method, attacks nearest object when the range <= distance to that object (for NPCs)
* takeDamage/depleteHealth: method, takes health away from the character when hit

### Collaborators
Player, Enemy, Defender, Collidable 

---

# `class gameManager (?)`

### Responsibilities
* Spawn: Method, spawns enemies at position on the map
* deleteCharacter: removes a character from the map when health = 0


### Collaborators
Player, Enemy, Defender, Map

---

<!-- _class: lead -->
# Controller Classes

---

# `class InputHandler`

### Responsibilities
* + keyLeft(input), move player left
* + keyRight(input), move player right
* + keyDown(input), move player down
* + keyUp(input), move player up
* + keyOpenInventory, browse inventory
* + keyChooseInventoryItem, pick inventory item
* + keyPlaceItem(input), place inventory item
* + keyLevelUpDefender(?), level up the defender
* + keyAttack(input), attack with weapon

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

# `class MainMenu`

### Responsibilities
* startButton, attribute, position of start button
* helpButton, attribute, position of help button
* quitButton, attribute, position of quit button
* start_game, method, start game by generating map, spawning player (w/ SpawnController?)


### Collaborators
Player, Map 

---

# `class PauseMenu`

### Responsibilities
* resumeButton, attribute, position of resume button
* helpButton, attribute, position of help button
* quitButton, attribute, position of quit button



### Collaborators
Player, Map

<style>
:root {
  font-size: 24px;
}
</style>
