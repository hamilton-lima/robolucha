package com.robolucha.game;

import org.junit.Test;



public class LuchadorRunnerTest2 {

	@Test
	public void testFireAmount() {

		LuchadorRunner runner = new LuchadorRunner(new Luchador(), new MatchRunner(), null);

		assertEquals(1, runner.cleanUpAmount(-1));
		assertEquals(1, runner.cleanUpAmount(0));
		assertEquals(3, runner.cleanUpAmount(3));
		assertEquals(10, runner.cleanUpAmount(40));
		assertEquals(10, runner.cleanUpAmount(22222222));
	}

}
