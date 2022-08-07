package sk.stuba.fei.uim.oop.players;


import lombok.Getter;
import lombok.Setter;



@Getter
public class PcPlayer  {
    @Setter
    private int points;
    private final String name;


    public PcPlayer() {
        points = 0;
        name = "White";

    }
}
