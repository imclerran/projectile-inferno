package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.ISystem;
import com.droptableteams.game.components.HitboxComponent;
import com.droptableteams.game.components.LocationComponent;

import java.util.HashSet;

public class SetHitboxLocationSystem extends ISystem {

    public SetHitboxLocationSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "SetHitboxLocationSystem";
        _cm = ComponentManager.getInstance();
    }

    @Override
    public void update(int id) {
        LocationComponent lc = (LocationComponent)_cm.getComponent(id, "LocationComponent");
        HitboxComponent hc = (HitboxComponent)_cm.getComponent(id, "HitboxComponent");
        hc.getHitbox().setCenter(lc.getX(), lc.getY());
    }
}
