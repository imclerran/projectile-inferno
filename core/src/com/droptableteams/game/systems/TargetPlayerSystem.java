package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.FirePatternComponent;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;

import java.util.HashSet;

public class TargetPlayerSystem extends AbstractSystem {

    public TargetPlayerSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "TargetPlayerSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        LocationComponent lc = (LocationComponent)ComponentManager.getInstance().getComponent(id, "LocationComponent");
        LocationComponent plc = (LocationComponent)ComponentManager.getInstance().getComponent(SpecialEntityIds.PLAYER_ENTITY, "LocationComponent");
        FirePatternComponent fpc = (FirePatternComponent)cm.getComponent(id, "FirePatternComponent");

        float dy = plc.getY() - lc.getY();
        float dx = plc.getX() - lc.getX();
        float theta = (float)Math.atan2(dy,dx);
        fpc.setBaseDirection(theta);
    }
}
