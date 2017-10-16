package com.robolucha.game.processor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;

import org.apache.log4j.Logger;

import com.robolucha.old.RespawnPoint;
import com.robolucha.runner.Calc;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;

public class RespawnProcessor {
	
	RespawnPoint[] locations;
	
	private static Logger logger = Logger.getLogger(RespawnProcessor.class);
	
	public RespawnProcessor(MatchRunner matchRunner) {
		calculateLocations(matchRunner.getGameDefinition().getLuchadorSize(), matchRunner.getGameDefinition()
				.getArenaWidth(), matchRunner.getGameDefinition().getArenaHeight());

	}
	
	public void cleanup(){
		for (int i = 0; i < locations.length; i++) {
			locations[i] = null;
		}
		
		locations = null;
	}

	public RespawnPoint getRespawnPoint(LuchadorRunner runner,
			LinkedHashMap<Long, LuchadorRunner> runners) {

		int pos = -1;
		boolean lookForPlaceToRespawn = true;
		while (lookForPlaceToRespawn) {
			Random r = new Random();
			pos = r.nextInt(locations.length);
			if (canRespawnHere(locations[pos], runner, runners)) {
				lookForPlaceToRespawn = false;
			}
		}

		return locations[pos];

	}

	/**
	 * verifica se pode fazer respawn em determinada posicao
	 * 
	 * @param point
	 * @param current
	 * @param runners
	 * @return
	 */
	boolean canRespawnHere(RespawnPoint point, LuchadorRunner current,
			LinkedHashMap<Long, LuchadorRunner> runners) {

		if( logger.isDebugEnabled() ){
			logger.debug("canRespawnHere : point = " + point);
			logger.debug("canRespawnHere : current = " + current);
		}

		Iterator iterator = runners.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			LuchadorRunner runner = runners.get(key);

			if (logger.isDebugEnabled()) {
				logger.debug(">canRespawnHere : runner = " + runner);
			}

			// nao precisa conferir o ID do current porque o luchador origem 
			// nao esta ativo ...esta usando o respawn ora bolas !! :)
			// 
			if (runner != null && runner.isActive()) {

				if (Calc.intersectRobot(point.x, point.y, current, runner)) {
					return false;
				}

			}

		}

		return true;
	}

	/**
	 * calcula as posicoes possiveis para respawn
	 * 
	 * @param size
	 * @param width
	 * @param height
	 */
	void calculateLocations(int size, int width, int height) {

		int border = size / 3;

		int lines = ((width - (2 * border)) / size) - 1;
		int columns = ((height - (2 * border)) / size) - 1;
		
		logger.debug("calculateLocations().lines=" + lines);
		logger.debug("calculateLocations().columns=" + columns);

		locations = new RespawnPoint[lines * columns];
		int pos = 0;
		int line = border + size;
		int column = border + size;

		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				locations[pos++] = new RespawnPoint(line, column);
				column = column + size;
			}
			line = line + size;
			column = border + size;
		}

	}

	public RespawnPoint[] getLocations() {
		return locations;
	}

}
