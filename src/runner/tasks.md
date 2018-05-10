#Todo

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

* MatchStatePublisher
    missing review of getMessages - probably will be resolved by the MatchEventHandler
    how the events are sent?
        MatchEventPublisher - receive all the events
    where statepublisher is assigned to the matchrunner?


* build redis docker locally to test
* keep score updates in the MatchRunner

# Done 
* copy unit tests from original project
* remove compilation errors from unit tests
    * working on GeneralEventManagerTest
    
* remove duplicated calc

