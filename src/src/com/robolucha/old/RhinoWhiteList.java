package com.robolucha.old;

import org.apache.log4j.Logger;
import org.mozilla.javascript.ClassShutter;

public class RhinoWhiteList implements ClassShutter {

	private static Logger logger = Logger.getLogger(RhinoWhiteList.class);

	private String[] visibleClasses = { "com.robolucha.game.JavascriptFacade",
			"com.robolucha.game.LuchadorPublicState" };

	public boolean visibleToScripts(String className) {
		logger.debug("START visibleToScripts(), className=" + className);

		for (int i = 0; i < visibleClasses.length; i++) {
			if (className.equals(visibleClasses[i])) {
				logger.debug("ok");
				return true;
			}
		}
		logger.debug("NOK");
		return false;
	}

}
