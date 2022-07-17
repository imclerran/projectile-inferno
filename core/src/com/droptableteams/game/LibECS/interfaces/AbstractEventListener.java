package com.droptableteams.game.LibECS.interfaces;

import java.util.HashMap;

/**
 * The interface to be implemented by all concrete EventListeners.
 * <p>
 * EventListeners contain a callback to be processed when triggered by an event.</p>
 *
 * TODO: clarify whether id and type apply to the listener itself, or what it is listening for
 */
public abstract class AbstractEventListener {
    protected int _id;
    protected String _type;
    protected String _triggerType;

    public Integer getId() {
        return _id;
    }
    public String getType() {
        return _type;
    }
    public String getTriggerType() {
        return _triggerType;
    }
    public abstract boolean canHandle(Integer id, String type);
    public abstract void handleEvent(HashMap<String, Object> args);
}