/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scroller;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author aquilax
 */
public class Scroller extends BasicGame {

  private TiledMap map = null;
  private Hero hero = null;

  int tileW = 0;
  int tileH = 0;

  int visx = 9;
  int visy = 7;

  int centerx = 216;
  int centery = 168;

  float offx = 0;
  float offy = 0;

  int halfvisx = (int)(visx/2);
  int halfvisy = (int)(visy/2);

  int mapW = 0;
  int mapH = 0;

  boolean[][] blocked;

  public Scroller() {
    super("Scroller");
  }

  @Override
  public void init(GameContainer container) throws SlickException {
    map = new TiledMap("data/testone.tmx");
    tileW = map.getTileWidth();
    tileH = map.getTileHeight();
    mapW = map.getWidth();
    mapH = map.getHeight();
    fillBlocked(map);
    hero = new Hero("data/hero.png");
    hero.xtile = Integer.parseInt(map.getMapProperty("herox", "16"));
    hero.ytile = Integer.parseInt(map.getMapProperty("heroy", "8"));
    hero.xpos = centerx - hero.awidth/2;
    hero.ypos = centery - hero.aheight/2;
    hero.speed = 0.008f;
    offx = hero.xtile;
    offy = hero.ytile;
    hero.xmap = (int)((offx*tileW));
    hero.ymap = (int)((offy*tileH));
  }

  public boolean walkable(int x, int y){
    return !blocked[x][y];
  }

  private void fillBlocked(TiledMap map) {
    blocked = new boolean[mapW][mapH];
    for(int x = 0; x < mapW; x++){
      for (int y = 0; y < mapH; y++){
        blocked[x][y]  = "true".equals(map.getTileProperty(map.getTileId(x, y, 0), "blocked", "false"));
      }
    }
  }

  private void getMyCorners(float x, float y, Actor ob) {
    ob.downY = (int) ((y + ob.aheight-1) / tileH);
    ob.upY = (int) (y / tileH);
    ob.leftX = (int) (x / tileW);
    ob.rightX = (int) ((x + ob.awidth-1) / tileW);
    //check if they are walls
    ob.upleft = walkable(ob.upY, ob.leftX);
    ob.downleft = walkable(ob.downY, ob.leftX);
    ob.upright = walkable(ob.upY, ob.rightX);
    ob.downright = walkable(ob.downY, ob.rightX);
  }

  //TODO: Fix this, make the map move instead of the character
  private void moveChar(Actor ob, int dirx, int diry){
//    getMyCorners((float)ob.xmap, ob.ymap+ob.speed*diry, ob);
//    if (diry == -1) {
//      if (ob.upleft && ob.upright) {
//        offy += ob.speed*diry;
//      } else {
//        offy = (ob.ytile);
//      }
//    }
//    if (diry == 1) {
//      if (ob.downleft && ob.downright) {
//        offy += ob.speed*diry;
//      } else {
//        offy = (ob.ytile);
//      }
//    }
//    getMyCorners(ob.xmap+ob.speed*dirx, (float)ob.ymap, ob);
//    if (dirx == -1) {
//      if (ob.downleft && ob.upleft) {
//        offx += ob.speed*dirx;
//      } else {
//        offx = ob.xtile;
//      }
//    }
//    if (dirx == 1) {
//      if (ob.upright && ob.downright) {
//        offx += ob.speed*dirx;
//      } else {
//        offx = ob.xtile;
//      }
//    }

    offx += (dirx*ob.speed);
    offy += (diry*ob.speed);
    ob.xtile = (int)offx;
    ob.ytile = (int)offy;
    ob.xmap = (int)((offx*tileW)+ob.awidth/2);
    ob.ymap = (int)((offy*tileH)+ob.aheight/2);
  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    Input input = container.getInput();
    if (input.isKeyDown(Input.KEY_LEFT)) {
      moveChar(hero, -delta, 0);
    }
    if (input.isKeyDown(Input.KEY_RIGHT)) {
      moveChar(hero, +delta, 0);
    }
    if (input.isKeyDown(Input.KEY_UP)) {
      moveChar(hero, 0, -delta);
    }
    if (input.isKeyDown(Input.KEY_DOWN)) {
      moveChar(hero, 0, +delta);
    }
  }

  public void render(GameContainer container, Graphics g) throws SlickException {
    int shx = (int)(((int)offx-offx)*tileW);
    int shy = (int)(((int)offy-offy)*tileH);
    map.render(shx, shy, (int)offx-(visx/2), (int)offy-(visy/2), visx+1, visy+1);
    //g.translate((halfvisx*tileW) - offx, (halfvisy*tileH) *offy);
    hero.draw(hero.xpos, hero.ypos);
    g.setColor(Color.red);
    g.drawString("offx "+offx, 300, 10);
    g.drawString("offy "+offy, 300, 30);
    g.drawString("tilex "+hero.xtile, 300, 50);
    g.drawString("tiley "+hero.ytile, 300, 70);
    g.drawString("mapx "+hero.xmap, 300, 90);
    g.drawString("mapy "+hero.ymap, 300, 110);
    if (walkable(hero.xtile, hero.ytile)){
      g.drawString("walkable", 300, 130);
    } else {
      g.drawString("blocked", 300, 130);
    }
  }

  public static void main(String[] args) throws SlickException {
    AppGameContainer app = new AppGameContainer(new Scroller());
    app.setDisplayMode(432, 336, false);
    app.start();
  }
}