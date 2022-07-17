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
        _cm = ComponentManager.getInstance();
        _type = "SpeedModifierSystem";
    }

    @Override
    public void update(int id) {
        GameCheatsComponent gcc = (GameCheatsComponent) _cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameCheatsComponent");
        VelocityComponent vc = (VelocityComponent)_cm.getComponent(id,"VelocityComponent");
        vc.setModifiedSpeed(vc.getBaseSpeed() * gcc.getSpeedMultiplier());
    }
}
