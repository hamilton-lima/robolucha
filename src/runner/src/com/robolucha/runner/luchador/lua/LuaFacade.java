package com.robolucha.runner.luchador.lua;

import org.apache.log4j.Logger;

import com.robolucha.game.vo.MessageVO;
import com.robolucha.runner.luchador.LuchadorRunner;

/**
 * 
 * @author hamiltonlima
 *
 */
public class LuaFacade {

	private static Logger logger = Logger.getLogger(LuaFacade.class);
	private LuchadorRunner owner;
	private String lastCall = null;

	public LuaFacade(LuchadorRunner owner) {
		this.owner = owner;
	}

	public void move(int amount) {
		lastCall = "move(" + amount + ")";

		if (logger.isDebugEnabled()) {
			logger.debug(lastCall);
			logger.debug(">> running " + lastCall);
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

	//TODO: restrict String size
	public void say(String message) {
		lastCall = "say(" + message + ")";
		logger.debug(lastCall);
		owner.say(message);
	}

	//TODO: restrict String size
	public void debug(String message) {
		lastCall = "debug(" + message + ")";
		logger.debug(lastCall);
		owner.onMessage(MessageVO.DEBUG, message);
	}

	//TODO: restrict String size
	public void warning(String message) {
		lastCall = "warning(" + message + ")";
		logger.debug(lastCall);
		owner.onMessage(MessageVO.WARNING, message);
	}

	//TODO: restrict String size
	public void danger(String message) {
		lastCall = "danger(" + message + ")";
		logger.debug(lastCall);
		owner.onMessage(MessageVO.DANGER, message);
	}

	//TODO: restrict String size
	public void setHeadColor(String color) {
		lastCall = "setHeadColor(" + color + ")";
		logger.debug(lastCall);
		owner.setHeadColor(color);
	}

	//TODO: restrict String size
	public void setBodyColor(String color) {
		lastCall = "setBodyColor(" + color + ")";
		logger.debug(lastCall);
		owner.setBodyColor(color);
	}

	public String getLastCall() {
		return lastCall;
	}

}
