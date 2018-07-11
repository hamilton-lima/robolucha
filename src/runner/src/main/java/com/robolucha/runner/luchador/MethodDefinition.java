package com.robolucha.runner.luchador;

public class MethodDefinition {

	private String name;
	private String start;
	private String end;

	public MethodDefinition(String name, String start, String end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "MethodDefinition [name=" + name + ", start=" + start + ", end=" + end + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
