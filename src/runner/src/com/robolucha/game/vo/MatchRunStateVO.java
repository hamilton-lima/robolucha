package com.robolucha.game.vo;

import java.util.ArrayList;
import java.util.List;

public class MatchRunStateVO {
	
	public static final MatchRunStateVO EMPTY = new MatchRunStateVO();
	
	public List<EventVO> events;
	public List<BulletVO> bullets;
	public List<PunchVO> punches;
	public List<LuchadorPublicStateVO> luchadores;
	public List<ScoreVO> scores;
	public long clock;
	public ArrayList<MessageVO> messages;
	public String state;
	
	public MatchRunStateVO() {
		luchadores = new ArrayList<LuchadorPublicStateVO>();
		bullets = new ArrayList<BulletVO>();
		punches = new ArrayList<PunchVO>();
		events = new ArrayList<EventVO>();
		scores = new ArrayList<ScoreVO>();
		messages = new ArrayList<MessageVO>(); 
	}

}
