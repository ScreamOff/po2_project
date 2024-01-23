package org.example.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
public class Card implements Serializable {
    private final String pathToBack = "src/cards/back.png";
    private String kolor;
    private String ranga;
    private String pathToPng;
    private boolean restricted = false;

    public Card(String ranga, String kolor) {
        this.ranga = ranga;
        this.kolor = kolor;
        if (ranga.equals("Jack") || ranga.equals("Queen") || ranga.equals("King") || ranga.equals("Ace")) {
            this.pathToPng = "src/cards/" + ranga.toLowerCase() + "_of_" + kolor.toLowerCase() + ".png";

        } else {
            switch (Integer.parseInt(ranga)) {
                case 2:
                    this.pathToPng = "src/cards/two_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 3:
                    this.pathToPng = "src/cards/three_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 4:
                    this.pathToPng = "src/cards/four_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 5:
                    this.pathToPng = "src/cards/five_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 6:
                    this.pathToPng = "src/cards/six_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 7:
                    this.pathToPng = "src/cards/seven_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 8:
                    this.pathToPng = "src/cards/eight_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 9:
                    this.pathToPng = "src/cards/nine_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 10:
                    this.pathToPng = "src/cards/ten_of_" + kolor.toLowerCase() + ".png";
                    break;


            }
        }
    }


    @Override
    public String toString() {
        return ranga.toLowerCase() + "_of_" + kolor.toLowerCase();
    }


}