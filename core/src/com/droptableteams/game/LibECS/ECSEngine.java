package com.droptableteams.game.LibECS;

import com.droptableteams.game.LibECS.interfaces.IComponent;
import com.droptableteams.game.LibECS.interfaces.IEntity;
import com.droptableteams.game.LibECS.interfaces.ISystem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ECSEngine {
    private static ECSEngine _engine;

    private ComponentManager _cm;
    private EntityManager _em;
    private EventManager _evm;
    private SystemManager _sm;

    private String[] _orderedSystemTypes;

    /**
     * A private constructor for the singleton pattern.
     *
     * @param orderedSystemTypes  an array of system types in the order in which they should be processed.
     */
    private ECSEngine(String[] orderedSystemTypes) {
        _orderedSystemTypes = orderedSystemTypes;
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
        _evm = EventManager.getInstance();
        _sm = SystemManager.getInstance();
    }

    /**
     * Singleton getter: Creates the ComponentManager if none exists, then returns it.
     *
     * @param orderedSystemTypes  an array of system types in the order in which they should be processed.
     * @return  the singleton ECSEngine.
     */
    public static ECSEngine getInstance(String[] orderedSystemTypes) {
        if(null == _engine) {
            _engine = new ECSEngine(orderedSystemTypes);
        }
        return _engine;
    }

    /**
     * Getters for manager classes.
     */
    public ComponentManager getComponentManager() { return _cm; }
    public EntityManager getEntityManager() { return _em; }
    public EventManager getEventManager() { return _evm; }
    public SystemManager getSystemManager() { return _sm; }

    /**
     * Getter and setter for _orderedSystemTypes list.
     */
    public String[] getOrderedSystemTypes() { return _orderedSystemTypes; }
    public void setOrderedSystemTypes(String[] types) { _orderedSystemTypes = types; }

    /**
     * Get an available entity id from the entity manager.
     *
     * @return  an available entity id.
     */
    public int acquireEntityId() {
        return _em.acquireEntityId();
    }

    /**
     * Add an entity and its associated components and systems to the managers.
     *
     * @param e  the entity to add.
     * @param cl  a list of components to add.
     * @param sl  a list of systems to add.
     */
    public void addEntity(IEntity e, ArrayList<IComponent> cl, ArrayList<ISystem> sl) {
        _em.addEntity(e);
        for (IComponent c : cl) {
            _cm.addComponent(c);
        }
        for (ISystem s : sl) {
            _sm.addSystem(s);
        }
    }

    /**
     * Called each tick. This calls the update method for each syst_em in the order defined,
     * then dispatches all events created during this tick cycle.
     */
    public void update() {
        for (String sType : _orderedSystemTypes) {
            Set<Map.Entry<Integer, ISystem>> systems = _sm.getSystemEntries(sType);
            for (Map.Entry<Integer, ISystem> e : systems) {
                e.getValue().update();
            }
        }
        _evm.dispatchEvents();
    }
}