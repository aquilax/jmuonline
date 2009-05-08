/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scroller;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author aquilax
 */
public class Scroller extends BasicGame{

  private TiledMap map = null;
  private Hero hero = null;

  final static int screenw = 288;
  final static int screenh = 224;
  final static int centerx = screenw/2;
  final static int centery = screenh/2;
  final static int visx = 9;
  final static int visy = 7;
  final static float halfvisx = visx/2;
  final static float halfvisy = visy/2;

  private int tileW;
  private int tileH;
  private int mapW;
  private int mapH;
  private boolean[][] blocked;
  private int offx;
  private int offy;

  private int heroOffX = 0;
  private int heroOffY = 0;

  public Scroller(){
    super("Scroller2");
  }

  @Override
  public void init(GameContainer container) throws SlickException {
    map = new TiledMap("data/test2.tmx");
    tileW = map.getTileWidth();
    tileH = map.getTileHeight();
    mapW = map.getWidth();
    mapH = map.getHeight();
    fillBlocked(map);
    hero = new Hero("data/ari_charanim.png");
    hero.speed = 1;
    hero.name = "Hero";
    hero.xtile = Integer.parseInt(map.getMapProperty("herox", "16"));
    hero.ytile = Integer.parseInt(map.getMapProperty("heroy", "8"));

    hero.xpos = centerx - hero.awidth/2;
    hero.ypos = centery - hero.aheight/2;

    heroOffX = (tileW-hero.awidth)/2;
    heroOffY = (tileH-hero.aheight)/2;

    offx = hero.xtile*tileW;
    offy = hero.ytile*tileH;
    
    hero.xmap = offx + heroOffX;
    hero.ymap = offx + heroOffY;
  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
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
  }

  public void render(GameContainer container, Graphics g) throws SlickException {
    int shx = (int)(offx/tileW)*tileW - offx;
    int shy = (int)(offy/tileH)*tileH - offy;
    map.render(shx, shy, (int)(offx/tileW-halfvisx), (int)(offy/tileH-halfvisy), visx+1, visy+1);  
    hero.drawActor(g);
//    g.drawString("offx "+offx, 300, 10);
//    g.drawString("offy "+offy, 300, 30);
//    g.drawString("shx "+shx, 300, 50);
//    g.drawString("shy "+shy, 300, 70);
//    g.drawString("shx "+hero.xmap, 300, 90);
//    g.drawString("shy "+hero.ymap, 300, 110);
  }

  public static void main(String[] args) throws SlickException {
    AppGameContainer app = new AppGameContainer(new Scroller());
    app.setTargetFrameRate(120);
    app.setDisplayMode(screenw, screenh, false);
    app.start();
  }

  private void fillBlocked(TiledMap map) {
    blocked = new boolean[mapW][mapH];
    for(int x = 0; x < mapW; x++){
      for (int y = 0; y < mapH; y++){
        blocked[x][y]  = "true".equals(map.getTileProperty(map.getTileId(x, y, 0), "blocked", "false"));
      }
    }
  }

  public boolean walkable(int x, int y){
    return !blocked[x][y];
  }

  private void moveChar(Hero ob, int dirx, int diry) {

    if (ob.astate == 1){
      ob.astate = 2;
    } else {
      ob.astate = 1;
    }

    getMyCorners((float)ob.xmap, ob.ymap+ob.speed*diry, ob);
    if (diry == -1) {
      ob.face = 3;
      if (ob.upleft && ob.upright) {
        offy += ob.speed*diry;
      } else {
        ob.astate = 0;
      }
    }
    if (diry == 1) {
      ob.face = 0;
      if (ob.downleft && ob.downright) {
        offy += ob.speed*diry;
      } else {
        ob.astate = 0;
      }
    }
    getMyCorners(ob.xmap+ob.speed*dirx, (float)ob.ymap, ob);
    if (dirx == -1) {
      ob.face = 9;
      if (ob.downleft && ob.upleft) {
        offx += ob.speed*dirx;
      } else {
        ob.astate = 0;
      }
    }
    if (dirx == 1) {
      ob.face = 6;
      if (ob.upright && ob.downright) {
        offx += ob.speed*dirx;
      } else {
        ob.astate = 0;
      }
    }

    hero.xmap = offx + heroOffX;
    hero.ymap = offy + heroOffY;

//    offx += (int)(dirx*ob.speed);
//    offy += (int)(diry*ob.speed);
  }

  private void getMyCorners(float x, float y, Actor ob) {
    ob.downY = (int) ((y + ob.aheight-1) / tileH);
    ob.upY = (int) (y / tileH);
    ob.leftX = (int) (x / tileW);
    ob.rightX = (int) ((x + ob.awidth-1) / tileW);
    //check if they are walls
    ob.upleft = walkable(ob.leftX, ob.upY);
    ob.downleft = walkable(ob.leftX, ob.downY);
    ob.upright = walkable(ob.rightX, ob.upY);
    ob.downright = walkable(ob.rightX, ob.downY);
  }
}
