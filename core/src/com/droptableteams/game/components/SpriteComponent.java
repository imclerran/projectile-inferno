package com.droptableteams.game.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class SpriteComponent extends AbstractComponent {

    private Sprite _sprite;
    private boolean _visible;

    public SpriteComponent(int id, Sprite sprite) {
        _id = id;
        _sprite = sprite;
        _type = "SpriteComponent";
        _visible = true;
    }

    public SpriteComponent(int id, Sprite sprite, boolean visible) {
        _id = id;
        _sprite = sprite;
        _type = "SpriteComponent";
        _visible = visible;
    }

    public Sprite getSprite() {
        return _sprite;
    }

    public boolean isVisible() {
        return _visible;
    }

    public void setVisible(boolean visible) {
        _visible = visible;
    }
}
