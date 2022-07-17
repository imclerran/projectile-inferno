package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class LifeCounterComponent extends AbstractComponent {

    private int _count;
    private boolean _isDead;

    public LifeCounterComponent(int id, int initialLives){
        _id = id;
        _type = _type = "LifeCounterComponent";
        _count = initialLives;
    }

    public  int getLifeCount(){
        return _count;
    }
    public void incrementLife(){
        _count++;
    }

    public boolean getIsDead(){
        return _isDead;
    }
    public void decrementLife(){
        _count--;
        _isDead = true;
    }

    public void beginNewLife(){
        _isDead = false;
    }
    @Override
    public int getId() {
        return _id;
    }

    @Override
    public String getType() {
        return _type;
    }
}
