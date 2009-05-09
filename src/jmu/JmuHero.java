/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jmu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;

/**
 *
 * @author aquilax
 */
class JmuHero extends JmuActor{

  JmuHero(String string) throws SlickException{
    super(string);
    //Init Name
    name = "Hero";
  }

  @Override
  public void drawActor(org.newdawn.slick.Graphics g) {
    setCurrentFrame(face+astate);
    draw(xpos, ypos);
 
    //TODO: This should be optimized
    if (nameXOffset == -1){
      Font font = g.getFont();
      nameWidth = font.getWidth(name);
      nameHeight = font.getHeight(name);
      nameXOffset = (awidth/2) - nameWidth/2;
    }
    g.setColor(Color.black);
    g.fillRoundRect(xpos + nameXOffset-1, ypos-16, nameWidth+2, nameHeight, 2);
    g.setColor(Color.red);
    g.drawString(name, xpos + nameXOffset, ypos-18);
  }

}
