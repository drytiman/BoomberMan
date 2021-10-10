package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DeltaMapMoveOrBomb {
    int player_x1 = 13, player_y1 = 11, player_x2 = 1, player_y2 = 1;
    GameState gameState = GameState.START;

    ArrayList<List> BombSet = new ArrayList<>();

    int bombcounter1 = 0, bombcounter2 = 0;

    private Box get(Matrix map, Coord coord) {
        return map.get(coord);
    }

    public GameState getGameState() {
        return gameState;
    }


    public void setPlayer(Integer x_start, Integer y_start, String direction, Matrix map, Box box, Integer TypePlayer) {
        map = IsMatrix(x_start, y_start, map);
        int x = x_start, y = y_start;
        if (direction.equals("left")) {
            x -= 1;
            if (IsFree(x, y, map)) {
                x += 1;
            }
        }
        if (direction.equals("right")) {
            x += 1;
            if (IsFree(x, y, map)) {
                x -= 1;
            }
        }
        if (direction.equals("up")) {
            y -= 1;
            if (IsFree(x, y, map)) {
                y += 1;
            }
        }
        if (direction.equals("down")) {
            y += 1;
            if (IsFree(x, y, map)) {

                y -= 1;
            }
        }
        if (TypePlayer == 1) {
            player_x1 = x;
            player_y1 = y;
        } else {
            player_x2 = x;
            player_y2 = y;
        }
        if (!(x == x_start && y == y_start && get(map, new Coord(x, y)) == Box.BOMB)) {
            map.set(new Coord(x, y), box);
        }
    }

    public boolean IsFree(Integer x, Integer y, Matrix map) {
        return get(map, new Coord(x, y)) == Box.SOFT_WALL || get(map, new Coord(x, y)) == Box.WALL || get(map, new Coord(x, y)) == Box.BOMB;
    }

    public Matrix IsMatrix(Integer x, Integer y, Matrix map) {
        if (get(map, new Coord(x, y)) == Box.BOMB) {
            map.set(new Coord(x, y), Box.BOMB);
        } else {
            map.set(new Coord(x, y), Box.ZERO);
        }
        return map;

    }

    public void SetBomb(Integer x, Integer y, Matrix map, Integer PlayerType) {
        int size;
        if (PlayerType == 1) {
            size = bombcounter1;
        } else {
            size = bombcounter2;
        }

        if (size < 3) {
            map.set(new Coord(x, y), Box.BOMB);

            ArrayList<Integer> InfoBomb = new ArrayList<>(2);
            InfoBomb.add(x);
            InfoBomb.add(y);
            BombSet.add(InfoBomb);
            int delay = 2000;
            if (PlayerType == 1) {
                bombcounter1 += 1;
            } else {
                bombcounter2 += 1;
            }

            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (PlayerType == 1) {
                        bombcounter1 -= 1;
                    } else {
                        bombcounter2 -= 1;
                    }
                    int coordBomb_x = (int) BombSet.get(0).get(0);
                    int coordBomb_y = (int) BombSet.get(0).get(1);
                    map.set(new Coord((int) BombSet.get(0).get(0), (int) BombSet.get(0).get(1)), Box.ZERO);
                    BombSet.remove(0);
                    int coordBomb_x_Change_x = coordBomb_x;
                    int coordBomb_y_Change_y = coordBomb_y;
                    for (int u = 0; u <= 1; u++) {
                        for (int i = 0; i <= 1; i++) {
                            while (get(map, new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)) != Box.WALL) {

                                if (get(map, new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)) == Box.PLAYER1) {
                                    gameState = GameState.WINNER2;
                                }
                                if (get(map, new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)) == Box.PLAYER2) {
                                    gameState = GameState.WINNER1;
                                }
                                if (u == 1) {
                                    if (coordBomb_y != coordBomb_y_Change_y && get(map, new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)) != Box.BOMB) {
                                        map.set((new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)), Box.FIRE_UP_DOWN);
                                    }
                                } else {
                                    if (coordBomb_x != coordBomb_x_Change_x && get(map, new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)) != Box.BOMB) {
                                        map.set((new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)), Box.FIRE_LEFT_RIGHT);
                                    }

                                }


                                int fire_Delay = 25;
                                int finalCoordBomb_x_Change_x2 = coordBomb_x_Change_x;
                                int finalCoordBomb_y_Change_y2 = coordBomb_y_Change_y;

                                ActionListener taskPerformer1 = new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        if (get(map, new Coord(finalCoordBomb_x_Change_x2, finalCoordBomb_y_Change_y2)) != Box.BOMB) {
                                            map.set(new Coord(finalCoordBomb_x_Change_x2, finalCoordBomb_y_Change_y2), Box.ZERO);
                                        }
                                    }
                                };
                                Timer timer1 = new Timer(fire_Delay, taskPerformer1);
                                timer1.start();
                                timer1.setRepeats(false);
                                if (u == 0) {
                                    coordBomb_x_Change_x += i * 2 - 1;
                                } else {
                                    coordBomb_y_Change_y += i * 2 - 1;
                                }
                                if (get(map, new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y)) == Box.SOFT_WALL) {
                                    map.set(new Coord(coordBomb_x_Change_x, coordBomb_y_Change_y), Box.ZERO);
                                    break;
                                }
                            }

                            coordBomb_x_Change_x = coordBomb_x;
                            coordBomb_y_Change_y = coordBomb_y;
                        }
                    }
                }
            };
            Timer timer = new Timer(delay, taskPerformer);
            timer.start();
            timer.setRepeats(false);
        }
    }

    public void changeGameState() {
        gameState = GameState.PLAYED;
    }

}
