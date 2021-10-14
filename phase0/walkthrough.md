## 1-2 Paragraph Version

The game is controlled by World Entity and it stores the coordinates of all objects created. After the player press the start button, the game launched by the GameManager and the levelManager will reset the level and leverage the spawn controller to spam enemies. The player spawn in the middle of the screen and can move around through the KeyboardInputHandler. Every in-game character has health, attack power, position as the base attribute. Some of them are can take on damage (the enemies) while some doesn't (defendants). The damage is determined by collision. The player is able to build defenses during the game to attack enemies so they don't reach castle. Each defendent is "equipped" with a weapon that determine the amount of damage it cand dealt. During the game, the player can build defenders who are able to shoot "ballet/weapons" and the damages is observed when there's a collision between the damagable colliable, which is when the bullet hits the enemies. If the player fails to kill the enmeies and they reach the castle, the game ends and the ending screen with the stored high score is presented. If the player is able to kill all the enemies, the currentLevel will be updated and a new wave of enemy will spawn.

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
