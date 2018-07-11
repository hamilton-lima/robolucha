package com.robolucha.game.vo;

import java.util.ArrayList;

public class MessageVO {

	public static final ArrayList<MessageVO> EMPTY_LIST = new ArrayList<MessageVO>();

	public static final String DEBUG = "debug";
	public static final String WARNING = "warning";
	public static final String SUCCESS = "success";
	public static final String DANGER = "danger";

	public String type;
	public String event;
	public String message;

	@Override
	public String toString() {
		return "MessageVO [type=" + type + ", event=" + event + ", message=" + message + "]";
	}

	public MessageVO(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public MessageVO(String type, String event, String message) {
		this.type = type;
		this.event = event;
		this.message = message;
	}

	public MessageVO() {
	}

}
