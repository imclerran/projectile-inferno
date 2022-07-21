package com.droptableteams.game.util.constants;

import com.droptableteams.game.entities.LifeDisplayEntity;

public class EntityRenderOrder {
    private static final String[] ENTITY_RENDER_ORDER = {
            "BossEntity",
            "EnemyEntity",
            "BulletEntity",
            "PlayerEntity",
            "VisibleHitboxEntity",
            "ShieldEntity",
            "LifeDisplayEntity",
            "StaticSpriteEntity",
            "PowerUpEntity"
    };

    // TODO: #7 make ENTITY_RENDER_ORDER public and remove get() method
    public static String[] get() {
        return ENTITY_RENDER_ORDER;
    }
}
