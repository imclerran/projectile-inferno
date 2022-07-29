package com.droptableteams.game.builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.SizeComponent;
import com.droptableteams.game.components.SpriteComponent;
import com.droptableteams.game.entities.LifeDisplayEntity;
import com.droptableteams.game.systems.UpdateSpriteSystem;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.ArrayList;

public class LifeDisplayBuilder extends AbstractEntityBuilder {
    private static LifeDisplayBuilder _self;
    private final AssetManager _am;

    public static LifeDisplayBuilder getInstance(AssetManager am){
        if(null == _self) {
            _self = new LifeDisplayBuilder(am);
        }
        return _self;
    }
    private LifeDisplayBuilder(AssetManager am){
        _am = am;
        _id = null;
    }
    @Override
    public AbstractEntity buildEntity() {
        checkIdNotNull();
        return new LifeDisplayEntity(_id);
    }

    @Override
    public ArrayList<AbstractComponent> buildComponentList() {
        checkIdNotNull();
        ArrayList<AbstractComponent> cl = new ArrayList<AbstractComponent>();
        float width = 14;
        float height = 14;
        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();
        ECSEngine engine = ECSEngine.getInstance(SystemUpdateOrder.SYSTEM_UPDATE_ORDER);
        EntityManager em = engine.getEntityManager();
        int numOfLifeEntities = 0;
        if(em.getEntities("LifeDisplayEntity") != null)
            numOfLifeEntities = em.getEntities("LifeDisplayEntity").size();
        Sprite sp = new Sprite(_am.get("sprites/player.png", Texture.class));
        x = x-width*(numOfLifeEntities+1);
        y = y-height;
        cl.add(new SpriteComponent(_id, sp, true));
        cl.add(new SizeComponent(_id,width,height));
        cl.add(new LocationComponent(_id, x,y));
        return cl;
    }

    @Override
    public ArrayList<AbstractSystem> buildSystemList() {
        ArrayList<AbstractSystem> sl = new ArrayList<AbstractSystem>();
        sl.add(new UpdateSpriteSystem(_id));

        return sl;
    }
}
