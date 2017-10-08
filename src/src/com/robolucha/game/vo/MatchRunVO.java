package com.robolucha.game.vo;

import java.util.Date;

public class MatchRunVO  {
	
	public Long id;
	public String name;
	public Long gameId;
	public Boolean canJoin;
	public Integer minRank;
	public Long generatedRankingPoints;
	public String definition;

	public RankingVO rankingValidate;
	public RankingVO rankingGenerate;

	public String start;
	public String end;

	public Date startDate;
	public Date endDate;

	public JoinDetailsVO joinDetails;

}
