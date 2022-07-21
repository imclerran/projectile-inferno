package com.droptableteams.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractEventListener;
import com.droptableteams.game.components.HitpointComponent;
import com.droptableteams.game.util.types.BulletType;

import java.util.HashMap;

public class BulletCollisionEventListener extends AbstractEventListener {


    public BulletCollisionEventListener(int id) {
        _id = id;
        _type = "BulletCollision";
        _triggerType = "BulletCollisionEvent";
    }

    @Override
    public boolean canHandle(Integer id, String type) {
        return (id == _id && _triggerType == _type);
    }

    @Override
    public void handleEvent(HashMap<String, Object> args) {
        ComponentManager cm = ComponentManager.getInstance();
        HitpointComponent hpc = (HitpointComponent)cm.getComponent(_id, "HitpointComponent");
        if(args.containsKey("bulletType")) {
            //BulletType bt = BulletTypeFactory.make((String)args.get("bulletType"));
            // TODO: #2 don't parse json at event handling time - json should be parsed at first time use and then data cached
            Json json = new Json();
            BulletType bt = json.fromJson(BulletType.class, Gdx.files.internal("scripts/enemies/"+ args.get("bulletType") + ".json"));
            hpc.subtractHp(bt.damage);
        }
    }
}
