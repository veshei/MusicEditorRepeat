package cs3500.music.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.INote;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final IMusicEditor c;

  /**
   * Constructs an instance of a {@code MidiViewImpl}.
   *
   * @param c   an {@code IMusicEditor}
   */
  public MidiViewImpl(IMusicEditor c) {
    try {
      this.synth = MidiSystem.getSynthesizer();  // gets the MIDI synthesizer
      this.receiver = synth.getReceiver();  // gets the receiver from the synthesizer
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      throw new IllegalStateException("Error in getting the synthesizer and receiver.");
    }

    this.c = c;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  /**
   * Sends MIDI messages to play an {@code INote}.
   *
   * @throws InvalidMidiDataException
   */
  public void playNote() throws InvalidMidiDataException {
    if (this.c.getSong().containsKey(c.getCurrentBeat())) {
      ArrayList<INote> notes = c.getNotesAtBeat(c.getCurrentBeat());

      for (INote note : notes) {
        // if the note is a start note
        if (note.getStartTime() == c.getCurrentBeat()) {
          // send new message
          MidiMessage start = null;
          try {
            // start message
            start = new ShortMessage(ShortMessage.NOTE_ON,
                note.getInstrument() - 1,
                note.getPitch(),
                note.getVolume());
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
          this.receiver.send(start, -1);

          // stop message at end beat
          MidiMessage stop = null;
          try {
            // stop message
            stop = new ShortMessage(ShortMessage.NOTE_OFF,
                note.getInstrument() - 1,
                note.getPitch(),
                note.getVolume());
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }

          this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
        }
      }
    }
  }

  /**
   * Calls the playNote() method and updates the beat tracker.
   *
   * @param beat    the current beat to update to
   * @throws InvalidMidiDataException
   */
  @Override
  public void updateTime(int beat) {
    try {
      this.playNote();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    c.setCurrentBeat(beat);
  }

  /**
   * Plays the music piece of the {@code IMusicEditor}. Creates a {@code Timer} and schedules a task
   * to play each note.
   *
   */
  @Override
  public void initialize() {
    long beatLength;

    // rounds the beat length up to 1 if it's less than 1000
    if (this.c.getTempo() < 1000)
      beatLength = 1;
    else
      beatLength = this.c.getTempo() / 1000;

    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        updateTime(c.getCurrentBeat() + 1);
      }
    };

    // Set timer up
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(timerTask, 0, beatLength);
  }
}
