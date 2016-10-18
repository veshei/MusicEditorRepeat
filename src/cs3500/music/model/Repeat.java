package cs3500.music.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by veronicashei on 6/28/16.
 */
public class Repeat implements IRepeat {
  private int repeatBeat;
//  private int firstEnding;
////  private int secondEnding;

  public Repeat(int repeatBeat) {
    if (repeatBeat <= 0) {
      throw new IllegalArgumentException("Invalid repeat beat");
    }
    this.repeatBeat = repeatBeat;
  }

/*  public Repeat(int repeatBeat, int firstEnding, int secondEnding) {
    if (repeatBeat <= 0) {
      throw new IllegalArgumentException("Invalid repeat beat");
    }
    if (firstEnding < 0) {
      throw new IllegalArgumentException("Invalid repeat beat");
    }
    if (secondEnding < 0) {
      throw new IllegalArgumentException("Invalid repeat beat");
    }
    this.repeatBeat = repeatBeat;
//    this.firstEnding = firstEnding;
//    this.secondEnding = secondEnding;
  }*/

  public int getRepeatBeat() {
    return this.repeatBeat;
  }

/*  public int getFirstEnding() {
    return this.firstEnding;
  }

  public int getSecondEnding() {
    return this.secondEnding;
  }*/
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!Note.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final Repeat repeat = (Repeat) o;
    return this.repeatBeat == repeat.getRepeatBeat();

  }

  /**
   * Overrides the default hashCode method for the Note class
   *
   * @return an int that represents the hash code for that note
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.repeatBeat);
  }

/*  @Override
  public int compareTo(Repeat repeat) {
    if (this.repeatBeat > repeat.getRepeatBeat()) {
      return 1;
    }
    else if (this.repeatBeat < repeat.getRepeatBeat()) {
      return -1;
    }
    else {
      return 0;
    }
  }*/
}
