package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.RelativePositionComponent;

import java.util.HashSet;

public class RelativePositionSystem extends AbstractSystem {

    public RelativePositionSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "RelativePositionSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        RelativePositionComponent rpc = (RelativePositionComponent)cm.getComponent(id, "RelativePositionComponent");
        LocationComponent thisLc = (LocationComponent)cm.getComponent(id, "LocationComponent");
        LocationComponent thatLc = (LocationComponent)cm.getComponent(rpc.getRelativeEntityId(), "LocationComponent");
        float angle = rpc.getAngle();
        float hyp = rpc.getRadius();
        float adj = (float)(Math.cos(angle)*hyp);
        float opp = (float)(Math.sin(angle)*hyp);
        thisLc.setX(thatLc.getX()+adj);
        thisLc.setY(thatLc.getY()+opp);
    }
}
