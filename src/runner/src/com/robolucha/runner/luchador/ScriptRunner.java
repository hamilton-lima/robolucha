package com.robolucha.runner.luchador;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ScriptRunner implements Runnable {

	private static Logger logger = Logger.getLogger(ScriptRunner.class);

	private Object[] parameter;
	private String name;
	private String currentName;
	private LuchadorRunner runner;

	ScriptRunner(LuchadorRunner runner, String name, Object... parameter) {
		this.name = name;
		this.parameter = parameter;
		this.runner = runner;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("ScriptRunner-Thread-GameComponentID-" + runner.getGameComponent().getId());

		if (logger.isDebugEnabled()) {
			logger.debug("START run(" + name + "," + parameter + ")");
		}

		Object result = null;

		long elapsed = 0L;
		long start = 0L;

		try {
			start = System.currentTimeMillis();
			currentName = name;
			if (logger.isDebugEnabled()) {
				logger.debug("== running function " + name + "()");
			}

			runner.getScriptDefinition().run(name, parameter);
			
		} catch (Exception e) {
			String message = "server.exception.error.running=run(" + name + "," + parameter + ") " + e.getMessage();
			if (logger.isDebugEnabled()) {
				logger.error(message, e);
			} else {
				logger.error(message);
			}

			runner.exceptionCounter++;
			runner.saveExceptionToCode(name, e.getMessage());

		} finally {
			try {
				runner.getScriptDefinition().afterCompile();
			} catch (Exception e) {
				if (logger.isEnabledFor(Level.WARN)) {
					logger.warn("error aftercompile running : run(" + name + "," + parameter + ")", e);
				}
			}
		}

		logger.debug("END run(), result=" + result);

		elapsed = System.currentTimeMillis() - start;
		currentName = null;

		this.name = null;
		this.parameter = null;
		this.runner = null;
	}

	public String getCurrentName() {
		return currentName;
	}

}
