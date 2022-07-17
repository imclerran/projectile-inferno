package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class OwnerComponent extends AbstractComponent {

    private int _ownerId;


    public OwnerComponent(int id, int ownerId) {
        _id = id;
        _ownerId = ownerId;
        _type = "OwnerComponent";
    }

    public int getOwnerId() {
        return _ownerId;
    }

    public void setOwnerId(int ownerId) {
        _ownerId = ownerId;
    }
}
