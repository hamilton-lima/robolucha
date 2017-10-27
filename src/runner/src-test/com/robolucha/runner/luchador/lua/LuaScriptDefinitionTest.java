package com.robolucha.runner.luchador.lua;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.robolucha.runner.luchador.MethodDefinition;
import com.robolucha.runner.luchador.MethodNames;

public class LuaScriptDefinitionTest {

	LuaScriptDefinition definition;

	@Before
	public void setup() {
		definition = new LuaScriptDefinition();
	}

	@Test
	public void testGetDefaultMethods() throws IllegalArgumentException, IllegalAccessException {
		
		HashMap<String, MethodDefinition> methods = definition.getDefaultMethods();
		Field[] fields = MethodNames.class.getFields();
		for (Field field : fields) {
			String name = field.getName();
			String value = (String) field.get(null);
			
			System.out.println("checking for default methods of: " + name+ "=" + value);
			MethodDefinition methodDefinition = methods.get(value);
			assertNotNull(methodDefinition);

			if( value.equals("start")) {
				assertTrue(methodDefinition.getStart().length() == 0 );
				assertTrue(methodDefinition.getEnd().length() == 0 );
				
			} else {
				assertTrue(methodDefinition.getStart().startsWith("function "));
				assertTrue(methodDefinition.getEnd().endsWith("end") );
			}
		}
		
	}

	@Test
	public void testRunWithNoParameter() throws Exception {

		String start = "a = 'start'";
		String nop = "function nop() a = 'OMG' end";

		definition.eval(start);
		definition.eval(nop);

		definition.run("nop");

		assertEquals("OMG", definition.getString("return a"));
	}

	@Test
	public void testRunWithOneParameter() throws Exception {

		String start = "a = 'start'";
		String one = "function one(value) a = value.stringValue end";

		definition.eval(start);
		definition.eval(one);

		PublicSharedData data = new PublicSharedData();
		data.stringValue = "hey";
		definition.run("one", data);

		assertEquals("hey", definition.getString("return a"));
	}

	@Test
	public void testRunWithTwoParameter() throws Exception {
		String start = "a = 'start' b = 10";
		String two = "function two(value, intValue) a = value.stringValue b = intValue end";

		definition.eval(start);
		definition.eval(two);

		PublicSharedData data = new PublicSharedData();
		data.stringValue = "hey";
		definition.run("two", data, 42);

		assertEquals("hey", definition.getString("return a"));
		assertEquals(42, definition.getInt("return b"));
	}

	@Test
	public void testRunWithThreeParameter() throws Exception {
		String start = "a = 'start' b = 10 c = 23.5";
		String three = "function three(value, intValue, doubleValue) a = value.stringValue b = intValue c = doubleValue end";

		definition.eval(start);
		definition.eval(three);

		PublicSharedData data = new PublicSharedData();
		data.stringValue = "hey";
		data.doubleValue = 12.8;

		definition.run("three", data, 42, 12.8);

		assertEquals("hey", definition.getString("return a"));
		assertEquals(42, definition.getInt("return b"));
		assertEquals(12.8, definition.getDouble("return c"), 0.001);
	}

	@Test
	public void testRunWithMoreThan3Parameters() throws Exception {
		String start = "a = 'start' b = 10 c = 23.5";
		String four = "function four(value, intValue, doubleValue, x) a = value.stringValue b = intValue c = doubleValue end";

		definition.eval(start);
		definition.eval(four);

		PublicSharedData data = new PublicSharedData();
		data.stringValue = "hey";
		data.doubleValue = 12.8;

		try {
			definition.run("four", data, 42, 12.8, 1);
			fail("This should generate an exception");
		} catch (Exception e) {

		}

	}

	@Test
	public void testAfterCompile() {
		definition.afterCompile();
	}

	@Test
	public void testRun() {
		assertTrue(definition != null);
	}

}
