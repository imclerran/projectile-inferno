package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.game.GameCheatsComponent;
import com.droptableteams.game.components.VelocityComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;

import java.util.HashSet;

public class SpeedModifierSystem extends AbstractSystem {

    public SpeedModifierSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "SpeedModifierSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        GameCheatsComponent gcc = (GameCheatsComponent) cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameCheatsComponent");
        VelocityComponent vc = (VelocityComponent)cm.getComponent(id,"VelocityComponent");
        vc.setModifiedSpeed(vc.getBaseSpeed() * gcc.getSpeedMultiplier());
    }
}
