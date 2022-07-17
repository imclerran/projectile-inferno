package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.ISystem;
import com.droptableteams.game.builders.ShieldEntityBuilder;
import com.droptableteams.game.components.HitpointComponent;
import com.droptableteams.game.components.LifeCounterComponent;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.components.game.GameTimeComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

public class RespawnSystem extends ISystem {
    // TODO: move shield logic and variables into separate system and components
    private int _id;
    private String _type;
    private ComponentManager _cm;
    private EntityManager _em;
    private long shieldStartTime;
    private GameTimeComponent gtc;
    private boolean isShielded;

    public RespawnSystem(int id) {
        _id = id;
        _type = "RespawnSystem";
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
        gtc = ((GameTimeComponent)_cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameTimeComponent"));
        shieldStartTime = 0;
    }

    @Override
    public void update(int id) {
        LifeCounterComponent lifeComp = (LifeCounterComponent) _cm.getComponent(_id, "LifeCounterComponent");
        HitpointComponent hc = (HitpointComponent) _cm.getComponent(_id, "HitpointComponent");
        if(isShielded && gtc.getTimeInMillis() >= shieldStartTime+1500){
            ECSEngine.getInstance(SystemUpdateOrder.get()).flagEntityForRemoval(SpecialEntityIds.SHIELD_ENTITY);
            isShielded = false;
        }else {
            if (lifeComp.getIsDead()) {
                float x = Gdx.graphics.getWidth() / 2f;
                float y = Gdx.graphics.getHeight() / 4f;
                LocationComponent comp = (LocationComponent) _cm.getComponent(_id, "LocationComponent");
                comp.setX(x);
                comp.setY(y);
                createShield();
                lifeComp.beginNewLife();
                isShielded = true;
                hc.setHp(1);
            }
        }
    }
    private void createShield(){
        AssetManagerComponent amc = (AssetManagerComponent)_cm.getComponent(SpecialEntityIds.GAME_ENTITY, "AssetManagerComponent");
        shieldStartTime = gtc.getTimeInMillis();
        ECSEngine engine = ECSEngine.getInstance(SystemUpdateOrder.get());
        ShieldEntityBuilder builder = ShieldEntityBuilder.getInstance(amc.getAssetManager());
        engine.addEntity(builder);
    }
}
