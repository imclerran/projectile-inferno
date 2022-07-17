package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.OwnerComponent;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.ArrayList;
import java.util.HashSet;

public class DestroyOnOwnerDeathSystem extends AbstractSystem {

    public DestroyOnOwnerDeathSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "DestroyOnOwnerDeathSystem";
    }

    @Override
    public void update(int id) {
        ECSEngine engine = ECSEngine.getInstance(SystemUpdateOrder.get());
        OwnerComponent oc = (OwnerComponent)engine.getComponentManager().getComponent(id, "OwnerComponenet");
        ArrayList<Integer> flagged = engine.getFlaggedForRemoval();
        for(int i = 0; i < flagged.size(); i++) {
            if(oc.getOwnerId() == flagged.get(i)) {
                engine.flagEntityForRemoval(id);
            }
        }
    }
}
