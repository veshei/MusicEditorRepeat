package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import cs3500.music.util.CompositionBuilder;

/**
 * Implements the IMusicEditor by implementing all of the methods in the interface along with the
 * necessary helper functions to do so. Songs are represented by a TreeMap, the list of all notes
 * played are stored in an ArrayList, there is a list of notes needed for the output header, and an
 * int to represent the tempo of the song
 */
public class MusicEditorModel implements IMusicEditor {
  private TreeMap<Integer, ArrayList<INote>> songBeat; // the song, with beats mapped to notes
  private List<INote> songNotes; // all notes that exist within the song
  private List<INote> notes; // all notes necessary for the header
  private int tempo; // the tempo of the song
  private int currentBeat;

  public MusicEditorModel() {
    this.songBeat = new TreeMap<>();
    this.songNotes = new ArrayList<>();
    this.notes = new ArrayList<>();
    this.tempo = 0;
    this.currentBeat = 0;
  }

  /**
   * Generate an appropriate list of notes from the lowest to highest note in the song
   *
   * @return a list of notes
   */
  @Override
  public List<INote> getNotes() {
    this.notes.clear(); // clears the note list so that notes are not populated more than once
    int startPitch = lowestNote().getPitch(); // retrieves the lowest pitch
    int endPitch = highestNote().getPitch(); // retrieves the highest pitch

    for (int pitch = startPitch; pitch <= endPitch; pitch++) {
      notes.add(new Note(pitch));
    }

    Collections.sort(notes); // sorts the list so it outputs in the right order

    return notes;
  }

  /**
   * Getter method for the song
   *
   * @return a Tree Map representing the song
   */
  @Override
  public TreeMap<Integer, ArrayList<INote>> getSong() {
    return this.songBeat;
  }

  /**
   * Getter method for the list of notes that were played in the song
   *
   * @return a list representing the notes that have been played
   */
  @Override
  public List<INote> getPlayedNotes() {
    return this.songNotes;
  }

  /**
   * @return the lowest note in the song
   */
  private INote lowestNote() {
    return Collections.min(songNotes); // lowest note in the song
  }


  /**
   * @return the highest note in the song
   */
  private INote highestNote() {
    return Collections.max(songNotes); // highest note in the song
  }


  /**
   * Adds a note to the song. If key has not been initialized, a new ArrayList is created for the
   * note to be added to, else add to the existing ArrayList
   *
   * @param n the note that is being added to the song
   */
  @Override
  public void addNote(INote n) {
    // the start beat of the note
    int start = n.getStartTime();
    // the end beat of the note
    int end = n.getEndTime();
    // iterates from the start and end beat of the note
    for (int beat = start; beat <= end; beat++) {
      // checks to see if the ArrayList has been initialized at that beat
      this.ensureInit(beat);
      // adds the note to that beat
      this.songBeat.get(beat).add(n);
    }
    if (!this.songNotes.contains(n)) { // checks if songNotes already has this note
      this.songNotes.add(n); // adds the note to the list
    }
  }

  /**
   * Method to edit the note at that beat and note
   *
   * @param beat at which the note is to be edited
   * @param n1   the note which you want to replace
   * @param n2   the new note
   */
  @Override
  public void editNote(int beat, INote n1, INote n2) {
    this.ensureInit(beat); // checks to see if the ArrayList has been initialized at that beat
    // iterates through the ArrayList of notes at that beat
    for (int i = 0; i < songBeat.get(beat).size(); i++) {
      // checks if both notes exist at that beat, i.e. the beat is within the start and end beats
      if (this.noteExistsAtBeat(beat, n1) && this.noteExistsAtBeat(beat, n2)) {
        // checks if the note at that index for the ArrayList of notes is equal to the n1
        if (songBeat.get(beat).get(i).equals(n1)) {
          // checks if the startTime and endTime are not the same, to determine if the start and end
          // time need to be increased
          if (songBeat.get(beat).get(i).getEndTime() != songBeat.get(beat).get(i).getStartTime()) {
            // increases the start time by 1
            songBeat.get(beat).get(i).setStartTime(1);
            // increases the end time by 1
            songBeat.get(beat).get(i).setEndTime(1);
          }
          // sets the note at that index to n2 if n1 is equivalent to the note at i
          songBeat.get(beat).set(i, n2);
          for (int r = 0; r < songNotes.size(); r++) { // iterates through the songNotes list
            // checks if a note within songNotes is equivalent to the note that needs to be edited
            if (songNotes.get(r).equals(songBeat.get(beat).get(i))) {
              // edits the start time of the original note
              songNotes.get(r).setStartTime(1);
              // edits the end time of the original note
              songNotes.get(r).setEndTime(1);
              // sets the original note to n2 in the songNotes list
              songNotes.set(r, n2);
            }
          }
        }
      } else {
        // if the original note does not exist at that beat, note cannot be changed
        throw new IllegalArgumentException("Note does not exist at that beat");
      }
    }
  }

  /**
   * Helper method to check if the note exists at that beat
   *
   * @param beat beat of the song
   * @param note note that you are checking against
   * @return a boolean indicating whether or not the beat exists
   */
  private boolean noteExistsAtBeat(int beat, INote note) {
    boolean flag = false; // initializes the flag to false
    int start = note.getStartTime(); // sets the start to the start time of the note
    int end = note.getEndTime(); // sets the end to the end time of the note
    for (int i = start; i <= end; i++) { // iterates from the start to end of the note
      // checks if the beat is equivalent to the a beat within start time -> end time
      // sets flag to true if any of the beats are equivalent
      flag = beat == i || flag;
// sets flag to false if none of the beats are equivalent
    }
    return flag;
  }

