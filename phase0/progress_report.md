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

Specifically, the entity classes core to our model are WorldEntity, gameCharacter, Tile, Item, and 
Weapon. To ensure minimum dependency on lower level code, and to amalgamate uniform behaviors and 
characteristics, the entity classes may choose extend either of the abstract classes. A class that 
extends WorldEntity would be able to store its position and shape. All characters are WorldEntities.

Classes may choose to implement the Collidable interface. A Collidable class would instantiate an object 
that is unable to exist in the same place at the same time as another Collidable object without consequence. The 
consequence and nature of collision is determined by the child interfaces implemented by the class. DamagableCollidable 
and DamagingCollidable are interfaces that extend Collidable and quantify the extent to which an object can take and/or 
deal damage. All characters are Collidable. The Weapon abstract class instantiates different kinds of 
DamagingCollidable objects through its WeaponUsageDelegate use case class with information of their own level, damage 
inflicted upon collision, and range of attack. 

Other core use case classes include the Map class, which holds the layout of tiles instantiated. LevelState manages
the configuration of a level and storing its progress by maintaining static and dynamic records on spawned entities.
The information stored in the entity classes is mutated by use case classes CharacterManager and GameManager. The
former provides methods to move, attack and deplete health, whilst the latter allows to spawn enemies and delete
characters upon death. 

The LevelManager, ControlsState, InputHandler, and SpawnController constitute the controller classes.
The InputHandler is an abstraction of the AIInputHandler and KeyboardInputHandler controller classes that take input in 
the form of either keyboard instructions or AI. These instructions are passed to the controlsState to be used by the 
LevelManager and CharacterManager to reposition and spawn objects using the SpawnController.  

The MainMenu and PauseMenu classes extend the MenuScreen abstract class and store positions of key buttons that the user 
can interact with.

# Sample Walk-Through

In order to test the design of our system, our team designed a typical scenario in the game. The game would begin
with the main menu, wherein the user would be able to select the new game button. A set of Tiles, Map, and World
objects would be instantiated. A Player would be instantiated with the ability to reposition, attack, deplete, and 
access their inventory through the InputHandler and CharacterManager. The inventory may include multiple Weapon and 
Defender objects which can be used with the SpawnController. Eventually, the SpawnController instantiates multiple
enemies with their individual paths directed towards the Tower. The Collidable interface ensures that characters are
able to take and/or deal damage. The GameManager ensures that characters with 0 health are eliminated. The user is 
able to pause the game at any point to read instructions, quit, or resume through the Pause Menu class. If the Tower
is destroyed, its health is depleted to 0 and the GameManager informs the user that they have lost. In the case that
all the spawned enemies are eliminated, the GameManager informs the user that they have won. 

# Skeleton Code

The skeleton code is where we begin to bring our ideas to life. At the moment, it simply entails the main menu, which
is rendered using the MainMenuScreen class. From there, the user can enter the game, where it currently utilizes the classes:
GameScreen, MainMenuScreen, World, WorldEntity, Player, and Map. In the game screen, the player can
only move their character around for now.

Our skeleton program revolves around rendering our key GameCharacter objects and being able to reposition the Player. We
created our GameCharacter class. We also created a CharacterSprite class which instantiates our character objects and 
renders their sprites with an image file. Finally, the DesktopLauncher instantiates an instance of the GdxGame which was 
configured. LibGDX was a key library leveraged in implementing our skeleton program. 

# Reflection

Our 6 group members synergized to efficiently complete the planning stage of our project. Although we collaborated
heavily on designing our CRC cards, we delegated most our remaining deliverables for the current phase. All final
deliverables were also reviewed collaboratively.

An aspect of our design that has realized value while implementing code is our decision to abstract to GameCharacter. So
far, this has truly allowed us to simplify the Player, Defender, and Enemy objects for rendering and mutating. Our 
architecture also lends itself to intuitive package structures which enhances collaborative productivity. 

One aspect of clean architecture that our group has struggled with is the role of Entity classes. Our initial structure
was over-weighed on Entity classes. Since these Entity classes were not all core business properties, we have readjusted
our class structure to delegate more functionality to controllers and use case classes. However, we continue to struggle
with assessing the scope of the game as we lack the understanding to evaluate potential additional features.
  
# Individual Progress
<ul> 
<li>Akshay: Summarized deliverables and reflection for the progress report. Began implementing the Level_System with the LevelState use class. Plan to continue working on implementing an efficient system for describing / storing progress on game levels and enemy spawn scheduling. 
<li>Wei Qi:</li>
<li>Ian:</li>
<li>Roy: Refactored the screen components of the GdxGame class into GameScreen and implemented the MainMenuScreen with the libGDX library.
Connected the two screens together so that you can click start on the MainMenuScreen to enter the GameScreen. For the next
phase, I plan on working on the Inventory class that GameCharacters will be carrying.
</li>
<li>Philip-Nicolas:</li>
<li>Michael-Anthony: Implemented GameCharacter abstract class, along with Player, Defender and Enemy classes. Also added the base level implementation of the CharacterManager use case class and basic rendering to put characters on the screen. Going forward, will continue to flesh out CharacterManager use case class and refine subclasses of GameCharacter.</li>
</ul>  
  
# Open Questions
  
  <ol>
    <li>What is the best way to implement the leveling system within the game to make it in line with SOLID design principles?</li>
  </ol>
