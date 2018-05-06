package com.robolucha.game;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JavascriptEventsCallTest {

	private static Logger logger = Logger
			.getLogger(JavascriptEventsCallTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOnHitWall() throws InterruptedException {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Code c = new Code();
		c.setEvent("onHitWall");
		c.setScript("foo = 42;");

		Code c1 = new Code();
		c1.setEvent("repeat");
		c1.setScript("move(10);");

		Code c2 = new Code();
		c2.setEvent("start");
		c2.setScript("var inicio = 13; var foo = 0;");

		List<Code> codes = new ArrayList<Code>();
		codes.add(c);
		codes.add(c1);
		codes.add(c2);

		a.getCodePackage().setCodes(codes);
		match.add(a);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
		
		// grudado na parede a direita
		runnerA.getState().setX(match.getGameDefinition().getArenaWidth() - runnerA.getSize() - 2);
		runnerA.getState().setY(100);
		
		logger.debug("--- A : " + runnerA.getState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(1000);
		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState());

		String inicio = runnerA.getFromJavascript("inicio");
		logger.debug("---- inicio = " + inicio);

		String result = runnerA.getFromJavascript("foo");
		logger.debug("---- foo = " + result);

		assertEquals(
				"verifica se variavel foi criada corretamente no codigo de start",
				"13", inicio);

		assertEquals("verifica se evento onhitwall foi disparado em A", "42",
				result);

	}

	@Test
	public void testOnHitOtherOnFoundOnGotDamage() throws InterruptedException {

		MatchRunner match = MockMatchRunner.build();

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Luchador b = MockLuchador.build();
		b.setId(2L);

		Code c = new Code();
		c.setEvent("onHitOther");
		c.setScript("hit = 'yes';");

		Code damage = new Code();
		damage.setEvent("onGotDamage");
		damage.setScript("machucador = other.id; tamnhoDoProblema = amount; ");

		Code foundC = new Code();
		foundC.setEvent("onFound");
		foundC.setScript("found = other.id;");

		Code c1 = new Code();
		c1.setEvent("repeat");

		// verificando se conseguimos alterar valores de life :)
		c1.setScript("move(100); me.life = 500;");

		Code c2 = new Code();
		c2.setEvent("start");
		c2.setScript("var inicio = 13; var hit = 'NO'; var found = ''; var machucador = 0; var vida = me.life; var tamnhoDoProblema = -1;");

		List<Code> codes = new ArrayList<Code>();
		codes.add(c);
		codes.add(c1);
		codes.add(c2);
		codes.add(foundC);
		codes.add(damage);

		a.getCodePackage().setCodes(codes);
		match.add(a);

		Code shooter = new Code();
		shooter.setEvent("repeat");
		shooter.setScript("fire(1);");

		List<Code> codesB = new ArrayList<Code>();
		codesB.add(shooter);
		b.getCodePackage().setCodes(codesB);
		match.add(b);

		while (match.getRunners().size() < 2) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
		runnerA.getState().setX(110);
		runnerA.getState().setY(100);

		LuchadorRunner runnerB = match.getRunners().get(new Long(2L));
		runnerB.getState().setX(150);
		runnerB.getState().setY(100);
		runnerB.getState().setGunAngle(180);

		logger.debug("--- A : " + runnerA.getState());
		logger.debug("--- B : " + runnerB.getState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(3000);
		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState());
		logger.debug("--- B depois : " + runnerB.getState());

		String inicio = runnerA.getFromJavascript("inicio");
		String hit = runnerA.getFromJavascript("hit");
		String found = runnerA.getFromJavascript("found");
		String machucador = runnerA.getFromJavascript("machucador");
		String vida = runnerA.getFromJavascript("vida");
		String vidaFinal = runnerA.getFromJavascript("me.life");
		String tamnhoDoProblema = runnerA.getFromJavascript("tamnhoDoProblema");

		logger.debug("---- inicio = " + inicio);
		logger.debug("---- hit = " + hit);
		logger.debug("---- found = " + found);
		logger.debug("---- machucador = " + machucador);
		logger.debug("---- vida = " + vida);
		logger.debug("---- vidaFinal = " + vidaFinal);
		logger.debug("---- tamnhoDoProblema = " + tamnhoDoProblema);

		assertEquals(
				"verifica se variavel foi criada corretamente no codigo de start",
				"13", inicio);

		assertEquals("testa vida inicial", "20", vida);
		assertEquals("testa vida final publica para codigo", "17", vidaFinal);
		assertEquals("testa vida REAL", 17, (int) runnerA.getState().getLife());

		assertEquals("testa onhitother", "yes", hit);
		assertEquals("testa onFound (id do other)", "2", found);
		assertEquals("testa onGotDamage, com o outro luchador dando fire(1)",
				"1", machucador);	
		
		assertEquals("testa onGotDamage, com o outro luchador dando fire(1)",
						"1.0", tamnhoDoProblema);

	}

}
