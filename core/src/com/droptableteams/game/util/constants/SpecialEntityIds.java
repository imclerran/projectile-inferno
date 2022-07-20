package com.droptableteams.game.util.constants;

public class SpecialEntityIds {

    public static final int GAME_ENTITY = -1;
    public static final int PLAYER_ENTITY = -2;
    public static final int VISIBLE_HITBOX_ENTITY = -3; // TODO: consider using normally assigned id for hitbox as well
    public static final int SHIELD_ENTITY = -4; // TODO: shield entity does not need special id

    // TODO: #6 Refactor codebase to remove deprecated SpecialEntityIds getter methods

    @Deprecated(forRemoval = true)
    public static int getGameEntityId() { return GAME_ENTITY; }

    @Deprecated(forRemoval = true)
    public static int getPlayerEntityId() { return PLAYER_ENTITY; }

    @Deprecated(forRemoval = true)
    public static int getVisibleHitboxEntity() { return VISIBLE_HITBOX_ENTITY; }
}
