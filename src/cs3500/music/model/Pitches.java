package cs3500.music.model;

/**
 * Created by veronicashei on 6/6/16.
 */

/**
 * an enum representing the pitches
 */
public enum Pitches {
  C(0), CSharp(1), D(2), DSharp(3), E(4), F(5), FSharp(6), G(7), GSharp(8),
  A(9), ASharp(10), B(11);

  private int value;

  Pitches(int value) {
    this.value = value;
  }

  /**
   * Getter method for the value of the Pitch
   *
   * @return an int representing the Pitch
   */
  public int getValue() {
    return this.value;
  }

  /**
   * converts the pitch to a String
   *
   * @return a String representation of the pitch
   */
  public String toString() {
    String output = "";
    switch (this.getValue()) {
      case 0:
        output = "C";
        break;
      case 1:
        output = "CSharp";
        break;
      case 2:
        output = "D";
        break;
      case 3:
        output = "DSharp";
        break;
      case 4:
        output = "E";
        break;
      case 5:
        output = "F";
        break;
      case 6:
        output = "FSharp";
        break;
      case 7:
        output = "G";
        break;
      case 8:
        output = "GSharp";
        break;
      case 9:
        output = "A";
        break;
      case 10:
        output = "ASharp";
        break;
      case 11:
        output = "B";
        break;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
    return output;
  }
}