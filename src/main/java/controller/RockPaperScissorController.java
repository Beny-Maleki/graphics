package controller;

import model.enums.GameEnums.PlayerTurn;


public class RockPaperScissorController {
    public static PlayerTurn recognizeGameWinner(String firstPlayerTool, String secondPlayerTool) {
        if (firstPlayerTool.equals(secondPlayerTool)) return PlayerTurn.None;
        else if (firstPlayerTool.equals("paper")) {
            if (secondPlayerTool.equals("rock")) {
                return PlayerTurn.PLAYER_ONE;
            } else {
                return PlayerTurn.PLAYER_TWO;
            }
        } else if (firstPlayerTool.equals("rock")) {
            if (secondPlayerTool.equals("scissor")) {
                return PlayerTurn.PLAYER_ONE;
            } else {
                return PlayerTurn.PLAYER_TWO;
            }
        } else {
            if (secondPlayerTool.equals("paper")) {
                return PlayerTurn.PLAYER_ONE;
            } else {
                return PlayerTurn.PLAYER_TWO;
            }
        }
    }
}

