package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
  private static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  private GameCanvas canvas = new GameCanvas();

  public GUI() {
    ParamDialog params = new ParamDialog( this );
    setTitle( "Battle City" );
    setIconImage( canvas.getScenario().loadImage( "sprites/icon.gif" ));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
        System.exit( 0 ); 
      }
    } );

    canvas.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent actionEvent ){}
    } );
    getContentPane().add( canvas, BorderLayout.CENTER );
    setSize( 500, 444 );
    setLocation((( int )( screen.getWidth() / 2 ) - ( getWidth() / 2 )), (( int )( screen.getHeight() / 2 ) - ( getHeight() / 2 )));
    setResizable( false );
  }
  public GameCanvas getCanvas() { return this.canvas; }
}
