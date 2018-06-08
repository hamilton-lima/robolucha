package com.robolucha.runner.luchador;

import com.robolucha.models.Luchador;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.publisher.RemoteQueue;
import com.robolucha.runner.Config;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.Server;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BugNaoRetornandoMatchRunnerAtivoTest {

	private static Logger logger = Logger
			.getLogger(BugNaoRetornandoMatchRunnerAtivoTest.class);

	/**
	 * inicia uma partida e le do ThreadMonitor qual a partida atual, conclui a
	 * primeira e inicia a segunda recuperando novamente a partida atual do
	 * ThreadMonitor os valores precisam ser diferentes
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testRun() throws Exception {
		RemoteQueue queue = new RemoteQueue(Config.getInstance());
		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);
		match.getGameDefinition().setDuration(500);

		Luchador a = MockLuchador.build();
		a.setId(1L);

		match.add(a);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate (1)");
			Thread.sleep(200);
		}

		// start the match
		Thread t = Server.buildRunner(match, queue, ThreadMonitor.getInstance(), publisher);
		t.start();

		MatchRunner runner = ThreadMonitor.getInstance().getMatch();
		logger.debug("*** primeira partida : " + runner.getThreadName());
		String primeira = runner.getThreadName();

		// stop the match
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);

		// depois que acabou tem que retornar nulo
		runner = ThreadMonitor.getInstance().getMatch();
		assertNull(runner);

		match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);
		match.getGameDefinition().setDuration(500L);

		match.add(a);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate (2)");
			Thread.sleep(200);
		}

		// start the match
		t = Server.buildRunner(match, queue, ThreadMonitor.getInstance(), publisher);
		t.start();

		runner = ThreadMonitor.getInstance().getMatch();
		logger.debug("*** segunda partida : " + runner.getThreadName());
		String segunda = runner.getThreadName();

		// stop the match
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);

		// depois que acabou tem que retornar nulo
		runner = ThreadMonitor.getInstance().getMatch();
		assertNull(runner);

		assertTrue(!primeira.equals(segunda));

	}
}
