package com.robolucha.models;

import java.util.ArrayList;
import java.util.List;

import com.robolucha.shared.Const;

public class GameComponent {

	private long id;
	private String name;
	private String calculateRanking = Const.NO;
	private List<Code> codes = new ArrayList<Code>();

	private int radarAngle = 45;
	private int radarRadius = 200;
	private int punchAngle = 90;
	private int life = 20;
	private int energy = 30;
	private int punchDamage = 2;
	private int punchCoolDown = 2;
	private int moveSpeed = 50;
	private int turnSpeed = 90;
	private int turnGunSpeed = 60;
	private int respawnCooldown = 10;
	private int maxFireCooldown = 10;
	private int minFireDamage = 1;
	private int maxFireDamage = 10;

	// how fast energy is restored to the luchador
	private int restoreEnergyperSecond = 3;

	// how much energy is restored when collecting luchador parts
	private int recycledLuchadorEnergyRestore = 6;

	// how much energy will cost to increase speed
	private int increaseSpeedEnergyCost = 10;

	// percentage of speed increase
	private int increaseSpeedPercentage = 20;

	// how much energy cost to fire
	private int fireEnergyCost = 2;

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

	public String getCalculateRanking() {
		return calculateRanking;
	}

	public void setCalculateRanking(String calculateRanking) {
		this.calculateRanking = calculateRanking;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
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

	public int getRestoreEnergyperSecond() {
		return restoreEnergyperSecond;
	}

	public void setRestoreEnergyperSecond(int restoreEnergyperSecond) {
		this.restoreEnergyperSecond = restoreEnergyperSecond;
	}

	public int getRecycledLuchadorEnergyRestore() {
		return recycledLuchadorEnergyRestore;
	}

	public void setRecycledLuchadorEnergyRestore(int recycledLuchadorEnergyRestore) {
		this.recycledLuchadorEnergyRestore = recycledLuchadorEnergyRestore;
	}

	public int getIncreaseSpeedEnergyCost() {
		return increaseSpeedEnergyCost;
	}

	public void setIncreaseSpeedEnergyCost(int increaseSpeedEnergyCost) {
		this.increaseSpeedEnergyCost = increaseSpeedEnergyCost;
	}

	public int getIncreaseSpeedPercentage() {
		return increaseSpeedPercentage;
	}

	public void setIncreaseSpeedPercentage(int increaseSpeedPercentage) {
		this.increaseSpeedPercentage = increaseSpeedPercentage;
	}

	public int getFireEnergyCost() {
		return fireEnergyCost;
	}

	public void setFireEnergyCost(int fireEnergyCost) {
		this.fireEnergyCost = fireEnergyCost;
	}

}
