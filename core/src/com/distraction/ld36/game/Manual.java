package com.distraction.ld36.game;

import com.distraction.ld36.Vars;

import java.util.HashSet;
import java.util.Set;

public class Manual {

    private String[][] areaCodes;

    public Manual() {
        areaCodes = new String[Vars.NUM_JACK_ROWS][Vars.NUM_JACK_COLS];

        Set<String> existingNumbers = new HashSet<String>();
        for(int row = 0; row < Vars.NUM_JACK_ROWS; row++) {
            for(int col = 0; col < Vars.NUM_JACK_COLS; col++) {

                do {
                    String areaCode = String.valueOf((int) (Math.random() * 900 + 100));
                    if (existingNumbers.contains(areaCode)) {
                        continue;
                    }
                    existingNumbers.add(areaCode);

                    areaCodes[row][col] = areaCode;

                    break;
                } while(true);
            }
        }
    }

    public String getAreaCode(int row, int col) {
        return areaCodes[row][col];
    }

}
