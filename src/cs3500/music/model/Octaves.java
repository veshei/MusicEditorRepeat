package cs3500.music.model;

/**
 * Created by veronicashei on 6/6/16.
 */

/**
 * enum to represent the octaves
 */
public enum Octaves {
  One(1), Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10);
  private int value;

  Octaves(int value) {
    this.value = value;
  }

  /**
   * Getter method for the octave
   *
   * @return an in representing the octave
   */
  public int getValue() {
    return this.value;
  }
}