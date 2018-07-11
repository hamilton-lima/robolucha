package com.robolucha.runner;

public class RespawnPoint {
	public RespawnPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x;
	public int y;
	
	@Override
	public String toString() {
		return "RespawnPoint [x=" + x + ", y=" + y + "]";
	}
	
	
}