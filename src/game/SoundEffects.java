package game;

import java.awt.*;
import java.applet.*;
import java.net.*;

public class SoundEffects {
  public static AudioClip BACKGROUND, BONUS, BRICK, EXPLOSION, FIRE, GAMESTART, SCORE, STEEL;
  public SoundEffects(){
    try {
      BACKGROUND = this.loadSound ("background");
      BONUS = this.loadSound ("bonus");
      BRICK = this.loadSound ("brick");
      EXPLOSION = this.loadSound ("explosion");
      FIRE = this.loadSound ("fire");
      GAMESTART = this.loadSound ("gamestart");
      SCORE = this.loadSound ("score");
      STEEL = this.loadSound ("steel");
    } catch (Exception mfe){}
  }
  
  public AudioClip loadSound ( String filename ) {
    return Applet.newAudioClip ( getClass().getResource( "sounds/" + filename + ".wav" ) );
  }

  public void play (AudioClip a) {
    a.play();
  }

  public void play (AudioClip a, boolean loop) {
    if (loop == true) a.loop();
    else a.play();
  }

  public void stop () {
    BACKGROUND.stop();
    BONUS.stop();
    BRICK.stop();
    EXPLOSION.stop();
    FIRE.stop();
    GAMESTART.stop();
    SCORE.stop();
    STEEL.stop();
  }

  public void stop (AudioClip a) {
    a.stop();
  }
}
