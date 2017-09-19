package com.robolucha.poc.luaj;

import java.io.FileInputStream;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.parser.LuaParser;

public class LuajEngineWithoutRestrictions {

	ScriptEngineManager sem = new ScriptEngineManager();
	ScriptEngine e = sem.getEngineByName("luaj");
	ScriptEngineFactory f = e.getFactory();

	Object run(String script) throws Exception {
		return e.eval(script);
	}
	
	Object runFromFile(String filename) throws Exception {
		FileReader reader = new FileReader(filename);
		return e.eval(reader);
	}
	
	Chunk compileFromFile(String filename) throws Exception {
		LuaParser parser = new LuaParser(new FileInputStream(filename));
		Chunk chunk = parser.Chunk();
		return chunk;
	}
	
}
