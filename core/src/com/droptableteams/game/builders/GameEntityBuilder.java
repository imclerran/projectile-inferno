package com.droptableteams.game.builders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.game.*;
import com.droptableteams.game.entities.GameEntity;
import com.droptableteams.game.systems.game.*;
import com.droptableteams.game.util.ScriptReader;
import com.droptableteams.game.util.Wave;
import com.droptableteams.game.util.constants.EntityRenderOrder;
import com.droptableteams.game.util.constants.SpecialEntityIds;

import java.util.ArrayList;

public class GameEntityBuilder extends AbstractEntityBuilder {
    private static GameEntityBuilder _self;
    private final AssetManager _am;
    private final SpriteBatch _batch;

    private GameEntityBuilder(AssetManager am, SpriteBatch batch) {
        _am = am;
        _batch = batch;
        _id = null;
        Sound sound = am.get("audio/background_music.mp3", Sound.class);
        sound.play(0.35f);
    }

    public static GameEntityBuilder getInstance(AssetManager am, SpriteBatch batch) {
        if(null == _self) {
            _self = new GameEntityBuilder(am, batch);
        }
        return _self;
    }

    @Override
    public void startBuild() {
        _id = SpecialEntityIds.GAME_ENTITY;
    }

    @Override
    public AbstractEntity buildEntity() throws NullPointerException {
        checkIdNotNull();
        return new GameEntity(_id);
    }

    @Override
    public ArrayList<AbstractComponent> buildComponentList() throws NullPointerException {
        checkIdNotNull();
        if(null == _batch) {
            throw new NullPointerException("Must call setBuildData() first.");
        }
        ArrayList<AbstractComponent> cl = new ArrayList<AbstractComponent>();
        ArrayList<Wave> waveList = ScriptReader.readLevel("test-level-with-waves.json");
        waveList = (null == waveList) ? new ArrayList<Wave>() : waveList;
        cl.add(new RenderComponent(_id, _batch, EntityRenderOrder.ENTITY_RENDER_ORDER));
        cl.add(new AssetManagerComponent(_id, _am));
        cl.add(new GameCheatsComponent(_id, 0.5f));
        cl.add(new SpawnListComponent(_id, waveList));
        cl.add(new GameTimeComponent(_id));
        return cl;
    }

    @Override
    public ArrayList<AbstractSystem> buildSystemList() throws NullPointerException {
        checkIdNotNull();
        ArrayList<AbstractSystem> sl = new ArrayList<AbstractSystem>();
        sl.add(new RenderSystem(_id));
        sl.add(new HandleInputSystem(_id));
        sl.add(new SpawnerSystem(_id));
        sl.add(new LifeUpdateSystem(_id));
        sl.add(new EndGameSystem(_id));
        return sl;
    }
}
