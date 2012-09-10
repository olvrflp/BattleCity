package game;

public class Bullet {
  public static final boolean FLYING = true;
  public static final boolean QUIET = false;
  public static final int NORTH = java.awt.event.KeyEvent.VK_UP;
  public static final int SOUTH = java.awt.event.KeyEvent.VK_DOWN;
  public static final int WEST  = java.awt.event.KeyEvent.VK_LEFT;
  public static final int EAST  = java.awt.event.KeyEvent.VK_RIGHT;

  private boolean flying;
  private int left, top, direction;
  private Explosion e;

  public Bullet (int x, int y, int d) {
    flying = QUIET;
    left = x;
    top = y;
    direction = d;
    e = new Explosion (x, y);
  }

  public void setCoords (int x, int y) { left = x; top = y; }
  public void setStatus (boolean f) { flying = f; }
  public void setDirection (int d) { direction = d; }
  public int  getDirection () { return direction; }
  public int  getX () { return left; }
  public int  getY () { return top;  }
  public int  getFixedX () { return (int) Math.round(left / 32); }
  public int  getFixedY () { return (int) Math.round(top / 32);  }

  public int[] getCoords () {
    int[] coords = new int[2];
    coords[0] = left;
    coords[1] = top;
    return coords;
  }

  public boolean getStatus() { return flying; }
  public static boolean validate(Object o){ return o instanceof Bullet; }
}
