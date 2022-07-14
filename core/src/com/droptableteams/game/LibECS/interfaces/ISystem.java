package com.droptableteams.game.LibECS.interfaces;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.EntityManager;

import java.util.Set;

/**
 * The interface to be implemented by all concrete Systems.
 * <p>
 * Systems are used to store handle the logic and behavior of entities.</p>
 */
public abstract class ISystem {

    protected Set<Integer> _idSet;
    protected String _type;
    protected ComponentManager _cm;
    protected EntityManager _em;

    public Set<Integer> getIds() {
        return _idSet;
    }

    public boolean isUsedBy(int id) {
        return _idSet.contains(id);
    }

    public void addEntity(int id) {
        _idSet.add(id);
    }

    public void removeEntity(int id) {
        _idSet.remove(id);
    }

    public boolean isUnused() {
        return _idSet.isEmpty(); // system is unused if there are no entities which depend on it - so idSet is empty
    }

    public String getType() { return _type; }
    public abstract void update(int id);

    public void update() {
        for (int id : _idSet) {
            update(id);
        }
    }
}