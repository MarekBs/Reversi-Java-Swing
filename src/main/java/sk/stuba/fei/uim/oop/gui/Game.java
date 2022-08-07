package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.controls.GameLogic;
import sk.stuba.fei.uim.oop.players.PcPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Game {
    @Getter
    @Setter
    private JFrame frame;
    //private final Menu menu;

    public Game(int x) throws IOException {
        frame = new JFrame("Reversi");
        frame.setLocation(750,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(115*x,123*x);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout());


        GameLogic logic = new GameLogic(x,frame);
        frame.addKeyListener(logic);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setVisible(true);
    }
}
