package cs3500.music.controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.TimerTask;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.INote;
import cs3500.music.model.IRepeatEditor;
import cs3500.music.model.MusicRepeatModel;
import cs3500.music.model.Note;
import cs3500.music.view.GuiView;

/**
 * Represents a {@code Controller} class.
 */
public class Controller implements IController {
  private final IRepeatEditor c;
  private final GuiView guiView;
  private KeyboardHandler keyHandler;
  private MouseListener mouseHandler;
  private int currentBeat;
  private int beat;

  /**
   * Constructs an instance of a {@code Controller}.
   *
   * @param c       the {@code IMusicEditor} model
   * @param guiView the {@code GuiView} view
   */
  public Controller(IRepeatEditor c, GuiView guiView) {
    this.c = c;
    this.guiView = guiView;
    this.keyHandler = new KeyboardHandler();

    guiView.setParent(this);
    this.initialize();
//    this.initRepeat();
  }

  /**
   * initializes all the key presses
   */
  @Override
  public void initialize() {
    // pauses the music
    this.keyHandler.addKeyPressed(KeyEvent.VK_P, new Runnable() {
      @Override
      public void run() {
        if (c.getCurrentBeat() != c.getSong().lastKey())
          currentBeat = c.getCurrentBeat();
        c.setCurrentBeat(c.getSong().lastKey());
        guiView.getTimer().cancel();
      }
    });

    // resumes the piece
    this.keyHandler.addKeyPressed(KeyEvent.VK_R, new Runnable() {
      @Override
      public void run() {
        if (currentBeat != 0)
          c.setCurrentBeat(currentBeat);
/*        if (c.getCurrentBeat() < ((guiView.getWidth() - 40) / 20) / guiView.getCount()) {
          guiView.setCount(1);
        }
        if (guiView.getScrolls().getHorizontalScrollBar().getValue()
            == guiView.getScrolls().getHorizontalScrollBar().getMaximum() - guiView.getScrolls().getHorizontalScrollBar().getVisibleAmount()) {
          guiView.getScrolls().getHorizontalScrollBar().setValue((c.getCurrentBeat() * 20) + 40);
          guiView.setCount((int) Math.ceil((((guiView.getScrolls().getVisibleRect().width - 40) / 20) / guiView.getCount())));
          guiView.setWidth(guiView.getScrolls().getVisibleRect().width * guiView.getCount());
        }*/
        guiView.getPanel().revalidate();
        guiView.getPanel().repaint();
        long beatLength;
        // rounds the beat length up to 1 if it's less than 1000
        if (c.getTempo() < 1000)
          beatLength = 1;
        else
          beatLength = c.getTempo() / 1000;
        TimerTask timerTask = new TimerTask() {
          @Override
          public void run() {
            updateTime(c.getCurrentBeat() + 1);
          }
        };
        java.util.Timer timer2 = new java.util.Timer();
        timer2.scheduleAtFixedRate(timerTask, 0, beatLength);
      }
    });

    // goes to the start of the piece
    this.keyHandler.addKeyPressed(KeyEvent.VK_HOME, new Runnable() {
      @Override
      public void run() {
        c.setCurrentBeat(0);
        guiView.getScrolls().getHorizontalScrollBar().setValue(
            // sets the scroll to the beginning
            guiView.getScrolls().getHorizontalScrollBar().getMinimum());
//        guiView.setCount(1);
//        guiView.setWidth(guiView.getScrolls().getVisibleRect().width);
        long beatLength;
        // rounds the beat length up to 1 if it's less than 1000
        if (c.getTempo() < 1000)
          beatLength = 1;
        else
          beatLength = c.getTempo() / 1000;
        TimerTask timerTask = new TimerTask() {
          @Override
          public void run() {
            updateTime(c.getCurrentBeat() + 1);
          }
        };
        java.util.Timer timer1 = new java.util.Timer();
        timer1.scheduleAtFixedRate(timerTask, 0, beatLength);
      }
    });

    // goes to the end of the piece
    this.keyHandler.addKeyPressed(KeyEvent.VK_END, new Runnable() {
      @Override
      public void run() {
        c.setCurrentBeat(c.getSong().lastKey());
        guiView.getScrolls().getHorizontalScrollBar().setValue(
            // sets the scroll to the end
            guiView.getScrolls().getHorizontalScrollBar().getMaximum());
//        guiView.setCount(Math.round(c.getSong().lastKey() / ((guiView.getWidth() - 40) / 20)));
        guiView.getTimer().cancel(); // cancels the timer
      }
    });

    // adds scroll right function
    this.keyHandler.addKeyPressed(KeyEvent.VK_RIGHT, new Runnable() {
      @Override
      public void run() {
        guiView.scrollForward();
      }
    });

    // adds scroll left function
    this.keyHandler.addKeyPressed(KeyEvent.VK_LEFT, new Runnable() {
      @Override
      public void run() {
        guiView.scrollBackward();
      }
    });

    // adds scroll up function
    this.keyHandler.addKeyPressed(KeyEvent.VK_UP, new Runnable() {
      @Override
      public void run() {
        guiView.scrollUpwards();
      }
    });

    // adds scroll down function
    this.keyHandler.addKeyPressed(KeyEvent.VK_DOWN, new Runnable() {
      @Override
      public void run() {
        guiView.scrollDownwards();
      }
    });

    ((Component) guiView).addKeyListener(keyHandler);
    ((Component) guiView).addMouseListener(mouseHandler);
  }

