package com.robolucha.event.match;

public abstract class MatchEventVO {
    public long when;
    public String name;

    public MatchEventVO(String name, long when){
        this.when = when;
        this.name = name;
    }
}
