package game;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Sprites {
  public static final int SMALL_SIZE = 16;
  public static final int NORMAL_SIZE = 32;
  private BufferedImage spriteMap;

  public Sprites( BufferedImage i ) {
    this.spriteMap = i;
  }
  
  public BufferedImage get ( int col, int row, int size ) {
    BufferedImage returnImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    returnImage.getGraphics().drawImage(this.spriteMap, 0, 0, size, size, col * size, row * size, ( col * size ) + size, ( row * size ) + size, null);
    return returnImage;
  }
}
