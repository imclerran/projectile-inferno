package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.badlogic.gdx.math.Rectangle;

public class HitboxComponent extends AbstractComponent {

    private Rectangle _hitbox;

    public HitboxComponent(int id, Rectangle hitbox) {
        _id = id;
        _hitbox = hitbox;
        _type = "HitboxComponent";
    }

    public Rectangle getHitbox() {
        return _hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        _hitbox = hitbox;
    }
}
