# Progress Report

# Introduction and Specification

Internet explorers envisions developing a fluid, captivating, and engaging tower defense game whilst considering
SOLID design principles and efficient collaboration. We developed a project specification which further provides
a tangible criteria of success. The specification highlights the key user interactions, functions, and overall 
journey through an ordinary game. The game revolves around a controllable player that navigates an enemy-ridden map
to defend a tower for as long as possible by spawning defenses. 

# CRC Cards

When developing a dynamic product, it is pivotal to maintain a simple structure which follows key design principles. 
Internet explorers uses CRC cards to design individual classes and delineate how they interact. We further distributed 
the classes into three layers of architecture: entity classes, use case classes and controller classes. The classes 
interact effectively by implementing interfaces and inheriting from abstracted classes. 

Specifically, the entity classes core to our model are game.World, game.mapSystem.Map, Tower, game.mapSystem.Tile, GameCharacter<Abstract>, and 
Weapon<Abstract>. To ensure minimum dependency on lower level code, and to amalgamate uniform behaviors and 
characteristics, the entity classes may choose to implement interfaces: game.WorldEntity and Collidable. A class that 
implements game.WorldEntity would be able to store its position and shape. A Collidable class would instantiate an object 
that is unable to exist in the same place at the same time as another Collidable object without consequence. The 
consequence and nature of collision is determined by the child interfaces implemented by the class. DamagableCollidable 
and DamagingCollidable are interfaces that extend Collidable and quantify the extent to which an object can take and/or 
deal damage. 

The game.World class holds the positions of all WorldEntities in the game and the game.mapSystem.Map class holds the layout of tiles 
instantiated. The Tower class holds information about the current state of the tower including its health, whilst 
implementing the game.WorldEntity and DamagableCollidable interfaces. The GameCharacter class is extended by the 
Player, Enemy, and Defender subclasses and includes common attributes such as Health, Position, and Inventory. Notably,
the enemy object is instantiated with a path that it will follow in the game. All characters are WorldEntities and
Collidable. The Weapon abstract class instantiates different kinds of DamagingCollidable objects with information of
their own level, damage inflicted upon collision, and range of attack. 

The information stored in the entity classes is mutated by use case classes CharacterManager and GameManager. The
former provides methods to move, attack and deplete health, whilst the latter allows to spawn enemies and delete
characters upon death. The InputHandler, SpawnController, Main Menu, and Pause Menu constitute the controller classes.
While the InputHandler facilitates movement and selection through keyboard interaction, the SpawnController facilitates
spawning an enemy or object. The Menu classes store positions of key buttons that the user can interact with.

# Sample Walk-Through

In order to test the design of our system, our team designed a typical scenario in the game. The game would begin
with the main menu, wherein the user would be able to select the new game button. A tower, set of Tiles, game.mapSystem.Map, and game.World
objects would be instantiated. A Player would be instantiated with the ability to reposition, attack, deplete, and 
access their inventory through the InputHandler and CharacterManager. The inventory may include multiple Weapon and 
Defender objects which can be used with the SpawnController. Eventually, the SpawnController instantiates multiple
enemies with their individual paths directed towards the Tower. The Collidable interface ensures that characters are
able to take and/or deal damage. The GameManager ensures that characters with 0 health are eliminated. The user is 
able to pause the game at any point to read instructions, quit, or resume through the Pause Menu class. If the Tower
is destroyed, its health is depleted to 0 and the GameManager informs the user that they have lost. In the case that
all the spawned enemies are eliminated, the GameManager informs the user that they have won. 