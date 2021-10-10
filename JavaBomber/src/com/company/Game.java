package com.company;

/*
 Фасад.
 Отсюда стартует сборка карты и происходит работа с клавиатурой.
 */

public class Game {
    private MAP map;

    private GameState gameState;

    public GameState getGameState() {
        gameState = map.getGameState();
        return gameState;
    }

    // Задается карта.
    public Game(int cols, int rows) {
        Ranges.setSize(new Coord(cols, rows));
        map = new MAP();
    }


    // Отрисовка карты.
    public void start() {
        map.start();
    }


    //Передаем значения в клетках, для вывода на экран карты.
    public Box getBox(Coord coord) {
        return map.get(coord);
    }

    //В случае нажатия на клавиатуре клавиши LEFT or A.
    public void goLeftOrA(String string) {
        map.setPressLeftOrA(string);
    }

    //В случае нажатия на клавиатуре клавиши RIGHT or D.
    public void goRightOrD(String string) {
        map.setPressRightOrD(string);
    }

    //В случае нажатия на клавиатуре клавиши UP or W.
    public void goUpOrW(String string) {
        map.setPressUpOrW(string);
    }

    //В случае нажатия на клавиатуре клавиши DOWN or S.
    public void goDownOrS(String string) {
        map.setPressDownOrS(string);
    }

    //В случае нажатия на клавиатуре клавиши SPACE.
    public void Bomb1() {
        map.firstBomb();
    }

    //В случае нажатия на клавиатуре клавиши ENTER.
    public void Bomb2() {
        map.secondBomb();
    }


    public void changeGameState() {
        map.changeGameState();
    }
}
