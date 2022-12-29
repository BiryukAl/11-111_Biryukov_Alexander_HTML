package ru.kpfu.itis.semesterworksecond.server.message;

public enum TypeMessage {
    //Request
    CHECK_CONNECT,
    CONNECT_TRUE,
    //Response
    SEND_PUSH_POINT,
    RECEIVE_PUSH_POINT,
    PULL_OPPONENT_STEP,
    //Response
    BREAK_GAME,
    //Request
    IS_MY_STEP,
    //Response
    STEP_HOLDER,
    //Request
    WHATS_MY_TURN,
    //Response
    YOU_TURN,
    //Response
    WIN_Holder,

    GET_BATTLE_GRID,
    //Response
    SEND_BATTLE_GRID,
    //Request
    PULL_ALL_STEP,
    ALL_STEP,

}
