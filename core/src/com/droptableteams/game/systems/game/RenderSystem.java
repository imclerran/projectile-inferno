package com.droptableteams.game.systems.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.game.RenderComponent;
import com.droptableteams.game.components.SpriteComponent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RenderSystem extends AbstractSystem {
    private EntityManager _em;

    public RenderSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
        _type = "RenderSystem";
    }

    @Override
    public void update(int id) {
        RenderComponent rc = (RenderComponent) _cm.getComponent(id,"RenderComponent");
        SpriteBatch batch = rc.getBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(String type : rc.getEntityRenderOrder()) {
            HashMap<Integer, AbstractEntity> entityMap = _em.getEntities(type);
            if(null != entityMap) {
                Set<Map.Entry<Integer, AbstractEntity>> entities = entityMap.entrySet();
                for(Map.Entry<Integer, AbstractEntity> e : entities) {
                    int renderTargetId = e.getValue().getId();
                    SpriteComponent sp = (SpriteComponent)_cm.getComponent(renderTargetId, "SpriteComponent");
                    if(sp.isVisible()) {
                        sp.getSprite().draw(batch);
                    }
                }
            }
        }
        batch.end();
    }
}
