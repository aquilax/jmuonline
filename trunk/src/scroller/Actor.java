/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scroller;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author aquilax
 */
public class Actor extends Animation{
  public int xpos = 0;
  public int ypos = 0;
  public int xtile = 0;
  public int ytile = 0;
  public float speed = 1f;
  public int awidth = 0;
  public int aheight = 0;
  public int xmap = 0;
  public int ymap = 0;

  public boolean upleft = false;
  public boolean upright = false;
  public boolean downleft = false;
  public boolean downright = false;

  public int downY = 0;
  public int upY = 0;
  public int leftX = 0;
  public int rightX = 0;

  public int face = 0;
  public int astate = 0;

  Actor(String string) throws SlickException{
    super(new SpriteSheet(string, 32, 60), 5);
    setAutoUpdate(false);
    awidth = getWidth();
    aheight = getHeight();
  }
}
