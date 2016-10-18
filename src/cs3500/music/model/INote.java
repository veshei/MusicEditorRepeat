package cs3500.music.model;

/**
 * A extra level of abstraction for the Note class
 */
public interface /**/INote extends Comparable<INote> {
  /**
   * Getter method for pitch
   *
   * @return the pitch
   */
  int getPitch();

  /**
   * Getter method for the start time
   *
   * @return the start time
   */
  int getStartTime();

  /**
   * Getter method for the end time
   *
   * @return the end time
   */
  int getEndTime();

  /**
   * Setter method for start time, adds a set amount to the start time
   *
   * @param i the amount to be added to start time
   */
  void setStartTime(int i);

  /**
   * Setter method for end time, adds a set amount to the end time
   *
   * @param i the amount to be added to the end time
   */
  void setEndTime(int i);

  /**
   * Gets the instrument of the {@code INote}.
   *
   * @return the instrument of the INote
   */
  int getInstrument();

  /**
   * Gets the volume of the {@code INote}.
   *
   * @return the volume of the INote
   */
  int getVolume();

  /**
   * Gets the pitch octave of the {@code INote}.
   *
   * @return the pitch octave of the INote
   */
  int getPitchOctave();
}