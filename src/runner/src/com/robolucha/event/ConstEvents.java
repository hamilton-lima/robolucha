package com.robolucha.event;

public interface ConstEvents {
    int ACTION_INIT = 0;
    int ACTION_START = 1;
    int ACTION_END = 2;
    int ACTION_DAMAGE = 3;
    int ACTION_KILL = 4;
    int ACTION_ALIVE = 5;

    String LUCHADOR_NAME_CHANGE = "luchador_name_change";

    String DAMAGE = "DAMAGE";
    String KILL = "KILL";
    String INIT = "INIT";
    String START = "START";
}
