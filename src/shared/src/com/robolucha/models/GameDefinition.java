package com.robolucha.models;

public class GameDefinition {

	private long id = 1;
	private String name = "default";
	private long duration = 60000;
	private int minParticipants = 2;
	private int maxParticipants = 14;
	private int radarAngle = 45;
	private int radarRadius = 200;
	private int punchAngle = 90;
	private int arenaWidth = 1200;
	private int arenaHeight = 600;
	private int bulletSize = 16;
	private int luchadorSize = 60;
	private int fps = 30;

	private int buletSpeed = 120;
	private int life = 20;
	private int punchDamage = 2;
	private int punchCoolDown = 2;
	private int moveSpeed = 50;
	private int turnSpeed = 90;
	private int turnGunSpeed = 60;
	private int respawnCooldown = 10;
	private int maxFireCooldown = 10;
	private int minFireDamage = 1;
	private int maxFireDamage = 10;

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

	public int getRadarAngle() {
		return radarAngle;
	}

	public void setRadarAngle(int radarAngle) {
		this.radarAngle = radarAngle;
	}

	public int getRadarRadius() {
		return radarRadius;
	}

	public void setRadarRadius(int radarRadius) {
		this.radarRadius = radarRadius;
	}

	public int getPunchAngle() {
		return punchAngle;
	}

	public void setPunchAngle(int punchAngle) {
		this.punchAngle = punchAngle;
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

	public int getLuchadorSize() {
		return luchadorSize;
	}

	public void setLuchadorSize(int luchadorSize) {
		this.luchadorSize = luchadorSize;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPunchDamage() {
		return punchDamage;
	}

	public void setPunchDamage(int punchDamage) {
		this.punchDamage = punchDamage;
	}

	public int getPunchCoolDown() {
		return punchCoolDown;
	}

	public void setPunchCoolDown(int punchCoolDown) {
		this.punchCoolDown = punchCoolDown;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getTurnSpeed() {
		return turnSpeed;
	}

	public void setTurnSpeed(int turnSpeed) {
		this.turnSpeed = turnSpeed;
	}

	public int getTurnGunSpeed() {
		return turnGunSpeed;
	}

	public void setTurnGunSpeed(int turnGunSpeed) {
		this.turnGunSpeed = turnGunSpeed;
	}

	public int getRespawnCooldown() {
		return respawnCooldown;
	}

	public void setRespawnCooldown(int respawnCooldown) {
		this.respawnCooldown = respawnCooldown;
	}

	public int getMaxFireCooldown() {
		return maxFireCooldown;
	}

	public void setMaxFireCooldown(int maxFireCooldown) {
		this.maxFireCooldown = maxFireCooldown;
	}

	public int getMinFireDamage() {
		return minFireDamage;
	}

	public void setMinFireDamage(int minFireDamage) {
		this.minFireDamage = minFireDamage;
	}

	public int getMaxFireDamage() {
		return maxFireDamage;
	}

	public void setMaxFireDamage(int maxFireDamage) {
		this.maxFireDamage = maxFireDamage;
	}

	@Override
	public String toString() {
		return "GameDefinition [id=" + id + ", name=" + name + ", duration=" + duration + ", minParticipants="
				+ minParticipants + ", maxParticipants=" + maxParticipants + ", radarAngle=" + radarAngle
				+ ", radarRadius=" + radarRadius + ", punchAngle=" + punchAngle + ", arenaWidth=" + arenaWidth
				+ ", arenaHeight=" + arenaHeight + ", bulletSize=" + bulletSize + ", luchadorSize=" + luchadorSize
				+ ", fps=" + fps + ", buletSpeed=" + buletSpeed + ", life=" + life + ", punchDamage=" + punchDamage
				+ ", punchCoolDown=" + punchCoolDown + ", moveSpeed=" + moveSpeed + ", turnSpeed=" + turnSpeed
				+ ", turnGunSpeed=" + turnGunSpeed + ", respawnCooldown=" + respawnCooldown + ", maxFireCooldown="
				+ maxFireCooldown + ", minFireDamage=" + minFireDamage + ", maxFireDamage=" + maxFireDamage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arenaHeight;
		result = prime * result + arenaWidth;
		result = prime * result + buletSpeed;
		result = prime * result + bulletSize;
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + fps;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + life;
		result = prime * result + luchadorSize;
		result = prime * result + maxFireCooldown;
		result = prime * result + maxFireDamage;
		result = prime * result + maxParticipants;
		result = prime * result + minFireDamage;
		result = prime * result + minParticipants;
		result = prime * result + moveSpeed;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + punchAngle;
		result = prime * result + punchCoolDown;
		result = prime * result + punchDamage;
		result = prime * result + radarAngle;
		result = prime * result + radarRadius;
		result = prime * result + respawnCooldown;
		result = prime * result + turnGunSpeed;
		result = prime * result + turnSpeed;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameDefinition other = (GameDefinition) obj;
		if (arenaHeight != other.arenaHeight)
			return false;
		if (arenaWidth != other.arenaWidth)
			return false;
		if (buletSpeed != other.buletSpeed)
			return false;
		if (bulletSize != other.bulletSize)
			return false;
		if (duration != other.duration)
			return false;
		if (fps != other.fps)
			return false;
		if (id != other.id)
			return false;
		if (life != other.life)
			return false;
		if (luchadorSize != other.luchadorSize)
			return false;
		if (maxFireCooldown != other.maxFireCooldown)
			return false;
		if (maxFireDamage != other.maxFireDamage)
			return false;
		if (maxParticipants != other.maxParticipants)
			return false;
		if (minFireDamage != other.minFireDamage)
			return false;
		if (minParticipants != other.minParticipants)
			return false;
		if (moveSpeed != other.moveSpeed)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (punchAngle != other.punchAngle)
			return false;
		if (punchCoolDown != other.punchCoolDown)
			return false;
		if (punchDamage != other.punchDamage)
			return false;
		if (radarAngle != other.radarAngle)
			return false;
		if (radarRadius != other.radarRadius)
			return false;
		if (respawnCooldown != other.respawnCooldown)
			return false;
		if (turnGunSpeed != other.turnGunSpeed)
			return false;
		if (turnSpeed != other.turnSpeed)
			return false;
		return true;
	}

}
