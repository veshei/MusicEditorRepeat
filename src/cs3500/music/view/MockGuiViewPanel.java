package cs3500.music.view;

import java.util.List;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.INote;
import cs3500.music.model.IRepeatEditor;
import cs3500.music.model.Note;

/**
 * Created by veronicashei on 6/17/16.
 */

/**
 * A mock gui view that represents the different coordinates of each aspect of the view in String
 * form. This allows us to test if the given input is returning the correct output.
 */
public class MockGuiViewPanel extends ConcreteGuiViewPanel {
  IMusicEditor model; // the model that is used by the view

  public MockGuiViewPanel(IRepeatEditor model) {
    super(model); // takes the model from the ConcreteGuiViewPanel
    this.model = model;
  }

  public String printPitches() {
    int lowest = this.model.getNotes().get(0).getPitch();
    int highest = this.model.getNotes().get(this.model.getNotes().size() - 1).getPitch();
    int pitchHeight = 31;
    StringBuilder stringBuilder = new StringBuilder();

    for (int pitch = lowest; pitch <= highest; pitch++) {
      Note n = new Note(pitch);
      stringBuilder.append(n.toString() + "(5, " + String.valueOf(pitchHeight) + ")\n");
      pitchHeight = pitchHeight + MEASURE_Y;
    }
    return stringBuilder.toString();
  }

  public String printBeatHeader() {
    int songLength = this.model.getSong().lastKey();
    int beatX = 40;
    int beatY = 10;
    StringBuilder stringBuilder = new StringBuilder();
    for (int measure = 0; measure <= songLength; measure++) {
      if (measure % 4 == 0) {
        stringBuilder.append(String.valueOf(measure) + "     ");
      }
    }
    return stringBuilder.toString();
  }

  public String printPlayedNotes() {
    List<INote> initNotes = this.model.getPlayedNotes();
    int offsetX = 40;
    int offsetY = 20;
    StringBuilder stringBuilder = new StringBuilder();

    int p1, p2;
    for (INote initNote : initNotes) {
      p1 = (initNote.getStartTime() * 20) + offsetX;
      p2 = (this.pitchIndex(initNote.getPitch()) * 20) + offsetY;

      stringBuilder.append("(Black, ");
      stringBuilder.append(String.valueOf(p1) + ", ");
      stringBuilder.append(String.valueOf(p2) + ", ");
      stringBuilder.append(String.valueOf(20) + ", ");
      stringBuilder.append(String.valueOf(20) + ")\n");

      stringBuilder.append("(Green, ");
      stringBuilder.append(String.valueOf(p1 + 20) + ", ");
      stringBuilder.append(String.valueOf(p2) + ", ");
      stringBuilder.append(String.valueOf((initNote.getEndTime()
          - initNote.getStartTime()) * 20) + ", ");
      stringBuilder.append(String.valueOf(20) + ")\n");
    }
    return stringBuilder.toString();
  }

  public String printGrid() {
    int musicLength = this.model.getSong().lastKey();
    int offsetX = 40;
    int offsetY = 20;
    StringBuilder stringBuilder =
        new StringBuilder("Coordinates of each grid in terms of x and y\n");
    for (int pitch = 0; pitch < this.model.getNotes().size(); pitch++) {
      for (int beat = 0; beat <= musicLength; beat++) {
        if (beat % 4 == 0) {
          stringBuilder.append("(");
          stringBuilder.append(String.valueOf((beat * 20) + offsetX) + ", ");
          stringBuilder.append(String.valueOf(((pitch * 20) + offsetY)) + ", ");
          stringBuilder.append(String.valueOf(80) + ", ");
          stringBuilder.append(String.valueOf(20) + ")\n");
        }
      }
    }
    return stringBuilder.toString();
  }

  private int pitchIndex(int pitch) {
    int index = -1;

    for (int i = 0; i < this.model.getNotes().size(); i++) {
      for (int r = 0; r < this.model.getPlayedNotes().size(); r++) {
        if (this.model.getNotes().get(i).getPitch() == pitch) {
          index = i;
        }
      }
    }

    return index;
  }
}
