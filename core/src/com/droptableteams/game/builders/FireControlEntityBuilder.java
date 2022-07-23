package com.droptableteams.game.builders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.*;
import com.droptableteams.game.components.game.GameTimeComponent;
import com.droptableteams.game.entities.FireControlEntity;
import com.droptableteams.game.systems.*;
import com.droptableteams.game.util.constants.*;
import com.droptableteams.game.util.data.FireControlData;
import com.droptableteams.game.util.types.FirePatternType;
import com.droptableteams.game.util.types.SubtypeManager;

import java.util.ArrayList;

public class FireControlEntityBuilder extends AbstractEntityBuilder {
    private final AssetManager _am;
    FireControlData _fcd;

    private static FireControlEntityBuilder _self;

    private FireControlEntityBuilder(AssetManager am) {
        _am = am;
        _fcd = null;
        _id = null;
    }

    public static FireControlEntityBuilder getInstance(AssetManager am) {
        if (null == _self) {
            _self = new FireControlEntityBuilder(am);
        }
        return _self;
    }

    public void setBuildData(FireControlData fcd) {
        _fcd = fcd;
    }

    @Override
    public void finishBuild() {
        super.finishBuild();
        _fcd = null;
    }

    @Override
    public AbstractEntity buildEntity() {
        checkIdNotNull();
        return new FireControlEntity(_id);
    }

    @Override
    public ArrayList<AbstractComponent> buildComponentList() {
        if (null == _fcd) {
            throw new NullPointerException("Must call `setBuildData()` first.");
        }
        FirePatternType fpt = (FirePatternType) SubtypeManager.getInstance().getSubtype(_fcd.firePattenrType);
        ComponentManager cm = ComponentManager.getInstance();
        LocationComponent plc = (LocationComponent) cm.getComponent(_fcd.parentId, "LocationComponent");
        GameTimeComponent gtc = (GameTimeComponent) cm.getComponent(SpecialEntityIds.GAME_ENTITY, "GameTimeComponent");
        long currentTime = gtc.getTimeInMillis();
        ArrayList<AbstractComponent> cl = new ArrayList<AbstractComponent>();
        cl.add(new RelativePositionComponent(_id, fpt.fcInitialTheta, fpt.fcInitialRadius, _fcd.parentId));
        cl.add(new DurationComponent(_id, fpt.fcDurationType, fpt.duration, currentTime));
        cl.add(new LocationComponent(_id, plc.getX(), plc.getY()));
        cl.add(new FireControlComponent(_id, _fcd));
        cl.add(new OwnerComponent(_id, _fcd.parentId));
        cl.add(new FirePatternComponent(_id, fpt.baseDirection, fpt.rateOfFire, fpt.numberOfBullets,
                fpt.dividingAngle, fpt.deltaTheta, fpt.bulletType));
        if (!fpt.fcTexture.equals("")) {
            Sprite sp = new Sprite(_am.get(fpt.fcTexture, Texture.class));
            cl.add(new SpriteComponent(_id, sp));
            cl.add(new SizeComponent(_id, fpt.fcWidth, fpt.fcHeight));
        }
        if (fpt.deltaTheta != 0f || fpt.fcDeltaRadius != 0) {
            cl.add(new SpiralComponent(_id, fpt.fcDeltaRadius, fpt.fcDeltaTheta));
        }
        return cl;
    }

    @Override
    public ArrayList<AbstractSystem> buildSystemList() {
        checkIdNotNull();
        if (null == _fcd) {
            throw new NullPointerException("Must call `setBuildData()` first.");
        }
        FirePatternType fpt = (FirePatternType) SubtypeManager.getInstance().getSubtype(_fcd.firePattenrType);
        ArrayList<AbstractSystem> sl = new ArrayList<AbstractSystem>();
        sl.add(new RelativePositionSystem(_id));
        if (fpt.fcDeltaRadius != 0 || fpt.fcDeltaTheta != 0) {
            sl.add(new SpiralAroundEntitySystem(_id));
        }
        if (!fpt.fcTexture.equals("")) {
            sl.add(new UpdateSpriteSystem(_id));
        }
        sl.add(new FireControlSystem(_id));
        sl.add(new DestroyOnOwnerDeathSystem(_id));
        sl.add(new DurationSystem(_id));
        sl.add(new DespawnWithDurationSystem(_id));
        return sl;
    }
}
