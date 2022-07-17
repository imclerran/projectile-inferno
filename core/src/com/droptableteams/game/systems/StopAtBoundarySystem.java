package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.LocationComponent;

import java.util.HashSet;

public class StopAtBoundarySystem extends AbstractSystem {

    public StopAtBoundarySystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "StopAtBoundarySystem";
        _cm = ComponentManager.getInstance();
    }

    @Override
    public void update(int id) {
        LocationComponent lc = (LocationComponent)_cm.getComponent(id, "LocationComponent");
        float leftBound = 0;
        float rightBound = Gdx.graphics.getWidth();
        float bottomBound = 0;
        float topBound = Gdx.graphics.getHeight();

        if(lc.getX() < leftBound) {
            lc.setX(leftBound);
        }
        else if(lc.getX() > rightBound) {
            lc.setX(rightBound);
        }
        if(lc.getY() < bottomBound) {
            lc.setY(bottomBound);
        }
        else if(lc.getY() > topBound) {
            lc.setY(topBound);
        }
    }
}
