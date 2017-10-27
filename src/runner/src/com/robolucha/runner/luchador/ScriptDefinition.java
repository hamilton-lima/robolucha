package com.robolucha.runner.luchador;

import java.util.HashMap;

public interface ScriptDefinition {

	HashMap<String, MethodDefinition> getDefaultMethods();

	void afterCompile();

	void run(String name, Object... parameter);

	void eval(String script) throws Exception;

	String getString(String script) throws Exception;

	int getInt(String script) throws Exception;

	double getDouble(String script) throws Exception;

}
