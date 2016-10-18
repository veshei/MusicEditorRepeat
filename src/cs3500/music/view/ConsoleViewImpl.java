package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.INote;

/**
 * class to implement the console view
 */
public class ConsoleViewImpl implements IView {
  private final IMusicEditor c;

  public ConsoleViewImpl(IMusicEditor c) {
    this.c = c;
  }


  @Override
  public void initialize()
  {
    System.out.print(this.musicEditorOutput());
  }

  /**
   * Outputs the piece of music
   *
   * @return the string output of the model
   */
  public String musicEditorOutput() {
    if (this.c.getNotes() == null) { // checks if the notes has been initialized yet
      throw new NoSuchElementException("Notes have not been initialized");
    }

    // uses the populated list from the getNotes() method rather than directly using notes because
    // the list may not have been populated yet
    List<INote> initNotes = this.c.getNotes();
    // sets the output to the String header generated from the helper function
    String output = notesOutput();
    StringBuilder l = new StringBuilder();
    int lastBeat = this.c.getSong().lastKey();

    // iterates from the first beat to the last beat in the song
    for (int i = 0; i <= lastBeat; i++) {
      // checks if the beat number is less than 10
      if (i < 10) {
        // the output needs a leading space since the beat is only one digit
        l.append(" ").append(Integer.toString(i));
      } else {
        // the output does not need a leading space since the beat is two digits
        l.append(Integer.toString(i));
      }
      for (int r = 0; r < initNotes.size(); r++) { // iterates through the header
        // checks if that note is played in the song at that beat
        if (atBeat(i, initNotes.get(r))) {
          // checks if that note is being played for the first time, if so put an X
          if (writeX(i, initNotes.get(r))) {
            if (r == (initNotes.size() - 1)) { // checks if the index is at the end of the line
              // adds a trailing newline if the index is at the end
              l.append("  X  \n");
            } else {
              // does not add a trailing newline to the X
              l.append("  X  ");
            }
          } else {
            if (r == (initNotes.size() - 1)) { // checks if the index is at the end of the line
              // adds a trailing newline if the index is at the end
              l.append("  |  \n");
            } else {
              // does not add a trailing newline to the |
              l.append("  |  ");
            }
          }
        } else if (r == (initNotes.size() - 1)) { // checks if the index is at the end of the line
          // adds a trailing newline if the index is at the end
          l.append("     \n");
        } else {
          // does not add a trailing newline
          l.append("     ");
        }
      }
    }

    output = output + l.toString(); // combines the header with the rest of the song

    return output;
  }

  private boolean writeX(int beat, INote note) {
    boolean flag = false; // initializes flag to false

    ArrayList<INote> notesBeat = this.c.getNotesAtBeat(beat);

    // iterates through the ArrayList of notes at that beat
    for (INote aNotesBeat : notesBeat) {
      // checks if the songBeat at that beat and ArrayList index is equal to the given note
      if (aNotesBeat.getPitch() == note.getPitch()) {
        // returns true if any of the note's start time within songBeat is equal
        flag = flag || aNotesBeat.getStartTime() == beat;
      }
    }

    return flag;
  }

  private boolean atBeat(int beat, INote note) {
    // initializes the flag to false
    boolean flag = false;

    ArrayList<INote> notesBeat = this.c.getNotesAtBeat(beat);

    // checks if the song has been initialized
    if (notesBeat == null) {
      flag = false; // return false if the song is null
    } else {
      // iterates through the ArrayList of notes at that beat
      for (INote aNotesBeat : notesBeat) {
        if (aNotesBeat.getPitch() == note.getPitch()) {
          flag = true;
          break;
        }
      }
    }

    return flag;
  }

  /**
   * Outputs the header representing the list of notes
   *
   * @return the String output of the notes variable
   */
  private String notesOutput() {
    INote note;
    String output = "  ";
    for (int i = 0; i < this.c.getNotes().size(); i++) { // iterates through the list of notes
      note = this.c.getNotes().get(i); // the note at that index
      switch (note.getPitch() % 12) { // switch method for the pitches
        case 0:
          output = output + "  C";
          break;
        case 1:
          output = output + " C#";
          break;
        case 2:
          output = output + "  D";
          break;
        case 3:
          output = output + " D#";
          break;
        case 4:
          output = output + "  E";
          break;
        case 5:
          output = output + "  F";
          break;
        case 6:
          output = output + " F#";
          break;
        case 7:
          output = output + "  G";
          break;
        case 8:
          output = output + " G#";
          break;
        case 9:
          output = output + "  A";
          break;
        case 10:
          output = output + " A#";
          break;
        case 11:
          output = output + "  B";
          break;
        default:
          throw new IllegalArgumentException("Invalid note");
      }
      switch (note.getPitchOctave()) { // switch method for the octaves
        case 1:
          output = output + "1 ";
          break;
        case 2:
          output = output + "2 ";
          break;
        case 3:
          output = output + "3 ";
          break;
        case 4:
          output = output + "4 ";
          break;
        case 5:
          output = output + "5 ";
          break;
        case 6:
          output = output + "6 ";
          break;
        case 7:
          output = output + "7 ";
          break;
        case 8:
          output = output + "8 ";
          break;
        case 9:
          output = output + "9 ";
          break;
        case 10:
          output = output + "10";
          break;
        default:
          throw new IllegalArgumentException("Invalid octave");
      }
      if (i == (this.c.getNotes().size() - 1)) { // checks if the index is at the end of a line
        output = output + "\n"; // adds a trailing new line
      }
    }
    return output;
  }

  public void updateTime(int beat) {

  }
}