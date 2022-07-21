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
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        SpriteComponent sp = (SpriteComponent)cm.getComponent(id, "SpriteComponent");
        LocationComponent loc = (LocationComponent)cm.getComponent(id, "LocationComponent");
        SizeComponent sz = (SizeComponent)cm.getComponent(id, "SizeComponent");

        sp.getSprite().setCenter(loc.getX(), loc.getY());
        sp.getSprite().setSize(sz.getWidth(), sz.getHeight());
    }
}
