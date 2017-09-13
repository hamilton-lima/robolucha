package com.robolucha.poc.luaj;

import static org.junit.Assert.assertEquals;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author hamiltonlima
 * @see https://github.com/luaj/luaj/blob/master/examples/jse/ScriptEngineSample.java
 * 
 */
public class UpdateJavaCodeFromLua {

	ScriptEngineManager sem;
	ScriptEngine e;
	SharedData data;

	@Before
	public void setup() {
		sem = new ScriptEngineManager();
		e = sem.getEngineByName("luaj");
		// ScriptEngineFactory f = e.getFactory();
		
		data = new SharedData();
		data.setFloatValue(50.1F);
		data.setIntValue(42);
		data.setStringValue("foo.bar");
	}

	@Test
	public void testReadValuesInLuaCode() throws ScriptException {

		e.put("data", data);

		e.eval("f = data:getFloatValue()");
		e.eval("i = data:getIntValue()");
		e.eval("s = data:getStringValue()");

		assertEquals(data.getFloatValue(), (Double) e.get("f"), 0.01);

		assertEquals(data.getIntValue(), e.get("i"));
		assertEquals(data.getStringValue(), e.get("s"));

	}

	@Test
	public void testUpdateValuesInLuaCode() throws ScriptException {

		e.put("data", data);

		e.eval("data:setFloatValue(45.3)");
		e.eval("data:setIntValue(23)");
		e.eval("data:setStringValue(\"OMG Really.\")");

		assertEquals(45.3, data.getFloatValue(), 0.01);

		assertEquals(23, data.getIntValue());
		assertEquals("OMG Really.", data.getStringValue());

	}
	
	@Test
	public void testUpdateValuesInLuaCodeWithFunction() throws ScriptException {

		e.put("data", data);
		e.eval("function update(value) data:setStringValue(value) end");

		e.eval("update(\"lol\")");
		assertEquals("lol", data.getStringValue());

		e.eval("update(\"again lol\")");
		assertEquals("again lol", data.getStringValue());

	}

}
