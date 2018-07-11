local ReaderBind = luajava.bindClass("java.io.FileReader")
local BufferBind = luajava.bindClass("java.io.BufferedReader")

local reader = luajava.newInstance("java.io.FileReader", "data/text1.txt");
local buffer = luajava.newInstance("java.io.BufferedReader", reader);

return buffer:readLine()
