package com.droptableteams.game.components.game;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.util.Spawnable;
import com.droptableteams.game.util.Wave;

import java.util.ArrayList;

public class SpawnListComponent extends AbstractComponent {
    private ArrayList<Spawnable> _spawnList;

    public SpawnListComponent(int id, ArrayList<Wave> waveList) {
        _id = id;
        _spawnList = new ArrayList<Spawnable>();
        _type = "SpawnListComponent";
        for (Wave x: waveList)
        {
            _spawnList.addAll(x.enemies);
        }
    }

    public ArrayList<Spawnable> getSpawnList() {
        return _spawnList;
    }

    public void setSpawnList(ArrayList<Spawnable> spawnList) {
        _spawnList = spawnList;
    }
}
