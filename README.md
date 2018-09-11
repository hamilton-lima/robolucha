# Robolucha

## Overview
Robolucha is a game where you define how your virtual robots, a.k.a. LUCHADORES, will behave in a virtual arena, while playing you learn how to code. Have fun. 

See here how all started: [The birth of robolucha @Medium](https://medium.com/@athanazio/the-born-of-robolucha-90431401cab4)

### Implementation overview 

![Robolucha Overview][overview]

[overview]: https://raw.githubusercontent.com/hamilton-lima/robolucha/master/docs/robolucha-overview.png

## Projects

To be updated

## Roadmap 

### Release 1

Done
- Cleanup actions
    - Move runner to separated project
    - create api project
    - move pocs to robolucha-sandbox

Todo    
- Docker images
    - www, main page before login
    - game, page after login with the game details
    - api
    - publisher, reads redis and send data to the active clients
    - redis, not exposed
    - mariadb, not exposed
    - runner, not exposed
- Deployment
- Public API 
    - /public/login
- Private API 
    - validate session on every call
    - /private/matches, to list the active matches
    - /private/matches/{id}/join, join a match and publish to Redis
    - /private/luchador, to get the current luchador code
    - /private/luchador, put to update the luchador
    - /private/match{id}, to subscribe to websocket updates of matchstate
- Internal API 
    - validate API key on every call
    - set API on runner using environment variable
    - /internal/match, to create match
    - /internal/match/{id} to end match
    - /internal/luchador/{id} to get luchador details, when a luchador join the match or there is an update to a luchador
- Game interface
    - Login page
    - List of active matches with option to join
    - Arena display matchstate, using 3D cubes
    - Score updated by matchstate
    - Display End game when matchstate indicates
- Runner
    - Create a match when start
    - Publish matchstate on every server tick
    - Listen to joinMatch events
    - Listen to luchadorUpdate events
    - Update match with matchend flag

### Release 2 

- Mask Editor
- Listen to matchevent
- Show debug information from luchador code
- Luchador API documentation
- Replace Luchador Cubes with 3D Model
- Increase unit test coverage

### Release 3 

- Map Creator
- Map API 
- Upload custom 3D models
- Increase unit test coverage
