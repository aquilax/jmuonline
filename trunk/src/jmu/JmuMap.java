/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jmu;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;

/**
 *
 * @author aquilax
 */
public class JmuMap extends TiledMap implements TileBasedMap{

  public int tileW;
  public int tileH;
  public int mapW;
  public int mapH;

  public boolean[][] blocked;

  public JmuMap(String ref) throws SlickException{
    super(ref);

    tileW = getTileWidth();
    tileH = getTileHeight();
    mapW = getWidth();
    mapH = getHeight();

    fillBlocked();
  }

  public int getWidthInTiles() {
    return tileW;
  }

  public int getHeightInTiles() {
    return tileH;
  }

  public void pathFinderVisited(int x, int y) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public boolean blocked(PathFindingContext context, int tx, int ty) {
    return !walkable(tx, ty);
  }

  public float getCost(PathFindingContext context, int tx, int ty) {
    return 1f;
  }

  private void fillBlocked() {
    blocked = new boolean[mapW][mapH];
    for (int x = 0; x < mapW; x++) {
      for (int y = 0; y < mapH; y++) {
        blocked[x][y] = "true".equals(getTileProperty(getTileId(x, y, 0), "blocked", "false"));
      }
    }
  }

  public boolean walkable(int x, int y) {
    return !blocked[x][y];
  }

  public void getCorners(float x, float y, JmuActor ob) {
    ob.downY = (int) ((y + ob.aheight - 1) / tileH);
    ob.upY = (int) (y / tileH);
    ob.leftX = (int) (x / tileW);
    ob.rightX = (int) ((x + ob.awidth - 1) / tileW);
    //check if they are walls
    ob.upleft = walkable(ob.leftX, ob.upY);
    ob.downleft = walkable(ob.leftX, ob.downY);
    ob.upright = walkable(ob.rightX, ob.upY);
    ob.downright = walkable(ob.rightX, ob.downY);
  }

}
