package com.droptableteams.game.util.constants;

/**
 * Contains a static array of system type strings,
 * in the order in which the systems should be updated.
 */
public class SystemUpdateOrder {
    public static final String[] SYSTEM_UPDATE_ORDER = {
            "DurationSystem",
            "HandleInputSystem",
            "SpawnerSystem",
            "TargetPlayerSystem",
            "FireControlSystem",
            "SpeedModifierSystem",
            "DirectionalMovementSystem",
            "DestinationMovementSystem",
            "UpdateLocationSystem",
            "RelativePositionSystem",
            "SetHitboxLocationSystem",
            "StopAtBoundarySystem",
            "BulletCollisionSystem",
            "PowerUpCollisionSystem",
            "CollisionDamageSystem",
            "RotateSpriteToDirectionSystem",
            "UpdateSpriteSystem",
            "LifeUpdateSystem",
            "RespawnSystem",
            "DespawnWithDurationSystem",
            "DespawnOutOfBoundsSystem",
            "DestroyOnOwnerDeathSystem",
            "EndGameSystem",
            "RenderSystem",
    };

//    public static String[] get() { return _systemUpdateOrder; }
}
