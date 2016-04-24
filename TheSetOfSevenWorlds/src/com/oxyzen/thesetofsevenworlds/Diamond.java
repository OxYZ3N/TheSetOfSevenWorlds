package com.oxyzen.thesetofsevenworlds;

public class Diamond {
    public static final int PURPLE = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    public static final int RED = 3;
    public static final int PINK = 4;
    
    public int x, y;
    
    public int type;

    public Diamond(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