  /**
   * updates the scrolling for the keyboard functions
   *
   * @param currBeat the current beat
   */
  private void updateTime(int currBeat) {
    this.guiView.getScrolls().revalidate();
    this.guiView.getScrolls().repaint();
    if (((beat * 20) + 40) % this.guiView.getScrolls().getVisibleRect().width == 0) {
      this.guiView.getScrolls().getHorizontalScrollBar().setValue((beat * 20) + 40);
    }
    beat = currBeat;
/*    guiView.getPanel().revalidate(); // revalidate the panel for flexible window dimensions
    guiView.getPanel().repaint(); // repaints the panel with the updates
    // checks to see if the current beat is greater than or equal to the window size
    if (c.getCurrentBeat() > ((guiView.getWidth() / guiView.getCount()) - 40) / 20) {
      guiView.setWidth(guiView.getWidth() + guiView.getScrolls().getVisibleRect().width);
      guiView.setCount(guiView.getCount() + 1);
      guiView.getScrolls().getHorizontalScrollBar().setValue((c.getCurrentBeat() * 20) + 40); // sets the value for auto scroll
    }
    else if (c.getCurrentBeat() <= (((guiView.getWidth() / guiView.getCount()) - 40) / 20) && guiView.getCount() != 1) {
      System.out.println(guiView.getCount());
      guiView.getScrolls().getHorizontalScrollBar().setValue((c.getCurrentBeat() * 20) + 40); // sets the value for auto scroll
    }*/
  }

  /**
   * adds a mouse listener
   *
   * @param m the mouse listener to add
   */
  @Override
  public void addMouseListener(MouseListener m) {
    this.mouseHandler = m;
  }

  /**
   * removes the mouse listener
   *
   * @param m the mouse listener to remove
   * @throws IllegalArgumentException if it is an invalid mouse listener
   */
  @Override
  public void removeMouseListener(MouseListener m) throws IllegalArgumentException {
    if (this.mouseHandler == null)
      throw new IllegalArgumentException("There is no mouse listener in this controller.");
    else
      this.mouseHandler = null;
  }

  /**
   * getter method for key listener
   *
   * @return a key listener
   */
  @Override
  public KeyListener getKeyListener() {
    return this.keyHandler;
  }

  /**
   * getter method for mouse listener
   *
   * @return a mouse listener
   * @throws IllegalArgumentException if it is an invalid mouse listener
   */
  @Override
  public MouseListener getMouseListener() throws IllegalArgumentException {
    if (this.mouseHandler == null)  // if there is no mouse listener set
      throw new IllegalArgumentException("There is no mouse listener in this controller.");

    return this.mouseHandler;
  }

  /**
   * adds the note to the composite and gui view
   *
   * @param start      start time in String form
   * @param end        end time in String form
   * @param instrument instrument in String form
   * @param pitch      pitch in String form
   * @param octave     octave in String form
   * @param volume     volume in String form
   */
  @Override
  public void addNote(String start, String end, String instrument,
                      String pitch, String octave, String volume) {
    int startTime = Integer.parseInt(start);
    int endTime = Integer.parseInt(end);
    int instrumentNum = this.InstrumentToInt(instrument);
    int pitchNum = this.pitchStringToInt(pitch, octave);
    int volumeNum = Integer.parseInt(volume);
    this.c.addNote(new Note(startTime, endTime, instrumentNum, pitchNum, volumeNum));
  }

