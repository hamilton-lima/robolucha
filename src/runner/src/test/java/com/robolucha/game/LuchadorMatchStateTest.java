package com.robolucha.game;

import com.robolucha.models.LuchadorMatchState;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class LuchadorMatchStateTest {

	@Test
	public void testSetLife() {
		LuchadorMatchState state = new LuchadorMatchState();
		Random r = new Random();
		double randomDouble = r.nextDouble();
		state.setLife(randomDouble);
		assertEquals((int)randomDouble, state.getPublicState().life, 0.001);
	}

	@Test
	public void testSetAngle() {
		LuchadorMatchState state = new LuchadorMatchState();
		Random r = new Random();
		double randomDouble = r.nextDouble();
		state.setAngle(randomDouble);
		assertEquals((int)randomDouble, state.getPublicState().angle, 0.001);
	}

	@Test
	public void testSetGunAngle() {
		LuchadorMatchState state = new LuchadorMatchState();
		Random r = new Random();
		double randomDouble = r.nextDouble();
		state.setGunAngle(randomDouble);
		assertEquals((int)randomDouble, state.getPublicState().gunAngle, 0.001);
	}

	@Test
	public void testSetX() {
		LuchadorMatchState state = new LuchadorMatchState();
		Random r = new Random();
		double randomDouble = r.nextDouble();
		state.setX(randomDouble);
		assertEquals((int)randomDouble, state.getPublicState().x, 0.001);
	}

	@Test
	public void testSetY() {
		LuchadorMatchState state = new LuchadorMatchState();
		Random r = new Random();
		double randomDouble = r.nextDouble();
		state.setY(randomDouble);
		assertEquals((int)randomDouble, state.getPublicState().y, 0.001);
	}

}
