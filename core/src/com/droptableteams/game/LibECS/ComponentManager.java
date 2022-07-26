package com.droptableteams.game.LibECS;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

    private static ComponentManager _cm;
    private HashMap<Integer, HashMap<String, AbstractComponent>> _componentIdPools;
    private HashMap<String, HashMap<Integer, AbstractComponent>> _componentTypePools;

    /**
     * A private constructor for the singleton pattern.
     */
    private ComponentManager() {
        _componentIdPools = new HashMap<Integer, HashMap<String, AbstractComponent>>();
        _componentTypePools = new HashMap<String, HashMap<Integer, AbstractComponent>>();
    }

    /**
     * Singleton getter: Creates the ComponentManager if none exists, then returns it.
     *
     * @return  the singleton ComponentManager.
     */
    public static ComponentManager getInstance() {
        if(null == _cm) {
            _cm = new ComponentManager();
        }
        return _cm;
    }

    /**
     * Get a hashmap of all components belonging to a given entity.
     *
     * @param id  the entity id whose components should be returned.
     * @return  a list of the matching components.
     */
    public HashMap<String, AbstractComponent> getComponents(int id) { return _componentIdPools.get(id); }
    public HashMap<Integer, AbstractComponent> getComponents(String type) {
        HashMap<Integer, AbstractComponent> result = _componentTypePools.get(type);
        if(result == null){
            return new HashMap<Integer, AbstractComponent>();
        }else {
            return result;
        }
    }

    /**
     * Get a component of specified id and type.
     *
     * @param id  the id of the component to be retrieved.
     * @param type  the type of the component to be retrieved.
     * @return  the requested component.
     */
    public AbstractComponent getComponent(int id, String type) {
        if(_componentIdPools.get(id) != null){
            return _componentIdPools.get(id).get(type);
        }
        return null;
    }

    /**
     * Add a component to the manager.
     *
     * @param c  the component to be added.
     * @return  the added component.
     */
    public AbstractComponent addComponent(AbstractComponent c) {
        int id = c.getId();
        String type = c.getType();


        if(_componentIdPools.containsKey(id)) {
            _componentIdPools.get(id).put(type, c);
        }
        else {
            _componentIdPools.put(id, new HashMap<String, AbstractComponent>());
            _componentIdPools.get(id).put(type, c);
        }
        if(_componentTypePools.containsKey(type)) {
            _componentTypePools.get(type).put(id, c);
        }
        else {
            _componentTypePools.put(type, new HashMap<Integer, AbstractComponent>());
            _componentTypePools.get(type).put(id, c);
        }
        return c;
    }

    /**
     * remove all components with a matching entity id.
     *
     * @param id  the entity id whose components should be removed.
     */
    public boolean removeComponents(int id) {
        HashMap<String, AbstractComponent> flaggedForRemoval = _componentIdPools.remove(id);
        if(null == flaggedForRemoval) {
            return false;
        }
        for (Map.Entry<String, AbstractComponent> e : flaggedForRemoval.entrySet()) {
            String type = e.getValue().getType();
            _componentTypePools.get(type).remove(id);
        }
        return true;
    }

    /**
     * Remove a given component.
     *
     * @param id  the id of the component to remove.
     * @param type  the type of the component to remove.
     * @return  returns true if the component was successfully removed.
     */
    public boolean removeComponent(int id, String type) {
        AbstractComponent c1 = _componentIdPools.get(id).remove(type);
        AbstractComponent c2 = _componentTypePools.get(type).remove(id);
        if(null != c1 || null != c2) {
            return true;
        }
        return false;
    }

    /**
     * Remove a given component.
     *
     * @param c  a component to remove.
     * @return  returns true if the component was successfully removed.
     */
    public boolean removeComponent(AbstractComponent c) {
        int id = c.getId();
        String type = c.getType();
        if(_componentIdPools.containsKey(id)) {
            _componentIdPools.get(id).remove(type);
        }
        if(_componentTypePools.containsKey(type)) {
            _componentTypePools.get(type).remove(id);
            return true;
        }
        return false;
    }
}