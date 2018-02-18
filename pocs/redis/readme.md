# Redis poc for robolucha 

## Projects 

### Java publish and subscribe
    Console Java application using Jedis library to publish and subscribe to multiple queues.

### Node publish and subscribe
    NodeJs typescript application to publish and subscribe to multiple queues.

## Redis docker commands

Description | Command
--- | --- 
How to run redis docker image | docker run -it --rm --name=robolucha-redis -p 6379:6379 -d redis
Open redis console | docker exec -it robolucha-redis bash
Open redis-cli console | docker exec -it robolucha-redis redis-cli
Stop redis | docker stop robolucha-redis 

## Redis-CLI pub/sub sequence

```
SUBSCRIBE queue1
PUBLISH queue1 "some data"
PUBLISH queue2 "some other data"
UNSUBSCRIBE
```

## References

- [Redis docker](https://docs.docker.com/samples/library/redis/)
- [Java redis client](https://github.com/xetorthio/jedis/)
- [REDIS - A NODE.JS REDIS CLIENT](http://redis.js.org/#api-publish-subscribe)
- [Redis publish and subscribe commands](https://redis.io/topics/pubsub)