package com.droptableteams.game.LibECS.interfaces;

/**
 * The interface to be implemented by all concrete Components.
 * <p>
 * Components are used to store information about entities.</p>
 */
public abstract class AbstractComponent {

    protected int _id;
    protected String _type;

    public int getId() { return _id; }
    public String getType() { return _type; }
}