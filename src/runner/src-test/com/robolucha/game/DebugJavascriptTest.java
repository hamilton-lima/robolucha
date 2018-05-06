package com.robolucha.game;

import com.robolucha.api.StartGame;
import com.robolucha.bean.MatchRun;
import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.game.vo.MessageVO;
import com.robolucha.publisher.MatchRunStateKeeper;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * teste das mensagens de debug e erro de codigo javascript retornando ao
 * cliente
 * 
 * @author hamiltonlima
 *
 */
public class DebugJavascriptTest {

	private static Logger logger = Logger.getLogger(DebugJavascriptTest.class);

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testDebugMessage() throws Exception {

		BeanMockDataGenerator helper = new BeanMockDataGenerator() {
			public Class getClassToTest() {
				return MatchRun.class;
			}
		};
		helper.setup();

		MatchRun matchRun = (MatchRun) helper.mock();
		matchRun.getGame().getGameDefinition().setArenaHeight(500);
		matchRun.getGame().getGameDefinition().setArenaWidth(500);

		MatchRunner match = new MatchRunner(matchRun);
		match.getGameDefinition().setMinParticipants(1);

		// match.getMatch().setId(42L);

		StartGame job = new StartGame();
		Thread t = job.buildRunner(match);

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Luchador b = MockLuchador.build();
		b.setId(2L);

		Code c = new Code();
		c.setEvent(MethodNames.START);
		c.setScript("debug('ola mundo');warning('warn.me');danger('danger.me');");
		List<Code> codes = new ArrayList<Code>();

		codes.add(c);
		a.getCodePackage().setCodes(codes);

		Code c1 = new Code();
		c1.setEvent(MethodNames.START);
		c1.setScript("erro de compilacao");
		List<Code> codes1 = new ArrayList<Code>();

		codes1.add(c1);
		b.getCodePackage().setCodes(codes1);

		match.add(a);
		match.add(b);

		while (match.getRunners().size() < 2) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

		runnerA.getState().setX(100);
		runnerA.getState().setY(100);

		logger.debug("--- A : " + runnerA.getState().getPublicState());

		// start the match
		t.start();

		// stop the match
		Thread.sleep(500);

		// getMessages vai recuperar somente uma MENSAGEM !!!
		// esta previsto este comportamento para publicar uma por vez
		// ler varias vezes ateh nao recuperar nenhuma nova esvaziando a pilha
		// de mensagens

		ArrayList<MessageVO> messagesA = new ArrayList<MessageVO>();
		ArrayList<MessageVO> messagesB = new ArrayList<MessageVO>();

		MatchRunStateVO state = MatchRunStateKeeper.getInstance().getMessages(new MatchRunStateVO(),
				match.getMatch().getId(), 1L);

		while (state.messages.size() > 0) {
			messagesA.addAll(state.messages);
			state = MatchRunStateKeeper.getInstance().getMessages(new MatchRunStateVO(), match.getMatch().getId(), 1L);
		}

		MatchRunStateVO stateB = MatchRunStateKeeper.getInstance().getMessages(new MatchRunStateVO(),
				match.getMatch().getId(), 2L);

		while (stateB.messages.size() > 0) {
			messagesB.addAll(stateB.messages);
			stateB = MatchRunStateKeeper.getInstance().getMessages(new MatchRunStateVO(), match.getMatch().getId(), 2L);
		}

		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- messages A : " + messagesA);
		logger.debug("--- messages B : " + messagesB);

		assertTrue("verifica se mensagem foi transmitida para o state", messagesA.get(0).type.equals(MessageVO.DEBUG));
		assertTrue("verifica se mensagem foi transmitida para o state", messagesA.get(0).message.equals("ola mundo"));

		assertTrue("mensagem de erro de sintaxe", messagesB.get(0).message.startsWith("Erro em [start]"));
		assertTrue("mensagem de erro de sintaxe", messagesB.get(0).type.equals(MessageVO.DANGER));

		assertTrue("verifica se mensagem foi transmitida para o state", messagesA.get(1).type.equals(MessageVO.WARNING));
		assertTrue("verifica se mensagem foi transmitida para o state", messagesA.get(1).message.equals("warn.me"));

		assertTrue("verifica se mensagem foi transmitida para o state", messagesA.get(2).type.equals(MessageVO.DANGER));
		assertTrue("verifica se mensagem foi transmitida para o state", messagesA.get(2).message.equals("danger.me"));

	}
}
