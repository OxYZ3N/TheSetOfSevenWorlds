package com.oxyzen.thesetofsevenworlds;

public class Candy {
    public static final int RED_SUGAR = 0;
    public static final int GREEN_SUGAR = 1;
    public static final int YELLOW_STAR = 2;
    public static final int YELLOW_DARK_PLANET = 3;
    public static final int PINK_DONUTS = 4;
    public static final int YELLOW_STAR_5 = 5;
    public static final int RED_SUGAR_6 = 6;
    public static final int BLUE_SUGAR = 7;
    
    public int x, y;
    
    public int type;

    public Candy(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
