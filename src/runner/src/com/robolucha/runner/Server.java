package com.robolucha.runner;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;
import com.robolucha.game.MatchEventAddNPC;
import com.robolucha.game.MatchRunner;
import com.robolucha.models.GameDefinition;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.publisher.GameSubscription;
import com.robolucha.publisher.MatchEventToPublish;
import com.robolucha.storage.MatchEventStorage;

/*
 * Runs a Match based on the input MatchDefinition ID
 */
public class Server {
	
	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			throw new RuntimeException("Invalid use, must provide GameDefinition json file name");
		}
		
		GameDefinition gamedefinition = loadGameDefinition(args[0]);
		MatchRunner runner = new MatchRunner(gamedefinition);
		Thread thread = buildRunner(runner);
		thread.start();
		
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
	
	public static Thread buildRunner(MatchRunner runner) {
		ThreadMonitor.getInstance().register(runner);
		GameSubscription.getInstance().addBroadCast(runner.getMatch().getId(), runner);

		// listener para gravar eventos da partida
		runner.addListener(new MatchEventStorage(runner.getMatch()));

		// listener para adicionar NPC a partida
		runner.addListener(new MatchEventAddNPC());

		// listener para transmitir eventos da partida
		MatchEventToPublish eventPublisher = new MatchEventToPublish(runner.getMatch());
		runner.addListener(eventPublisher);
		runner.setEventToPublish(eventPublisher);

		// cria thread da partida
		Thread t = new Thread(runner);
		return t;
	}
}
