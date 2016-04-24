package com.oxyzen.thesetofsevenworlds;

import java.util.List;

import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Screen;
import com.oxyzen.framework.Input.TouchEvent;

public class AboutScreen2 extends Screen {

	public AboutScreen2(Game game) 
	{
		super(game);
	}

	@Override
	public void update(float deltaTime) 
	{
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new AboutScreen(game));
                    return;
                }
            }
        }
	}

	@Override
	public void present(float deltaTime) 
	{
        Graphics g = game.getGraphics();      
        g.drawPixmap(Assets.background, 0, 0);
        //g.drawPixmap(Assets.help1, 64, 100);
        g.drawPixmap(Assets.buttonGaucheDroite, 0, 416, 64, 0, 65, 65);
        //g.drawPixmap(Assets.buttonGaucheDroite, 253, 416, 0, 0, 65, 65);
	}
	
	@Override
	public void pause() 
	{
	}

	@Override
	public void resume() 
	{
	}

	@Override
	public void dispose() 
	{
	}

}
