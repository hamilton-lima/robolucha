package com.robolucha.runner;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;
import com.robolucha.game.action.OnInitAddNPC;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Match;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.publisher.MatchEventStorage;
import com.robolucha.publisher.MatchEventToPublish;

/*
 * Runs a Match based on the input MatchDefinition ID
 */
public class Server {
	
	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			throw new RuntimeException("Invalid use, must provide GameDefinition json file name");
		}
		
		GameDefinition gameDefinition = loadGameDefinition(args[0]);
		Match match = createMatch(gameDefinition);
		MatchRunner runner = new MatchRunner(gameDefinition, match);
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
		runner.addListener(new OnInitAddNPC());

		// listener para transmitir eventos da partida
		MatchEventToPublish eventPublisher = new MatchEventToPublish(runner.getMatch());
		runner.addListener(eventPublisher);
		runner.setEventToPublish(eventPublisher);

		// cria thread da partida
		Thread t = new Thread(runner);
		return t;
	}
}
