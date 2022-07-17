package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.ISystem;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.RelativePositionComponent;

import java.util.HashSet;

public class RelativePositionSystem extends ISystem {

    public RelativePositionSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "RelativePositionSystem";
        _cm = ComponentManager.getInstance();
    }

    @Override
    public void update(int id) {
        RelativePositionComponent rpc = (RelativePositionComponent)_cm.getComponent(id, "RelativePositionComponent");
        LocationComponent thisLc = (LocationComponent)_cm.getComponent(id, "LocationComponent");
        LocationComponent thatLc = (LocationComponent)_cm.getComponent(rpc.getRelativeEntityId(), "LocationComponent");
        float angle = rpc.getAngle();
        float hyp = rpc.getRadius();
        float adj = (float)(Math.cos(angle)*hyp);
        float opp = (float)(Math.sin(angle)*hyp);
        thisLc.setX(thatLc.getX()+adj);
        thisLc.setY(thatLc.getY()+opp);
    }
}
