package com.company;

import java.util.Random;


public class MAP {

    private GameState gameState;
    DeltaMap deltaMap = new DeltaMap();
    private Matrix map;

    public GameState getGameState() {
        gameState = deltaMap.getGameState();
        return gameState;
    }

    //Заполняем карты.
    void start() {
        map = new Matrix(Box.ZERO);

        for (int i = 0; i <= 14; i++) {
            map.set(new Coord(i, 0), Box.WALL);
            map.set(new Coord(i, 12), Box.WALL);
        }

        for (int j = 0; j <= 12; j++) {
            map.set(new Coord(0, j), Box.WALL);
            map.set(new Coord(14, j), Box.WALL);
        }

        for (int i = 0; i <= 14; i = i + 2) {
            for (int j = 0; j <= 12; j = j + 2) {
                map.set(new Coord(i, j), Box.WALL);
            }
        }

        Random random = new Random();

        for (int i = 1; i <= 13; i++) {
            for (int j = 1; j <= 11; j++) {
                if (((j % 2 != 0) || (i % 2 != 0)) & (i + j > 3) & (i + j < 23) & random.nextBoolean()) {
                    map.set(new Coord(i, j), Box.SOFT_WALL);
                }
            }
        }

        map.set(new Coord(1, 1), Box.PLAYER1);
        map.set(new Coord(13, 11), Box.PLAYER2);
    }


    Box get(Coord coord) {
        return map.get(coord);
    }

    public void setPressLeftOrA(String string) {
        deltaMap.deltaMapMove(map, string, "left");
    }

    public void setPressRightOrD(String string) {
        deltaMap.deltaMapMove(map, string, "right");
    }

    public void setPressUpOrW(String string) {
        deltaMap.deltaMapMove(map, string, "up");
    }

    public void setPressDownOrS(String string) {
        deltaMap.deltaMapMove(map, string, "down");
    }

    public void firstBomb() {
        deltaMap.deltaMapBomb("player1", map);
    }

    public void secondBomb() {
        deltaMap.deltaMapBomb("player2", map);
    }


    public void changeGameState() {
        deltaMap.changeGameState();
    }
}






