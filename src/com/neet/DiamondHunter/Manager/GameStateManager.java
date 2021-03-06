// The GameStateManager does exactly what its
// name says. It contains a list of GameStates.
// It decides which GameState to update() and
// draw() and handles switching between different
// GameStates.

package com.neet.DiamondHunter.Manager;

import java.awt.Graphics2D;

import com.neet.DiamondHunter.GameState.GameOverState;
import com.neet.DiamondHunter.GameState.GameState;
import com.neet.DiamondHunter.GameState.IntroState;
import com.neet.DiamondHunter.GameState.MenuState;
import com.neet.DiamondHunter.GameState.PauseState;
import com.neet.DiamondHunter.GameState.PlayState;
import com.neet.DiamondHunter.GameState.InstructionState; //ZAC: InstructionState imported.


public class GameStateManager {
	
	private boolean paused;
	private PauseState pauseState;
	
	private GameState[] gameStates;
	private int currentState;
	private int previousState;
	
	public static final int NUM_STATES = 5;
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
	public static final int GAMEOVER = 3;
	public static final int INSTRUCTIONS = 4; //ZAC: Added instructions state int.
	
	public GameStateManager() {
		
		JukeBox.init();
		
		paused = false;
		pauseState = new PauseState(this);
		
		gameStates = new GameState[NUM_STATES];
		setState(INTRO);
		
	}
	
	public void setState(int i) {
		if(i < NUM_STATES) 
		{
			previousState = currentState;
			unloadState(previousState);
			currentState = i;
			if(i == INTRO) {
				gameStates[i] = new IntroState(this);
				gameStates[i].init();
			}
			else if(i == MENU) {
				gameStates[i] = new MenuState(this);
				gameStates[i].init();
			}
			else if(i == PLAY) {
				gameStates[i] = new PlayState(this);
				gameStates[i].init();
			}
			else if(i == GAMEOVER) {
				gameStates[i] = new GameOverState(this);
				gameStates[i].init();
			}
			else if(i == INSTRUCTIONS)
			{
				//ZAC: Added instructions setState. Not implemented yet.
				gameStates[i] = new InstructionState(this);
				gameStates[i].init();
			}
		}
	}
	
	/*
	 * If you try to give `setState` an invalid state this will return an error -- Wezley
	 */
	public void unloadState(int i) {
		if(i < NUM_STATES)
			gameStates[i] = null;
	}
	
	public void setPaused(boolean b) {
		paused = b;
	}
	
	public void update() {
		if(paused) {
			pauseState.update();
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	public void draw(Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}
	
	public int getState() { return currentState; }
	public boolean isPaused() { return paused; }
}
