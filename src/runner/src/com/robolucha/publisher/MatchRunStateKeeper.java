package com.robolucha.publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.robolucha.game.vo.BulletVO;
import com.robolucha.game.vo.EventVO;
import com.robolucha.game.vo.LuchadorPublicStateVO;
import com.robolucha.game.vo.MaskConfigVO;
import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.game.vo.MessageVO;
import com.robolucha.game.vo.PunchVO;
import com.robolucha.models.Bullet;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorPublicState;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.Punch;
import com.robolucha.runner.RunAfterThisTask;

public class MatchRunStateKeeper {

	private static Logger logger = Logger.getLogger(MatchRunStateKeeper.class);

	private static MatchRunStateKeeper instance;
	private Map<Long, MatchRunStateVO> matchStates;
	private Map<Long, MatchRunner> matchRunners;

	private MatchRunStateKeeper() {
		matchStates = Collections.synchronizedMap(new HashMap<Long, MatchRunStateVO>());

		matchRunners = Collections.synchronizedMap(new HashMap<Long, MatchRunner>());
	}

	public static MatchRunStateKeeper getInstance() {
		if (instance == null) {
			instance = new MatchRunStateKeeper();
		}
		return instance;
	}

	/**
	 * consome evento somente quando chama metodo get()
	 * 
	 * @param matchId
	 * @return
	 */
	public MatchRunStateVO get(Long matchId) {
		MatchRunStateVO state = matchStates.get(matchId);

		if (state != null) {
			// recupera um evento da frente da fila de eventos
			//
			EventVO event = null;
			MatchRunner runner = matchRunners.get(matchId);
			if (runner != null && runner.getEventToPublish() != null) {
				event = runner.getEventToPublish().getEvent();
			}
			if (event != null) {
				state.events.add(event);
			}
		}

		return state;

	}

	public Map<Long, MatchRunStateVO> getMatchStates() {
		return matchStates;
	}

	public Map<Long, MatchRunner> getMatchRunners() {
		return matchRunners;
	}

	public void update(MatchRunner matchRunner) throws Exception {
		Long matchId = matchRunner.getMatch().getId();

		// recupera objeto que representa o estado da partida
		MatchRunStateVO vo = new MatchRunStateVO();

		// percorre lista de lutchadores
		Iterator<Long> iterator = matchRunner.getRunners().keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			LuchadorRunner runner = matchRunner.getRunners().get(key);

			// adiciona placar na lista
			if (runner != null) {
				vo.scores.add(runner.getScoreVO());

				if (runner.isActive()) {
					// recupera dados para atualizar estado
					Long lutchadorId = runner.getGameComponent().getId();
					String name = runner.getGameComponent().getName();
					LuchadorPublicState publicState = runner.getState().getPublicState();
					MaskConfigVO mask = runner.getMask();

					// dados do proprietario do luchador
					Long ownerId = null;
					if (runner.getGameComponent() instanceof Luchador) {
						Luchador luchador = (Luchador) runner.getGameComponent();
						if (luchador.getCustomer() != null) {
							ownerId = luchador.getCustomer().getId();
						}
					}

					// atualiza o estado do luchador no objeto
					vo.luchadores.add(new LuchadorPublicStateVO(publicState, name, ownerId, mask));
				}
			}

		}

		// ordena a lista baseado no comparable do ScoreVO
		Collections.sort(vo.scores);

		// percorre bullets
		int pos = 0;
		while (pos < matchRunner.getBullets().size()) {
			Bullet bullet = (Bullet) matchRunner.getBullets().get(pos++);
			if (bullet == null) {
				continue;
			}

			BulletVO foo = new BulletVO();
			foo.id = bullet.getId();
			foo.x = bullet.getX();
			foo.y = bullet.getY();
			foo.amount = bullet.getAmount();

			vo.bullets.add(foo);
		}

		// percorre punches
		pos = 0;
		while (pos < matchRunner.getPunches().size()) {
			Punch punch = (Punch) matchRunner.getPunches().get(pos++);
			if (punch == null) {
				continue;
			}

			PunchVO foo = new PunchVO();
			foo.x = punch.getX();
			foo.y = punch.getY();

			vo.punches.add(foo);
		}

		vo.clock = matchRunner.getGameDefinition().getDuration() - matchRunner.getTimeElapsed();

		matchStates.put(matchId, vo);

	}

	public void start(MatchRunner matchRunner) {
		logger.debug("START " + matchRunner);
		matchStates.put(matchRunner.getMatch().getId(), new MatchRunStateVO());
		matchRunners.put(matchRunner.getMatch().getId(), matchRunner);
	}

	/**
	 * a mudanca para finished fara com que o publicador de estado informe ao
	 * cliente do novo estado e chame o metodo remove()
	 * 
	 * @see PublishServerStateRunner
	 * @param matchRunner
	 */
	public void end(MatchRunner matchRunner, RunAfterThisTask... runAfterThis) {
		logger.debug("END " + matchRunner);
		MatchRunStateVO vo = matchStates.get(matchRunner.getMatch().getId());
		if (vo != null) {
			vo.clock = 0;
		}

		if (runAfterThis != null) {
			for (int i = 0; i < runAfterThis.length; i++) {
				runAfterThis[i].run();
			}
		}
	}

	public void remove(Long key) {
		logger.debug("REMOVE " + key);
		matchStates.remove(key);
		matchRunners.remove(key);
	}

	/**
	 * adiciona as mensagens ao MatchRunStateVO
	 * 
	 * @param state
	 * @param matchId
	 * @param luchadorId
	 * @return
	 */
	public MatchRunStateVO getMessages(MatchRunStateVO state, Long matchId, Long luchadorId) {

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("getMessages() matchId=%s, luchadorId=%s", matchId, luchadorId));
		}

		state.messages = new ArrayList<MessageVO>();
		MatchRunner matchRunner = matchRunners.get(matchId);
		LuchadorRunner luchadorRunner = matchRunner.getRunner(luchadorId);

		if (logger.isDebugEnabled()) {
			String matchRunnerId = matchRunner == null ? "NULL" : matchRunner.getMatch().getId().toString();
			String luchadorRunnerId = luchadorRunner == null ? "NULL"
					: Long.toString(luchadorRunner.getGameComponent().getId());
			logger.debug(String.format("matchrunner=%s, luchadorrunner=%s", matchRunnerId, luchadorRunnerId));
		}

		// luchador ainda nao consta na lista do MatchRunner
		if (luchadorRunner != null) {
			MessageVO message = luchadorRunner.getMessage();

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("message=%s", message));
			}

			if (message != null) {
				state.messages.add(message);
			}
		}

		return state;
	}

}
