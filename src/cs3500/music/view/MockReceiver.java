package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Created by veronicashei on 6/17/16.
 */

/**
 * A MockReceiver that acts as a receiver for the mock midi device, lets us test to see if the given
 * input is the same as the output
 */
public class MockReceiver implements Receiver {
  private StringBuilder output;

  public MockReceiver(StringBuilder output) {
    this.output = output;
  }

  /**
   * send method to represent the right String output of the midi input
   *
   * @param message   the midi input
   * @param timeStamp the time stamp of the midi
   */
  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage shortMessage = (ShortMessage) message;
    int instrument = shortMessage.getChannel();
    int pitchValue = shortMessage.getData1();
    int volume = shortMessage.getData2();

    if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
      this.output.append("note " + "ON" + " ").append(instrument).append(" ").append
          (pitchValue).append(" ").append(volume).append(" ").append(timeStamp).append("\n");
    } else if (shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
      this.output.append("note " + "OFF" + " ").append(instrument).append(" ").append
          (pitchValue).append(" ").append(volume).append(" ").append(timeStamp).append("\n");
    }
  }

  @Override
  public void close() {
    // do nothing
  }
}
