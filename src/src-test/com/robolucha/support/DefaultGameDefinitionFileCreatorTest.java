package com.robolucha.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

import com.google.gson.Gson;
import com.robolucha.runner.models.GameDefinition;

public class DefaultGameDefinitionFileCreatorTest {

	@Test
	public void testJsonOutput() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		PrintStream stream = new PrintStream(buffer);
		System.setOut(stream);

		DefaultGameDefinitionFileCreator.main(new String[] {});
		String output = buffer.toString();
		assertNotNull(output);
		
		Gson gson = new Gson();
		GameDefinition actual = gson.fromJson(output, GameDefinition.class);
		GameDefinition expected = new GameDefinition();

		assertEquals(expected, actual);
	}

	@Test
	public void testFileOutput() throws Exception {
		String filename = "/tmp/gamedefinition-default.json";

		DefaultGameDefinitionFileCreator.main(new String[] { filename });
		String output = readFile(filename);
		assertNotNull(output);
		
		Gson gson = new Gson();
		GameDefinition actual = gson.fromJson(output, GameDefinition.class);
		GameDefinition expected = new GameDefinition();

		assertEquals(expected, actual);
	}

	private String readFile(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		StringBuffer buffer = new StringBuffer();

		String line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			line = reader.readLine();
		}
		return buffer.toString();
	}

	@Test
	public void testIsJSONPrettyFormated() throws Exception {
		String filename = "/tmp/gamedefinition-default-pretty.json";

		DefaultGameDefinitionFileCreator.main(new String[] { filename });
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String actual = reader.readLine();
		assertNotNull(actual);
		assertEquals("{", actual);
		
	}
	
}