  /**
   * removes the note from the composite and gui view
   *
   * @param start      start time in String form
   * @param end        end time in String form
   * @param instrument instrument in String form
   * @param pitch      pitch in String form
   * @param octave     octave in String form
   * @param volume     volume in String form
   */
  @Override
  public void removeNote(String start, String end, String instrument,
                         String pitch, String octave, String volume) {
    int startTime = Integer.parseInt(start);
    int endTime = Integer.parseInt(end);
    int instrumentNum = this.InstrumentToInt(instrument);
    int pitchNum = this.pitchStringToInt(pitch, octave);
    int volumeNum = Integer.parseInt(volume);
    INote n;
    for (int i = 0; i < this.c.getPlayedNotes().size(); i++) {
      if (pitchNum == this.c.getPlayedNotes().get(i).getPitch()
          && startTime == this.c.getPlayedNotes().get(i).getStartTime()
          && endTime == this.c.getPlayedNotes().get(i).getEndTime()) {
        n = this.c.getPlayedNotes().get(i);
        this.c.removeNote(n);
      } else {
        this.c.removeNote(new Note(startTime, endTime, instrumentNum, pitchNum, volumeNum));
      }
    }
  }

  /**
   * helper function to convert a String pitch to int
   *
   * @param pitch  the String pitch
   * @param octave the String octave
   * @return an int representing the pitch
   */
  private int pitchStringToInt(String pitch, String octave) {
    int pitches;
    int octaves;
    switch (pitch) {
      case "C":
        pitches = 0;
        break;
      case "CSharp":
        pitches = 1;
        break;
      case "D":
        pitches = 2;
        break;
      case "DSharp":
        pitches = 3;
        break;
      case "E":
        pitches = 4;
        break;
      case "F":
        pitches = 5;
        break;
      case "FSharp":
        pitches = 6;
        break;
      case "G":
        pitches = 7;
        break;
      case "GSharp":
        pitches = 8;
        break;
      case "A":
        pitches = 9;
        break;
      case "ASharp":
        pitches = 10;
        break;
      case "B":
        pitches = 11;
        break;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
    switch (octave) {
      case "One":
        octaves = 1;
        break;
      case "Two":
        octaves = 2;
        break;
      case "Three":
        octaves = 3;
        break;
      case "Four":
        octaves = 4;
        break;
      case "Five":
        octaves = 5;
        break;
      case "Six":
        octaves = 6;
        break;
      case "Seven":
        octaves = 7;
        break;
      case "Eight":
        octaves = 8;
        break;
      case "Nine":
        octaves = 9;
        break;
      case "Ten":
        octaves = 10;
        break;
      default:
        throw new IllegalArgumentException("Invalid octave");
    }
    return ((octaves - 1) * 12) + pitches;
  }

  /**
   * helper function to convert a String instrument to the midi channel value
   *
   * @param instrument String instrument
   * @return the int representing the correct channel value
   */
  private int InstrumentToInt(String instrument) {
    int instrumentNum;
    switch (instrument.toLowerCase()) {
      case "piano":
        instrumentNum = 1;
        break;
      case "chromatic percussion":
        instrumentNum = 2;
        break;
      case "organ":
        instrumentNum = 3;
        break;
      case "guitar":
        instrumentNum = 4;
        break;
      case "bass":
        instrumentNum = 5;
        break;
      case "strings":
        instrumentNum = 6;
        break;
      case "ensemble":
        instrumentNum = 7;
        break;
      case "brass":
        instrumentNum = 8;
        break;
      case "reed":
        instrumentNum = 9;
        break;
      case "pipe":
        instrumentNum = 10;
        break;
      case "synth lead":
        instrumentNum = 11;
        break;
      case "synth pad":
        instrumentNum = 12;
        break;
      case "synth effects":
        instrumentNum = 13;
        break;
      case "ethnic":
        instrumentNum = 14;
        break;
      case "percussive":
        instrumentNum = 15;
        break;
      case "sound effects":
        instrumentNum = 16;
        break;
      default:
        throw new IllegalArgumentException("Invalid instrument type");
    }
    return instrumentNum;
  }

/*  public void initRepeat() {
    for (int i = 0; i < this.c.getRepeats().size(); i++) {
      if (i != 0) {
        this.c.setCurrentBeat(this.c.getRepeats().get(i - 1));
        this.guiView.setBeat(this.c.getRepeats().get(i - 1));
//        this.guiView.getScrolls().getHorizontalScrollBar().setValue(((i - 1) * 20) + 80);
      } else {
//        this.c.setCurrentBeat(0);
//        this.guiView.setBeat(0);
//        this.guiView.getScrolls().getHorizontalScrollBar().setValue(80);
      }
    }
  }*/
}

