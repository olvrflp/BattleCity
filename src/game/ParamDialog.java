package game;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class ParamDialog extends JDialog implements ActionListener {
  class JTextFieldLimit extends PlainDocument {
    private int limit;

    JTextFieldLimit( int limit ) {
      super();
      this.limit = limit;
    }

    public void insertString( int offset, String str, AttributeSet attr )throws BadLocationException {
      if ( str == null ) {
        return ;
      }
      try {
        int inChar = Integer.parseInt( str );
        if (( getLength() + str.length()) <= limit ) {
          super.insertString( offset, str, attr );
        } else {
          JOptionPane.showMessageDialog( null, "Este campo solo admite un numero de dos digitos", "Alerta", JOptionPane.WARNING_MESSAGE );
        }
      } catch ( NumberFormatException nFE ) {
        JOptionPane.showMessageDialog( null, "Este campo solo debe tener numeros", "Error", JOptionPane.ERROR_MESSAGE );
      }
    }
  }

  private static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  public JCheckBox blockTypes[] = new JCheckBox[5];
  public JTextField percentValues[] = new JTextField[5];
  private JButton sendParams = new JButton( "Generar" );
  private GUI tmpGUI;

  public ParamDialog( GUI g ) {
    String labels[] =  {
      "Ladrillo", "Concreto", "Bosque", "Nieve", "Agua"
    };
    tmpGUI = g;
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    this.setLayout( layout );


    for ( int i = 0; i < 5; i++ ) {
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1.0;
      c.gridwidth = 1;
      c.gridheight = 1;
      c.ipadx = 10;
      blockTypes[i] = new JCheckBox( labels[i], true );
      layout.setConstraints( blockTypes[i], c );
      this.add( blockTypes[i] );
      percentValues[i] = new JTextField( 2 );
      c.gridwidth = GridBagConstraints.REMAINDER;
      layout.setConstraints( percentValues[i], c );
      this.add( percentValues[i] );
      percentValues[i].setDocument( new JTextFieldLimit( 2 ));
      percentValues[i].setText( "0" );
    }

    c.weightx = 0.0;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.ipadx = 10;
    setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    layout.setConstraints( sendParams, c );
    this.add( sendParams );
    sendParams.addActionListener( this );

    // Muestra el Dialogo
    pack();
    setLocation((( int )( screen.getWidth() / 2 ) - ( getWidth() / 2 )), (( int )( screen.getHeight() / 2 ) - ( getHeight() / 2 )));
    setResizable( false );
    setVisible(true);
    setAlwaysOnTop(true);
  }

  public void getConfig() {
    int returnData[] = new int[5];
    for ( int i = 0; i < 5; i++ ) {
      returnData[i] = ( blockTypes[i].isSelected()) ? Integer.parseInt( percentValues[i].getText()): 0;
    }
  }

  public void actionPerformed( ActionEvent e ) {
    if ( e.getSource() == sendParams ) {
      int resultArray[] = new int[5];
      for ( int i = 0; i < blockTypes.length; i++ ) {
        resultArray[i] = ( blockTypes[i].isSelected() ) ? (int) Math.round( 13 * 13 * (Double.parseDouble(percentValues[i].getText()) / 100 ) ) : 0;
      }
      tmpGUI.setVisible(true);
      tmpGUI.getCanvas().generateEnvironment( resultArray );
      this.dispose();
    }
  }
}
