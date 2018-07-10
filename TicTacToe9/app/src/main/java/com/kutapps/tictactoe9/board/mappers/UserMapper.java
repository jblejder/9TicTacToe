package com.kutapps.tictactoe9.board.mappers;

import com.google.firebase.auth.FirebaseUser;
import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.models.PlayerModel;
import com.kutapps.tictactoe9.board.viewmodels.BoardViewModel;
import com.kutapps.tictactoe9.gameSetup.consts.GameMode;

public class UserMapper
{
    public static PlayerModel mapJoiner(FirebaseUser user, MarkerType
            hostMarker)
    {
        PlayerModel player = new PlayerModel();
        player.id = user.getUid();
        player.marker = BoardViewModel.getNextMarker(hostMarker);
        player.type = GameMode.Join;

        return player;
    }

    public static PlayerModel mapHost(FirebaseUser user, MarkerType hostMarker)
    {
        PlayerModel player = new PlayerModel();
        player.id = user.getUid();
        player.marker = hostMarker;
        player.type = GameMode.Host;

        return player;
    }
}
