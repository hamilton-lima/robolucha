package com.robolucha.runner.luchador;

import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @raquel - quando usando fire(1) no repeat nao consegue disparar um fire(10)
 *         usando onfound
 * 
 * @author hamiltonlima
 *
 */
public class BugFire1Fire10Test {

	private static Logger logger = Logger.getLogger(BugFire1Fire10Test.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() throws Exception {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);

		Luchador a = buildLuchador(1L, "fire(1);");
		addCode(a, MethodNames.START, "var foundIt = 0;");
		addCode(a, MethodNames.ON_FOUND, "foundIt = 1; fire(10);");

		match.add(a);

		match.add(buildLuchador(2L, "turn(20);"));

		while (match.getRunners().size() < 2) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
		LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

		runnerA.getState().setX(100);
		runnerA.getState().setY(100);

		runnerB.getState().setX(200);
		runnerB.getState().setY(100);

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- B : " + runnerB.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(3500);
		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- B depois : " + runnerB.getState().getPublicState());

		String foundIt = runnerA.getString("foundIt");
		logger.debug("*** foundIt = " + foundIt);

		assertTrue("found foi executado ? ", foundIt.equals("1.0"));
		assertTrue("B recebeu o disparo de 10 ?", runnerB.getState()
				.getPublicState().life < 10);

	}

	private Luchador buildLuchador(long id, String repeatCode) {
		Luchador a = MockLuchador.build();
		a.setId(id);

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript(repeatCode);
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);
		return a;
	}

	private Luchador addCode(Luchador a, String event, String script) {

		Code c = new Code();
		c.setEvent(event);
		c.setScript(script);

		a.getCodePackage().getCodes().add(c);
		return a;
	}
}
