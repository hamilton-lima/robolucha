Robolucha system architecture
#DEPRECATED

# Components 
- Client
- API
- CMS-API
- Mask-Generator
- Publisher
- Match-Runner
- Match-Scheduler
- Match-Runner-API
- Events-Tracker

> All necessary configuration should be read from environment variables
> All logging level should be changeable with the application running

## Client 
Main client, should offer the following features:
- login with facebook account
- navigate on the latest news
- choose one arena to play 
- navigate on the robolucha luchador API 
- edit code of one luchador
- watch a game live
- edit luchador mask
- search for match results

> technology: angular-cli + typescript

## API
Backend application to support Client features that persists information 
on the database
Notify match runner of changes to the code by publishing luchador updates

> technology: playframework + postgredb + redis

## CMS-API
Content Management System, provides latest news and the API documentation to the site
- reads .md files from folder at google drive
- publishes the files as html to the site
- use folders to create category of posts: main-page, archive

> TBD: Look for standard way to add tags to .md file
> technology: playframework + postgredb + redis

## Mask-Generator
Webservice endpoint to generate the mask image for the luchador based 
on the image specification, it should return in different sizes based on parameters
- keep the last generated masks in cache

> technology: playframework + java

## Publisher
Websocket application to update a client with the current state of an Match.
Read the state from the cache and send the updated match state to 
each client that is connected.

> technology: nodejs + redis

## Match-Runner 
Runs the game itself using the robolucha API definition in Lua
Receives update events from the cache about luchador code updates
Update each match state in the cache at every game cycle
Publish to the cache all the events from the match
Publish final scores when each match ends

## Match-Runner-API
Make all the Database calls to store and read data for the MatchRunner

> technology: java + redis + Lua VM 

## Events-Tracker
Consumes the match events and save to a database
Saves final scores for each match as well

> technology: TBD check temporal databases or postgreedb

## TBD
- document cache and message system usage on Redis
- document map definition files for the match-runner
- define how to monitor the components
