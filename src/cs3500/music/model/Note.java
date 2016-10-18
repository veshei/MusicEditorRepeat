package cs3500.music.model;

import java.util.Objects;

/**
 * Note implements INote and all its methods. Note is made up of an int pitch, int octave, int
 * startTime, int endTime, int instrument, and int volume.
 */
public class Note implements INote {
  private int pitch; // the pitch of the note
  private int octave;
  private int startTime; // the start time of the note
  private int endTime; // the end time of the note
  private int instrument;
  private int volume;

  // constructor that contains all the fields
  public Note(int startTime, int endTime, int instrument, int pitch, int volume) {
    // throws an exception if the start time is less than zero or greater than 64
    if (startTime < 0) {
      throw new IllegalArgumentException("Invalid start time");
    }
    // throws an exception if the end time is less than zero or greater than 64
    else if (endTime < 0 || endTime < startTime) {
      throw new IllegalArgumentException("Invalid end time");
    } else if (instrument < 1 || instrument > 128) {
      throw new IllegalArgumentException("Invalid instrument input");
    } else if (pitch < 1 || pitch > 127) {
      throw new IllegalArgumentException("Invalid pitch");
    } else if (volume < 0) {
      throw new IllegalArgumentException("Invalid volume");
    }
    this.startTime = startTime;
    this.endTime = endTime;
    this.pitch = pitch;
    this.instrument = instrument;
    this.volume = volume;
  }

  // constructor that contains only the pitch field
  public Note(int pitch) {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException("Invalid pitch");
    }
    this.pitch = pitch;
  }

  @Override
  public int getPitch() {
    return this.pitch;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public void setStartTime(int i) {
    this.startTime += i;
  }

  @Override
  public void setEndTime(int i) {
    this.endTime += i;
  }

  @Override
  public int getInstrument() {
    return this.instrument;
  }

  @Override
  public int getVolume() {
    return this.volume;
  }

  @Override
  public int getPitchOctave() {
    this.octave = this.pitch / 12 + 1;

    return this.octave;
  }

  public int convertEnumToInt(Pitches pitches, Octaves octaves) {
    return ((octaves.getValue() - 1) * 12) + pitches.getValue();
  }

  /**
   * Overrides the default compareTo method for the Note class
   *
   * @param note the note that you are comparing the method to
   * @return the int representing if the note is greater than, less than, or equal to the given note
   */
  @Override
  public int compareTo(INote note) {
    if (this.getPitchOctave() == note.getPitchOctave()) {
      if (this.pitch == note.getPitch()) {
        return 0;
      } else if (this.pitch > note.getPitch()) {
        return 1;
      } else {
        return -1;
      }
    } else if (this.getPitchOctave() > note.getPitchOctave()) {
      return 1;
    } else {
      return -1;
    }
  }


  /**
   * Overrides the default equals method for the Note class
   *
   * @param o the object you are comparing to
   * @return a boolean indicating whether or not two objects are equivalent
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!Note.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final Note note = (Note) o;
    return this.startTime == note.getStartTime() &&
        this.endTime == note.getEndTime() &&
        this.instrument == note.getInstrument() &&
        this.pitch == note.getPitch() &&
        this.volume == note.getVolume();
  }

  /**
   * Overrides the default hashCode method for the Note class
   *
   * @return an int that represents the hash code for that note
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.startTime, this.endTime, this.instrument, this.pitch, this.volume);
  }

  public String convertToString() {
    String output = "";
    switch (this.getPitch() % 12) {
      case 0:
        output = "C";
        break;
      case 1:
        output = "C#";
        break;
      case 2:
        output = "D";
        break;
      case 3:
        output = "D#";
        break;
      case 4:
        output = "E";
        break;
      case 5:
        output = "F";
        break;
      case 6:
        output = "F#";
        break;
      case 7:
        output = "G";
        break;
      case 8:
        output = "G#";
        break;
      case 9:
        output = "A";
        break;
      case 10:
        output = "A#";
        break;
      case 11:
        output = "B";
        break;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
    return output;
  }

  /**
   * A String that represents the pitch and octave of that note
   *
   * @return a String version of the note
   */
  public String toString() {
    return this.convertToString() + "" + this.getPitchOctave();
  }
}