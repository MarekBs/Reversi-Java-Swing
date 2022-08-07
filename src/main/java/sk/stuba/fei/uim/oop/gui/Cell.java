package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import sk.stuba.fei.uim.oop.controls.GameLogic;


import javax.swing.*;
import java.awt.*;




public class Cell extends JPanel  {
    @Getter
    private final JPanel cell;


    public Cell(int x, JFrame frame,GameLogic logic) {

        cell = new JPanel();
        cell.setBorder(BorderFactory.createLineBorder(Color.RED));
        cell.setBackground(Color.gray);
        cell.addMouseListener(logic);


    }


}
