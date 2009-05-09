/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jmu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;

/**
 *
 * @author aquilax
 */
public class JmuGameplayState extends BasicGameState{

  private int stateID = -1;

  private JmuMap map = null;
  private JmuHero hero = null;
  private int screenW;
  private int screenH;

  private int centerX;
  private int centerY;

  private int offx;
  private int offy;

  final static int visx = 9;
  final static int visy = 7;

  final static float halfvisx = visx/2;
  final static float halfvisy = visy/2;


  public JmuGameplayState(int GAMEPLAYSTATE) {
    stateID = GAMEPLAYSTATE;
  }

  @Override
  public int getID() {
    return stateID;
  }

  public void init(GameContainer container, StateBasedGame game) throws SlickException {
    map = new JmuMap("data/test2.tmx");
    hero = new JmuHero("data/ari_charanim.png");

    hero.xtile = Integer.parseInt(map.getMapProperty("herox", "16"));
    hero.ytile = Integer.parseInt(map.getMapProperty("heroy", "8"));

    //This doesn't work and returns the screen size
    screenW = container.getWidth();
    screenH = container.getHeight();

    //Bypass it and hardcode the values
    screenW = 288;
    screenH = 224;

    centerX = screenW/2;
    centerY = screenH/2;

    hero.xpos = centerX - hero.awidth / 2;
    hero.ypos = centerY - hero.aheight / 2;

    hero.OffX = (map.tileW - hero.awidth) / 2;
    hero.OffY = (map.tileH - hero.aheight) / 2;

    offx = hero.xtile * map.tileW;
    offy = hero.ytile * map.tileH;

    hero.xmap = offx + hero.OffX;
    hero.ymap = offx + hero.OffY;

  }

  public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    int shx = (int) (offx / map.tileW) * map.tileW - offx;
    int shy = (int) (offy / map.tileH) * map.tileH - offy;
    map.render(shx, shy, (int) (offx / map.tileW - halfvisx), (int) (offy / map.tileH - halfvisy), visx + 1, visy + 1);
    hero.drawActor(g);
  }

  public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    Input input = container.getInput();
    if (input.isKeyDown(Input.KEY_LEFT)) {
      moveChar(hero, -1, 0);
    }
    if (input.isKeyDown(Input.KEY_RIGHT)) {
      moveChar(hero, +1, 0);
    }
    if (input.isKeyDown(Input.KEY_UP)) {
      moveChar(hero, 0, -1);
    }
    if (input.isKeyDown(Input.KEY_DOWN)) {
      moveChar(hero, 0, +1);
    }
    if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
      //targetX = input.getMouseX();
      //targetY = input.getMouseY();
      //System.out.println("x:" + targetX + "\ty:" + targetY);
    }
  }

  private void moveChar(JmuActor ob, int dirx, int diry) {

    if (ob.astate == 1) {
      ob.astate = 2;
    } else {
      ob.astate = 1;
    }

    map.getCorners((float) ob.xmap, ob.ymap + ob.speed * diry, ob);
    if (diry == -1) {
      ob.face = 3;
      if (ob.upleft && ob.upright) {
        offy += ob.speed * diry;
      } else {
        ob.astate = 0;
      }
    }
    if (diry == 1) {
      ob.face = 0;
      if (ob.downleft && ob.downright) {
        offy += ob.speed * diry;
      } else {
        ob.astate = 0;
      }
    }
    map.getCorners(ob.xmap + ob.speed * dirx, (float) ob.ymap, ob);
    if (dirx == -1) {
      ob.face = 9;
      if (ob.downleft && ob.upleft) {
        offx += ob.speed * dirx;
      } else {
        ob.astate = 0;
      }
    }
    if (dirx == 1) {
      ob.face = 6;
      if (ob.upright && ob.downright) {
        offx += ob.speed * dirx;
      } else {
        ob.astate = 0;
      }
    }

    ob.xmap = offx + hero.OffX;
    ob.ymap = offy + hero.OffY;
// Free move
//    offx += (int)(dirx*ob.speed);
//    offy += (int)(diry*ob.speed);
  }


}
