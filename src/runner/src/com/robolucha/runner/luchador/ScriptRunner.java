package com.robolucha.runner.luchador;

import java.util.function.Function;

import javax.naming.Context;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ScriptRunner implements Runnable {

	private static Logger logger = Logger.getLogger(ScriptRunner.class);

	private Object[] parameter;
	private String name;
	private LuchadorRunner luchadorRunner;

	private ScriptRunner(LuchadorRunner luchadorRunner, String name, Object... parameter) {
		this.name = name;
		this.parameter = parameter;
		this.luchadorRunner = luchadorRunner;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("ScriptRunner-Thread-GameComponentID-" + luchadorRunner.gameComponent.getId());

		if (logger.isDebugEnabled()) {
			logger.debug("START run(" + name + "," + parameter + ")");
		}

		
		Object result = null;
		
		//TODO: convert to attr?
		long elapsed = 0L;
		long start = 0L;

		try {
			start = System.currentTimeMillis();
			currentJavascriptRunningName = name;
			Context cx = Context.enter();
			cx.setClassShutter(new RhinoWhiteList());

			Object fObj = scope.get(name, scope);

			if (!(fObj instanceof Function)) {
				if (logger.isDebugEnabled()) {
					logger.debug("function " + name + " is empty or not found");
				}
				// currentJavascriptRunningName = null;
				// luchadorRunner.exceptionCounter++;
				// throw new
				// NoSuchMethodException("server.exception.function.notfound="
				// + name);
			} else {
				Function f = (Function) fObj;

				if (logger.isDebugEnabled()) {
					logger.debug("== executando funcao " + name + "()");
				}

				result = f.call(cx, scope, null, parameter);
			}
		} catch (Exception e) {
			String message = "server.exception.error.running=run(" + name + "," + parameter + ") " + e.getMessage();
			if (logger.isDebugEnabled()) {
				logger.error(message, e);
			} else {
				logger.error(message);
			}

			luchadorRunner.exceptionCounter++;
			luchadorRunner.saveExceptionToCode(name, e.getMessage());

		} finally {
			try {
				Context.exit();
				// cx = null;
			} catch (IllegalStateException e) {
				if (logger.isEnabledFor(Level.WARN)) {
					logger.warn(
							"desligando contexto javascript invalido executando : run(" + name + "," + parameter + ")",
							e);
				}
			}
		}

		logger.debug("END run(), result=" + result);

		elapsed = System.currentTimeMillis() - start;
		currentJavascriptRunningName = null;

		// libera para a proxima execucao
		currentRunner = null;

		// libera referencia
		this.name = null;
		this.parameter = null;
		this.luchadorRunner = null;
	}

}
