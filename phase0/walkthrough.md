## 1-2 Paragraph Version

The game starts at the main menu page with the option to start or quit. 
After the player press the start button, the game launched as the InputHandler receives the message and uses GameManager and the levelManager to launch the game and reset the level. 
The system will leverage the spawn controller to spam enemies. 
The player spawn in the middle of the screen and can move around with arrow keys. 
The KeyboardInputHandler will translate the keys into a control state and then update the position of the play through the CharacterManager. 
Every in-game character has health, attack power, position as the base attribute. 
They are separated into two teams (enemies vs. 
allies) to determine damage when there’s a collision. 
During the game, the player can build defenders who are able to shoot "ballet/weapons" and the damages is observed when there's a collision between the damagable colliable, which is when the bullet hits the enemies. 
Each defendant is "equipped" with a weapon that determine the amount of damage it can dealt, and the impacts is managed by WeaponUsageDelegate. 
If the player fails to kill the enemies and they reach the castle, the game ends and the ending screen with the stored high score is presented. 
If the player is able to kill all the enemies, the currentLevel will be updated and a new wave of enemy will spawn.

### appendix 

## Start Game

- Shows menu with start and quit game
- Click start to enter game

## In-Game user inputs

- A Player spawn in the middle of the screen and can move around
- Player can also attack
- Click “i” to open inventory
  - Select a defender with arrows keys
- Build selected defender by pressing “b”

## In-Game scenarios

- Shows current score and currency (maybe)
- One enemy spawns in after 20 seconds
- The enemy follows a specific path
- If enemy reaches the end of the path then game ends and show score
- If enemy is killed (after two hits from the defendant), then score + 10 and a new one (maybe two and then three) will spawn after 10 seconds
- After three enemies spawned, the fourth one will have unkillable health and the player will lose and game ends showing the player’s final score

## In-Game Pause game.menus.Menu

- Game pauses when on menu
- Can resume
- Help (controls and how game works)
- Or quit
