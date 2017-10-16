# Models to represent game state 


# API
- LuchadorCoach, the end user
- Luchador, the characte	r
- MaskConfig, luchador mask configuration
- Code, each action has one code
- CodeHistory, everytime a code is created/updated should save one record at codeHistory

# Events Tracker
- Match, the match itself
- MatchEvent, events in a match
- MatchScore, scores from the match
- MatchParticipant, luchador participation in a match

- Ranking, ranking name
- RankingData, ranking numbers generated from a Match


# Match Runner API

- Game, schedule of matchs to run based on date and time and ranking

# Match Runner 

- GameDefinition, rules to the game, instances will be stored in a json file
- Bullet

- GameComponent, super class of elements that are inserted in the game
List<code> codes
name -> can be null

- GameComponentAttributes, created to support the map editor, x,y, width and height 
	TODO: should be used in the super class of NPC and static gamecomponents
- GameDefinitionNPC, association of GameDefinition and NPC
	TODO: move this to an array of NPC in the json file
	
- GameElement, static elements in the game
	TODO: create a class child of GameComponent that is not an NPC

- NPC, non player character that will have code to move around the arena
	TODO: use another name that can acomodate moving and static elements, as both will have code
- Obstacle, child of NPC with position, 
	TODO: should use the same class? 
	TODO: child classes and move attributes up
	TODO: create class to represent scenario elements also child of GameComponent with code
	
- ObstacleAttacker, child of Obstacle with damage set
- ObstacleDestroyable, child of Obstacle with life set	

TODO: Create this class to define NPC and other scene elements
- SceneComponent extends GameComponent
x,y,
width,height
rotation
respawn:boolean
colider: boolean 
showinRadar: boolean
blockMovement: boolean
life: int
damage: int -> amount of damage that does
type: string -> to the radar

