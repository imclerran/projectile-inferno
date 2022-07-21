package com.droptableteams.game.systems.game;

import com.badlogic.gdx.assets.AssetManager;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.EntityManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.builders.StaticSpriteEntityBuilder;
import com.droptableteams.game.components.LifeCounterComponent;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.components.game.SpawnListComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

public class EndGameSystem extends AbstractSystem {

    // TODO: Move state variables out of system -> should be in components
    private boolean victory;
    private boolean defeat;
    private  boolean gameOver;
    
    private EntityManager _em;
    private ComponentManager _cm;
    private AssetManager _am;


    public EndGameSystem(int id){
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "EndGameSystem";
        victory = false;
        defeat = false;
        gameOver = false;
        _em = EntityManager.getInstance();
        _cm = ComponentManager.getInstance();
        _am = ((AssetManagerComponent)_cm.getComponent(SpecialEntityIds.GAME_ENTITY, "AssetManagerComponent")).getAssetManager();
    }

    @Override
    public void update(int id) {
        if(!gameOver) {
            SpawnListComponent slc = (SpawnListComponent) _cm.getComponent(id, "SpawnListComponent");

            if (slc.getSpawnList().size() == 0 && (_em.getEntities("EnemyEntity") == null || _em.getEntities("EnemyEntity").size() == 0)
                    && (_em.getEntities("BossEntity") == null || _em.getEntities("BossEntity").size() == 0)) {
                victory = true;
            }
            if (((LifeCounterComponent) _cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "LifeCounterComponent")).getLifeCount() == -1) {
                defeat = true;
            }

            if (victory) {
                displayVictory();
                gameOver = true;
            }
            if (defeat) {
                displayDefeat();
                gameOver = true;
            }
        }
    }
    private void displayVictory(){
        StaticSpriteEntityBuilder.getInstance(_am).setBuildData("sprites/victory.png");
        ECSEngine.get().addEntity(StaticSpriteEntityBuilder.getInstance(_am));
        StaticSpriteEntityBuilder.getInstance(_am).finishBuild();

    }
    private void displayDefeat(){
        StaticSpriteEntityBuilder.getInstance(_am).setBuildData("sprites/defeat.png");
        ECSEngine.get().addEntity(StaticSpriteEntityBuilder.getInstance(_am));
        StaticSpriteEntityBuilder.getInstance(_am).finishBuild();
    }
}
