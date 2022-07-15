package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.ISystem;
import com.droptableteams.game.components.HasBeenInboundsComponent;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

/**
 * TODO: Should be updated to to despawn when fully invisible, instead of after center-point passes boundary.
 */
public class DespawnOutOfBoundsSystem extends ISystem {

    public DespawnOutOfBoundsSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "DespawnOutOfBoundsSystem";
        _cm = ComponentManager.getInstance();
    }

    @Override
    public void update(int id) {
        LocationComponent lc = (LocationComponent)_cm.getComponent(id, "LocationComponent");
        HasBeenInboundsComponent ibc = (HasBeenInboundsComponent)_cm.getComponent(id, "HasBeenInboundsComponent");
        boolean hasBeenInbounds = ibc.getHasBeenInBounds();
        float leftBound = 0;
        float rightBound = Gdx.graphics.getWidth();
        float bottomBound = 0;
        float topBound = Gdx.graphics.getHeight();
        float x = lc.getX();
        float y = lc.getY();

        if(!hasBeenInbounds) {
            if((leftBound < x && rightBound > x) && (bottomBound < y && topBound > y)) {
                ibc.setHasBeenInBounds(true);
            }
        }
        else {
            if((leftBound > x ||  x > rightBound) || (bottomBound > y || y > topBound)) {
                ECSEngine.getInstance(SystemUpdateOrder.get()).flagEntityForRemoval(id);
            }
        }
    }
}
