package com.droptableteams.game.components.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class RenderComponent extends AbstractComponent {
    private SpriteBatch _batch;
    private String[] _entityRenderOrder;

    public RenderComponent(int id, SpriteBatch batch, String[] entityRenderOrder) {
        _id = id;
        _batch = batch;
        _type = "RenderComponent";
        _entityRenderOrder = entityRenderOrder;
    }

    public SpriteBatch getBatch() {
        return _batch;
    }

    public String[] getEntityRenderOrder() {
        return _entityRenderOrder;
    }
}
