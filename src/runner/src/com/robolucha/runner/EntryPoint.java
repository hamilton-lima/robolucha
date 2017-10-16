package com.robolucha.runner;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;
import com.robolucha.models.GameDefinition;

/*
 * Runs a Match based on the input MatchDefinition ID
 */
public class EntryPoint {
	
	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			throw new RuntimeException("Invalid use, must provide GameDefinition json file name");
		}
		
		GameDefinition definition = loadGameDefinition(args[0]);
		
		
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
}
