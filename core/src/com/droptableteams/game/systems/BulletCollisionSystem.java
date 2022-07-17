package com.droptableteams.game.systems;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.IEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.CollisionsComponent;
import com.droptableteams.game.components.HitboxComponent;
import com.droptableteams.game.components.OwnerComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO: find way to make collision system generic?
 * TODO: decouple damage from collision...
 */
public class BulletCollisionSystem extends AbstractSystem {

    EntityManager _em;

    public BulletCollisionSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "BulletCollisionSystem";
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
    }

    @Override
    public void update(int id) {
        OwnerComponent oc = (OwnerComponent) _cm.getComponent(id, "OwnerComponent");
        HitboxComponent thisHbc = (HitboxComponent) _cm.getComponent(id, "HitboxComponent");
        Rectangle intersection = new Rectangle();

        if (oc.getOwnerId() != SpecialEntityIds.PLAYER_ENTITY) { // bullet is an enemey bullet
            if (_em.getEntities("PlayerEntity").size() == 0) {
                return; // If there is no player entity, return.
            }
            HitboxComponent thatHbc = (HitboxComponent) _cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "HitboxComponent");
            HitboxComponent shieldHbc = (HitboxComponent) _cm.getComponent(SpecialEntityIds.SHIELD_ENTITY, "HitboxComponent");
            CollisionsComponent cc = (CollisionsComponent) _cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "CollisionsComponent");
            if (shieldHbc != null && Intersector.intersectRectangles(thisHbc.getHitbox(), shieldHbc.getHitbox(), intersection)) {
                ECSEngine.getInstance(SystemUpdateOrder.get()).flagEntityForRemoval(id);
            } else {
                if (Intersector.intersectRectangles(thisHbc.getHitbox(), thatHbc.getHitbox(), intersection)) {
                    cc.addCollision(id);
                    ECSEngine.getInstance(SystemUpdateOrder.get()).flagEntityForRemoval(id);
                }
            }
        }
        else { // bullet is a player bullet
            Set<Map.Entry<Integer, IEntity>> entries = _em.getEntities("EnemyEntity").entrySet();
            for (Map.Entry<Integer, IEntity> e : entries) {
                int enemyId = e.getKey();

                HitboxComponent thatHbc = (HitboxComponent) _cm.getComponent(enemyId, "HitboxComponent");
                CollisionsComponent cc = (CollisionsComponent) _cm.getComponent(enemyId, "CollisionsComponent");
                if (Intersector.intersectRectangles(thisHbc.getHitbox(), thatHbc.getHitbox(), intersection)) {
                    cc.addCollision(id);
                    ECSEngine.getInstance(SystemUpdateOrder.get()).flagEntityForRemoval(id);
                }
            }

            Set<Map.Entry<Integer, IEntity>> entries2 = _em.getEntities("BossEntity").entrySet();
            for (Map.Entry<Integer, IEntity> e : entries2) {
                int enemyId = e.getKey();

                HitboxComponent thatHbc = (HitboxComponent) _cm.getComponent(enemyId, "HitboxComponent");
                CollisionsComponent cc = (CollisionsComponent) _cm.getComponent(enemyId, "CollisionsComponent");
                if (Intersector.intersectRectangles(thisHbc.getHitbox(), thatHbc.getHitbox(), intersection)) {
                    cc.addCollision(id);
                    ECSEngine.getInstance(SystemUpdateOrder.get()).flagEntityForRemoval(id);
                }
            }
        }
    }
}

