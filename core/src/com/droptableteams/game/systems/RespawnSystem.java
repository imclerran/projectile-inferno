package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.builders.ShieldEntityBuilder;
import com.droptableteams.game.components.HitpointComponent;
import com.droptableteams.game.components.LifeCounterComponent;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.ShieldComponent;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.components.game.GameTimeComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

public class RespawnSystem extends AbstractSystem {

    public RespawnSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "RespawnSystem";
    }

    // TODO: decouple shield from respawn system -> move shield into it's own entity
    // -> in progress: entity already exists
    // -> TODO: begin using shield entity and remove shielding logic from respawn system

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        GameTimeComponent gtc = (GameTimeComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameTimeComponent");
        LifeCounterComponent lcc = (LifeCounterComponent) cm.getComponent(id, "LifeCounterComponent");
        ShieldComponent sc = (ShieldComponent) cm.getComponent(id, "ShieldComponent");
        HitpointComponent hpc = (HitpointComponent) cm.getComponent(id, "HitpointComponent");
        if(sc.isShielded() && gtc.getTimeInMillis() >= sc.getShieldStartTime()+1500) {
            ECSEngine.get().flagEntityForRemoval(SpecialEntityIds.SHIELD_ENTITY);
            sc.setShielded(false);
        }else {
            if (lcc.getIsDead()) {
                float x = Gdx.graphics.getWidth() / 2f;
                float y = Gdx.graphics.getHeight() / 4f;
                LocationComponent comp = (LocationComponent) cm.getComponent(id, "LocationComponent");
                comp.setX(x);
                comp.setY(y);
                createShield();
                lcc.beginNewLife();
                sc.setShielded(true);
                hpc.setHp(1);
            }
        }
    }
    private void createShield(){
        ComponentManager cm = ComponentManager.getInstance();
        GameTimeComponent gtc = (GameTimeComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameTimeComponent");
        AssetManagerComponent amc = (AssetManagerComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "AssetManagerComponent");
        ShieldComponent sc = (ShieldComponent) cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "ShieldComponent");
        sc.setShieldStartTime(gtc.getTimeInMillis());
        ECSEngine engine = ECSEngine.get();
        ShieldEntityBuilder builder = ShieldEntityBuilder.getInstance(amc.getAssetManager());
        engine.addEntity(builder);
    }
}
