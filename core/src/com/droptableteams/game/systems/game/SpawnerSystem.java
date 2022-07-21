package com.droptableteams.game.systems.game;

import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.ECSEngine;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.builders.BossEntityBuilder;
import com.droptableteams.game.components.game.AssetManagerComponent;
import com.droptableteams.game.components.game.GameTimeComponent;
import com.droptableteams.game.components.game.SpawnListComponent;
import com.droptableteams.game.builders.EnemyEntityBuilder;
import com.droptableteams.game.util.constants.SystemUpdateOrder;
import com.droptableteams.game.util.data.BossData;
import com.droptableteams.game.util.data.EnemyData;
import com.droptableteams.game.util.Spawnable;

import java.util.ArrayList;
import java.util.HashSet;

public class SpawnerSystem extends AbstractSystem {

    public SpawnerSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "SpawnerSystem";
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        SpawnListComponent slc = (SpawnListComponent) cm.getComponent(id, "SpawnListComponent");
        GameTimeComponent gtc = (GameTimeComponent) cm.getComponent(id, "GameTimeComponent");
        AssetManagerComponent amc = (AssetManagerComponent) cm.getComponent(id, "AssetManagerComponent");
        EnemyEntityBuilder enemyBuilder = EnemyEntityBuilder.getInstance(amc.getAssetManager());
        BossEntityBuilder bossBuilder = BossEntityBuilder.getInstance(amc.getAssetManager());
        ArrayList<Spawnable> flaggedForRemoval = new ArrayList<Spawnable>();
        ArrayList<Spawnable> currentWave;
        currentWave = slc.getSpawnList();
        for (Spawnable spawnable : currentWave) {
            if (gtc.getTimeInMillis() >= spawnable.spawnTime) {
                if (spawnable.entityType.equals("BossEntity")) {
                    flaggedForRemoval.add(spawnable);
                    bossBuilder.setBuildData((BossData) spawnable.data);
                    ECSEngine.get().addEntity(bossBuilder);
                }
                else if(spawnable.entityType.equals("EnemyEntity"))
                {
                    flaggedForRemoval.add(spawnable);
                    enemyBuilder.setBuildData((EnemyData) spawnable.data);
                    ECSEngine.get().addEntity(enemyBuilder);
                }
            }
        }
        for (Spawnable flagged : flaggedForRemoval) {
            slc.getSpawnList().remove(flagged);
        }
    }
}
