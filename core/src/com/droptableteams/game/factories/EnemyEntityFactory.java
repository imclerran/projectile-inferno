package com.droptableteams.game.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.IComponent;
import com.droptableteams.game.LibECS.interfaces.IEntity;
import com.droptableteams.game.LibECS.interfaces.ISystem;
import com.droptableteams.game.components.*;
import com.droptableteams.game.entities.EnemyEntity;
import com.droptableteams.game.util.constants.Directions;
import com.droptableteams.game.util.constants.SystemUpdateOrder;
import com.droptableteams.game.systems.*;

import java.util.ArrayList;

/**
 * Factory is not currently very extensible or adaptable
 * for use with script inputs or other forms of argument.
 *
 * TODO: Redesign Factory, and consider building an interface.
 *
 * TODO: Rename Factories to Builders -- since not technically factory pattern.
 */
public class EnemyEntityFactory {
    private static ECSEngine _engine = ECSEngine.getInstance(SystemUpdateOrder.get());
    private static ArrayList<IComponent> _cl = new ArrayList<IComponent>();
    private static ArrayList<ISystem> _sl = new ArrayList<ISystem>();

    public static void create(AssetManager assetManager) {
        int id = _engine.acquireEntityId();
        IEntity entity = new EnemyEntity(id);
        generateComponentList(id, assetManager);
        generateSystemList(id);
        _engine.addEntity(entity, _cl, _sl);
    }

    private static void generateComponentList(int id, AssetManager am) {
        float x = -64;
        float y = Gdx.graphics.getHeight()/2;
        float width = 42;
        float height = 52;
        Sprite sp = new Sprite(am.get("sprites/enemyA.png", Texture.class));

        sp.setSize(width,height);
        sp.setCenter(x,y);
        _cl.clear();
        _cl.add(new SpriteComponent(id, sp));
        _cl.add(new LocationComponent(id, x,y));
        _cl.add(new SizeComponent(id, width,height));
        _cl.add(new VelocityComponent(id, 32));
        _cl.add(new HasBeenInboundsComponent(id, false));
        _cl.add(new MoveDirectionComponent(id, 0f));
        _cl.add(new FireControlComponent(id, 0.15f, true));
        _cl.add(new FirePatternComponent(id, Directions.DOWN,2,
                (float)(Math.PI), (float)Math.PI/2, "EnemyBulletA"));
    }

    private static void generateSystemList(int id) {
        _sl.clear();
        _sl.add(new UpdateSpriteSystem(id));
        _sl.add(new DirectionalMovementSystem(id));
        _sl.add(new DespawnOutOfBoundsSystem(id));
        _sl.add(new SpeedModifierSystem(id));
        _sl.add(new FireControlSystem(id));
    }

    public static void createTypeB(AssetManager assetManager) {
        int id = _engine.acquireEntityId();
        IEntity entity = new EnemyEntity(id);
        generateComponentListB(id, assetManager);
        generateSystemListB(id);
        _engine.addEntity(entity, _cl, _sl);
    }

    private static void generateComponentListB(int id, AssetManager am) {
        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();
        float width = 42;
        float height = 52;
        Sprite sp = new Sprite(am.get("sprites/enemyA.png", Texture.class));

        float destX = Gdx.graphics.getWidth()/2;
        float destY = 3*Gdx.graphics.getHeight()/4;
        ArrayList<Float> xList = new ArrayList<Float>();
        xList.add(destX);
        ArrayList<Float> yList = new ArrayList<Float>();
        yList.add(destY);
        ArrayList<Long> stayForList = new ArrayList<Long>();
        stayForList.add(0l);

        sp.setSize(width,height);
        sp.setCenter(x,y);
        _cl.clear();
        _cl.add(new SpriteComponent(id, sp));
        _cl.add(new LocationComponent(id, x,y));
        _cl.add(new SizeComponent(id, width,height));
        _cl.add(new VelocityComponent(id, 400));
        _cl.add(new HasBeenInboundsComponent(id, false));
        _cl.add(new DestinationMovementComponent(id, xList, yList, stayForList, false ));
        _cl.add(new FireControlComponent(id, 0.15f, true));
        _cl.add(new FirePatternComponent(id, Directions.DOWN,2,
                (float)(Math.PI), (float)Math.PI/2, "EnemyBulletA"));
    }

    private static void generateSystemListB(int id) {
        _sl.clear();
        _sl.add(new UpdateSpriteSystem(id));
        _sl.add(new DestinationMovementSystem(id));
        _sl.add(new DespawnOutOfBoundsSystem(id));
        _sl.add(new SpeedModifierSystem(id));
        _sl.add(new FireControlSystem(id));
    }

}