  /**
   * Removes a note from the song. Checks if that key has been initialized, if not create that
   * ArrayList and add the note to it, else remove the note from the existing ArrayList
   *
   * @param n the note being removed
   */
  @Override
  public void removeNote(INote n) {
    int start = n.getStartTime();
    int end = n.getEndTime();
    for (int beat = start; beat <= end; beat++) {
      this.ensureInit(beat);
      songBeat.get(beat).remove(n);
    }
    if (this.songNotes.contains(n)) {
      this.songNotes.remove(n);
    }
  }

  /**
   * Checks to see if that key has been initialized. Creates a new ArrayList for that key if it
   * hasn't been made yet
   *
   * @param beat the key in the TreeMap
   */
  public void ensureInit(int beat) {
    if (beat < 0) { // checks if the beat is less than zero
      // throws an exception that an invalid beat has been given
      throw new IllegalArgumentException("Invalid beat");
    } else if (!this.songBeat.containsKey(beat)) { // checks if the key has been initialized
      // initializes the key and inputs and empty ArrayList
      this.songBeat.put(beat, new ArrayList<>());
    }
  }

  /**
   * Combines two songs so they play at the same time
   *
   * @param song the song that the current piece of music will be combined with
   */
  @Override
  public void playSimultaneously(IMusicEditor song) {
    if (song == null) { // checks if the song has been initialized
      // throws exception if song is null
      throw new NullPointerException("Song has not been initialized");
    }
    // iterates from the first beat to the last beat
    for (int beat = 0; beat < this.getLastBeat(); beat++) {
      // ensures the keys have been initialized at that beat for the current piece of music
      this.ensureInit(beat);
      // ensures the keys have been initialized at that beat for the song you are appending
      song.ensureInit(beat);
      // iterates through all the notes in the ArrayList of notes at that beat
      for (INote note : song.getSong().get(beat)) {
        // checks if the note's start time is the same as the beat
        if (note.getStartTime() == beat) {
          // ensures the keys have been initialized at that beat
          this.ensureInit(beat);
          // adds the note at that beat
          this.addNote(note);
        }
      }
    }
  }

  /**
   * Combines two songs so they play one after the other
   *
   * @param song the song that the current piece of music will be combined with
   */
  @Override
  public void playConsecutively(IMusicEditor song) {
    if (song == null) { // checks if the song has been initialized
      // throws exception if song is null
      throw new NullPointerException("Song has not been initialized");
    }
    int offset = this.getLastBeat(); // sets the offset to the last beat of the current song
    // sets the length to the last beat in the other song
    int otherLength = song.getSong().lastKey();
    // iterates through the other song from the first to the last beat
    for (int beat = 0; beat <= otherLength; ++beat) {
      // ensures the keys have been initialized for the current song
      this.ensureInit(beat);
      // ensures the keys have been initialized for the other song
      song.ensureInit(beat);
      // iterates through all the notes in the ArrayList of notes at that beat
      for (INote note : song.getSong().get(beat)) {
        // checks if the note's start time is the same as the beat
        if (note.getStartTime() == beat) {
          // ensures the keys have been initialized at the beat plus the offset, i.e.
          // the portion you are appending
          this.ensureInit(beat + offset);
          // adds the offset to the start time
          note.setStartTime(offset);
          // adds the offset to the end time
          note.setEndTime(offset);
          // adds the note after the current piece of music
          this.addNote(note);
        }
      }
    }
  }

  /**
   * Helper method that returns the last beat (key) in the song
   *
   * @return the last beat in the song
   */
  private int getLastBeat() {
    return songBeat.lastKey() + 1;
  }

  /**
   * Getter method for the tempo of the song
   *
   * @return an int representing the tempo of the song
   */
  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Sets the tempo for the song
   *
   * @param tempo the tempo the song should be set to
   */
  @Override
  public void setTempo(int tempo) {
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be negative");
    }
    this.tempo = tempo;
  }

  public int getCurrentBeat() {
    return this.currentBeat;
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    if (currentBeat < 0) {
      throw new IllegalArgumentException("Current beat cannot be negative");
    }
    this.currentBeat = currentBeat;
  }


  /**
   * Returns the notes at that beat
   *
   * @param beat the beat to get the notes from
   * @return a list of notes at that beat
   * @throws IllegalArgumentException if the beat is negative
   */
  @Override
  public ArrayList<INote> getNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0)
      throw new IllegalArgumentException("Beat must be >= 0");

    return this.songBeat.get(beat);
  }

  /**
   * Builder class to implement the CompositionBuilder class
   */
  public static final class Builder implements CompositionBuilder<IMusicEditor> {
    private IMusicEditor model;

    public Builder() {
      this.model = new MusicEditorModel();
    }

    /**
     * Builds the model by returning an instance of it
     *
     * @return the model
     */
    @Override
    public IMusicEditor build() {
      return this.model;
    }

    /**
     * Sets the tempo of the model
     *
     * @param tempo The speed, in microseconds per beat
     * @return the model with the updated tempo
     */
    @Override
    public CompositionBuilder<IMusicEditor> setTempo(int tempo) {
      this.model.setTempo(tempo);
      return this;
    }

    /**
     * Adds a note to the model
     *
     * @param start      The start time of the note, in beats
     * @param end        The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
     *                   piano)
     * @param volume     The volume (in the range [0, 127])
     * @return the model with the added note
     */
    @Override
    public CompositionBuilder<IMusicEditor> addNote(int start, int end, int instrument,
                                                    int pitch, int volume) {
      Note newNote = new Note(start, end, instrument, pitch, volume);
      this.model.addNote(newNote);
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditor> removeNote(int start, int end, int instrument,
                                                       int pitch, int volume) {
      Note newNote = new Note(start, end, instrument, pitch, volume);
      this.model.removeNote(newNote);
      return this;
    }
  }
}
