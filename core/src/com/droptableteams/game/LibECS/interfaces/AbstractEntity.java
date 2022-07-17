package com.droptableteams.game.LibECS.interfaces;

/**
 * The interface to be implemented by all concrete Entities.
 * <p>
 * Entities represent any in game object. They are composed of Components and Systems.</p>
 */
public abstract class AbstractEntity {

    protected int _id;
    protected String _type;

    public int getId() { return _id; }
    public String getType() { return _type; }
}