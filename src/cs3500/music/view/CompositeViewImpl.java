package cs3500.music.view;

import java.util.Timer;

import javax.swing.*;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IRepeatEditor;

/**
 * Represents a {@code CompositeViewImpl} class. This synchronizes the audio and visual views.
 */
public final class CompositeViewImpl extends JFrame implements GuiView {
  private final IRepeatEditor c;
  private final GuiView guiView;
  private final MidiViewImpl midiView;
  private final IController controller;
  private Timer timer;
  /**
   * Constructs an instance of a {@code CompositeViewImpl}.
   *
   * @param c             the {@code IMusicEditor} object to use as a model
   * @param compositeView the {@code GuiViewFrame} object to generate a visual view
   * @param midiView      the {@code MidiViewImpl} object to generate audio
   */
  public CompositeViewImpl(IRepeatEditor c, CompositeViewFrame compositeView,
                           MidiViewImpl midiView) {
    this.c = c;
    this.guiView = compositeView;
    this.midiView = midiView;
    this.controller = new Controller(this.c, this);
    this.timer = new Timer();
  }

  /**
   * initializes the two displays
   */
  @Override
  public void initialize() {
    this.guiView.initialize();
    this.midiView.initialize();
  }

  @Override
  public void updateTime(int currBeat) {
  }

  public void setParent(IController controller) {

  }

  @Override
  public void scrollForward() {
    this.guiView.scrollForward();
  }

  @Override
  public void scrollBackward() {
    this.guiView.scrollBackward();
  }

  @Override
  public void scrollUpwards() {
    this.guiView.scrollUpwards();
  }

  @Override
  public void scrollDownwards() {
    this.guiView.scrollDownwards();
  }

  @Override
  public JScrollPane getScrolls() {
    return this.guiView.getScrolls();
  }

  @Override
  public Timer getTimer() {
    return this.timer;
  }

  @Override
  public JPanel getPanel() {
    return this.guiView.getPanel();
  }

}
