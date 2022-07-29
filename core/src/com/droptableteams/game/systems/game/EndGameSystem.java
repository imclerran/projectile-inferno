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
import com.droptableteams.game.components.game.VictoryStateComponent;
import com.droptableteams.game.util.constants.SpecialEntityIds;
import com.droptableteams.game.util.constants.SystemUpdateOrder;

import java.util.HashSet;

public class EndGameSystem extends AbstractSystem {

    public EndGameSystem(int id){
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "EndGameSystem";
    }

    @Override
    public void update(int id) {
        EntityManager em = EntityManager.getInstance();
        ComponentManager cm = ComponentManager.getInstance();
        VictoryStateComponent vsc = (VictoryStateComponent)cm.getComponent(id, "VictoryStateComponent");
        if(!vsc.isGameOver()) {
            SpawnListComponent slc = (SpawnListComponent) cm.getComponent(id, "SpawnListComponent");
            if (slc.getSpawnList().size() == 0 && (em.getEntities("EnemyEntity") == null || em.getEntities("EnemyEntity").size() == 0)
                    && (em.getEntities("BossEntity") == null || em.getEntities("BossEntity").size() == 0)) {
                vsc.gameOverVictory();
                displayGameOverMessage(vsc.isVictory());
            }
            if (((LifeCounterComponent) cm.getComponent(SpecialEntityIds.PLAYER_ENTITY, "LifeCounterComponent")).getLifeCount() == -1) {
                vsc.gameOverDefeat();
                displayGameOverMessage(vsc.isVictory());
            }

        }
    }

    // TODO: consider refactoring how victory/defeat sprites are added
    // TODO: add GameOverMessage entity type template to the types folder
    private void displayGameOverMessage(boolean isVictory) {
        ComponentManager cm = ComponentManager.getInstance();
        AssetManager am = ((AssetManagerComponent)cm.getComponent(SpecialEntityIds.GAME_ENTITY, "AssetManagerComponent")).getAssetManager();
        String spriteTexture = isVictory ? "sprites/victory.png" : "sprites/defeat.png";
        StaticSpriteEntityBuilder.getInstance(am).setBuildData(spriteTexture);
        ECSEngine.get().addEntity(StaticSpriteEntityBuilder.getInstance(am));
        StaticSpriteEntityBuilder.getInstance(am).finishBuild();
    }
}
