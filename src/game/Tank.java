package game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class Tank {

  private int left, top, type, rank, status, direction, lastDirection;
  public Bullet bullet;
  public static final int NORTH = java.awt.event.KeyEvent.VK_UP;
  public static final int SOUTH = java.awt.event.KeyEvent.VK_DOWN;
  public static final int WEST = java.awt.event.KeyEvent.VK_LEFT;
  public static final int EAST = java.awt.event.KeyEvent.VK_RIGHT;
  public static final int SHOOT = java.awt.event.KeyEvent.VK_SPACE;
  private boolean track = false;
  private Timer timer;
  private ActionListener bulletAnimation, moving;

  public Tank() {
    lastDirection = 0;
    direction = NORTH;
    type = 0;
    rank = 0;
    left = 0;
    top = 0;
    status = 1;
    bullet = new Bullet(0, 0, NORTH);
    animateTrack();
  }

  public Tank(int x, int y) {
    lastDirection = 0;
    direction = NORTH;
    type = 0;
    rank = 0;
    left = x;
    top = y;
    status = 1;
    bullet = new Bullet(x + 13, y, NORTH);
    animateTrack();
  }

  public Tank(int x, int y, int t) {
    lastDirection = 0;
    direction = NORTH;
    type = t;
    rank = 0;
    left = x;
    top = y;
    status = 1;
    bullet = new Bullet(x + 13, y, NORTH);
    animateTrack();
  }

  public Tank(int x, int y, int t, int r) {
    lastDirection = 0;
    direction = NORTH;
    type = t;
    rank = r;
    left = x;
    top = y;
    status = 1;
    bullet = new Bullet(x + 13, y, NORTH);
    animateTrack();
  }

  public Tank(int x, int y, int t, int r, int s) {
    lastDirection = 0;
    direction = NORTH;
    type = t;
    rank = r;
    left = x;
    top = y;
    status = s;
    bullet = new Bullet(x + 13, y, NORTH);
    animateTrack();
  }
  
  public final void animateTrack () {
    Timer t = new Timer (50, 
      new ActionListener () {
        @Override public void actionPerformed(ActionEvent e) {
          track = !track;
        }
      }
    );
    t.start();
  }

  public void moveTank(int d, final ArrayList<ArrayList<Block>> b) {
    setDirection(d);
    if (lastDirection == d) {
      int nextX = left;
      int nextY = top;
      switch (d) {
        case WEST:
          nextX = left - 8;
          break;
        case EAST:
          nextX = left + 8;
          break;
        case NORTH:
          nextY = top - 8;
          break;
        case SOUTH:
          nextY = top + 8;
          break;
      }
      // Colisiona con el borde del Canvas
      nextX = (nextX > 384) ? 384 : nextX;
      nextX = (nextX < 0) ? 0 : nextX;
      nextY = (nextY > 384) ? 384 : nextY;
      nextY = (nextY < 0) ? 0 : nextY;
      // Colisiona con un elemento del tablero
      boolean pA = false;
      boolean pB = false;
      switch (d) {
        case WEST:
          pA = b.get(getFixedV(nextX + 1)).get(getFixedV(nextY + 1)).isPassable();
          pB = b.get(getFixedV(nextX + 1)).get(getFixedV(nextY + 31)).isPassable();
          break;
        case EAST:
          pA = b.get(getFixedV(nextX + 31)).get(getFixedV(nextY + 1)).isPassable();
          pB = b.get(getFixedV(nextX + 31)).get(getFixedV(nextY + 31)).isPassable();
          break;
        case NORTH:
          pA = b.get(getFixedV(nextX + 1)).get(getFixedV(nextY + 1)).isPassable();
          pB = b.get(getFixedV(nextX + 31)).get(getFixedV(nextY + 1)).isPassable();
          break;
        case SOUTH:
          pA = b.get(getFixedV(nextX + 1)).get(getFixedV(nextY + 31)).isPassable();
          pB = b.get(getFixedV(nextX + 31)).get(getFixedV(nextY + 31)).isPassable();
          break;
      }
      if (pA && pB) {
        left = nextX;
        top = nextY;
      }
    }
    if (lastDirection != d) {
      fixSquare();
    }
    lastDirection = d;
  }

  public void fire(final ArrayList<ArrayList<Block>> b) {
    bulletAnimation = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        int bulletSpeed = 1;
        int nextX = bullet.getX(), nextY = bullet.getY();
        switch (bullet.getDirection()) {
          case WEST:
            nextX = bullet.getX() - bulletSpeed;
            nextY = bullet.getY();
            break;
          case EAST:
            nextX = bullet.getX() + bulletSpeed + 16;
            nextY = bullet.getY();
            break;
          case NORTH:
            nextY = bullet.getY() - bulletSpeed;
            nextX = bullet.getX();
            break;
          case SOUTH:
            nextY = bullet.getY() + bulletSpeed + 16;
            nextX = bullet.getX();
            break;
        }
        // Colisiona con el borde del Canvas
        if (nextX < 0 || nextX > 416 || nextY < 0 || nextY > 416) {
          timer.stop();
          bullet.setStatus(false);
        } else if (b.get(bullet.getFixedX()).get(bullet.getFixedY()).isCollisionable()) {
          // Colisiona con un bloque
          timer.stop();
          bullet.setStatus(false);
        } else {
          switch (bullet.getDirection()) {
            case EAST:
              nextX -= 16;
              break;
            case SOUTH:
              nextY -= 16;
              break;
          }
          bullet.setCoords(nextX, nextY);
        }
      }
    };
    if (bullet.getStatus() != true) {
      if (timer != null) {
        timer.stop();
      }
      timer = null;
      timer = new Timer(1, bulletAnimation);
      bullet.setDirection(direction);
      switch (direction) {
        case WEST:
          bullet.setCoords(left + 16, top + 8);
          break;
        case EAST:
          bullet.setCoords(left, top + 8);
          break;
        case NORTH:
          bullet.setCoords(left + 8, top);
          break;
        case SOUTH:
          bullet.setCoords(left + 8, top + 16);
          break;
      }
      bullet.setStatus(true);
      timer.start();
    }
  }

  public void setDirection(int d) {
    direction = d;
  }

  public void setType(int t) {
    type = t;
  }

  public void setRank(int r) {
    rank = r;
  }

  public void setCoords(int x, int y) {
    left = x;
    top = y;
  }

  public void setStatus(int s) {
    status = s;
  }

  public int getType() {
    return type;
  }

  public int getRank() {
    return rank;
  }

  public int getDirection() {
    return direction;
  }

  public int getX() {
    return left;
  }

  public int getY() {
    return top;
  }

  public int getFixedX() {
    return (int) Math.round(left / 32);
  }

  public int getFixedY() {
    return (int) Math.round(top / 32);
  }

  public int getFixedV(int v) {
    return Math.max((int) Math.round(v / 32), 0);
  }

  public int[] getCoords() {
    int[] coords = new int[2];
    coords[0] = left;
    coords[1] = top;
    return coords;
  }

  public static boolean validate(Object o) {
    return (o instanceof Tank);
  }

  public int getStatus() {
    return status;
  }

  public int getSquare(int x) {
    return x * 32;
  }

  public void fixSquare() {
    //setCoords((int) Math.round((left + 16) / 32) * 32, (int) Math.round((top + 16) / 32) * 32);
  }

  public boolean getTrack() {
    return this.track;
  }
}