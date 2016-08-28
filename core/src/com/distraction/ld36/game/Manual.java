package com.distraction.ld36.game;

import com.distraction.ld36.Vars;

import java.util.HashSet;
import java.util.Set;

public class Manual {

    private String[][] areaCodes;

    public Manual() {
        areaCodes = new String[Vars.NUM_JACK_ROWS][Vars.NUM_JACK_COLS];

        Set<String> existingNumbers = new HashSet<String>();
        for (int row = 0; row < areaCodes.length; row++) {
            for (int col = 0; col < areaCodes[0].length; col++) {

                do {
                    String areaCode = String.valueOf((int) (Math.random() * 900 + 100));
                    if (existingNumbers.contains(areaCode)) {
                        continue;
                    }
                    existingNumbers.add(areaCode);

                    areaCodes[row][col] = areaCode;

                    break;
                } while (true);
            }
        }
    }

    public String getAreaCode(int row, int col) {
        return areaCodes[row][col];
    }

    public Element getCoordinatesFromAreaCode(String areaCode) {
        for (int row = 0; row < areaCodes.length; row++) {
            for (int col = 0; col < areaCodes[0].length; col++) {
                if (areaCodes[row][col].equals(areaCode)) {
                    return new Element(row, col);
                }
            }
        }
        return null;
    }

    public static String formatRandomNumberFromAreaCode(String areaCode) {
        return "(" + areaCode + ") 555-" + String.valueOf((int) (Math.random() * 9000 + 1000));
    }

    public static class Element {
        private int row;
        private int col;

        public Element(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

}
