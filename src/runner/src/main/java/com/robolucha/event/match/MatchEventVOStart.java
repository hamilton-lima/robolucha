package com.robolucha.event.match;

public class MatchEventVOStart extends MatchEventVO {
    public static final String NAME = "MATCH-START";

    public MatchEventVOStart() {
        super(NAME, System.currentTimeMillis());
    }
}
