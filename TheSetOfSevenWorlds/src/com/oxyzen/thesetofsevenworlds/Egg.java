package com.oxyzen.thesetofsevenworlds;

public class Egg {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;
    public static final int TYPE_5 = 4;
    public static final int TYPE_6 = 5;
    public static final int TYPE_7 = 6;
    public static final int TYPE_8 = 7;
    public static final int TYPE_9 = 8;
    public int x, y;
    public int type;

    public Egg(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
