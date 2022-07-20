package com.droptableteams.game.LibECS;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;

import java.util.ArrayList;

/**
 * ECSEngine is the primary endpoint for accessing and interacting with Entities, Components, and Systems.
 * The engine contains methods for adding and removing entities, as well as the main update method for 
 * updating all systems.
 *
 * @author Ian McLerran
 */
public class ECSEngine {
    private static ECSEngine _engine;

    private ComponentManager _cm;
    private EntityManager _em;
    private EventManager _evm;
    private SystemManager _sm;

    private String[] _systemUpdateOrder;
    private ArrayList<Integer> _flaggedForRemoval;

    // TODO: rework ECSEngine to allow more elegant access:
    // -> Either: eliminate the singleton pattern,
    // -> or: modify getInstance() to require no arguments

    /**
     * A private constructor for the singleton pattern.
     *
     * @param systemUpdateOrder  an array of system types in the order in which they should be processed.
     */
    private ECSEngine(String[] systemUpdateOrder) {
        _systemUpdateOrder = systemUpdateOrder;
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
        _evm = EventManager.getInstance();
        _sm = SystemManager.getInstance();
        _flaggedForRemoval = new ArrayList<Integer>();
    }

    /**
     * Allows the client to check which entities are queued to be removed this cycle.
     * @return ArrayList of the ids of entities to be removed
     */
    public ArrayList<Integer> getFlaggedForRemoval() {
        return _flaggedForRemoval;
    }

    /**
     * Removes all entities that have been flagged for removal,
     * as well as their associated components and systems.
     */
    private void removeFlagged() {
        for(Integer id : _flaggedForRemoval) {
            removeEntity(id);
        }
        _flaggedForRemoval.clear();
    }

    /**
     * Singleton getter: Creates the ComponentManager if none exists, then returns it.
     *
     * @param systemUpdateOrder  an array of system types in the order in which they should be processed.
     * @return  the singleton ECSEngine.
     */
    public static ECSEngine getInstance(String[] systemUpdateOrder) {
        if(null == _engine) {
            _engine = new ECSEngine(systemUpdateOrder);
        }
        return _engine;
    }

    /*
     * Gets an instance of ECSEngine, only when ECSEngine is already initialized.
     * <p> NOTE: ECSEngine should always be initialized by calling {@link #getInstance(String[]) getInstance} first.
     *
     * @return  the pre-initialized instance of ECSEngine singleton
     */
    public static ECSEngine get() throws Exception {
        if (null == _engine) {
            throw new NullPointerException("ECSEngine must be initialized with getInstance(String[] systemUpdateOrder)");
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
     * Getter and setter for _systemUpdateOrder list.
     */
    public String[] getOrderedSystemTypes() { return _systemUpdateOrder; }
    public void setOrderedSystemTypes(String[] types) { _systemUpdateOrder = types; }

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
    public void addEntity(AbstractEntity e, ArrayList<AbstractComponent> cl, ArrayList<AbstractSystem> sl) {
        _em.addEntity(e);
        for (AbstractComponent c : cl) {
            _cm.addComponent(c);
        }
        for (AbstractSystem s : sl) {
            _sm.addSystem(s, e.getId());
        }
    }

    /**
     * Build an entity and its associated components and systems, and add them to their respective managers.
     *
     * @param builder  a builder which generates the entity, components, and systems.
     */
    public AbstractEntity addEntity(AbstractEntityBuilder builder) {
        builder.startBuild();
        AbstractEntity entity = _em.addEntity(builder.buildEntity());
        for (AbstractComponent c : builder.buildComponentList()) {
            _cm.addComponent(c);
        }
        for (AbstractSystem s : builder.buildSystemList()) {
            _sm.addSystem(s, entity.getId());
        }
        builder.finishBuild();
        return entity;
    }

    /**
     * Remove an entity, and all of its components and systems.
     * 
     * @deprecated use {@link #flagEntityForRemoval(int) flagEntityForRemoval} instead.
     * @param id  the id of the entity to remove.
     * @return  true if successfully removed.
     */
    @Deprecated(forRemoval = true)
    public boolean removeEntity(int id) {
        boolean removed = _em.removeEntity(id);
        _cm.removeComponents(id);
        _sm.removeSystems(id);
        return removed;
    }

    /**
     * flag an entity for removal at the end of the current update cycle.
     * @param id  the id of the entity to remove.
     */
    public void flagEntityForRemoval(int id) {
        _flaggedForRemoval.add(id);
    }

    /**
     * Called each tick. This calls the update method for each syst_em in the order defined,
     * then dispatches all events created during this tick cycle.
     */
    public void update() {
        for (String sType : _systemUpdateOrder) {
            _sm.updateSystemOfType(sType);
        }
        _evm.dispatchEvents();
        removeFlagged();
    }
}