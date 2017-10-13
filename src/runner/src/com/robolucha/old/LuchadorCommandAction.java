package com.robolucha.old;

import java.util.Iterator;
import java.util.LinkedList;

public class LuchadorCommandAction {

	private String name;
	private long start;
	private LinkedList<LuchadorCommand> commands;

	public LuchadorCommandAction(String name, long start) {
		this.name = name;
		this.commands = new LinkedList<LuchadorCommand>();
		this.start = start;
	}

	public String getName() {
		return name;
	}

	public LinkedList<LuchadorCommand> getCommands() {
		return commands;
	}

	public long getStart() {
		return start;
	}

	@Override
	public String toString() {
		return "LuchadorCommandAction [name=" + name + ", start=" + start + ", commands=" + commands + "]";
	}

	public void clear(String prefix) {
		Iterator<LuchadorCommand> iterator = commands.iterator();
		while (iterator.hasNext()) {
			LuchadorCommand command = iterator.next();

			if (command.getKey().startsWith(prefix)) {
				iterator.remove();
			}

		}
	}

}
