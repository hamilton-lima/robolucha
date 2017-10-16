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
- SceneComponent, non player elements




