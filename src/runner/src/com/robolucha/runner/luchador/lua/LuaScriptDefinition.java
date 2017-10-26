package com.robolucha.runner.luchador.lua;

import java.util.HashMap;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.robolucha.runner.luchador.MethodDefinition;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.runner.luchador.ScriptDefinition;

public class LuaScriptDefinition implements ScriptDefinition {

	private HashMap<String, MethodDefinition> methods;
	private LuaVM lua;

	@Override
	public HashMap<String, MethodDefinition> getDefaultMethods() {
		return methods;
	}

	public LuaScriptDefinition() {
		methods = new HashMap<String, MethodDefinition>();

		add(MethodNames.START, "", "");
		add(MethodNames.REPEAT, "function repeat()\n   //empty\n", "\nend");
		add(MethodNames.ON_HIT_WALL, "function onHitWall()\n   //empty\n", "\nend");
		add(MethodNames.ON_HIT_OTHER, "function onHitOther(other)\n   //empty\n", "\nend");
		add(MethodNames.ON_FOUND, "function onFound(other,chance)\n   //empty\n", "\nend");
		add(MethodNames.ON_GOT_DAMAGE, "function onGotDamage(other,amount)\n   //empty \n", "\nend");
		add(MethodNames.ON_LISTEN, "function onListen(other,message)\n   //empty \n", "\nend");

		lua = new LuaVM();
	}

	private void add(String name, String start, String end) {
		methods.put(name, new MethodDefinition(name, start, end));
	}

	@Override
	public void afterCompile() {

	}

	@Override
	public void run(String name, Object... parameter) {

		LuaFunction function = lua.getFunction(name);
		if (parameter == null) {
			function.call();
		} else {
			if (parameter.length == 1) {
				function.call(CoerceJavaToLua.coerce(parameter[0]));
			}
			if (parameter.length == 2) {
				function.call(CoerceJavaToLua.coerce(parameter[0]), CoerceJavaToLua.coerce(parameter[1]));
			}
			if (parameter.length == 3) {
				function.call(CoerceJavaToLua.coerce(parameter[0]), CoerceJavaToLua.coerce(parameter[1]),
						CoerceJavaToLua.coerce(parameter[2]));
			}
		}

	}

}
