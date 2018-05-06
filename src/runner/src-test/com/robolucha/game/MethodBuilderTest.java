package com.robolucha.game;

import com.robolucha.game.vo.MessageVO;
import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.LuchadorCodeChangeListener;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MethodBuilderTest {

	private static Logger logger = Logger.getLogger(MethodBuilderTest.class);

	@Test
	public void testBuildAll() throws Exception {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(3);

		Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, "var a = 1; var b = 2; if( a => b ) b = a;");
		Luchador b = MockLuchador.build(2L, MethodNames.REPEAT, "move(10);");

		// erro que soh aparece em tempo de execucao !!
		Luchador c = MockLuchador.build(3L, MethodNames.REPEAT, "move(10);nheco;");

		match.add(a);
		match.add(b);
		match.add(c);

		while (match.getRunners().size() < 3) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

		runnerA.getState().setX(200);
		runnerA.getState().setY(100);
		runnerA.getState().setAngle(90);
		runnerA.getState().setGunAngle(90);

		LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

		runnerB.getState().setX(300);
		runnerB.getState().setY(100);
		runnerB.getState().setAngle(90);
		runnerB.getState().setGunAngle(90);

		LuchadorRunner runnerC = match.getRunners().get(new Long(3L));

		runnerC.getState().setX(100);
		runnerC.getState().setY(300);
		runnerC.getState().setAngle(90);
		runnerC.getState().setGunAngle(90);

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- B : " + runnerB.getState().getPublicState());
		logger.debug("--- C : " + runnerC.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(500);

		// codigo tem erro
		for (Code code : runnerA.getGameComponent().getCodes()) {
			logger.debug(code.getEvent() + " " + code.getException());
			if (code.getEvent().equals(MethodNames.REPEAT)) {
				assertTrue("metodo start PRECISA ter exception marcada ", code.getException().contains("syntax error"));
			}
		}

		assertEquals("verifica se o luchador A ficou parado", 100, runnerA.getState().getPublicState().y);

		// procura por mensagem de erro para enviar ao usuario em A
		boolean erroEncontrado = false;
		MessageVO message = runnerA.getMessage();
		while (message != null) {
			logger.debug("mensagem em runnerA " + message);
			if (message.type.equals(MessageVO.DANGER)) {
				if (message.message.contains("Erro") && message.message.contains("[repeat]")) {
					erroEncontrado = true;
					break;
				}
			}

			message = runnerC.getMessage();
		}
		assertTrue("runnerA precisa ter mensagem indicando erro no metodo para ser enviado ao cliente", erroEncontrado);

		// atualiza o codigo
		LuchadorCodeVO fix = new LuchadorCodeVO();
		fix.setRepeat("move(10)");

		Luchador updated = (Luchador) GameComponentBuilder.getInstance().updateCode(runnerA.getGameComponent(), fix);
		runnerA.gameComponent = updated;
		LuchadorCodeChangeListener.getInstance().update(updated);

		// interrompe a partida
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);

		for (Code code : runnerA.getGameComponent().getCodes()) {
			logger.debug(code.getEvent() + " " + code.getException());
			if (code.getEvent().equals(MethodNames.REPEAT)) {
				assertTrue("metodo repeat NAO pode ter exception marcada ", code.getException() == null);
			}
		}

		// codigo tem erro
		for (Code code : runnerC.getGameComponent().getCodes()) {
			logger.debug(code.getEvent() + " " + code.getException());
			if (code.getEvent().equals(MethodNames.REPEAT)) {
				assertTrue("metodo em C PRECISA ter conteudo de erro ", code.getException() != null);
			}
		}

		erroEncontrado = false;
		message = runnerC.getMessage();
		while (message != null) {
			logger.debug("mensagem em runnerC " + message);
			if (message.type.equals(MessageVO.DANGER)) {
				if (message.message.contains("Erro") && message.message.contains("[repeat]")) {
					erroEncontrado = true;
					break;
				}
			}

			message = runnerC.getMessage();
		}
		assertTrue("runnerC precisa ter mensagem indicando erro no metodo para ser enviado ao cliente", erroEncontrado);

		assertEquals(
				"verifica se o contador de exception esta IGUAL a 1, contando somente a primeira execucao que gerou erro",
				1, runnerC.getExceptionCounter());

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		assertTrue("verifica se o luchador A andou apos atualizacao do codigo",
				runnerA.getState().getPublicState().y > 100);
		assertEquals("verifica se o contador de exception esta zerado", 0, runnerA.getExceptionCounter());

		assertTrue("verifica se o luchador B se moveu", runnerB.getState().getPublicState().y > 100);
		assertEquals("verifica se o contador de exception esta zerado", 0, runnerB.getExceptionCounter());

	}

	@Test
	public void testUpdateWithInvalidCode() throws Exception {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(3);

		Luchador b = MockLuchador.build(1L, MethodNames.REPEAT, "move(10);");

		match.add(b);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerB = match.getRunners().get(new Long(1L));

		runnerB.getState().setX(300);
		runnerB.getState().setY(100);
		runnerB.getState().setAngle(90);
		runnerB.getState().setGunAngle(90);

		logger.debug("--- B : " + runnerB.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(500);

		// atualiza o codigo
		LuchadorCodeVO fix = new LuchadorCodeVO();
		fix.setRepeat("if( 2 => 5 )move(10);");

		Luchador updated = (Luchador) GameComponentBuilder.getInstance().updateCode(runnerB.getGameComponent(), fix);
		runnerB.gameComponent = updated;
		LuchadorCodeChangeListener.getInstance().update(updated);

		
		logger.debug("procura mensagens em runnerB ");

		boolean erroEncontrado = false;
		MessageVO message = runnerB.getMessage();
		while (message != null) {
			logger.debug("mensagem em runnerB " + message);
			if (message.type.equals(MessageVO.DANGER)) {
				if (message.message.contains("Erro") && message.message.contains("[repeat]")) {
					erroEncontrado = true;
					break;
				}
			}

			message = runnerB.getMessage();
		}
		assertTrue("runnerB precisa ter mensagem indicando erro no metodo para ser enviado ao cliente", erroEncontrado);

		// interrompe a partida
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);

		// codigo tem erro
		for (Code code : runnerB.getGameComponent().getCodes()) {
			logger.debug("code : " + code.getEvent() + " " + code.getException());
			if (code.getEvent().equals(MethodNames.REPEAT)) {
				assertTrue("metodo em C PRECISA ter conteudo de erro ", code.getException() != null);
			}
		}


		assertEquals(
				"verifica se o contador de exception esta IGUAL a 0, erro esta sendo gerado ANTES da execucao",
				0, runnerB.getExceptionCounter());

		logger.debug("--- B depois : " + runnerB.getState().getPublicState());

		assertTrue("verifica se o luchador B andou apos atualizacao do codigo",
				runnerB.getState().getPublicState().y == 100);

	}

}
