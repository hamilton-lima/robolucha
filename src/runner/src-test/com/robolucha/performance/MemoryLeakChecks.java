package com.robolucha.performance;

import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import io.reactivex.functions.Consumer;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * testes para suporte a investigacao de vazamento de memoria apos a execucao de
 * partidas
 * 
 * @author Hamilton
 *
 */
public class MemoryLeakChecks {

	private static Logger logger = Logger.getLogger(MemoryLeakChecks.class);
	private static int counter = 0;

	private HashMap<String, ThreadInfoExpanded> threadInfoExp;

	class ThreadInfoExpanded {
		long id;
		String name;
		ThreadInfo info;
		boolean leftBehind;

		public ThreadInfoExpanded(long id, String name, ThreadInfo info, boolean leftBehind) {
			this.id = id;
			this.name = name;
			this.info = info;
			this.leftBehind = leftBehind;
		}
	}

	@Before
	public void setUp() throws Exception {
		counter = 0;
		threadInfoExp = new HashMap<String, MemoryLeakChecks.ThreadInfoExpanded>();
	}

	/**
	 * resultados historicos :
	 * 
	 * <pre>
	 * 19-mar-2016 
	 *   leak: [2938K] 2,66%; - original
	 *   leak: [-13480K] -12,19%; - scope=null no LuchadorRunner.cleanup();
	 * 
	 * 
	 * </pre>
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testRun() throws Exception {

		int startThreads = ManagementFactory.getThreadMXBean().getThreadCount();
		long startFreeMemory = Runtime.getRuntime().freeMemory();

		logger.debug(String.format("=== memoria livre: [%s]", Runtime.getRuntime().freeMemory()));
		logger.debug(String.format("/// threads : [%s]", ManagementFactory.getThreadMXBean().getThreadCount()));

		saveThreadInfo(false);
		showThreadDetails();

		// executa partida
		runMatch();

		// tempo para o GC atuar
		Thread.sleep(15000);

		long endFreeMemory = Runtime.getRuntime().freeMemory();
		int endThreads = ManagementFactory.getThreadMXBean().getThreadCount();
		saveThreadInfo(true);
		showThreadDetails();

		logger.debug(String.format("/// threads : antes [%s], depois[%s]", startThreads, endThreads));

		long memoryLeak = endFreeMemory - startFreeMemory;
		double percent = (memoryLeak / (double) startFreeMemory) * 100;
		logger.debug(String.format("=== memoria livre: antes [%s], depois[%s]", startFreeMemory, endFreeMemory));
		logger.debug(String.format("=== leak: [%sK] %.2f%%", memoryLeak / 1024, percent));

		// executa partida
		runMatch();

		// tempo para o GC atuar
		Thread.sleep(15000);

		endFreeMemory = Runtime.getRuntime().freeMemory();
		endThreads = ManagementFactory.getThreadMXBean().getThreadCount();
		saveThreadInfo(true);
		showThreadDetails();

		showThreadInfoSummary();
		showStackTraceDetais();

		logger.debug(String.format("/// threads : antes [%s], depois[%s]", startThreads, endThreads));

		memoryLeak = endFreeMemory - startFreeMemory;
		percent = (memoryLeak / (double) startFreeMemory) * 100;
		logger.debug(String.format("=== memoria livre: antes [%s], depois[%s]", startFreeMemory, endFreeMemory));
		logger.debug(String.format("=== leak: [%sK] %.2f%%", memoryLeak / 1024, percent));

		int numberOfExpectedNewThreads = 2;
		// 1 - com.mysql.jdbc.AbandonedConnectionCleanupThread.run
		// 2 - java.util.concurrent.locks.LockSupport.parkNanos
		assertTrue("verifica se threads foram liberados", endThreads <= (startThreads + numberOfExpectedNewThreads));
		assertTrue("verifica se memoria foi liberada", memoryLeak <= 0);

	}

	private void showThreadInfoSummary() {
		Iterator<String> iterator = threadInfoExp.keySet().iterator();

		logger.debug("Thread info after run");
		logger.debug("\tLeft \tState \tName");
		logger.debug("-----------------------------------------------------------------");

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			ThreadInfoExpanded exp = threadInfoExp.get(key);
			logger.debug(String.format("\t%s\t%s\t%s", exp.leftBehind, exp.info.getThreadState(), exp.name));
		}

		logger.debug("-----------------------------------------------------------------");

	}

	private void showStackTraceDetais() {
		Iterator<String> iterator = threadInfoExp.keySet().iterator();

		logger.debug("StackTrace Details");
		logger.debug("==================");

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			ThreadInfoExpanded exp = threadInfoExp.get(key);
			logger.debug(String.format("(%s) %s %s", exp.leftBehind, exp.info.getThreadState(), exp.name));
			StackTraceElement[] stackTrace = exp.info.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				logger.debug(String.format("%s", stackTrace[i].toString()));
			}
			logger.debug("-----------------------------------------------------------------");
		}

		logger.debug("==================");

	}

	private void saveThreadInfo(boolean leftBehind) {
		long[] threads = ManagementFactory.getThreadMXBean().getAllThreadIds();

		for (int i = 0; i < threads.length; i++) {
			ThreadInfo threadInfo = ManagementFactory.getThreadMXBean().getThreadInfo(threads[i], 100);
			ThreadInfoExpanded exp = new ThreadInfoExpanded(i, threadInfo.getThreadName(), threadInfo, leftBehind);

			if (threadInfoExp.get(exp.name) == null) {
				threadInfoExp.put(exp.name, exp);
			}
		}

	}

	private void showThreadDetails() {
		long[] threads = ManagementFactory.getThreadMXBean().getAllThreadIds();

		logger.debug("START thread details");

		for (int i = 0; i < threads.length; i++) {
			ThreadInfo threadInfo = ManagementFactory.getThreadMXBean().getThreadInfo(threads[i]);
			logger.debug(String.format("\t[%s]:%s - %s", i, threadInfo.getThreadName(), threadInfo.getThreadState()));
		}

		logger.debug("END thread details");
	}

	/**
	 * executa uma partida com dois luchadores
	 * 
	 * @throws InterruptedException
	 */
	protected void runMatch() throws Exception {
		MatchRunner match = MockMatchRunner.build();

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Luchador b = MockLuchador.build();
		b.setId(2L);

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript("turnGun(10);fire(1);turn(12);move(-10);");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodes().addAll(codes);

		Code c1 = new Code();
		c1.setEvent(MethodNames.REPEAT);
		c1.setScript("turnGun(-10);fire(2);turn(12);move(10);");
		List<Code> codes1 = new ArrayList<Code>();
		codes1.add(c1);

		b.getCodes().addAll(codes1);

		match.add(a);
		match.add(b);

		match.getMatchStart().blockingSubscribe(new Consumer<Long>() {
			public void accept(Long aLong) throws Exception {
				LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
				LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

				runnerA.getState().setX(100);
				runnerA.getState().setY(100);
				runnerA.getState().setGunAngle(180);

				runnerB.getState().setX(100);
				runnerB.getState().setY(150);
				runnerB.getState().setGunAngle(90);

				logger.debug(String.format("=== memoria livre: [%s]", Runtime.getRuntime().freeMemory()));
				logger.debug(String.format("/// threads : [%s]", ManagementFactory.getThreadMXBean().getThreadCount()));

				double gunAngleA1 = runnerA.getState().getPublicState().gunAngle;
				double gunAngleB1 = runnerB.getState().getPublicState().gunAngle;

				logger.debug("--- A : " + runnerA.getState().getPublicState());
				logger.debug("--- B : " + runnerB.getState().getPublicState());

				// start the match
				Thread t = new Thread(match);
				t.start();

				logger.debug("=== RUN THE MATCH FOR 15 seconds ");
				logger.debug("=== RUN THE MATCH FOR 15 seconds ");
				logger.debug("=== RUN THE MATCH FOR 15 seconds ");

				// stop the match
				Thread.sleep(15000);
				match.kill();
				logger.debug(String.format("=== memoria livre: [%s]", Runtime.getRuntime().freeMemory()));
				logger.debug(String.format("/// threads : [%s]", ManagementFactory.getThreadMXBean().getThreadCount()));

				Thread.sleep(500);

				logger.debug(String.format("=== memoria livre: [%s]", Runtime.getRuntime().freeMemory()));
				logger.debug(String.format("/// threads : [%s]", ManagementFactory.getThreadMXBean().getThreadCount()));

			}
		});

	}

}
