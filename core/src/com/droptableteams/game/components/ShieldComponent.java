package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class ShieldComponent extends AbstractComponent {

    private long _shieldStartTime;
    private boolean _isShielded;

    public ShieldComponent(int id) {
        _id = id;
        _type = "ShieldComponent";
        _shieldStartTime = 0;
        _isShielded = false;
    }

    public ShieldComponent(int id, boolean isShielded, long shieldStartTime) {
        _id = id;
        _type = "ShieldComponent";
        _isShielded = isShielded;
        _shieldStartTime = shieldStartTime;
    }

    public void setShielded(boolean shielded) {
        _isShielded = shielded;
    }

    public void setShieldStartTime(long shieldStartTime) {
        this._shieldStartTime = shieldStartTime;
    }
    public boolean isShielded() {
        return _isShielded;
    }

    public long getShieldStartTime() {
        return _shieldStartTime;
    }
}
