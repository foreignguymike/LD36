package com.distraction.ld36.game;

public class ManualUtils {

    public static String formatRandomNumber(String areaCode) {
        return  "(" + areaCode + ") 555-" + String.valueOf((int) (Math.random() * 9000 + 1000));
    }

}
