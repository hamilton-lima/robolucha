package com.robolucha.runner.luchador.lua;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author hamiltonlima
 * 
 */
@SuppressWarnings("restriction")
public class UpdateJavaCodeFromLua {

	LuaVM lua;
	SharedData data;

	@Before
	public void setup() {
		lua = new LuaVM();

		data = new SharedData();
		data.setFloatValue(50.1F);
		data.setDoubleValue(42.42);
		data.setIntValue(42);
		data.setStringValue("foo.bar");
		data.setLongValue(System.currentTimeMillis());
	}

	@Test
	public void testReadValuesInLuaCode() throws Exception {

		lua.put("data", data);

		lua.eval("f = data:getFloatValue()");
		lua.eval("i = data:getIntValue()");
		lua.eval("s = data:getStringValue()");
		lua.eval("l = data:getLongValue()");
		lua.eval("d = data:getDoubleValue()");

		assertEquals(data.getFloatValue(), lua.getFloat("return f"), 0.01);
		assertEquals(data.getIntValue(), lua.getInt("return i"));
		assertEquals(data.getStringValue(), lua.getString("return s"));

		assertEquals(data.getLongValue(), lua.getLong("return l"));
		assertEquals(data.getDoubleValue(), lua.getDouble("return d"), 0.01);
	}

	@Test
	public void testUpdateValuesInLuaCode() throws Exception {

		lua.put("data", data);

		lua.eval("data:setFloatValue(45.3)");
		lua.eval("data:setIntValue(23)");
		lua.eval("data:setStringValue(\"OMG Really.\")");

		assertEquals(45.3, data.getFloatValue(), 0.01);
		assertEquals(23, data.getIntValue());
		assertEquals("OMG Really.", data.getStringValue());

	}

	@Test
	public void testUpdateValuesInLuaCodeWithFunction() throws Exception {

		lua.put("data", data);
		lua.eval("function update(value) data:setStringValue(value) end");

		lua.eval("update(\"lol\")");
		assertEquals("lol", data.getStringValue());

		lua.eval("update(\"again lol\")");
		assertEquals("again lol", data.getStringValue());

	}

}
