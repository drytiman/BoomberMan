package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @Author Dmitry Pavlunin & Roman Yusufyanov;
 * @Version 1.0;
 */

public class JavaBomber extends JFrame {

    private Game game;



    private JLabel label;
    private JPanel panel;
    private final int COLS = 15; //кол-во строк
    private final int ROWS = 13; //кол-во столбцов
    private final int IMAGE_SIZE = 50; // размер клетки 50*50 пикселей

    //Отсюда запускаем игру.
    public static void main(String[] args) {
        new JavaBomber();
    }

    JavaBomber() {
        game = new Game(COLS, ROWS);
        game.start();
        setImage();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }


    //Рисуем игровое поле в окне.
    private void initPanel() {

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoord()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.setFocusable(true);


        //Привязываем клавиатуру.
        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                    game.changeGameState();
                }

                if (game.getGameState() == GameState.PLAYED) {

                    if (e.getKeyCode() == KeyEvent.VK_LEFT)
                        game.goLeftOrA("player1");
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                        game.goRightOrD("player1");
                    if (e.getKeyCode() == KeyEvent.VK_UP)
                        game.goUpOrW("player1");
                    if (e.getKeyCode() == KeyEvent.VK_DOWN)
                        game.goDownOrS("player1");
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                        game.Bomb1();
                    if (e.getKeyCode() == KeyEvent.VK_A)
                        game.goLeftOrA("player2");
                    if (e.getKeyCode() == KeyEvent.VK_D)
                        game.goRightOrD("player2");
                    if (e.getKeyCode() == KeyEvent.VK_W)
                        game.goUpOrW("player2");
                    if (e.getKeyCode() == KeyEvent.VK_S)
                        game.goDownOrS("player2");
                    if (e.getKeyCode() == KeyEvent.VK_SPACE)
                        game.Bomb2();
                    panel.removeAll();
                    panel.repaint();

                } else {
                    JLabel textLabel = new JLabel(getMessage(), SwingConstants.CENTER);
                    textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    textLabel.setVerticalAlignment(SwingConstants.CENTER);
                    textLabel.setFont(new Font("Serif", Font.BOLD, 40));
                    if(game.getGameState() == GameState.WINNER1){
                        textLabel.setForeground(Color.RED);
                    }
                    else if(game.getGameState() == GameState.WINNER2){
                        textLabel.setForeground(Color.BLUE);
                    } else {
                         textLabel.setForeground(Color.ORANGE);
                    }

                    textLabel.setVisible(true);
                    panel.add(textLabel);
                }
                label.setText(getMessage());
                Timer timer = new Timer(10, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        panel.repaint();
                    }
                });
                timer.start();
            }
        });


        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));

        Color color = new Color(34, 139, 34);
        panel.setBackground(color);
        add(panel);
    }

    private String getMessage() {
        switch (game.getGameState()) {

            case START:
                return "Press Shift to start the game";
            case PLAYED:
                return "Good Luck!";
            case WINNER2:
                return "Blue Man Wins";
            case WINNER1:
                return "Red Man Wins";
            default:
                return "Welcome";
        }
    }


    private void initFrame() {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("BomberMan");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("2"));
    }

    //Привязывае Box с названием png.
    private void setImage() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name());
        }
    }

    //Получаем нужное нам png.
    private Image getImage(String name) {
        String filename = "/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
