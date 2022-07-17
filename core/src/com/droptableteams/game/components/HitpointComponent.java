package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class HitpointComponent extends AbstractComponent {

    private int _hp;

    public HitpointComponent(int id, int hp) {
        _id = id;
        _type = "HitpointComponent";
        _hp = hp;
    }

    public int getHp() {
        return _hp;
    }

    public void setHp(int hp) {
        _hp = hp;
    }

    public void subtractHp(int amount) {
        _hp -= amount;
    }

    public void addHp(int amount) {
        _hp += amount;
    }
}
