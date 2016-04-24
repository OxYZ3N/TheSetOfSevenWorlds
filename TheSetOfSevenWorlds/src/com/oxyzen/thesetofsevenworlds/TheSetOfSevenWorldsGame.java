package com.oxyzen.thesetofsevenworlds;

import com.oxyzen.framework.Screen;
import com.oxyzen.framework.impl.AndroidGame;

public class TheSetOfSevenWorldsGame extends AndroidGame 
{
	public Screen getStartScreen()
	{
		return new LoadingScreen(this);
	}
}
