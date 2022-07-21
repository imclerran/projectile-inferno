package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.MoveDirectionComponent;
import com.droptableteams.game.components.VelocityComponent;

import java.util.HashSet;

public class DirectionalMovementSystem extends AbstractSystem {

    public DirectionalMovementSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "DirectionalMovementSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        VelocityComponent vc = (VelocityComponent)cm.getComponent(id, "VelocityComponent");
        MoveDirectionComponent mdc = (MoveDirectionComponent) cm.getComponent(id, "MoveDirectionComponent");
        LocationComponent lc = (LocationComponent)cm.getComponent(id, "LocationComponent");
        float newDx = 0;
        float newDy = 0;
        Float angle = mdc.getRadians();
        if(null != angle) {
            float hyp = vc.getModifiedSpeed();
            newDx = (float)Math.cos(angle)*hyp;
            newDy = (float)Math.sin(angle)*hyp;
        }
        vc.setDx(0);
        vc.setDy(0);
        float dt = Gdx.graphics.getDeltaTime();
        lc.setX(lc.getX()+newDx*dt);
        lc.setY(lc.getY()+newDy*dt);
    }
}
