/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scroller;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;

/**
 *
 * @author aquilax
 */
class Hero extends Actor{

  Hero(String string) throws SlickException{
    super(string);
  }

  @Override
  public void drawActor(org.newdawn.slick.Graphics g) {
    setCurrentFrame(face+astate);
    draw(xpos, ypos);
    g.setColor(Color.red);
    
    //TODO: This should be optimized
    if (nameXOffset == -1){
      Font font = g.getFont();
      nameXOffset = (awidth/2) - font.getWidth(name)/2;
    }

    g.drawString(name, xpos + nameXOffset, ypos-16);
  }

}
