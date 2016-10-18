import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IRepeatEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicRepeatModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionRepeatBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ViewFactory;

/**
 * Music editor, contains main class
 */
public final class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException,
      MidiUnavailableException {
    if (args.length != 2) {
      System.err.println("Invalid input size");
      System.exit(0);
    }
    // Empty composition to pass into parser
    CompositionRepeatBuilder<IRepeatEditor> cB = new MusicRepeatModel.Builder();
    // Read in file from argument
    File file = new File(args[0]);
    Readable r = new FileReader(file);
    // Parse file
    MusicReader.parseFile(r, cB);
    // Create view
    ViewFactory view = new ViewFactory(cB.build());
    view.generateView(args[1]);
  }
}