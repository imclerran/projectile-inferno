package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class TargetableEntityComponent extends AbstractComponent {

    public TargetableEntityComponent(int id) {
        _id = id;
        _type = "TargetableEntityComponent";
    }
}