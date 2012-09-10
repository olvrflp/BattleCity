package game;

public class Explosion {
  private int left, top, level;
  public Explosion() {
    setLevel (1);
    setCoords (192, 384);
  }

  public Explosion (int x, int y) {
    setLevel (1);
    setCoords (x, y);
  }

  public void setLevel (int l) { level = l; }
  public void setCoords (int x, int y) { left = x; top = y; }
  public int  getX () { return left; }
  public int  getY () { return top;  }

  public int[] getCoords () {
    int[] coords = new int[2];
    coords[0] = left;
    coords[1] = top;
    return coords;
  }

  public int getLevel () { return level; }
}