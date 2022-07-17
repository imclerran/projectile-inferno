package com.droptableteams.game.LibECS;

import com.droptableteams.game.LibECS.interfaces.AbstractSystem;

import java.util.*;

public class SystemManager {
    private static SystemManager _sm;
    private EventManager _evm;
    private HashMap<String, AbstractSystem> _systemsPool;


    /**
     * A private constructor for the singleton pattern.
     */
    private SystemManager() {
        _evm = EventManager.getInstance();
        //_systemIdPools = new HashMap<Integer, HashMap<String, ISystem>>();
        //_systemTypePools = new HashMap<String, HashMap<Integer, ISystem>>();
    }

    /**
     * Singleton getter: Creates the SystemManager if none exists, then returns it.
     *
     * @return  the singleton SystemManager.
     */
    public static SystemManager getInstance() {
        if(null == _sm) {
            _sm = new SystemManager();
        }
        return _sm;
    }


    /**
     * Add a system to the manager.
     *
     * @param s  the system to add.
     * @return  the added system.
     */
    public AbstractSystem addSystem(AbstractSystem s, int id) {
        String type = s.getType();
        if(!_systemsPool.containsKey(type)) {
            _systemsPool.put(type, s);
        }
        else {
            _systemsPool.get(type).addEntity(id);
        }
        return _systemsPool.get(type);
    }

    /**
     * remove all systems with a matching entity id.
     *
     * @param id  the entity id whose systems should be removed.
     * @return  true if systems were removed.
     */
    public boolean removeSystems(int id) {
        ArrayList<String> flaggedForRemoval = new ArrayList<String>();
        for(Map.Entry<String, AbstractSystem> e : _systemsPool.entrySet()) {
            if(e.getValue().isUsedBy(id)) {
                flaggedForRemoval.add(e.getKey());
            }
        }
        for (String type : flaggedForRemoval) {
            _systemsPool.get(type).removeEntity(id);
            if (_systemsPool.get(type).isUnused()) {
                _systemsPool.remove(type);
            }
        }
        if (flaggedForRemoval.isEmpty()) {
            return false;
        }
        return true;
    }
    /**
     * update all systems matching the specified type.
     *
     * @param type  the type of the system to be updated
     */
    public void updateSystemOfType(String type) {
        if(_systemsPool.containsKey(type)) {
            _systemsPool.get(type).update();
        }
    }
}