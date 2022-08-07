package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.controls.GameLogic;



import javax.swing.*;
import java.awt.*;



public class Grid extends JPanel {

    @Getter
    private final JPanel panel;
    @Setter
    @Getter
    private Cell[][] cells;


    public Grid(int x, JFrame frame, GameLogic logic) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(x, x));
        cells = new Cell[x][x];
        int a = 0, b = 0;


        for (int i = 0; i < x * x; i++) {
            Cell cell = new Cell(x, frame, logic);
            panel.add(cell.getCell());
            if (i == x * x / 2 - x / 2 - 1 || i == x * x / 2 - x / 2 + x) {
                cell.getCell().setBackground(Color.black);


            }
            if (i == x * x / 2 - x / 2 || i == x * x / 2 - x / 2 + x - 1) {

                cell.getCell().setBackground(Color.white);

            }


            cells[a][b] = cell;
            a++;
            if (a >= x) {
                a = 0;
                b++;
            }
        }
    }



}
