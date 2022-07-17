package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class SizeComponent extends AbstractComponent {

    private float _width;
    private float _height;

    public SizeComponent(int id, float width, float height) {
        _id = id;
        _width = width;
        _height = height;
        _type = "SizeComponent";
    }

    public float getWidth() {
        return _width;
    }

    public float getHeight() {
        return _height;
    }
}
