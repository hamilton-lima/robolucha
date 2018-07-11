package com.robolucha.runner.luchador.lua;

import org.junit.Before;
import org.junit.Test;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.ast.Chunk;

import static org.junit.Assert.*;

public class MaliciousCodeHandler {

	LuaVM restrictedLua;

	@Before
	public void setup() {
		restrictedLua = new LuaVM();
	}

	@Test
	@SuppressWarnings("unused")
	public void testJavaThreads() throws Exception {
		String script = "lua/java-threads.lua";
		LuajEngineWithoutRestrictions noRestriction = new LuajEngineWithoutRestrictions();
		noRestriction.runFromFile(script);

		try {
			Varargs result = restrictedLua.runFromFile(script);
			fail("This should generate an exception");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void testJavaFileReader() throws Exception {
		String script = "lua/filereader.lua";
		LuajEngineWithoutRestrictions noRestriction = new LuajEngineWithoutRestrictions();
		String text = (String) noRestriction.runFromFile(script);
		assertEquals("foo.bar", text);

		try {
			Varargs result = restrictedLua.run(script);
			fail("This should generate an exception");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@SuppressWarnings("unused")
	public void testForever() throws Exception {
		String script = "lua/forever.lua";
		LuajEngineWithoutRestrictions noRestriction = new LuajEngineWithoutRestrictions();
		Chunk chunk = noRestriction.compileFromFile(script);
		assertNotNull(chunk);

		try {
			Varargs result = restrictedLua.runFromFile(script);
			fail("This should generate an exception");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
