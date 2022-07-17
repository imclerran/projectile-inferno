package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class DamageComponent extends AbstractComponent {

    private int _damage;

    public DamageComponent(int id, int damage) {
        _id = id;
        _damage = damage;
        _type = "DamageComponent";
    }

    public int getDamage() {
        return _damage;
    }

    public void setDamage(int damage) {
        _damage = damage;
    }
}
