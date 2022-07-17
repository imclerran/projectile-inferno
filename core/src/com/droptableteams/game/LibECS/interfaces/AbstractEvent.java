package com.droptableteams.game.LibECS.interfaces;

import java.util.HashMap;

/**
 * The interface to be implemented by all concrete Events.
 * <p>
 * Events are used to trigger a callback.</p>
 */
public abstract class AbstractEvent {
    protected int _id;
    protected String _type;
    protected HashMap<String, Object> _args;

    public int getId() {
        return _id;
    }
    public String getType() {
        return _type;
    }
    public HashMap<String, Object> getArgs() {
        return _args;
    }
}