package com.robolucha.monitor;

public class ThreadStatusVO {

	private String name;
	private String status;
	private Long start;

	public ThreadStatusVO(String name, String status, Long start) {
		this.name = name;
		this.status = status;
		this.start = start;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "ThreadStatusVO [name=" + name + ", status=" + status
				+ ", start=" + start + "]";
	}

}
