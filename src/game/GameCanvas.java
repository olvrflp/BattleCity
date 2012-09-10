package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.util.List;
import java.io.*;
import java.util.ArrayList;

public class GameCanvas extends Canvas {
  private GameFlag flag = new GameFlag();
  private Tank simOne = new Tank(4 * Sprites.NORMAL_SIZE, 12 * Sprites.NORMAL_SIZE, 0);
  private Tank simTwo = new Tank(8 * Sprites.NORMAL_SIZE, 12 * Sprites.NORMAL_SIZE, 1);
  private Scenario scenario = new Scenario();
  private ActionListener actionListenerList = null;
  private SoundEffects sounds = new SoundEffects();
  private Timer bufferTimer = null;

  public GameCanvas() {
    setBackground(Color.gray);
    setIgnoreRepaint(true);
  }
  
  public void generateEnvironment (int percentOfBlocks[]) {
    scenario.startScenario( percentOfBlocks );
    simOne.setRank(3);
    simTwo.setRank(2);
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent keyEvent) {
        if (actionListenerList != null) {
          int key = keyEvent.getKeyCode();
          switch (key) {
            case Tank.WEST:
            case Tank.EAST:
            case Tank.NORTH:
            case Tank.SOUTH:
              simOne.moveTank(key, scenario.getBoard()); 
              break;
            case Tank.SHOOT:
              if (!simOne.bullet.getStatus())
                simOne.fire(scenario.getBoard());
              break;
          }
          actionListenerList.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, KeyEvent.getKeyText(key)));
        }
      }
      public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key) {
          case Tank.WEST:
          case Tank.EAST:
          case Tank.NORTH:
          case Tank.SOUTH: 
        }
      }
    });
    this.emulateBuffering();
  }
  
  public void emulateBuffering () {
    this.createBufferStrategy(2);
    final BufferStrategy bufferStrategy = this.getBufferStrategy();
    if (bufferTimer instanceof Timer) bufferTimer.stop();
    bufferTimer = null;
    bufferTimer = new Timer (1, 
      new ActionListener () {
        public void actionPerformed(ActionEvent e) {
          canvasUpdate(bufferStrategy.getDrawGraphics());
          bufferStrategy.show();
        }
      }
    );
    bufferTimer.start();
  }

  public void paint (Graphics g) {
  }
  
  public void canvasUpdate (Graphics g) {
    BufferedImage bgBuffer = new BufferedImage(416, 416, BufferedImage.TYPE_INT_ARGB);
    bgBuffer = drawScenario(bgBuffer);
    g.drawImage(bgBuffer, 0, 0, this);
    g.dispose();
  }

  public void addActionListener(ActionListener actionListener) {actionListenerList = AWTEventMulticaster.add(actionListenerList, actionListener);}
  public void removeActionListener(ActionListener actionListener) {actionListenerList = AWTEventMulticaster.remove(actionListenerList, actionListener);}
  public boolean isFocusTraversable() { return true; }
  public Scenario getScenario() { return this.scenario; }

  public BufferedImage drawTanks(BufferedImage i) {
    Graphics g = i.getGraphics();
    if (simOne.bullet.getStatus() == true)
      g.drawImage(scenario.getImage(simOne.bullet), simOne.bullet.getX(), simOne.bullet.getY(), this);
    g.drawImage(scenario.getImage(simOne), simOne.getX(), simOne.getY(), this);
    if (simTwo.bullet.getStatus() == true)
      g.drawImage(scenario.getImage(simTwo.bullet), simTwo.bullet.getX(), simTwo.bullet.getY(), this);
    g.drawImage(scenario.getImage(simTwo), simTwo.getX(), simTwo.getY(), this);
    return i;
  }

  public BufferedImage drawBlock(Block b, BufferedImage i) {
    Graphics g = i.getGraphics();
    for (int j = 0; j < 2; j++)
      for (int k = 0; k < 2; k++)
        if (b.getData(j, k) == true) g.drawImage(scenario.getImage(b), ((b.getX() * 32) + (j * 16)), (b.getY() * 32) + (k * 16), this);
    return i;
  }
  
  public void drawObject (Graphics g, Object o){
    
  }
  
  public BufferedImage drawScenario (BufferedImage i) {
    Graphics g = i.getGraphics();
    g.drawImage(scenario.getBoardBg(), 0, 0, this);
    for (ArrayList <Block>a : scenario.getBoard())
      for (Block b : a)
        if (b.getType() != Block.TREE)
          i = drawBlock(b, i);

    i = drawTanks(i);

    for (ArrayList <Block>a : scenario.getBoard())
      for (Block b : a)
        if (b.getType() == Block.TREE)
          i = drawBlock(b, i);


    g.drawImage(scenario.getImage( flag ), flag.getX() * 32, flag.getY() * 32, this);
    return i;
  }
  
  @Override public String toString() {
    return this.scenario.toString();
  }
}

