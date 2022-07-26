package com.droptableteams.game.builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.SizeComponent;
import com.droptableteams.game.components.SpriteComponent;
import com.droptableteams.game.entities.StaticSpriteEntity;
import com.droptableteams.game.systems.*;

import java.util.ArrayList;

public class StaticSpriteEntityBuilder extends AbstractEntityBuilder {
    private static StaticSpriteEntityBuilder _self;
    private final AssetManager _am;
    private String sprite_data;

    private StaticSpriteEntityBuilder(AssetManager am) {
        _am = am;
        _id = null;
    }

    public static StaticSpriteEntityBuilder getInstance(AssetManager am) {
        if(null == _self) {
            _self = new StaticSpriteEntityBuilder(am);
        }
        return _self;
    }

    public void setBuildData(String bd) {
        sprite_data = bd;
    }

    @Override
    public void finishBuild() {
        super.finishBuild();
        sprite_data = null;
    }
    @Override
    public AbstractEntity buildEntity() {
        checkIdNotNull();

        return new StaticSpriteEntity(_id);
    }

    @Override
    public ArrayList<AbstractComponent> buildComponentList() throws NullPointerException {
        checkIdNotNull();
        ArrayList<AbstractComponent> cl = new ArrayList<AbstractComponent>();
        Sprite sp = new Sprite(_am.get(sprite_data, Texture.class));
        sp.getX();
        int width = Gdx.graphics.getWidth()*9/10;
        int height = Gdx.graphics.getHeight()/2;
        sp.setSize(width,height);
        int x = Gdx.graphics.getWidth()/2;
        int y = Gdx.graphics.getHeight()/2;
        sp.setCenter(x, y);
        cl.add(new SpriteComponent(_id, sp));
        cl.add(new LocationComponent(_id, x,y));
        cl.add(new SizeComponent(_id, width,height));
        return cl;
    }

    @Override
    public ArrayList<AbstractSystem> buildSystemList() {
        checkIdNotNull();
        ArrayList<AbstractSystem> sl = new ArrayList<AbstractSystem>();
        sl.add(new UpdateSpriteSystem(_id));
        return sl;    }
}
