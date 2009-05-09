/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;

/**
 *
 * @author aquilax
 */
public class JmuGame extends StateBasedGame {

  public static final int GAMEPLAYSTATE = 1;


  final static int screenw = 288;
  final static int screenh = 224;

  public JmuGame() {
    super("Jmu");

    this.addState(new JmuGameplayState(GAMEPLAYSTATE));
  }

  @Override
  public void initStatesList(GameContainer container) throws SlickException {
    this.getState(GAMEPLAYSTATE).init(container, this);
  }

  public static void main(String[] args) throws SlickException {
    AppGameContainer app = new AppGameContainer(new JmuGame());
    app.setTargetFrameRate(120);
    app.setDisplayMode(screenw, screenh, false);
    app.start();
  }
}
