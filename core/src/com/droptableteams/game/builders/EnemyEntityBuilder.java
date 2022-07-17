package com.droptableteams.game.builders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.droptableteams.game.LibECS.interfaces.AbstractEntityBuilder;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.LibECS.interfaces.IEntity;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.*;
import com.droptableteams.game.entities.EnemyEntity;
import com.droptableteams.game.util.types.EnemyType;
import com.droptableteams.game.util.data.EnemyData;
import com.droptableteams.game.systems.*;
import com.droptableteams.game.util.types.SubtypeManager;

import java.util.ArrayList;

public class EnemyEntityBuilder extends AbstractEntityBuilder {
    private static EnemyEntityBuilder _self;
    private AssetManager _am;
    private EnemyData _ed;

    private EnemyEntityBuilder(AssetManager am) {
        _am = am;
        _ed = null;
        _id = null;
    }

    public static EnemyEntityBuilder getInstance(AssetManager am) {
        if(null == _self) {
            _self = new EnemyEntityBuilder(am);
        }
        return _self;
    }

    public void setBuildData(EnemyData ed) {
        _ed = ed;
    }

    @Override
    public void finishBuild() {
        super.finishBuild();
        _ed = null;
    }

    @Override
    public IEntity buildEntity() throws NullPointerException {
        checkIdNotNull();
        return new EnemyEntity(_id);
    }

    @Override
    public ArrayList<AbstractComponent> buildComponentList() throws NullPointerException {
        checkIdNotNull();
        if(null == _ed) {
            throw new NullPointerException("Must call `setBuildData()` first.");
        }
        ArrayList<AbstractComponent> cl = new ArrayList<AbstractComponent>();
        EnemyType et = (EnemyType)SubtypeManager.getInstance().getSubtype(_ed.enemyType);
        Sprite sp = new Sprite(_am.get(et.texture, Texture.class));
        sp.setSize(et.width,et.height);
        sp.setCenter(_ed.x,_ed.y);
        cl.add(new PowerUpComponent(_id, _ed.dropType));
        cl.add(new SpriteComponent(_id, sp));
        cl.add(new LocationComponent(_id, _ed.x,_ed.y));
        cl.add(new SizeComponent(_id, et.width,et.height));
        cl.add(new VelocityComponent(_id, et.speed));
        cl.add(new HasBeenInboundsComponent(_id, false));
        cl.add(new DestinationMovementComponent(_id, _ed.destinationList,et.loopDestinations));
        cl.add(new HitpointComponent(_id, et.hp));
        cl.add(new CollisionsComponent(_id));
        cl.add(new HitboxComponent(_id, new Rectangle(_ed.x,_ed.y,et.width,et.height)));
        cl.add(new FireControlComponent(_id, et.firePattern.getFireRate(), true));
        cl.add(new FirePatternComponent(_id, et.firePattern.getBaseDirection(),et.firePattern.getFireRate(),et.firePattern.getNumberOfBullets(),
                et.firePattern.getDividingAngle(), et.firePattern.getDeltaTheta(), et.firePattern.getBulletType()));
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
