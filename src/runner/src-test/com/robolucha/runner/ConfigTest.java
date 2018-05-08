package com.robolucha.runner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.junit.Assert.*;


public class ConfigTest {

    @Before
    public void before(){
        Config.reset();
    }

    @Test
    public void isValidHost() {
        Config c = Config.getInstance();
        assertTrue(c.isValidHost("localhost"));
        assertTrue(c.isValidHost("a"));
        assertFalse(c.isValidHost(" "));
        assertFalse(c.isValidHost("  "));
        assertFalse(c.isValidHost(null));
    }

    @Test
    public void getInstance() {
        Config c = Config.getInstance();
        assertTrue(c != null);
    }

    @Test
    public void getRedisHost() {
        Config c = Config.getInstance();
        assertEquals(Config.DEFAULT_REDIS_HOST, c.getRedisHost());
    }

    @Rule
    public final EnvironmentVariables environmentVariable = new EnvironmentVariables();

    @Test
    public void getConfigFromEnvironmentVariable() {
        environmentVariable.set(Config.REDIS_HOST, "foo");

        Config c = Config.getInstance();
        assertEquals("foo", c.getRedisHost());
    }
}