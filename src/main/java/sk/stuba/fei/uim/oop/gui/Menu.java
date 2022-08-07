package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;


public class Menu extends JPanel {
    @Getter
    private final JPanel menu;
    @Getter
    final private JSlider slider;
    private final JButton button;
    @Getter
    private JLabel who;

    public Menu(int x, GameLogic logic)  {
        menu = new JPanel();
        menu.setLayout(new GridLayout(1,4));
        slider = new JSlider(JSlider.HORIZONTAL,6,12,x);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        who = new JLabel("Black's turn");
        slider.addChangeListener(logic);
        button = new JButton("RESTART");
        button.addActionListener(logic);
        slider.setFocusable(false);
        button.setFocusable(false);


        menu.add(who);
        menu.add(slider);
        menu.add(logic.getLabel());
        menu.add(button);

    }
}
