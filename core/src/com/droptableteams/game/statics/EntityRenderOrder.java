package com.droptableteams.game.statics;

public class EntityRenderOrder {
    private static String[] _entityRenderOrder = {
            "PlayerEntity",
            "EnemyEntity",
    };

    public static String[] get() {
        return _entityRenderOrder;
    }
}
