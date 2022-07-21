package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.DurationComponent;
import com.droptableteams.game.components.game.GameTimeComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;

import java.util.HashSet;

public class DurationSystem extends AbstractSystem {

    public DurationSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "DurationSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        DurationComponent dc = (DurationComponent)cm.getComponent(id, "DurationComponent");
        switch (dc.durationType) {
            case TIME:
                handleTime(dc);
                break;
            case ANGLE:
                handleAngle(dc);
                break;
            case RADIUS:
                handleRadius(dc);
                break;
            default:
                handleTime(dc);
        }
    }

    private void handleTime(DurationComponent dc) {
        ComponentManager cm = ComponentManager.getInstance();
        GameTimeComponent gtc = (GameTimeComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameTimeComponent");
        long duration = (long)(dc.duration * 1000);
        if(gtc.getTimeInMillis() - dc.startTime >= duration && duration != 0f) {
            dc.durationMet = true;
        }
    }

    private void handleAngle(DurationComponent dc) {
        if(dc.elapsedTheta >= dc.duration &&  dc.duration != 0f) {
            dc.durationMet = true;
        }
    }

    private void handleRadius(DurationComponent dc) {
        if(dc.elapsedRadius >= dc.duration && dc.duration != 0f) {
            dc.durationMet = true;
        }
    }
}
