package com.robolucha.runner.luchador;

import org.apache.log4j.Logger;

public class LuchadorCommand {

	private String command;

	private double originalValue;
	private double value;
	private int direction;
	private int speed;

	private static Logger logger = Logger.getLogger(LuchadorCommand.class);

	public LuchadorCommand(String command, double originalValue, int speed) {

		this.command = command;
		this.originalValue = originalValue;
		this.value = this.originalValue;
		this.speed = speed;
		calculateDirection();

		// corrige valor para poder consumir
		if (this.value < 0) {
			this.value = this.value * -1;
		}
	}
	
	private void calculateDirection() {
		if (this.originalValue < 0) {
			this.direction = -1;
		} else {
			this.direction = 1;
		}
	}

	public String getKey() {
		return this.command + "." + this.direction;
	}

	/**
	 * acumula valores do comando se execucao for no mesmo tick do servidor e
	 * for da mesma origem e sinal for o mesmo.
	 * 
	 * @param add
	 * @return
	 */
	// public LuchadorCommand addOnlyInTheSameTick(LuchadorCommand add) {
	//
	// if (logger.isDebugEnabled()) {
	// logger.debug("addOnlyInTheSameTick()");
	// logger.debug("add=" + add);
	// logger.debug("current=" + this);
	// }
	//
	// /**
	// * se tentar reinserir mesmo comando em uma execucao subsequente do
	// * REPEAT nao vai ser inserido porque add.start != this.start
	// */
	// if (add.start == this.start) {
	// if (add.getKey().equals(this.getKey())) {
	//
	// if (logger.isDebugEnabled()) {
	// logger.debug("==start ==getKey()");
	// }
	//
	// double current = (this.originalValue * this.direction)
	// + (add.originalValue * add.direction);
	//
	// current = current * this.direction;
	//
	// this.originalValue = current;
	// this.value = current;
	// calculateDirection();
	//
	// // corrige valor para poder consumir
	// if (this.value < 0) {
	// this.value = this.value * -1;
	// }
	// }
	// }
	//
	// if (logger.isDebugEnabled()) {
	// logger.debug("result=" + this);
	// }
	//
	// return this;
	// }

	public boolean empty() {
		if (value <= 0) {
			return true;
		}
		return false;
	}

	public double consume(double delta) {
		double fragment = this.speed * delta;

		if ((this.value - fragment) < 0) {
			fragment = this.value;
			this.value = 0;
		} else {
			this.value = this.value - fragment;
		}

		return fragment * this.direction;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public double getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(double originalValue) {
		this.originalValue = originalValue;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "LuchadorCommand [command=" + command + ", originalValue=" + originalValue + ", value=" + value
				+ ", direction=" + direction + ", speed=" + speed + "]";
	}

}
