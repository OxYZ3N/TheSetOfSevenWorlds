package com.oxyzen.thesetofsevenworlds;

import android.os.CountDownTimer;

public class MyCounter extends CountDownTimer{

    public MyCounter(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
    	GameScreen.timer = false;	
    }

    @Override
    public void onTick(long millisUntilFinished) {
        
    }
}
