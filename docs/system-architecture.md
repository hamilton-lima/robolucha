Robolucha system architecture

# Components 
- Client
- Api
- CMS-Api
- Mask-Generator
- Publisher
- Match-runner
- Match-scheduler
- Events-tracker

> Every component should have its own docker machine 
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

## Api
Backend application to support Client features that persists information 
on the database
Is should publish changes to the code on the cache

> technology: playframework + postgredb + redis

## CMS-Api
Content Management System, provides latest news and the API documentation to the site
- reads folder from google drive with .md files
- publishes the files as html to the site
- use folders to create category of posts: main-page, archive

> TBD: Look for standard way to add tags to .md file
> technology: playframework + postgredb + redis

## Mask-Generator
Webservice endpoint to generate the mask image for the luchador based 
on the image specification, it should return in different sizes based on parameters
- It should use the cache to keep the last generated masks in memory

> technology: playframework + java

## Publisher
Websocket application to update a client with the current state of an Match.
It should read the state from the cache and send the updated match state to 
each client that is connected.

> technology: nodejs + redis

## Match-Runner 
Runs the game itself using the robolucha API definition in Lua
It should be able to receive update events from the cache about luchador code updates
It should update each match state in the cache at every game cycle.
It should publish to the cache all the events from the match
It should publish final scores when each match ends

> technology: java + redis + Lua VM

## Events-tracker
Consumes the match events and save to a database
Saves final scores for each match as well

> technology: TBD check temporal databases or postgreedb

## TBD
- document cache and message system usage on Redis
- document mapdefinition files 
- define how to monitor the Components
