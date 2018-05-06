package com.robolucha.game;

import com.robolucha.models.Luchador;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.test.MockMatchRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FireAmountTest {

	@Test
	public void testFireAmount() {

		LuchadorRunner runner = new LuchadorRunner(new Luchador(), MockMatchRunner.build(), null);

		assertEquals(1, runner.cleanUpAmount(-1));
		assertEquals(1, runner.cleanUpAmount(0));
		assertEquals(3, runner.cleanUpAmount(3));
		assertEquals(10, runner.cleanUpAmount(40));
		assertEquals(10, runner.cleanUpAmount(22222222));
	}

}
