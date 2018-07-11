package com.robolucha.runner.luchador;

import java.util.HashMap;
import java.util.List;

import com.robolucha.models.Code;
import com.robolucha.runner.luchador.lua.LuaFacade;

public interface ScriptDefinition {

	HashMap<String, MethodDefinition> getDefaultMethods();

	void afterCompile();

	void run(String name, Object... parameter);

	void eval(String script) throws Exception;

	String getString(String script) throws Exception;

	int getInt(String script) throws Exception;

	double getDouble(String script) throws Exception;

	void set(String name, Object value) throws Exception;

	void loadDefaultLibraries() throws Exception;

	void addFacade(LuaFacade facade);

	//TODO: create interface for the Facade
	LuaFacade buildFacade(LuchadorRunner luchadorRunner);
	
	List<Code> getLuchadorFirstCode();

}
