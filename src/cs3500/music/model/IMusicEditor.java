package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Interface for the music editor model
 */
public interface IMusicEditor {

  /**
   * Initializes a list of notes that represent the notes present in the header
   *
   * @return a list of notes representing the header
   */
  List<INote> getNotes();

  /**
   * Represents the song
   *
   * @return a TreeMap that returns the song, with beats mapped to a list of notes
   */
  TreeMap<Integer, ArrayList<INote>> getSong();

  List<INote> getPlayedNotes();

  /**
   * Adds a note to the song
   *
   * @param n the note to be added
   */
  void addNote(INote n);

  /**
   * Edits a note in a song at that particular beat
   *
   * @param beat at which the note is to be edited
   * @param n1   the note which you want to replace
   * @param n2   the new note
   */
  void editNote(int beat, INote n1, INote n2);

  /**
   * Removes the note from the song
   *
   * @param n the note to be removed
   */
  void removeNote(INote n);

  /**
   * Ensures that the ArrayList within the TreeMap has been initialized, if not creates an empty
   * ArrayList
   *
   * @param beat the beat where the method checks if the ArrayList has been initialized
   */
  void ensureInit(int beat);

  /**
   * Combines two pieces of music so that they play at the same time
   *
   * @param model the song to be combined with the current piece of music
   */
  void playSimultaneously(IMusicEditor model);

  /**
   * Combine two pieces of music such that they play consecutively.
   *
   * @param model the song to be combined with the current piece of music
   */
  void playConsecutively(IMusicEditor model);

  /**
   * Sets the tempo of the {@code IMusicEditor}.
   *
   * @param tempo the tempo to set
   */
  void setTempo(int tempo);

  /**
   * Gets the tempo of the {@code IMusicEditor}.
   *
   * @return the tempo of the IMusicEditor
   */
  int getTempo();

  int getCurrentBeat();

  void setCurrentBeat(int beat);

  /**
   * Gets the list of notes at the inputted beat.
   *
   * @param beat the beat to get the notes from
   * @return the ArrayList of {@code INote}s at the beat
   * @throws IllegalArgumentException if the beat is invalid
   */
  ArrayList<INote> getNotesAtBeat(int beat) throws IllegalArgumentException;
}