package com.robolucha.runner;

import com.google.gson.Gson;
import com.robolucha.game.action.OnInitAddNPC;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Match;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.publisher.MatchEventPublisher;
import com.robolucha.publisher.MatchStatePublisher;
import com.robolucha.publisher.RemoteQueue;
import com.robolucha.publisher.ScoreUpdater;

import java.io.BufferedReader;
import java.io.FileReader;

/*
 * Runs a Match based on the input MatchDefinition ID
 */
public class Server {

	public static void main(String[] args) throws Exception {
		addRunTimeHook();

		if (args.length < 1) {
			throw new RuntimeException("Invalid use, must provide GameDefinition json file name");
		}

		RemoteQueue queue = new RemoteQueue(Config.getInstance());

		GameDefinition gameDefinition = loadGameDefinition(args[0]);
		Match match = MatchRunnerAPI.getInstance().createMatch(gameDefinition);
		MatchRunner runner = new MatchRunner(gameDefinition, match);
		Thread thread = buildRunner(runner, queue);
		thread.start();

	}

	private static void addRunTimeHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				ThreadMonitor.getInstance().contextDestroyed();
			}
		});
	}

	static GameDefinition loadGameDefinition(String filename) throws Exception {
		Gson gson = new Gson();
		String fileContent = readFile(filename);
		GameDefinition definition = gson.fromJson(fileContent, GameDefinition.class);
		return definition;
	}

	private static String readFile(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		StringBuffer buffer = new StringBuffer();

		String line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			line = reader.readLine();
		}
		return buffer.toString();
	}

	public static Thread buildRunner(MatchRunner runner, RemoteQueue queue) {
		ThreadMonitor.getInstance().register(runner);

		//add NPC to the match
		runner.addListener(new OnInitAddNPC());

		// listener to record match events
		runner.addListener(new MatchEventPublisher(runner.getMatch(), queue));

		// listener to update scores
		runner.addListener(new ScoreUpdater());

		// listener to the match state
		runner.setPublisher(MatchStatePublisher.getInstance());

		return new Thread(runner);
	}
}
