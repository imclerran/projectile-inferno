package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.HitboxComponent;
import com.droptableteams.game.components.LocationComponent;

import java.util.HashSet;

public class SetHitboxLocationSystem extends AbstractSystem {

    public SetHitboxLocationSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "SetHitboxLocationSystem";
        
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        LocationComponent lc = (LocationComponent)cm.getComponent(id, "LocationComponent");
        HitboxComponent hc = (HitboxComponent)cm.getComponent(id, "HitboxComponent");
        hc.getHitbox().setCenter(lc.getX(), lc.getY());
    }
}
