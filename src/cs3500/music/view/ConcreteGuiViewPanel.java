package cs3500.music.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.INote;
import cs3500.music.model.IRepeatEditor;
import cs3500.music.model.Note;

/**
 * Draws the GUI view
 */
public class ConcreteGuiViewPanel extends JPanel {
  public static final int MEASURE_X = 80; // constant to represent the width of a grid in the gui
  public static final int MEASURE_Y = 20; // constant to represent the height of a grid in the gui
  private IRepeatEditor model; // the model that the view takes in
  private final List<INote> notes; // a list of notes to be used in the header
  private int offsetX; // the x-coordinate offset to account for
  private int offsetY; // the y-coordinate offset to account for

  public ConcreteGuiViewPanel(IRepeatEditor model) {
    this.model = model; // instance of the model
    // calls on the getNotes method in the model to populate the list
    this.notes = this.model.getNotes();
    this.offsetX = 40;
    this.offsetY = 110;
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g); // calls on the super method to paint the component
    this.printBeatHeader(g); // paints the beats
    this.printPitches(g); // paints the pitches
    this.printPlayedNotes(g); // prints the notes that have been played
    this.printGrid(g); // prints all the grids
    this.printCursor(g); // prints the red line
    this.printRepeat(g);
    this.revalidate(); // revalidates the panel so that the red line location is updated
    this.repaint(); // repaints the panel so that the new location of the red line is shown
  }

  /**
   * Prints all the pitches from the lowest pitch to the highest pitch in a vertical line on the
   * left border of the frame
   *
   * @param g the graphic to draw the pitches on
   */
  public void printPitches(Graphics g) {
    int lowest = this.model.getNotes().get(0).getPitch(); // the lowest pitch
    // the highest pitch
    int highest = this.model.getNotes().get(this.model.getNotes().size() - 1).getPitch();
    // the initial y coordinate of the pitch
    int pitchHeight = 31 + 90;
    // iterates from the lowest to highest pitch
    for (int pitch = lowest; pitch <= highest; pitch++) {
      Note n = new Note(pitch); // a new note of the pitch value
      // draws the pitch in String form at those coordinates
      g.drawString(n.toString(), 5, pitchHeight);
      pitchHeight = pitchHeight + MEASURE_Y; // increments the y-coordinate for the next pitch
    }
  }

  /**
   * Print the beat header
   *
   * @param g the graphic to draw the beats on
   */
  public void printBeatHeader(Graphics g) {
    int songLength = this.model.getSong().lastKey(); // the last beat in the song
    int beatX = 40; // the initial x-coordinate of the beat
    int beatY = 10 + 90; // the y-coordinate of the beat
    // iterates from the first beat to the last beat
    for (int beat = 0; beat <= songLength; beat++) {
      if (beat % 4 == 0) { // only draws the beat every 4 beats
        // draws the beat in String form at those x and y coordinates
        g.drawString(String.valueOf(beat), beatX, beatY);
        beatX = beatX + MEASURE_X; // increments the x-coordinate of the beat for the next beat
      }
    }
  }

  /**
   * Prints all the notes that have been played as a green or black box
   *
   * @param g the graphic to draw the printed notes on
   */
  public void printPlayedNotes(Graphics g) {
    // sets the notes to the list of played notes
    List<INote> initNotes = this.model.getPlayedNotes();

    Color black = new Color(0, 0, 0); // an instance of the black color for us to call on
    Color green = new Color(0, 255, 0); // an instance of the green color for us to call on

    int p1, p2; // initializes the integers p1 and p2
    for (INote initNote : initNotes) { // iterates through the list of notes in initNotes
      // the formula to calculate the x location of each note based on its startTime
      p1 = (initNote.getStartTime() * 20) + offsetX;
      // the formula to calculate the y location of each note based on its pitch index
      p2 = (this.pitchIndex(initNote.getPitch()) * 20) + offsetY;

      g.setColor(black); // sets the graphic color to black
      g.fillRect(p1, p2, 20, 20); // draws the note as black if it is at the startBeat

      g.setColor(green); // sets the graphic color to green
      // draws the note as green if it is after the startBeat but the note is still playing
      g.fillRect(p1 + 20, p2, ((initNote.getEndTime() - initNote.getStartTime()) * 20), 20);
    }
  }

  /**
   * Helper function for printPlayedNotes() that returns the index of a pitch
   *
   * @param pitch the pitch that you want to find the index of in the header
   * @return an int representing the index of that pitch in the header
   */
  private int pitchIndex(int pitch) {
    int index = -1; // initializes the index

    // iterates through the list of notes in the header
    for (int i = 0; i < this.notes.size(); i++) {
      // iterates through all the played notes
      for (int r = 0; r < this.model.getPlayedNotes().size(); r++) {
        // checks if the pitch of the played note is the same as the pitch given
        if (this.notes.get(i).getPitch() == pitch) {
          index = i; // sets the index equal to the index of the header list
        }
      }
    }

    return index;
  }

  /**
   * Prints the grids as individual rectangles and populates the graphic based on the length of the
   * music piece
   *
   * @param g the graphic for the grid to be drawn on
   */
  public void printGrid(Graphics g) {
    int musicLength = this.model.getSong().lastKey(); // the last beat in the song
    Graphics2D g2 = (Graphics2D) g; // casts the graphic as a 2 dimensional graphic
    // iterates through the list of notes in the header
    for (int pitch = 0; pitch < this.notes.size(); pitch++) {
      if (this.notes.get(pitch).getPitch() % 12 == 0) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(offsetX, (pitch * 20) + offsetY, (musicLength * 20) + offsetX + 80,
            (pitch * 20) + offsetY);
      }
      for (int beat = 0; beat <= musicLength; beat++) { // iterates from the first to last beat
        if (beat % 4 == 0) { // draws the grid every 4 beats
          g2.setColor(Color.BLACK); // sets the grid color to black
          g2.setStroke(new BasicStroke(2)); // sets the stroke for the grid
          // formula to draw the grid based on the beat number and offset
          g2.drawRect(((beat * 20) + offsetX), ((pitch * 20) + offsetY), 80, 20);
        }
      }
    }
  }

  public void printCursor(Graphics g) {
    int pitchLength = this.model.getNotes().size(); // length of the pitch header
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.RED); // color of the line
    g2.setStroke(new BasicStroke(3)); // widht of the stroke
    // the line stops drawing after the song ends
    if (this.model.getCurrentBeat() != this.model.getSong().lastKey()) {
      // draws the red line
      g2.drawLine((this.model.getCurrentBeat() * 20) + offsetX, offsetY,
          (this.model.getCurrentBeat() * 20) + offsetX,
          (pitchLength * 20) + offsetY);
    }
  }
  public void printRepeat(Graphics g) {
    int pitchLength = this.model.getNotes().size();
    Graphics2D g2= (Graphics2D) g;
    g2.setColor(Color.BLUE);
    g2.setStroke(new BasicStroke(3));
    for (int i = 0; i < this.model.getRepeats().size(); i++) {
      g2.drawLine((this.model.getRepeats().get(i).getRepeatBeat() * 20) + offsetX, offsetY,
          (this.model.getRepeats().get(i).getRepeatBeat() * 20) + offsetX, (pitchLength * 20) + offsetY);
    }
  }
}
