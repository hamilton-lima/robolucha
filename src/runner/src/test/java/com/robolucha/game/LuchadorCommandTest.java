package com.robolucha.game;

import com.robolucha.models.Luchador;
import com.robolucha.runner.luchador.LuchadorCommand;
import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LuchadorCommandTest {

	private long start;

	@Before
	public void setUp() throws Exception {
		start = System.currentTimeMillis();
	}

	private static Logger logger = Logger.getLogger(LuchadorCommandTest.class);

	@Test
	public void testConsumePositive() {
		double delta = 0.02;
		Luchador foo = MockLuchador.build();
		LuchadorCommand c1 = new LuchadorCommand("turn", 45, foo.getTurnSpeed());

		assertEquals(45, c1.getValue(), 0.001);

		double amount = c1.consume(delta);
		double expected = foo.getTurnSpeed() * delta;
		assertEquals(expected, amount, 0.001);
	}

	@Test
	public void testConsumeNegative() {
		double delta = 0.02;
		Luchador foo = MockLuchador.build();

		LuchadorCommand c1 = new LuchadorCommand("turn", -45, foo.getTurnSpeed());

		assertEquals(45, c1.getValue(), 0.001);

		double amount = c1.consume(delta);
		double expected = foo.getTurnSpeed() * delta * -1;
		assertEquals(expected, amount, 0.001);
	}

	@Test
	public void testAddOnlyInTheSameTickFinalAmount() {

		Luchador foo = MockLuchador.build();

		LuchadorCommand c1 = new LuchadorCommand("turn", 45, foo.getTurnSpeed());
		LuchadorCommand c2 = new LuchadorCommand("turn", -45, foo.getTurnSpeed());
		double delta = 0.02;

		for (int i = 0; i < 10; i++) {
			c1.consume(delta);
			c2.consume(delta);
			logger.debug("***** consume c1 " + c1);
			logger.debug("***** consume c2 " + c2);
		}

		logger.debug("***** consume final c1 " + c1);
		logger.debug("***** consume final c2 " + c2);

		assertEquals(c1.getValue(), c2.getValue(), 0.0001);
	}

}
