package com.robolucha.models;

import java.util.LinkedList;
import java.util.List;

public class GameDefinition {

	private long id = 1;
	private String name = "default";
	private long duration = 60000;
	private int minParticipants = 2;
	private int maxParticipants = 14;

	private int arenaWidth = 1200;
	private int arenaHeight = 600;
	private int bulletSize = 16;
	private int fps = 30;
	private int buletSpeed = 120;

	// predefined luchadores that will join the match
	private List<GameComponent> gameComponents;

	// predefined scene elements
	private List<SceneComponent> sceneComponents;

	public GameDefinition() {
		gameComponents = new LinkedList<GameComponent>();
		sceneComponents = new LinkedList<SceneComponent>();
	}

	@Override
	public String toString() {
		return "GameDefinition [id=" + id + ", name=" + name + ", duration=" + duration + ", minParticipants="
				+ minParticipants + ", maxParticipants=" + maxParticipants + ", arenaWidth=" + arenaWidth
				+ ", arenaHeight=" + arenaHeight + ", bulletSize=" + bulletSize + ", fps=" + fps + ", buletSpeed="
				+ buletSpeed + ", gameComponents=" + gameComponents + ", sceneComponents=" + sceneComponents + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getMinParticipants() {
		return minParticipants;
	}

	public void setMinParticipants(int minParticipants) {
		this.minParticipants = minParticipants;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public int getArenaWidth() {
		return arenaWidth;
	}

	public void setArenaWidth(int arenaWidth) {
		this.arenaWidth = arenaWidth;
	}

	public int getArenaHeight() {
		return arenaHeight;
	}

	public void setArenaHeight(int arenaHeight) {
		this.arenaHeight = arenaHeight;
	}

	public int getBulletSize() {
		return bulletSize;
	}

	public void setBulletSize(int bulletSize) {
		this.bulletSize = bulletSize;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public int getBuletSpeed() {
		return buletSpeed;
	}

	public void setBuletSpeed(int buletSpeed) {
		this.buletSpeed = buletSpeed;
	}

	public List<GameComponent> getGameComponents() {
		return gameComponents;
	}

	public void setGameComponents(List<GameComponent> gameComponents) {
		this.gameComponents = gameComponents;
	}

	public List<SceneComponent> getSceneComponents() {
		return sceneComponents;
	}

	public void setSceneComponents(List<SceneComponent> sceneComponents) {
		this.sceneComponents = sceneComponents;
	}

}
