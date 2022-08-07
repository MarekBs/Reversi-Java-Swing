package sk.stuba.fei.uim.oop.players;


import lombok.Getter;
import lombok.Setter;
@Getter
public class Player  {

    @Setter
    private int points;
    private final String name;

    public Player() {
        this.points=0;
        name = "Black";


    }
}
