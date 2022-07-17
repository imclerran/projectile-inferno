package com.droptableteams.game.builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.AbstractEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.*;
import com.droptableteams.game.entities.BossEntity;
import com.droptableteams.game.systems.*;
import com.droptableteams.game.util.data.BossData;
import com.droptableteams.game.util.types.BossType;

import java.util.ArrayList;

public class BossEntityBuilder extends AbstractEntityBuilder {
    private static BossEntityBuilder _self;
    private AssetManager _am;
    private BossData _bd;

    private BossEntityBuilder(AssetManager am) {
        _am = am;
        _bd = null;
        _id = null;
    }

    public static BossEntityBuilder getInstance(AssetManager am) {
        if (null == _self) {
            _self = new BossEntityBuilder(am);
        }
        return _self;
    }

    public void setBuildData(BossData bd) {
        _bd = bd;
    }

    @Override
    public void finishBuild() {
        super.finishBuild();
        _bd = null;
    }

    @Override
    public AbstractEntity buildEntity() throws NullPointerException {
        checkIdNotNull();
        return new BossEntity(_id);
    }

    public ArrayList<AbstractComponent> buildComponentList() throws NullPointerException {
        checkIdNotNull();
        if (null == _bd) {
            throw new NullPointerException("Must call `setBuildData()` first.");
        }
        ArrayList<AbstractComponent> cl = new ArrayList<AbstractComponent>();
        Json json = new Json();
        //BossType bt = BossTypeFactory.make(_bd.bossType);
        BossType bt = json.fromJson(BossType.class, Gdx.files.internal("scripts/enemies/" + _bd.bossType + ".json"));
        Sprite sp = new Sprite(_am.get(bt.texture, Texture.class));
        sp.setSize(bt.width, bt.height);
        sp.setCenter(_bd.x, _bd.y);
        cl.add(new SpriteComponent(_id, sp));
        cl.add(new LocationComponent(_id, _bd.x, _bd.y));
        cl.add(new SizeComponent(_id, bt.width, bt.height));
        cl.add(new VelocityComponent(_id, bt.speed));
        cl.add(new HasBeenInboundsComponent(_id, false));
        cl.add(new DestinationMovementComponent(_id, _bd.destinationList, bt.loopDestinations));
        cl.add(new HitpointComponent(_id, bt.hp));
        cl.add(new CollisionsComponent(_id));
        cl.add(new HitboxComponent(_id, new Rectangle(_bd.x, _bd.y, bt.width, bt.height)));
        cl.add(new FireControlComponent(_id, bt.firePattern.getFireRate(), true));
        cl.add(new FirePatternComponent(_id, bt.firePattern.getBaseDirection(), bt.firePattern.getFireRate(), bt.firePattern.getNumberOfBullets(),
                bt.firePattern.getDividingAngle(), bt.firePattern.getDeltaTheta(), bt.firePattern.getBulletType()));
        return cl;
    }

    @Override
    public ArrayList<AbstractSystem> buildSystemList() throws NullPointerException {
        checkIdNotNull();
        ArrayList<AbstractSystem> sl = new ArrayList<AbstractSystem>();
        sl.add(new UpdateSpriteSystem(_id));
        sl.add(new DestinationMovementSystem(_id));
        sl.add(new DespawnOutOfBoundsSystem(_id));
        sl.add(new FireControlSystem(_id));
        sl.add(new CollisionDamageSystem(_id));
        sl.add(new SetHitboxLocationSystem(_id));
        return sl;
    }


}
