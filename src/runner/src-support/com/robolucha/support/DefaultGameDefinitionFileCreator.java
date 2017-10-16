package com.robolucha.support;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robolucha.models.GameDefinition;

/**
 * Create a json file with the default Game definition values to be used as
 * template for game definitions
 * 
 * @author hamiltonlima
 *
 */
public class DefaultGameDefinitionFileCreator {

	public static void main(String[] args) throws IOException {
		GameDefinition gameDefinition = new GameDefinition();
		String json = generateJson(gameDefinition);

		if (args.length > 0) {
			String fileName = args[0];
			writeFile(fileName, json);
		} else {
			System.out.println(json);
		}

	}

	private static void writeFile(String fileName, String json) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write(json);
		writer.flush();
		writer.close();
	}

	private static String generateJson(GameDefinition gameDefinition) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		String result = gson.toJson(gameDefinition);
		return result;
	}
}
