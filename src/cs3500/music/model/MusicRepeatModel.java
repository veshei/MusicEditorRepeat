package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.util.CompositionRepeatBuilder;

/**
 * Created by veronicashei on 6/27/16.
 */
public class MusicRepeatModel extends MusicEditorModel implements IRepeatEditor {
  private List<Repeat> repeats;

  public MusicRepeatModel() {
    super();
    this.repeats = new ArrayList<>();
  }

  public void addRepeat(Repeat repeat) {
    if (repeat.getRepeatBeat() <= 0) {
      throw new IllegalArgumentException("Repeat beat cannot exist at a negative beat");
    } else if(repeat.getRepeatBeat() > getSong().lastKey()) {
      throw new IllegalArgumentException("Repeat cannot exist past the music length");
    }
    if (!repeats.contains(repeat)) {
      this.repeats.add(repeat);
    }
    /*for (int i = 0; i < repeats.size(); i++) {
      if (repeat.equals(repeats.get(i))) {
        flag = true;
        break;
      }
    }

    if (!flag) {
      this.repeats.add(repeat);
    }*/
  }

  public void removeRepeat(Repeat repeat) {
    boolean flag = false;
    if (repeat.getRepeatBeat() < 0) {
      throw new IllegalArgumentException("Repeat beat cannot exist at a negative beat");
    } else if(repeat.getRepeatBeat() > getSong().lastKey()) {
      throw new IllegalArgumentException("Repeat cannot exist past the music length");
    }
    for (int i = 0; i < repeats.size(); i++) {
      if (repeat.equals(repeats.get(i))) {
        flag = true;
        break;
      }
    }
    if (flag) {
      this.repeats.remove(repeat);
    }
  }

  public List<Repeat> getRepeats() {
    return this.repeats;
  }

  public static final class Builder implements CompositionRepeatBuilder<IRepeatEditor> {
    private IRepeatEditor model;

    public Builder() {
      this.model = new MusicRepeatModel();
    }

    @Override
    public IRepeatEditor build() {
      return this.model;
    }

    /**
     * Sets the tempo of the model
     *
     * @param tempo The speed, in microseconds per beat
     * @return the model with the updated tempo
     */
    @Override
    public CompositionRepeatBuilder<IRepeatEditor> setTempo(int tempo) {
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
    public CompositionRepeatBuilder<IRepeatEditor> addNote(int start, int end, int instrument,
                                                    int pitch, int volume) {
      Note newNote = new Note(start, end, instrument, pitch, volume);
      this.model.addNote(newNote);
      return this;
    }

    @Override
    public CompositionRepeatBuilder<IRepeatEditor> removeNote(int start, int end, int instrument,
                                                       int pitch, int volume) {
      Note newNote = new Note(start, end, instrument, pitch, volume);
      this.model.removeNote(newNote);
      return this;
    }

    @Override
    public CompositionRepeatBuilder<IRepeatEditor> addRepeat(int repeatBeat) {
      Repeat repeat = new Repeat(repeatBeat);
      this.model.addRepeat(repeat);
      return this;
    }
  }

  }
