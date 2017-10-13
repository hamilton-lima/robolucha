package com.robolucha.old;

import org.apache.log4j.Logger;

import com.robolucha.game.vo.MessageVO;
import com.robolucha.runner.LuchadorRunner;

/**
 * conjunto de metodos permitidos para a execucao do lutchador
 * 
 * @author hamiltonlima
 *
 */
public class JavascriptFacade {

	private static Logger logger = Logger.getLogger(JavascriptFacade.class);
	private LuchadorRunner owner;
	private String lastCall = null;

	public JavascriptFacade(LuchadorRunner owner) {
		this.owner = owner;
	}

	public void move(int amount) {
		lastCall = "move(" + amount + ")";

		if (logger.isDebugEnabled()) {
			logger.debug(lastCall);
			logger.debug(">> executando " + lastCall);
		}
		owner.addMove(amount);
	}

	public void stop() {
		lastCall = "stop()";
		logger.debug(lastCall);
		owner.clearCommand(LuchadorRunner.COMMAND_MOVE);
	}

	public void reset() {
		lastCall = "reset()";
		logger.debug(lastCall);
		owner.clearAllCommands();
	}

	public void turn(int amount) {
		lastCall = "turn(" + amount + ")";
		logger.debug(lastCall);
		owner.addTurn(amount);
	}

	public void turnGun(int amount) {
		lastCall = "turnGun(" + amount + ")";
		logger.debug(lastCall);
		owner.addTurnGun(amount);
	}

	public void fire(int amount) {
		lastCall = "fire(" + amount + ")";
		logger.debug(lastCall);
		owner.addFire(amount);
	}

	public void punch() {
		lastCall = "punch()";
		logger.debug(lastCall);
		owner.punch();
	}

	// public void fix(int lutchador) {
	// lastCall = "fix(" + lutchador + ")";
	// logger.debug(lastCall);
	// owner.fix(lutchador);
	// }

	public void say(String message) {
		lastCall = "say(" + message + ")";
		logger.debug(lastCall);
		owner.say(message);
	}

	public void debug(String message) {
		lastCall = "debug(" + message + ")";
		logger.debug(lastCall);
		owner.addMessage(MessageVO.DEBUG, message);
	}

	public void warning(String message) {
		lastCall = "warning(" + message + ")";
		logger.debug(lastCall);
		owner.addMessage(MessageVO.WARNING, message);
	}

	public void danger(String message) {
		lastCall = "danger(" + message + ")";
		logger.debug(lastCall);
		owner.addMessage(MessageVO.DANGER, message);
	}

	public void setHeadColor(String color) {
		lastCall = "setHeadColor(" + color + ")";
		logger.debug(lastCall);
		owner.setHeadColor(color);
	}

	public void setBodyColor(String color) {
		lastCall = "setBodyColor(" + color + ")";
		logger.debug(lastCall);
		owner.setBodyColor(color);
	}

	public String getLastCall() {
		return lastCall;
	}

}
