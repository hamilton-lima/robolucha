package com.robolucha.runner.luchador.lua;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;


public class ScriptReader {
	
	private static Logger logger = Logger.getLogger(ScriptReader.class);

	public String readDefinitions(Class clazz, String file) {
		StringBuffer buffer = new StringBuffer();

		try {
			String fileName = resolveName(clazz, file);
			logger.debug("reading file name definition : " + fileName);
			InputStream in = clazz.getResourceAsStream(fileName);

			logger.debug("input stream = " + in);

			Reader fr = new InputStreamReader(in, "utf-8");

			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();
			while (line != null) {
				buffer.append(line + "\r");
				line = reader.readLine();
			}
		} catch (Exception e) {
			logger.error("error reading : " + file, e);
		}

		return buffer.toString();
	}

	/**
	 * add current folder to the file name 
	 * 
	 * @param c
	 * 
	 * @param name
	 * @return
	 * @see http 
	 *      ://docs.oracle.com/javase/6/docs/technotes/guides/lang/resources.
	 *      html
	 */
	private String resolveName(Class c, String name) {
		if (name == null) {
			return name;
		}
		if (!name.startsWith("/")) {

			while (c.isArray()) {
				c = c.getComponentType();
			}
			String baseName = c.getName();
			int index = baseName.lastIndexOf('.');
			if (index != -1) {
				name = "/" + baseName.substring(0, index).replace('.', '/') + "/" + name;
			}
		} else {
			name = name.substring(1);
		}
		return name;
	}
}
