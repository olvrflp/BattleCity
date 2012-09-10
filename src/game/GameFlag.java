package game;

public class GameFlag {
  private boolean status;
  private int left, top;
  public GameFlag () {
    setStatus (true);
    setCoords (6, 12);
  }

  public GameFlag (int x, int y) {
    setStatus (true);
    setCoords (x, y);
  }

  public void setCoords (int x, int y) { left = x; top = y; }
  public void setStatus (boolean s) { status = s; }
  public int  getX () { return left; }
  public int  getY () { return top;  }

  public int[] getCoords () {
    int[] coords = new int[2];
    coords[0] = left;
    coords[1] = top;
    return coords;
  }

  public boolean getStatus () { return status; }
  public static boolean validate(Object o){ return o instanceof GameFlag; }
}