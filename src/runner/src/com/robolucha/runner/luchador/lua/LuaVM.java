package com.robolucha.runner.luchador.lua;

import java.io.FileReader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;

/**
 * Adapted from LuaJ sandboxed example
 * 
 * @author hamiltonlima
 * @see https://github.com/luaj/luaj/blob/master/examples/lua/samplesandboxed.lua
 * 
 */
public class LuaVM {

	private static final int DEFAULT_MAX_INSTRUCTIONS = 200;
	
	private static Globals compiler = setupCompilerGlobal();

	private static Globals setupCompilerGlobal() {
		// Create server globals with just enough library support to compile user
		// scripts.
		compiler = new Globals();
		compiler.load(new JseBaseLib());
		compiler.load(new PackageLib());
		compiler.load(new StringLib());

		// To load scripts, we occasionally need a math library in addition to compiler
		// support.
		// To limit scripts using the debug library, they must be closures, so we only
		// install LuaC.
		compiler.load(new JseMathLib());
		LoadState.install(compiler);
		LuaC.install(compiler);

		// Set up the LuaString metatable to be read-only since it is shared across all
		// scripts.
		LuaString.s_metatable = new ReadOnlyLuaTable(LuaString.s_metatable);
		return compiler;
	}

	private Globals getLocalCompiler() {
		Globals user_globals = new Globals();
		user_globals.load(new JseBaseLib());
		user_globals.load(new PackageLib());
		user_globals.load(new Bit32Lib());
		user_globals.load(new TableLib());
		user_globals.load(new StringLib());
		user_globals.load(new JseMathLib());

		user_globals.load(new DebugLib());
		sethook = user_globals.get("debug").get("sethook");
		user_globals.set("debug", LuaValue.NIL);

		return user_globals;
	}

	private LuaValue sethook;
	private LuaValue hookfunc;
	private Globals localState;
	private int maxInstructions = DEFAULT_MAX_INSTRUCTIONS;

	LuaVM() {
		this.hookfunc = new ZeroArgFunction() {
			public LuaValue call() {
				System.out.println("script overan resource limits");
				// A simple lua error may be caught by the script, but a
				// Java Error will pass through to top and stop the script.
				throw new Error("Script overran resource limits.");
			}
		};

		this.localState = getLocalCompiler();
	}
	
	LuaVM(int maxInstructions) {
		this();
		this.maxInstructions = maxInstructions;
	}

	private Varargs run(LuaValue chunk) throws Exception {

		LuaThread thread = new LuaThread(localState, chunk);

		// Set the hook function to immediately throw an Error, which will not be
		// handled by any Lua code other than the coroutine.

		sethook.invoke(LuaValue.varargsOf(
				new LuaValue[] { thread, hookfunc, LuaValue.EMPTYSTRING, LuaValue.valueOf(maxInstructions) }));

		// When we resume the thread, it will run up to 'instruction_count' instructions
		// then call the hook function which will error out and stop the script.
		Varargs result = thread.resume(LuaValue.NIL);
		if( result.checkboolean(1) ) {
			return result;
		} else {
			throw new Exception(result.arg(2).toString());
		}
	}
	
	Varargs run(String script) throws Exception {
		LuaValue chunk = compiler.load(script, "main", localState);
		return run(chunk);
	}

	void exec(String script) throws Exception {
		run(script);
	}
	
	public Varargs runFromFile(String filename) throws Exception {
		FileReader reader = new FileReader(filename);
		LuaValue chunk = compiler.load(reader, "main", localState);
		return run(chunk);
	}


	String getString(String script) throws Exception {
		return run(script).arg(2).toString();
	}

	double getDouble(String script) throws Exception {
		return run(script).todouble(2);
	}

	int getInt(String script) throws Exception {
		return run(script).toint(2);
	}

	// Simple read-only table whose contents are initialized from another table.
	static class ReadOnlyLuaTable extends LuaTable {
		public ReadOnlyLuaTable(LuaValue table) {
			presize(table.length(), 0);
			for (Varargs n = table.next(LuaValue.NIL); !n.arg1().isnil(); n = table.next(n.arg1())) {
				LuaValue key = n.arg1();
				LuaValue value = n.arg(2);
				super.rawset(key, value.istable() ? new ReadOnlyLuaTable(value) : value);
			}
		}

		public LuaValue setmetatable(LuaValue metatable) {
			return error("table is read-only");
		}

		public void set(int key, LuaValue value) {
			error("table is read-only");
		}

		public void rawset(int key, LuaValue value) {
			error("table is read-only");
		}

		public void rawset(LuaValue key, LuaValue value) {
			error("table is read-only");
		}

		public LuaValue remove(int pos) {
			return error("table is read-only");
		}
	}

	
}
