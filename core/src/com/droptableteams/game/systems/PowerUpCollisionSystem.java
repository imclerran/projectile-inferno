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

    public PowerUpCollisionSystem(int id){
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "PowerUpCollisionSystem";
    }

    // TODO: rework to make generic for multiple types of powerups
    // -> consider adding a powerups component for player, along with a powerup consumer system
    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        HitboxComponent thisHbc = (HitboxComponent)cm.getComponent(id, "HitboxComponent");
        Rectangle intersection = new Rectangle();

        HitboxComponent thatHbc = (HitboxComponent)cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "HitboxComponent");
        CollisionsComponent cc = (CollisionsComponent)cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "CollisionsComponent");
        if(Intersector.intersectRectangles(thisHbc.getHitbox(), thatHbc.getHitbox(), intersection)) {
            cc.addCollision(id);
            ((LifeCounterComponent)cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "LifeCounterComponent")).incrementLife();

            ECSEngine.get().flagEntityForRemoval(id);
        }
    }
}
