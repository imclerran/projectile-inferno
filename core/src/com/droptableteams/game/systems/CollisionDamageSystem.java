package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Json;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.builders.PowerUpEntityBuilder;
import com.droptableteams.game.components.*;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;
import com.droptableteams.game.util.data.PowerUpData;

import java.util.HashSet;

public class CollisionDamageSystem extends AbstractSystem {

    // TODO: #3 refactor to move the sound into a component
    private Sound _sound;

    public CollisionDamageSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "CollisionDamageSystem";
        _sound = Gdx.audio.newSound(Gdx.files.internal("audio/damage_sound_effect.mp3")); // accessing sound w/o asset manager? -> see todo above
        
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        EntityManager em = EntityManager.getInstance();
        AssetManager am = ((AssetManagerComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "AssetManagerComponent")).getAssetManager();
        CollisionsComponent cc = (CollisionsComponent)cm.getComponent(id, "CollisionsComponent");
        HitpointComponent hc = (HitpointComponent)cm.getComponent(id, "HitpointComponent");
        ECSEngine engine = ECSEngine.get();

        for(int thatId : cc.getCollisions()) {

            DamageComponent thatDc = (DamageComponent)cm.getComponent(thatId, "DamageComponent");
            if(null != thatDc) {
                hc.subtractHp(thatDc.getDamage());

                // playing the taking damage sound effect
                if(id == SpecialEntityIds.PLAYER_ENTITY) {
                    _sound.play(1.0f);
                }

                if (hc.getHp() <= 0) {
                    if (id == SpecialEntityIds.PLAYER_ENTITY) {
                        ((LifeCounterComponent) cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "LifeCounterComponent")).decrementLife();
                    } else {
                        //int playerID = em.getEntityIds("PlayerEntity")[0];
                        // Check if _id is an enemy id and if the enemy has a powerupcomponent

                        PowerUpComponent powerUp = (PowerUpComponent)cm.getComponent(id, "PowerUpComponent");
                        if(powerUp != null && powerUp._hasPowerUp){
                            Json json = new Json();

                            PowerUpData pd = json.fromJson(PowerUpData.class, Gdx.files.internal(powerUp._powerUpType));
                            LocationComponent lc = (LocationComponent)cm.getComponent(id, "LocationComponent");
                            pd.x = lc.getX();
                            pd.y = lc.getY();

                            PowerUpEntityBuilder pueb = PowerUpEntityBuilder.getInstance(am);
                            pueb.setBuildData(pd);
                            engine.addEntity(pueb);
                            pueb.finishBuild();
                        }
                        engine.flagEntityForRemoval(id);

                    }
                    break;
                }
            }
        }
        cc.clearCollisions();
    }
}
