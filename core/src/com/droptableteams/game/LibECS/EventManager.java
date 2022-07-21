package com.droptableteams.game.LibECS;

import com.droptableteams.game.LibECS.interfaces.AbstractEvent;
import com.droptableteams.game.LibECS.interfaces.AbstractEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: EventManager class can currently only accept a single event listener
 * which listens to the same event from the same entity. While this may be acceptable
 * in most use cases, there may be situations where multiple entities might wish to
 * subscribe to a single event from a single other entity.
 *
 * This behavior is not currently supported.
 */
public class EventManager {
    private static EventManager _evm;

    private ArrayList<AbstractEvent> _events;
    private HashMap<Integer, HashMap<String, AbstractEventListener>> _eventListenerIdPool;
    private HashMap<String, HashMap<Integer, AbstractEventListener>> _eventListenerTypePool;

    /**
     * A private constructor for the singleton pattern.
     */
    private EventManager() {
        _events = new ArrayList<AbstractEvent>();
        _eventListenerIdPool = new HashMap<Integer, HashMap<String, AbstractEventListener>>();
        _eventListenerTypePool = new HashMap<String, HashMap<Integer, AbstractEventListener>>();
    }

    /**
     * Singleton getter: Creates the EventManager if none exists, then returns it.
     *
     * @return  the singleton EventManager.
     */
    public static EventManager getInstance() {
        if(null == _evm) {
            _evm = new EventManager();
        }
        return _evm;
    }

    /**
     * Add an event.
     *
     * @param e  an event to add.
     * @return   the added event.
     */
    public AbstractEvent addAddEvent(AbstractEvent e) {
        _events.add(e);
        return e;
    }

    /**
     * Add an event listener.
     *
     * @param el  an event listener to add.
     * @return  the added event listener.
     */
    public AbstractEventListener addEventListener(AbstractEventListener el) {
        Integer id = el.getId();
        String type = el.getType();

        if(null != id) {
            if (_eventListenerIdPool.containsKey(id)) {
                _eventListenerIdPool.get(id).put(type, el);
            } else {
                _eventListenerIdPool.put(id, new HashMap<String, AbstractEventListener>());
                _eventListenerIdPool.get(id).put(type, el);
            }
        }

        else if(null != type) {
            if(_eventListenerTypePool.containsKey(type)) {
                _eventListenerTypePool.get(type).put(id, el);
            }
            else {
                _eventListenerTypePool.put(type, new HashMap<Integer, AbstractEventListener>());
                _eventListenerTypePool.get(type).put(id, el);
            }
        }
        return el;
    }

    /**
     * dispatch all current events to all listeners that can handle them,
     * then clears the events list.
     */
    public void dispatchEvents() {
        for (AbstractEvent e : _events) {
            Integer id = e.getId();
            String type = e.getType();

            if(null != id) {
                HashMap<String, AbstractEventListener> elMap = _eventListenerIdPool.get(id);
                for (Map.Entry<String, AbstractEventListener> entry : elMap.entrySet()) {
                    AbstractEventListener el = entry.getValue();
                    if(el.canHandle(id, type)) {
                        el.handleEvent(e.getArgs());
                    }
                }
            }
            else if(null != type) {
                HashMap<Integer, AbstractEventListener> elMap = _eventListenerTypePool.get(type);
                for (Map.Entry<Integer, AbstractEventListener> entry : elMap.entrySet()) {
                    AbstractEventListener el = entry.getValue();
                    if(el.canHandle(id, type)) {
                        el.handleEvent(e.getArgs());
                    }
                }
            }
        }
        _events.clear();
    }

    /**
     * Remove all event listeners with a matching id.
     *
     * @param id  the entity id to be matched for listener removal.
     * @return  true if at least one event listener was removed.
     */
    public boolean removeEventListeners(int id) {
        HashMap<String, AbstractEventListener> flaggedForRemoval = _eventListenerIdPool.remove(id);
        for(Map.Entry<String, AbstractEventListener> e : flaggedForRemoval.entrySet()) {
            String type = e.getValue().getType();
            if(null != type) {
                _eventListenerTypePool.get(type).remove(id);
            }
        }
        return flaggedForRemoval.size() > 0;
    }

    /**
     * Remove all event listeners with a matching type.
     *
     * @param type  the event type to be matched for listener removal.
     * @return  true if at least one event listener was removed.
     */
    public boolean removeEventListeners(String type) {

        HashMap<Integer, AbstractEventListener> flaggedForRemoval = _eventListenerTypePool.remove(type);
        for(Map.Entry<Integer, AbstractEventListener> e : flaggedForRemoval.entrySet()) {
            Integer id = e.getValue().getId();
            if(null != id) {
                _eventListenerTypePool.get(type).remove(id);
            }
        }
        return flaggedForRemoval.size() > 0;
    }

    /**
     * Remove an event listener with a matching id and type.
     *
     * @param id  the entity id to be matched for listener removal.
     * @param type  the event type to be matched for listener removal.
     * @return  true if at least one event listener was removed.
     */
    public boolean removeEventListener(int id, String type) {
        // TODO: fix always returns true - fixed?
        AbstractEventListener el1 = _eventListenerIdPool.get(id).remove(type);
        AbstractEventListener el2 = _eventListenerTypePool.get(type).remove(id);
        if(null != el1 || null != el2) {
            return true;
        }
        return false;
    }
}