# Proof of Concept - Luaj

This project is to evaluate LuaJ as the Language interpreter for Robolucha

The following concepts will be evaluated:

- update java object from lua code, use eval and put 
![ScriptEngineSample.java](https://github.com/luaj/luaj/blob/master/examples/jse/ScriptEngineSample.java)

- keep multiple independent states, use JsePlatform.standardGlobals(); 
from ![SampleMultiThreaded.java](https://github.com/luaj/luaj/blob/master/examples/jse/SampleMultiThreaded.java)    

- get java object state updated in another thread in lua code

- white list of java code access
![SampleSandboxed.java](https://github.com/luaj/luaj/blob/master/examples/jse/SampleSandboxed.java)
![SampleUsingClassLoader.java](https://github.com/luaj/luaj/blob/master/examples/jse/SampleUsingClassLoader.java)

