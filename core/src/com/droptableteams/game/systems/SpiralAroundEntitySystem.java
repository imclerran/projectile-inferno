package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.DurationComponent;
import com.droptableteams.game.components.RelativePositionComponent;
import com.droptableteams.game.components.SpiralComponent;
import com.droptableteams.game.components.game.GameCheatsComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;

import java.util.HashSet;

public class SpiralAroundEntitySystem extends AbstractSystem {

    public SpiralAroundEntitySystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "SpiralAroundEntitySystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        SpiralComponent sc = (SpiralComponent) cm.getComponent(id, "SpiralComponent");
        RelativePositionComponent rpc = (RelativePositionComponent)cm.getComponent(id, "RelativePositionComponent");
        GameCheatsComponent gcc = (GameCheatsComponent) cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameCheatsComponent");
        DurationComponent dc = (DurationComponent) cm.getComponent(SpecialEntityIds.GAME_ENTITY, "DurationComponent");

        float currentAngle = rpc.getAngle();
        float currentRadius = rpc.getRadius();
        float deltaTheta = sc.getDeltaTheta();
        float deltaRadius = sc.getDeltaRadius();
        float timeDeltaAngle = deltaTheta * Gdx.graphics.getDeltaTime() * gcc.getSpeedMultiplier();
        float timeDeltaRadius = deltaRadius * Gdx.graphics.getDeltaTime() * gcc.getSpeedMultiplier();

        if(null != dc) {
            dc.elapsedRadius += timeDeltaRadius;
            dc.elapsedTheta += timeDeltaAngle;
        }
        rpc.setAngle(currentAngle + timeDeltaAngle);
        rpc.setRadius(currentRadius + timeDeltaRadius);
    }
}
