package com.robolucha.poc.luaj;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ KeepIndependentStates.class, MaliciousCodeHandler.class, UpdateJavaCodeFromLua.class })
public class AllTests {

}
