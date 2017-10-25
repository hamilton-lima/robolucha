package com.robolucha.runner.luchador.lua;

import java.io.FileInputStream;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.parser.LuaParser;

@SuppressWarnings("restriction")
public class LuajEngineWithoutRestrictions {

	ScriptEngineManager sem ;
	ScriptEngine e;
	ScriptEngineFactory f;
	
	public LuajEngineWithoutRestrictions() {
		 sem = new ScriptEngineManager();
		 e = sem.getEngineByName("luaj");
		 f = e.getFactory();
	}

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
