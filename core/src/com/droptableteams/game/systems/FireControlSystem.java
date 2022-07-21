package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.*;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.components.game.GameCheatsComponent;
import com.droptableteams.game.util.constants.SystemUpdateOrder;
import com.droptableteams.game.util.types.BulletType;
import com.droptableteams.game.util.data.BulletData;
import com.droptableteams.game.builders.BulletEntityBuilder;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.types.SubtypeManager;

import java.util.HashSet;

public class FireControlSystem extends AbstractSystem {

    // TODO: move _sound into a component
    private Sound _sound;

    public FireControlSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "FireControlSystem";
        _sound = Gdx.audio.newSound(Gdx.files.internal("audio/laser_sound.mp3")); // not using asset manager?
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        LocationComponent lc = (LocationComponent)ComponentManager.getInstance().getComponent(id, "LocationComponent");
        FireControlComponent fcc = (FireControlComponent)cm.getComponent(id, "FireControlComponent");
        FirePatternComponent fpc = (FirePatternComponent)cm.getComponent(id, "FirePatternComponent");
        AssetManagerComponent amc = (AssetManagerComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "AssetManagerComponent");
        GameCheatsComponent gcc = (GameCheatsComponent) cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameCheatsComponent");
        LocationComponent plc = (LocationComponent)ComponentManager.getInstance().getComponent(SpecialEntityIds.PLAYER_ENTITY, "LocationComponent");

        // apply fire pattern rotation
        float newDirection = fpc.getBaseDirection();

        switch(fcc.fireDirectionType) {
            case ANGLE:
                newDirection = (fpc.getDeltaTheta()*Gdx.graphics.getDeltaTime()*gcc.getSpeedMultiplier())+fpc.getBaseDirection();
                break;
            case OUTWARD:
                float xLen = plc.getX()- lc.getX();
                float yLen = plc.getY() - lc.getY();
                newDirection = atan(xLen, yLen);
                break;
            case ATPLAYER:
                // handled by TargetPlayerSystem
        }

        fpc.setBaseDirection(newDirection);

        if(fcc.isFiring()) {
            long time = System.nanoTime();
            long deltaTime = time - fcc.getLastFired();
            if((float)(deltaTime/Math.pow(10,9)) > fpc.rateOfFire) {

                // playing the laser sound effect
                if(id == SpecialEntityIds.PLAYER_ENTITY) {
                    _sound.play(1.0f);
                }
                spawnBullets(fpc, amc, lc.getX(), lc.getY(), id);
                fcc.setLastFired(time);
            }
        }
    }

    private void spawnBullets(FirePatternComponent fpc, AssetManagerComponent amc, float x, float y, int id) {
        BulletEntityBuilder builder = BulletEntityBuilder.getInstance(amc.getAssetManager());
        ECSEngine engine = ECSEngine.get();
        BulletType bt = (BulletType)SubtypeManager.getInstance().getSubtype(fpc.getBulletType());
        int numBullets = fpc.getNumberOfBullets();
        float baseDirection = fpc.getBaseDirection();
        float angle = fpc.getDividingAngle();

        float offset = 0;
        if(Math.PI != angle && numBullets > 1) {
            offset = -1*(angle * (numBullets-1))/2;
        }
        for(int i = 0; i < numBullets; i++) {
            float direction = baseDirection + offset;
            BulletData bd = new BulletData(direction, 0, x, y, id, bt.subtype);
            builder.setBuildData(bd);
            engine.addEntity(builder);
            offset += angle;
        }
    }

    private float atan(float x, float y) {
        return -1f*(float)(Math.atan2(x, y) - Math.PI/2);
    }
}
