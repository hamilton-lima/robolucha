package com.robolucha.runner.luchador;

import com.robolucha.runner.luchador.lua.LuaScriptDefinition;

public class ScriptDefinitionFactory {
	private static ScriptDefinitionFactory instance = new ScriptDefinitionFactory();

	private ScriptDefinitionFactory() {

	}

	public static ScriptDefinitionFactory getInstance() {
		return instance;
	}

	ScriptDefinition getDefault() {
		return new LuaScriptDefinition();
	}
}
