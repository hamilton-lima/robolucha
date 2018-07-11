package com.robolucha.runner.luchador.lua;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeepIndependentStates {

	LuaVM vm1;
	LuaVM vm2;
	
	@Before
	public void setup() {
		 vm1 = new LuaVM();
		 vm2 = new LuaVM();
	}
	
	@Test
	public void testInt() throws Exception {
		vm1.exec("a = 10");
		vm2.exec("a = 20");
	
		assertEquals(10, vm1.getInt("return a"));
		assertEquals(20, vm2.getInt("return a"));
	
	}

	@Test
	public void testString() throws Exception {
		vm1.exec("a = 'foo' ");
		vm2.exec("a = 'bar' ");
	
		assertEquals("foo", vm1.getString("return a"));
		assertEquals("bar", vm2.getString("return a"));
	}

}
