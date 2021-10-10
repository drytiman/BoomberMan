package com.company;

public class Matrix {
    private Box[][] matrix;

    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoord()) {
            matrix[coord.x][coord.y] = defaultBox;
        }
    }

    Box get(Coord coord) {
        if (Ranges.inRange(coord)) {
            return matrix[coord.x][coord.y];
        }
        return null;
    }

    Coord getPlayer() {
        Box box = Box.ZERO;
        for (Coord coord : Ranges.getAllCoord()) {
            if (get(coord) == Box.PLAYER1) {
                return new Coord(coord.x, coord.y);
            }
        }
        return null;
    }

    void set(Coord coord, Box box) {
        if (Ranges.inRange(coord)) {
            matrix[coord.x][coord.y] = box;
        }
    }

}
