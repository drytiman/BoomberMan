package com.company;


public class DeltaMap {

    GameState gameState;
    DeltaMapMoveOrBomb deltaMapMoveOrBomb = new DeltaMapMoveOrBomb();

    public GameState getGameState() {
        gameState = deltaMapMoveOrBomb.getGameState();
        return gameState;
    }


    public void deltaMapBomb(String string, Matrix map) {
        if (string.equals("player1")) {
            deltaMapMoveOrBomb.SetBomb(deltaMapMoveOrBomb.player_x1,deltaMapMoveOrBomb.player_y1,map,1);

        } else {
            deltaMapMoveOrBomb.SetBomb(deltaMapMoveOrBomb.player_x2,deltaMapMoveOrBomb.player_y2,map,2);
        }
    }

    public void deltaMapMove(Matrix map, String player, String direction) {
        if (player.equals("player1")) {
            deltaMapMoveOrBomb.setPlayer(deltaMapMoveOrBomb.player_x1, deltaMapMoveOrBomb.player_y1,direction,map,Box.PLAYER2,1);
        }

        if (player.equals("player2")) {
            deltaMapMoveOrBomb.setPlayer(deltaMapMoveOrBomb.player_x2, deltaMapMoveOrBomb.player_y2,direction,map,Box.PLAYER1,2);
        }
    }

    public void changeGameState() {
        deltaMapMoveOrBomb.changeGameState();
    }
}

