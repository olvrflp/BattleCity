package game;

public class Block {

  public static final int EMPTY = 0;
  public static final int BRICK = 1;
  public static final int BLOCK = 2;
  public static final int TREE = 3;
  public static final int SNOW = 4;
  public static final int WATER = 5;

  private boolean collisionable = true;
  private int left, top, type;
  boolean[][] data = new boolean[2][2];
  int[] status = new int[4];

  public Block( int x, int y ) {
    setType( 0 );
    setCoords( x, y );
    for ( int i = 0; i < 2; i++ )
    for ( int j = 0; j < 2; j++ ) {
      data[i][j] = false;
    }
    for ( int i = 0; i < 4; i++ ) {
      status[i] = 2;
    }
  }

  public Block( int x, int y, int t ) {
    setType( t );
    setCoords( x, y );
    for ( int i = 0; i < 2; i++ )
    for ( int j = 0; j < 2; j++ ) {
      data[i][j] = false;
    }
    for ( int i = 0; i < 4; i++ ) {
      status[i] = 2;
    }
  }

  public Block( int x, int y, int t, boolean d[][] ) {
    setType( t );
    setCoords( x, y );
    setData( d );
  }

  public Block( int x, int y, int t, boolean d[][], int s[] ) {
    setType( t );
    setCoords( x, y );
    setData( d );
    setStatus( s );
  }

  public boolean isCollisionable() {
    return collisionable;
  }

  public boolean isPassable() {
    return ( type == Block.WATER ) ? false : !collisionable;
  }

  public void setType( int t ) {
    type = t;
    switch ( t ) {
      case 1:
      case 2:
      collisionable = true;
      break;
      default:
      collisionable = false;
      break;
    }
  }
  public void setCoords( int x, int y ) {
    left = x;
    top = y;
  }

  public void setData() {
    if ( type == 0 ) {
      for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = false;
      }
    } else {
      for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = true;
      }
    }

  }

  public void setData( boolean d[][] ) {
    if ( type > 0 ) {
      if ( type == 1 || type == 2 )
        for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = d[i][j];
      } else
        for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = true;
      }
    } else {
      for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = false;
      }
    }

  }

  public void setData( boolean a, boolean b, boolean c, boolean d ) {
    /**
     * a|b
     * c|d
     */
    if ( type == BRICK || type == BLOCK ) {
      // Ladrillos y Concreto cubren parcial o totalmente (SINGLE VALUES)
      data[0][0] = a;
      data[1][0] = b;
      data[0][1] = c;
      data[1][1] = d;
    } else if ( type == EMPTY ) {
      // Vacio (ALL FALSE)
      for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = false;
      }
    } else if ( a || b || c || d ) {
      // Arboles, Agua o Nieve cubren toda el area (ALL TRUE)
      for ( int i = 0; i < 2; i++ )
      for ( int j = 0; j < 2; j++ ) {
        data[i][j] = true;
      }
    }
  }

  public void setStatus( int s[] ) {
    switch ( type ) {
      case BRICK:
      // Ladrillos (Destructible en s[i] golpes)
      for ( int i = 0; i < 4; i++ ) {
        status[i] = s[i];
      }
      break;
      case BLOCK:
      // Blindado (Colisionable, No destructible)
      for ( int i = 0; i < 4; i++ ) {
        status[i] =  - 1;
      }
      case EMPTY:
      // Vacio---|
      case TREE:
      // Arboles-|
      case SNOW:
      // Nieve---|
      case WATER:
      // Agua----|-> No colisionable, No destructible
      for ( int i = 0; i < 4; i++ ) {
        status[i] =  - 2;
      }
    }
  }

  public void setStatus( int a, int b, int c, int d ) {
    if ( type == BRICK ) {
      status[0] = a;
      status[1] = b;
      status[2] = c;
      status[3] = d;
    } else if ( type == BLOCK )
      for ( int i = 0; i < 4; i++ ) {
        status[i] =  - 1;
    } else
      for ( int i = 0; i < 4; i++ ) {
        status[i] =  - 2;
    }
  }

  public int getType() {
    return type;
  }
  public int getX() {
    return left;
  }
  public int getY() {
    return top;
  }

  public int[] getCoords() {
    int[] coords = new int[2];
    coords[0] = left;
    coords[1] = top;
    return coords;
  }

  public boolean[][] getData() {
    return data;
  }
  public boolean getData( int posA, int posB ) {
    return data[posA][posB];
  }
  public int[] getStatus() {
    return status;
  }
  public int getStatus( int pos ) {
    return status[pos];
  }
  // @Override
  public String toString() {
    String output = "Tipo:" + this.type + "\n";
    output += "Posicion: (" + this.left + "," + this.top + ")\n";
    output += "[" + ( this.data[0][0] ? "*" : " " ) + "]";
    output += "[" + ( this.data[0][1] ? "*" : " " ) + "]\n";
    output += "[" + ( this.data[1][0] ? "*" : " " ) + "]";
    output += "[" + ( this.data[1][1] ? "*" : " " ) + "]\n";
    return output;
  }
  public static boolean validate( Object o ) {
    return o instanceof Block;
  }
}
