package com.droptableteams.game.systems;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.MoveDirectionComponent;
import com.droptableteams.game.components.SpriteComponent;

import java.util.HashSet;

public class RotateSpriteToDirectionSystem extends AbstractSystem {

    public RotateSpriteToDirectionSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "RotateSpriteToDirectionSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        MoveDirectionComponent mdc = (MoveDirectionComponent) cm.getComponent(id, "MoveDirectionComponent");
        SpriteComponent sc = (SpriteComponent) cm.getComponent(id, "SpriteComponent");

        float degrees = (float)(mdc.getRadians()*180/ Math.PI )-90f; // TODO: replace 90f with defaultSpriteOrientation (in sprite component)
        sc.getSprite().setRotation(degrees);
        //sc.getSprite().rotate(degrees);
    }
}
