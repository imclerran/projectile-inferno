package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.SpriteComponent;
import com.droptableteams.game.components.SizeComponent;

import java.util.HashSet;

public class UpdateSpriteSystem extends AbstractSystem {

    public UpdateSpriteSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "UpdateSpriteSystem";
        _cm = ComponentManager.getInstance();
    }

    @Override
    public void update(int id) {
        SpriteComponent sp = (SpriteComponent)_cm.getComponent(id, "SpriteComponent");
        LocationComponent loc = (LocationComponent)_cm.getComponent(id, "LocationComponent");
        SizeComponent sz = (SizeComponent)_cm.getComponent(id, "SizeComponent");

        sp.getSprite().setCenter(loc.getX(), loc.getY());
        sp.getSprite().setSize(sz.getWidth(), sz.getHeight());
    }
}
