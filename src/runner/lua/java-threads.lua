local threadC = luajava.bindClass("java.lang.Thread")
local thread = luajava.newInstance("java.lang.Thread");
thread:start()