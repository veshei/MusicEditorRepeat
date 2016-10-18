package cs3500.music.view;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IRepeatEditor;

public class ViewFactory {
  private final IRepeatEditor c;
  private IView view;

  /**
   * Constructor for ViewFactory
   *
   * @param c           the composition piece
   */
  public ViewFactory(IRepeatEditor c) {
    this.c = c;
  }

  /**
   * Returns the view given the desired string and composition
   *
   * @param viewType    the view to generate
   * @return the desired view
   */
  public IView generateView(String viewType) {
    switch (viewType) {
      case "console":
        this.view = new ConsoleViewImpl(this.c);
        break;
      case "gui":
        this.view = new GuiViewFrame(this.c);
        break;
      case "midi":
        this.view = new MidiViewImpl(this.c);
        break;
      case "composite":
        CompositeViewFrame guiView = new CompositeViewFrame(this.c);
        MidiViewImpl midiView = new MidiViewImpl(this.c);
        this.view = new CompositeViewImpl(this.c, guiView, midiView);
        break;
      default:
        throw new IllegalArgumentException("Invalid view choice.");
    }

    this.view.initialize();

    return this.view;
  }
}