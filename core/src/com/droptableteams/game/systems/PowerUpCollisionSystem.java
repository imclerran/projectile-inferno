package com.droptableteams.game.systems;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.CollisionsComponent;
import com.droptableteams.game.components.HitboxComponent;
import com.droptableteams.game.components.LifeCounterComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

public class PowerUpCollisionSystem extends AbstractSystem {

    private EntityManager _em;
    public PowerUpCollisionSystem(int id){
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "PowerUpCollisionSystem";
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
    }

    // Copied from bullet collision system. Needs to be restructured in order to
    // work with power ups
    @Override
    public void update(int id) {
        HitboxComponent thisHbc = (HitboxComponent)_cm.getComponent(id, "HitboxComponent");
        Rectangle intersection = new Rectangle();

        HitboxComponent thatHbc = (HitboxComponent)_cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "HitboxComponent");
        CollisionsComponent cc = (CollisionsComponent)_cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "CollisionsComponent");
        if(Intersector.intersectRectangles(thisHbc.getHitbox(), thatHbc.getHitbox(), intersection)) {
            cc.addCollision(id);
            ((LifeCounterComponent)_cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "LifeCounterComponent")).incrementLife();

            ECSEngine.get().flagEntityForRemoval(id);
        }
    }
}
