#Todo

* Understand current state/events publising 
    * MatchStatePublisher
        - iterate over MatchRunner / LuchadorRunner to get the current match state
        - method getMessages collects a list of events 
         
    - MatchEventPublisher 
        - receive all the events
        - is assigned to MatchRunner at Server.buildRunner()
        - need to update the score!

* Publish state/events using RemoteQueue

* Change ScoreUpdater to change the score in the LuchadorRunner
or keep the score available to the statepublisher
    
* Implement golang publisher that reads from matchstate and 
updates the active clients with the state

* Implement angular client connected to publisher to listen to the 
match updates

* Update unit tests to use lua instead of javascript 

* Rewrite API documentation with lua examples

# Done 

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

