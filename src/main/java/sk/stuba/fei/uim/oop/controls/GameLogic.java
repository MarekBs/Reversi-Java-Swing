package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import sk.stuba.fei.uim.oop.gui.Menu;

import sk.stuba.fei.uim.oop.gui.Grid;
import sk.stuba.fei.uim.oop.players.PcPlayer;
import sk.stuba.fei.uim.oop.players.Player;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class GameLogic extends UniversalAdapter {
    @Getter
    private final JLabel label;
    private final JFrame frame;
    private int s;
    private  Grid grid;
    private final PcPlayer pcPlayer;
    private final Player player;
    private final Menu menu;
    private final Border bd;
    private Border bdi;

    public GameLogic(int x, JFrame frame1)  {
        pcPlayer = new PcPlayer();
        player = new Player();
        this.label = new JLabel("     SIZE: " + x + "X" + x);
        frame = frame1;
        s = x;
        grid = new Grid(x, frame1,this);
        menu = new Menu(x,this);
        bd = new LineBorder(Color.red,1);
        bdi = new LineBorder(Color.gray,(106*6/s)*35/106);

        frame.add(grid.getPanel());
        frame.add(menu.getMenu(),BorderLayout.PAGE_START);
        getPlayable(x,Color.black,Color.white);



    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting() ) {

            s=((JSlider) e.getSource()).getValue();
            frame.getContentPane().remove(grid.getPanel());

            grid = new Grid(s,frame,this);


            frame.getContentPane().add(grid.getPanel());
            //frame.setSize(115*s,123*s);
            frame.revalidate();
            frame.repaint();
            bdi = new LineBorder(Color.gray,(106*6/s)*35/106);
            menu.getWho().setText("Black's turn");
            getPlayable(s,Color.black,Color.white);


            this.label.setText("     SIZE: " + ((JSlider) e.getSource()).getValue() + "X" + ((JSlider) e.getSource()).getValue());

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='r'){
            restartGame();
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            frame.dispose();
            System.exit(0);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        restartGame();
        frame.setFocusable(true);
        frame.requestFocus();
        }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().getBackground()==Color.green) {
            e.getComponent().setBackground(Color.black);
            clearGreen();
            int x=0,y=0;
            for(int i=0;i<s;i++){
                for(int j=0;j<s;j++){
                    if(grid.getCells()[i][j].getCell()==e.getSource()){
                        x=i;
                        y=j;

                    }
                }
            }

            for(int i=0;i<s;i++){
                for(int j=0;j<s;j++){
                        grid.getCells()[i][j].getCell().setBorder(bd);

                }
            }

            fillClosed(x,y,Color.black,Color.white);
            getPlayable(s,Color.white,Color.black);




            if(cnt()!=0) {
            aiPlay(cnt());
            }


            if(cnt() == 0 ){
                getPlayable(s, Color.black, Color.white);
                cnt();
                if(cnt()==0){
                    winnerPick();
                }
            }

        }
    }

    private int cnt() {
        int cnt=0;
        for(int i=0;i<s;i++){
            for(int j=0;j<s;j++){
                if(grid.getCells()[i][j].getCell().getBackground()== Color.green){
                    cnt++;
                }
            }
        }
        return cnt;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getComponent().getBackground()==Color.green) {

            Object source = e.getSource();

            JPanel panelPressed = (JPanel) source;
            Border border = new LineBorder(Color.blue, 6, true);
            panelPressed.setBorder(BorderFactory.createCompoundBorder(border,bdi));

        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();

        JPanel panelPressed = (JPanel) source;

        panelPressed.setBorder(BorderFactory.createLineBorder(Color.RED));
        if(panelPressed.getBackground()==Color.green){
            panelPressed.setBorder(BorderFactory.createCompoundBorder(bd,bdi));
        }

    }
    private void clearGreen() {
        for(int i=0;i<s;i++){
            for(int j=0;j<s;j++){
                if(grid.getCells()[i][j].getCell().getBackground()== Color.green){
                    grid.getCells()[i][j].getCell().setBackground(Color.gray);
                }
            }
        }
    }

    public void winnerPick(){
        int b=0,w=0;
        for(int i=0;i<s;i++){
            for(int j=0;j<s;j++){
                if(grid.getCells()[i][j].getCell().getBackground()==Color.black){
                    b++;
                }
                if(grid.getCells()[i][j].getCell().getBackground()==Color.white){
                    w++;
                }
            }
        }
        player.setPoints(b);
        pcPlayer.setPoints(w);

        if(b>w) {
            menu.getWho().setText(" Winner: "+player.getName()+" ! - "+player.getPoints()+"r. |W- "+pcPlayer.getPoints());
        }
        if(w>b){
            menu.getWho().setText("Winner: "+pcPlayer.getName()+" ! - "+pcPlayer.getPoints()+"r. |B- "+player.getPoints());
        }
        if(w==b){
            menu.getWho().setText(" TIE"+" White- "+pcPlayer.getPoints()+" Black- "+player.getPoints());
        }

    }

    public void restartGame(){
        for(int i=0;i<s;i++){
            for(int j=0;j<s;j++){
                grid.getCells()[i][j].getCell().setBackground(Color.gray);
                if (i == s/2-1 && j == s/2-1 || i== s/2 && j==s/2) {
                    grid.getCells()[i][j].getCell().setBackground(Color.black);
                }
                if (i == s/2 && j== s/2-1 ||i==s/2-1 && j==s/2) {
                    grid.getCells()[i][j].getCell().setBackground(Color.white);
                }
            }
        }
        menu.getWho().setText("Black's turn");
        getPlayable(s,Color.black,Color.white);
    }

    public void aiPlay(int cnt){
        Random rand = new Random();
        int r = rand.nextInt(cnt);
        r++;
        cnt = 0;
        int a = 0, b = 0;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if (grid.getCells()[i][j].getCell().getBackground() == Color.green) {
                    cnt++;
                    if (cnt == r) {
                        a = i;
                        b = j;
                        grid.getCells()[a][j].getCell().setBackground(Color.white);
                    }
                }
            }
        }

        clearGreen();

        fillClosed(a, b, Color.white, Color.black);
        getPlayable(s, Color.black, Color.white);


        int g=cnt();
        if(g==0){
            getPlayable(s, Color.white, Color.black);
            for(int i=0;i<s;i++){
                for(int j=0;j<s;j++){
                    if(grid.getCells()[i][j].getCell().getBackground()==Color.green){
                        g++;
                    }

                }

            }
            if(g==0){
                winnerPick();

            }
            else{
                aiPlay(g);
            }

        }

    }
    public void setColor(int x, int y,Color colour){

        grid.getCells()[x][y].getCell().setBackground(colour);
    }


    public void fillClosed(int x,int y,Color colour,Color c2) {
        int r, c, count = 0;
        if (x-1>=0  && grid.getCells()[x - 1][y].getCell().getBackground() == c2) {
            r = x - 1;
            while (grid.getCells()[r][y].getCell().getBackground() == c2 && r > 0) {
                r--;
                count++;
                if (grid.getCells()[r][y].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        r++;
                        setColor(r,y,colour);
                    }
                    count = 0;
                }
            }
        }
        count=0;
        if (x+1<s  && grid.getCells()[x + 1][y].getCell().getBackground() == c2) {
            r = x + 1;
            while (grid.getCells()[r][y].getCell().getBackground() == c2 && r<s-1 ) {
                r++;
                count++;
                if (grid.getCells()[r][y].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        r--;
                        setColor(r,y,colour);
                    }
                    count = 0;
                }
            }
        }
        count=0;
        if (y-1>=0 && grid.getCells()[x][y - 1].getCell().getBackground() == c2) {
             c = y - 1;
            while (grid.getCells()[x][c].getCell().getBackground() == c2 && c > 0) {
                c--;
                count++;
                if (grid.getCells()[x][c].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        c++;
                        setColor(x,c,colour);
                    }
                    count = 0;
                }
            }
        }
        count=0;
        if (y+1<s && grid.getCells()[x][y + 1].getCell().getBackground() == c2) {
            c = y + 1;
            while (grid.getCells()[x][c].getCell().getBackground() == c2 && c<s-1) {
                c++;
                count++;
                if (grid.getCells()[x][c].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        c--;
                        setColor(x,c,colour);
                    }
                    count = 0;

                }
            }
        }
        count=0;
        if (x-1>=0 && y-1>=0 && grid.getCells()[x-1][y - 1].getCell().getBackground() == c2) {
            c = y -1;
            r = x-1;
            while (grid.getCells()[r][c].getCell().getBackground() == c2 && c>0 && r>0) {
                c--;
                r--;
                count++;
                if (grid.getCells()[r][c].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        c++;
                        r++;
                        setColor(r,c,colour);
                    }
                    count = 0;
                }
            }
        }
        count=0;
        if (x+1<s && y-1>=0 && grid.getCells()[x+1][y - 1].getCell().getBackground() == c2) {
            c = y -1;
            r = x+1;
            while (grid.getCells()[r][c].getCell().getBackground() == c2 && c>0 && r<s-1) {
                c--;
                r++;
                count++;
                if (grid.getCells()[r][c].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        c++;
                        r--;
                        setColor(r,c,colour);
                    }
                    count = 0;
                }
            }
        }
        count=0;
        if (x-1>=0 && y+1<s && grid.getCells()[x-1][y + 1].getCell().getBackground() == c2) {
            c = y +1;
            r = x-1;
            while (grid.getCells()[r][c].getCell().getBackground() == c2 && c<s-1 && r>0) {
                c++;
                r--;
                count++;
                if (grid.getCells()[r][c].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        c--;
                        r++;
                        setColor(r,c,colour);
                    }
                    count = 0;
                }
            }
        }
        count=0;
        if (x+1<s && y+1<s && grid.getCells()[x+1][y + 1].getCell().getBackground() == c2) {
            c = y +1;
            r = x+1;
            while (grid.getCells()[r][c].getCell().getBackground() == c2 && c<s-1 && r<s-1) {
                c++;
                r++;
                count++;
                if (grid.getCells()[r][c].getCell().getBackground() == colour) {
                    for (int i = 0; i < count; i++) {
                        c--;
                        r--;
                        setColor(r,c,colour);
                    }
                    count = 0;
                }
            }
        }
    }


    public void getPlayable(int x,Color colour,Color c2) {
        int r = 0, c = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (grid.getCells()[i][j].getCell().getBackground() == colour) {
                    if (i-1>=0 && grid.getCells()[i - 1][j].getCell().getBackground() == c2) {
                        r = i - 1;
                        while (grid.getCells()[r][j].getCell().getBackground() == c2 && r>0) {
                            r--;
                            if (grid.getCells()[r][j].getCell().getBackground() == Color.gray) {
                                grid.getCells()[r][j].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[r][j].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (i+1<s && grid.getCells()[i + 1][j].getCell().getBackground() == c2) {
                        r = i + 1;
                        while (grid.getCells()[r][j].getCell().getBackground() == c2 && r<s-1) {
                            r++;
                            if (grid.getCells()[r][j].getCell().getBackground() == Color.gray) {
                                grid.getCells()[r][j].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[r][j].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (j-1>=0 && grid.getCells()[i][j - 1].getCell().getBackground() == c2) {
                        c = j - 1;
                        while (grid.getCells()[i][c].getCell().getBackground() == c2 && c>0) {
                            c--;
                            if (grid.getCells()[i][c].getCell().getBackground() == Color.gray) {
                                grid.getCells()[i][c].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[i][c].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (j+1<s && grid.getCells()[i][j + 1].getCell().getBackground() == c2) {
                        c = j + 1;
                        while (grid.getCells()[i][c].getCell().getBackground() == c2 && c<s-1) {
                            c++;
                            if (grid.getCells()[i][c].getCell().getBackground() == Color.gray) {
                                grid.getCells()[i][c].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[i][c].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (i-1>=0 && j-1>=0 && grid.getCells()[i-1][j - 1].getCell().getBackground() == c2) {
                        c = j -1;
                        r = i-1;
                        while (grid.getCells()[r][c].getCell().getBackground() == c2 && c>0 && r>0) {
                            c--;
                            r--;
                            if (grid.getCells()[r][c].getCell().getBackground() == Color.gray) {
                                grid.getCells()[r][c].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[r][c].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (i+1<s && j-1>=0 && grid.getCells()[i+1][j - 1].getCell().getBackground() == c2) {
                        c = j -1;
                        r = i+1;
                        while (grid.getCells()[r][c].getCell().getBackground() == c2 && c>0 && r<s-1) {
                            c--;
                            r++;
                            if (grid.getCells()[r][c].getCell().getBackground() == Color.gray) {
                                grid.getCells()[r][c].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[r][c].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (i-1>=0 && j+1<s && grid.getCells()[i-1][j + 1].getCell().getBackground() == c2) {
                        c = j +1;
                        r = i-1;
                        while (grid.getCells()[r][c].getCell().getBackground() == c2 && c<s-1 && r>0) {
                            c++;
                            r--;
                            if (grid.getCells()[r][c].getCell().getBackground() == Color.gray) {
                                grid.getCells()[r][c].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[r][c].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                    if (i+1<s && j+1<s && grid.getCells()[i+1][j + 1].getCell().getBackground() == c2) {
                        c = j +1;
                        r = i+1;
                        while (grid.getCells()[r][c].getCell().getBackground() == c2 && c<s-1 && r<s-1) {
                            c++;
                            r++;
                            if (grid.getCells()[r][c].getCell().getBackground() == Color.gray) {
                                grid.getCells()[r][c].getCell().setBackground(Color.green);
                                if(colour==Color.black){
                                    grid.getCells()[r][c].getCell().setBorder(BorderFactory.createCompoundBorder(bd,bdi));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
