package com.droptableteams.game.systems.game;

import com.badlogic.gdx.assets.AssetManager;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.builders.LifeDisplayBuilder;
import com.droptableteams.game.components.LifeCounterComponent;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

// TODO: consider making this a normal system? (not game system)
public class LifeUpdateSystem extends AbstractSystem {
    private EntityManager _em;

    public LifeUpdateSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "LifeUpdateSystem";
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
    }

    @Override
    public void update(int id) {
        AssetManager am = ((AssetManagerComponent) _cm.getComponent(id, "AssetManagerComponent")).getAssetManager();
        LifeCounterComponent counter =(LifeCounterComponent) _cm.getComponents("LifeCounterComponent").get(SpecialEntityIds.PLAYER_ENTITY);
        if(counter != null  && _em.getEntities("LifeDisplayEntity") != null &&_em.getEntities("LifeDisplayEntity").size() > counter.getLifeCount()){
            int currentLife = FindHighestLifeID();
            ECSEngine.get().flagEntityForRemoval(currentLife);
        }
        while(counter != null &&  _em.getEntities("LifeDisplayEntity")!= null && _em.getEntities("LifeDisplayEntity").size() < counter.getLifeCount()) {
            ECSEngine.get().addEntity(LifeDisplayBuilder.getInstance(am));
        }
    }

    private int FindHighestLifeID() {
        int max = 0;
        for(AbstractEntity x : _em.getEntities("LifeDisplayEntity").values()){
            if(x.getId() > max){
                max = x.getId();
            }
        }
        return max;
    }
}
