package com.droptableteams.game.systems;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.CollisionsComponent;
import com.droptableteams.game.components.HitboxComponent;
import com.droptableteams.game.components.OwnerComponent;
import com.droptableteams.game.components.FriendFoeComponent;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO: find way to make collision system generic?
 */
public class BulletCollisionSystem extends AbstractSystem {

    public BulletCollisionSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "BulletCollisionSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        EntityManager em = EntityManager.getInstance();
        // get all targetable entity components
        Map<Integer, AbstractComponent> tecMap = cm.getComponents("TargetableEntityComponent");
        // for each targetable entity component, check friendfoe component
        for (Map.Entry<Integer, AbstractComponent> e : tecMap.entrySet()) {
            int targetId = e.getKey();
            FriendFoeComponent thisFfc = (FriendFoeComponent) cm.getComponent(id, "FriendFoeComponent");
            FriendFoeComponent thatFfc = (FriendFoeComponent) cm.getComponent(targetId, "FriendFoeComponent");
            if (!thatFfc.isFriendly(thisFfc.getTeam())) {
                // if not friendly, check hitbox intersection
                HitboxComponent thisHbc = (HitboxComponent) cm.getComponent(id, "HitboxComponent");
                HitboxComponent thatHbc = (HitboxComponent) cm.getComponent(targetId, "HitboxComponent");
                Rectangle intersection = new Rectangle();
                // assign collisions as appropriate
                if (Intersector.intersectRectangles(thisHbc.getHitbox(), thatHbc.getHitbox(), intersection)) {
                    CollisionsComponent cc = (CollisionsComponent) cm.getComponent(targetId, "CollisionsComponent");
                    if ("BulletEntity" == em.getEntityType(targetId)) { // if colliding with a bullet, simply remove that bullet
                        ECSEngine.get().flagEntityForRemoval(targetId);
                    } else {
                        cc.addCollision(id); // add bullet id to target's collision list
                        // TODO: #6 Dispatch event instead - can apply to bullet entities as well
                    }
                    ECSEngine.get().flagEntityForRemoval(id); // destroy bullet after collision

                }

            }
        }
    }
}