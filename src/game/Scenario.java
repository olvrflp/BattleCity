package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.applet.*;
import java.net.*;

public class Scenario {

  private ArrayList< ArrayList< Block>> board = new ArrayList< ArrayList< Block>>();
  private ArrayList< BufferedImage> terrainSprites = new ArrayList< BufferedImage>();
  private ArrayList< BufferedImage> bulletSprites = new ArrayList< BufferedImage>();
  private ArrayList< BufferedImage> tankSprites = new ArrayList< BufferedImage>();
  private ArrayList< BufferedImage> flagSprites = new ArrayList< BufferedImage>();
  private BufferedImage boardBackground;
  private int boardSize = 13;
  private int cellSize = Sprites.SMALL_SIZE;
  private boolean emptyBlocks[][];
  private Sprites spriteMap;

  public Scenario() {
    this.emptyBlocks = new boolean[boardSize][boardSize];
    /**
     * *****************************
     * Generando cache de imagenes * ****************************
     */
    // Tablero
    this.boardBackground = loadImage("sprites/board_bg.png");
    // Terreno
    for (int i = 0; i < 6; i++) {
      this.terrainSprites.add(loadImage("sprites/block_" + i + ".gif"));
    }
    // Proyectiles
    for (int i = 0; i < 4; i++) {
      this.bulletSprites.add(loadImage("sprites/b_" + (i + 37) + ".gif"));
    }
    // Tanques
    for (int i = 0; i < 4; i++) {
      this.tankSprites.add(loadImage("sprites/t1_" + (i + 37) + "0.gif"));
    }
    // Bandera
    for (int i = 0; i < 1; i++) {
      this.flagSprites.add(loadImage("sprites/flag_" + i + ".gif"));
    }
    this.spriteMap = new Sprites(this.loadImage("sprites/sprites.png"));
  }

  public void startScenario(int percentOfBlocks[]) {
    this.genInitialData(percentOfBlocks);
    this.fillInitialData();
  }

  public void genInitialData(int percentOfBlocks[]) {
    for (int i = 0; i < boardSize; i++) {
      board.add(new ArrayList< Block>());
      for (int j = 0; j < boardSize; j++) {
        boolean canDraw = true;
        canDraw = (j == 0) ? false : canDraw;
        canDraw = ((i == 4 || i == 6 || i == 8) && j == 12) ? false : canDraw;
        emptyBlocks[i][j] = canDraw;
        board.get(i).add(new Block(i, j, Block.EMPTY));
      }
    }

    int availableTypes[] = {
      Block.BRICK, Block.BLOCK, Block.TREE, Block.SNOW, Block.WATER
    };

    for (int i = 0; i < availableTypes.length; i++) {
      for (int j = 0; j < percentOfBlocks[i]; j++) {
        int posX = (int) (Math.random() * 13);
        int posY = (int) (Math.random() * 13);
        while (!emptyBlocks[posX][posY]) {
          posX = (int) (Math.random() * 13);
          posY = (int) (Math.random() * 13);
        }
        Block b = new Block(posX, posY, availableTypes[i]);
        b.setData(true, true, true, true);
        this.board.get(posX).set(posY, b);
        emptyBlocks[posX][posY] = false;
      }
    }
  }

  public void fillInitialData() {
    // Dibujamos el arco de la bandera con la configuracion por defecto
    setBlock(5, 12, new Block(5, 12, Block.BRICK, new boolean[][]{
              {
                false, false
              }, {
                true, true
              }
            }));
    setBlock(5, 11, new Block(5, 11, Block.BRICK, new boolean[][]{
              {
                false, false
              }, {
                false, true
              }
            }));
    setBlock(6, 11, new Block(6, 11, Block.BRICK, new boolean[][]{
              {
                false, true
              }, {
                false, true
              }
            }));
    setBlock(7, 11, new Block(7, 11, Block.BRICK, new boolean[][]{
              {
                false, true
              }, {
                false, false
              }
            }));
    setBlock(7, 12, new Block(7, 12, Block.BRICK, new boolean[][]{
              {
                true, true
              }, {
                false, false
              }
            }));
  }

  public Block getBlock(int x, int y) {
    return this.board.get(x).get(y);
  }

  public void setBlock(int x, int y, Block b) {
    this.board.get(x).set(y, b);
  }

  public BufferedImage loadImage(String filename) {
    java.net.URL imageURL = getClass().getResource(filename);
    BufferedImage img2load = null;
    try {
      img2load = ImageIO.read(imageURL);
      return img2load;
    } catch (IOException e) {
    }
    return null;
  }

  public ArrayList< ArrayList< Block>> getBoard() {
    return this.board;
  }

  public BufferedImage getBoardBg() {
    return this.boardBackground;
  }

  public BufferedImage getImage(Object o) {
    // Sprites de tanques
    if (Tank.validate(o)) {
      Tank t = (Tank) o;
      int col = (t.getDirection() - 37) * 2 + (t.getTrack() ? 1 : 0);
      int row = t.getRank() + 2 + (t.getType() * 4);
      return this.spriteMap.get(col, row, Sprites.NORMAL_SIZE);
    }
    // Sprites de proyectiles
    if (Bullet.validate(o)) {
      Bullet b = (Bullet) o;
      BufferedImage returnImg = null;
      switch (b.getDirection()) {
        case Bullet.WEST:
          returnImg = this.spriteMap.get(7, 1, Sprites.SMALL_SIZE);
          break;
        case Bullet.EAST:
          returnImg = this.spriteMap.get(7, 0, Sprites.SMALL_SIZE);
          break;
        case Bullet.NORTH:
          returnImg = this.spriteMap.get(6, 0, Sprites.SMALL_SIZE);
          break;
        case Bullet.SOUTH:
          returnImg = this.spriteMap.get(6, 1, Sprites.SMALL_SIZE);
          break;
      }
      return returnImg;
    }
    // Sprites de terreno
    if (Block.validate(o)) {
      Block b = (Block) o;
      return this.terrainSprites.get(b.getType());
    }
    // Sprites de bandera
    if (GameFlag.validate(o)) {
      GameFlag f = (GameFlag) o;
      return this.spriteMap.get(f.getStatus() ? 0 : 1, 10, Sprites.NORMAL_SIZE);
    }
    return null;
  }

  @Override
  public String toString() {
    String output = "";
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 13; j++) {
        output += "[" + this.getBlock(j, i).getType() + "]";
      }
      output += "\n";
    }
    return output;
  }
}
