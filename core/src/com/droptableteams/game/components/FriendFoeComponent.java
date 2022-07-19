package com.droptableteams.game.components;

import com.droptableteams.game.util.constants.Enums;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class FriendFoeComponent extends AbstractComponent {

    private Team _team;

    public FriendFoeComponent(int id, Team team) {
        _id = id;
        _type = "FriendFoeComponent";
        _team = team;
    }

    public Team getTeam() { return _team; }

    public isFriendly(Team team) { return _team == team; }
}