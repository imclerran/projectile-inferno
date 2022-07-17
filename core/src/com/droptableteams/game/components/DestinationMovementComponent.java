package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.util.TimeVector3;

import java.util.ArrayList;


public class DestinationMovementComponent extends AbstractComponent {

    private ArrayList<TimeVector3> _destinationList;
    private int _nextDest;
    private long _hereSinceMillis;
    private boolean _shouldLoop;


    public DestinationMovementComponent(int id, ArrayList<TimeVector3> destinationList, boolean shouldLoop) {
        _id = id;
        _type = "DestinationMovementComponent";
        _nextDest = 0;
        _hereSinceMillis = -1;
        _shouldLoop = shouldLoop;
        _destinationList = destinationList;

    }

    public float getNextX() {
        return _destinationList.get(_nextDest).getX();
    }

    public float getNextY() {
        return _destinationList.get(_nextDest).getY();
    }

    public long getStayFor() {
        return _destinationList.get(_nextDest).getTime();
    }

    public long getHereFor(long currentTimeMillis) {
        if(_hereSinceMillis != -1) {
            return currentTimeMillis - _hereSinceMillis;
        }
        return -1;
    }

    public boolean incrementNextDest() {
        if(_nextDest < _destinationList.size()-1) {
            _nextDest++;
            return true;
        }
        else {
            if(_shouldLoop) {
                _nextDest = 0;
                return true;
            }
        }
        return false;
    }

    public void setHereSinceMillis(long timeInMillis) {
        _hereSinceMillis = timeInMillis;
    }
}
