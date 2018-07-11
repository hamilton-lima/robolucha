#Todo

* Implement golang publisher that reads from matchstate and 
updates the active clients with the state
    - broadcast the matchstate from redis to the active connections

    - add logging to golang app
        - https://github.com/sirupsen/logrus
    

* Evaluate code analysis
    - Codacy vs sonarcloud
    - Update sonarcloud automation to publish unit test coverage 
    https://docs.sonarqube.org/display/PLUG/Code+Coverage+by+Unit+Tests+for+Java+Project

* Implement e2e test of MatchStatePublisher
    - run match with events to check for the matchstate to be published
    
* Test ScoreUpdater change the calculate in the LuchadorRunner

* Fix unit tests

* Update TravisCI

* Use CheckBulletHitActionTest as reference to fix unit tests 


* check how to listen to the code change events 
LuchadorCodeChangeListener

* Implement angular client connected to publisher to listen to the 
match updates

* Update unit tests to use lua instead of javascript 

* Rewrite API documentation with lua examples

* Redesign listeners to use Promises and Observables

* implement MatchRunnerAPI

* remove toString methods


# Done 

* Implement golang publisher that reads from matchstate and 
updates the active clients with the state
    - start redis 
        - docker run -it --rm --name=robolucha-redis -p 6379:6379 -d redis
        - docker exec -it robolucha-redis redis-cli
        - PUBLISH c1 "some data"

    - go routine that read from Redis 

    - track active connections, use struct to save the game id
        - Fix RemoveSession() from SessionManager - unit test it

* Subscribe to matchRunner.getOnMessage() to send luchadorRunner messages
    - clone MatchEventPublisher
    - Fix unit tests related to onMessage from LuchadorRunner 

* MatchStatePublisher
    - Send state to Redis using RemoteQueue
    - Test it 

* Document listeners
- MatchEventHandler 
    - no interface, init, start, alive, damage, end
    - send the event for each of the MatchEventListener
        - ScoreUpdater (onkill, ondamage) = internal update
        - MatchEventPublisher = send data to remotequeue
        

* MatchEventPublisher
    - Send events to Redis using RemoteQueue

* MatchEventPublisher
    - Change ScoreUpdater to change the score in the LuchadorRunner
      or keep the score available to the statepublisher

        - MatchRunner gameDefinition.getMinParticipants()
        trigger event using observable to facilitate the test
        of scoreupdater and others that check if the matchstarted
        
        - (!) validate how to check for errors 
        on match.getMatchStart().subscribe() 
        use CalcTest as reference.
        
        add throwable -> {fail(throwable.getMessage());}
        as second parameter to subscribe 

* Understand current state/events publising 
    * MatchStatePublisher
        - iterate over MatchRunner / LuchadorRunner to get the current match state
        - method getMessages collects a list of events 
         
    - MatchEventPublisher 
        - receive all the events
        - is assigned to MatchRunner at Server.buildRunner()
        - need to update the score!

* understand MatchEventHandler 
    - notifies matchEventListeners when any event happens 

* unit test RemoteQueue using redis docker 
    * wrap command line to start and stop in a unit test class 

```
docker run --name test-redis -it --rm -p 6379:6379 redis 
docker stop test-redis
```

* Add RemoteQueue
    * Generate queue names based on the class that is 
    subscribed

* Add redis configuration
    queue name
    host

* build redis docker locally to test
    
* copy unit tests from original project
* remove compilation errors from unit tests
    * working on GeneralEventManagerTest
    
* remove duplicated calc

