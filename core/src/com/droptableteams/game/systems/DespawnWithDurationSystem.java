package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.DurationComponent;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

public class DespawnWithDurationSystem extends AbstractSystem {

    public DespawnWithDurationSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "DespawnWithDurationSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        DurationComponent dc =  (DurationComponent)cm.getComponent(id, "DurationComponent");
        if(dc.durationMet) {
            ECSEngine.get().flagEntityForRemoval(id);
        }
    }
}
