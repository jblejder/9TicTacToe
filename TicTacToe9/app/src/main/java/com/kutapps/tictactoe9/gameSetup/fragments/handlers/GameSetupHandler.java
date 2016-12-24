package com.kutapps.tictactoe9.gameSetup.fragments.handlers;

import com.kutapps.tictactoe9.board.consts.MarkerType;

public interface GameSetupHandler
{
    void onChangeUserName();

    void onApproveUserName();

    void onChangeMarker();

    void onApproveMarker(MarkerType type);


    void onApproveHostRoomName();

    void onChangeHostedRoomName();

    void onClickHostGame();


    void onChangeJoiningRoomName();

    void onApproveJoiningRoomName();

    void onClickJoinGame();

}
