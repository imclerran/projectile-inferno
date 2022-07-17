package com.droptableteams.game.components.game;

import com.badlogic.gdx.assets.AssetManager;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class AssetManagerComponent extends AbstractComponent {
    private AssetManager _am;

    public AssetManagerComponent(int id, AssetManager am) {
        _id = id;
        _am = am;
        _type = "AssetManagerComponent";
    }

    public AssetManager getAssetManager() { return _am; }
}
