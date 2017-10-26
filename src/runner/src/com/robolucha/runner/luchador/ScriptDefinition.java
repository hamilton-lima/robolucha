package com.robolucha.runner.luchador;

import java.util.HashMap;

public interface ScriptDefinition {
	
	HashMap<String, MethodDefinition> getDefaultMethods();

	void afterCompile();

	void run(String name, Object... parameter);

}
