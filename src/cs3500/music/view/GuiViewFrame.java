package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IRepeatEditor;
import cs3500.music.model.Octaves;
import cs3500.music.model.Pitches;

/**
 * A skeleton Frame (i.e., a window) in Swing that implements the auto scroll
 */
public class GuiViewFrame extends JFrame implements GuiView {
  private static final int WIDTH = 1400; // constant to represent the window width
  private static final int HEIGHT = 640; // constant to represent the window height
  private JPanel backgroundPanel; // the background panel to put the button panel on
  private JPanel buttonPanel; // the panel to hold the buttons
  private ConcreteGuiViewPanel musicPanel; // the panel that holds the music editor output
  private JScrollPane scrolls; // the pane that implements scrolling
  private IRepeatEditor model; // an instance of the model
  private JButton addNote; // button for adding notes
  private JButton removeNote; // button for removing notes
  private JComboBox pitch; // drop down box for pitches
  private JComboBox octave; // drop down box for octaves
  private JTextField startTime; // text field for the start time of a note
  private JTextField endTime; // text field for the end time of a note
  private JTextField instrument; // text field for the instrument of a note
  private JTextField volume; // text field for the volume of a note
  private IController controller; // an instance of the controller
  private GuiView view; // an instance of the class
  private int beat; // beat variable to update the auto scroll
  private Timer timer; // timer variable to keep track of the auto scroll
  private int width; // width of the visible view
  private int count;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(IRepeatEditor model) {
    this.model = model;
    this.buttonPanel = new JPanel();
    // sets the display panel to our gui view
    this.musicPanel = new ConcreteGuiViewPanel(this.model);
    this.backgroundPanel = new JPanel();
    this.addNote = new JButton("Add note");
    this.removeNote = new JButton("Remove note");
    this.pitch = new JComboBox(Pitches.values()); // sets the drop down values to the pitch enum
    this.octave = new JComboBox(Octaves.values()); // sets the drop down values to the octave enum
    this.startTime = new JTextField(10);
    this.endTime = new JTextField(10);
    this.instrument = new JTextField(10);
    this.volume = new JTextField(10);
    this.view = this;
    this.controller = new Controller(this.model, this.view);
    this.beat = 0;
    this.timer = new Timer();
  }

  /**
   * Initializes the view and displays it
   */
  @Override
  public void initialize() {
    this.initializeButtons(); // initializes the buttons
    this.initializeText(); // initializes the text fields
    buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // sets the alignment for button panel
    musicPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // sets the alignment for music panel
    buttonPanel.setMaximumSize(new Dimension(1100, 85)); // limits the size of the button panel
    // ensures that the layout is ordered in the right manner
    backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
    // makes sure the opaque is not on
    backgroundPanel.setOpaque(false);
    // adds the buttons to the background panel
    backgroundPanel.add(buttonPanel);
    // adds the component listener
    backgroundPanel.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
      }
    });
    // sets the background pane to be on the glass pane
    SwingUtilities.getRootPane(this).setGlassPane(backgroundPanel);
    // makes the background visible
    backgroundPanel.setVisible(true);
    // initializes the scrolling
    this.initScrolling();
    // adds the scrolling to the JFrame
    this.add(scrolls);
    // sets the size of the canvas based on the music length
    this.setCanvasSize(this.beatLength(model), this.pitchLength(model));
    // closes the program when the window closes
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // packs the frame so that the content is at or above the preferred  size
    this.pack();
    // initializes the display so that it is visible
    this.setVisible(true);
    // initializes the buttons
    this.initButtonActions();
    // updates the frame
    this.revalidate();
    // paints the updated frame
    this.repaint();
    // sets the focus for the keyboard functions
    this.setFocusable(true);
    // requests the focus for the keyboard functions
    this.requestFocus();
  }

  /**
   * initializes the buttons
   */
  private void initializeButtons() {
    FlowLayout layout = new FlowLayout();
    layout.setHgap(5); // sets the horizontal gap
    layout.setVgap(5); // sets the vertical gap
    buttonPanel.setLayout(layout); // sets the layout of the button panel to flow layout
    pitch.setPreferredSize(new Dimension(90, 90)); // sets the size of the pitch button
    pitch.setRenderer(new ComboBoxRenderer("Pitch: ")); // sets the combo box for the pitch
    pitch.setSelectedIndex(-1); // sets the initial index to -1 ie the default title
    octave.setPreferredSize(new Dimension(100, 100)); // sets the size of the octave button
    octave.setRenderer(new ComboBoxRenderer("Octave: ")); // sets the combo box for the octaves
    octave.setSelectedIndex(-1); // sets the initial index to -1 ie the default title
    buttonPanel.add(addNote); // adds the add note button to the panel
    buttonPanel.add(removeNote); // adds the remove note button to the panel
    buttonPanel.add(pitch); // adds the pitch button to the panel
    buttonPanel.add(octave); // adds the octave button to the panel
  }

  /**
   * initializes the text fields
   */
  private void initializeText() {
    startTime.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Start Time: "),
        startTime.getBorder())); // names the text field start time
    endTime.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("End Time: "),
        endTime.getBorder())); // names the text field end time
    instrument.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Instrument:"),
        instrument.getBorder())); // names the text field instrument
    volume.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Volume: "),
        volume.getBorder())); // names the text field volume
    buttonPanel.add(startTime); // adds the start time text field to the panel
    buttonPanel.add(endTime); // adds the end time text field to the panel
    buttonPanel.add(instrument); // adds the instrument text field to the panel
    buttonPanel.add(volume); // adds the volume text field to the panel
  }

  @Override
  public void updateTime(int currBeat) {
  }

  /**
   * initializes the scrolling
   */
  private void initScrolling() {
    // makes the display panel scrollable
    this.scrolls = new JScrollPane(musicPanel);
    // allows horizontal scrolling if needed
    scrolls.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    // allows vertical scrolling if needed
    scrolls.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // sets the horizontal scrolling to increments of 16
    scrolls.getHorizontalScrollBar().setUnitIncrement(16);
    // sets the vertical scrolling to increments of 16
    scrolls.getVerticalScrollBar().setUnitIncrement(16);
  }

  // sets the preferred size of the window
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(WIDTH, HEIGHT);
  }

  // returns the graphics from the superclass
  public Graphics getGraphics() {
    return super.getGraphics();
  }

  // sets the canvas size for the JScrollPanel
  private void setCanvasSize(int width, int height) {
    // sets the preferred size of the panel
    musicPanel.setPreferredSize(new Dimension(width, height));
    // updates the panel
    musicPanel.revalidate();
    // paints the updated panel
    musicPanel.repaint();
  }

  // Helper function to set the scrolling window pane to scroll to the last beat
  private int beatLength(IMusicEditor model) {
    int lastBeat = model.getSong().lastKey();
    return (lastBeat * 20) + 150;
  }

  // Helper function to set the scrolling window pane to scroll to the last pitch
  private int pitchLength(IMusicEditor model) {
    int lastPitch = model.getNotes().size();
    return (lastPitch * 20) + 150;
  }

  /**
   * sets the controller
   *
   * @param controller the controller input
   */
  @Override
  public void setParent(IController controller) {
    this.controller = controller;
  }

  /**
   * initializes all the button actions
   */
  private void initButtonActions() {
    // adds an action listener for the note button
    this.addNote.addActionListener(new ActionListener() {
      private JFrame frame;

      @Override
      public void actionPerformed(ActionEvent e) {
        // checks if the note fields have been filled out
        if (e.getActionCommand().equals("Add note")) {
          if (pitch.getSelectedIndex() == -1 || octave.getSelectedIndex() == -1 ||
              startTime.getText().equals("") || endTime.getText().equals("") ||
              instrument.getText().equals("") || volume.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Note fields have not been filled in");
          } else {
            try {
              // adds a note based on the inputted note fields
              controller.addNote(startTime.getText(), endTime.getText(), instrument.getText(),
                  pitch.getSelectedItem().toString(), octave.getSelectedItem().toString(),
                  volume.getText());
              // resets the current beat to the start
              model.setCurrentBeat(0);
              // resets the horizontal scroll bar to the start
              scrolls.getHorizontalScrollBar().
                  setValue(scrolls.getHorizontalScrollBar().getMinimum());
              // resets the vertical scroll bar to the start
              scrolls.getVerticalScrollBar().
                  setValue(scrolls.getVerticalScrollBar().getMinimum());
              musicPanel.revalidate();
              musicPanel.repaint();
              // resets the canvas size based on the new music length
              setCanvasSize(beatLength(model), pitchLength(model));
            } catch (IllegalArgumentException i) {
              // pop up message if not all the fields are filled in/correct
              JOptionPane.showMessageDialog(frame, "Invalid input");
            }

          }
        }
      }

      // alows me to use the frame in a lambda
      private ActionListener init(JFrame f) {
        this.frame = f;
        return this;
      }
    }.init(this));
    // adds the actions for remove note
    this.removeNote.addActionListener(new ActionListener() {
      private JFrame frame;
      private IController controller = new Controller(model, view);

      // sets the actions for remove note
      @Override
      public void actionPerformed(ActionEvent e) {
        // checks if the button has been pressed
        if (e.getActionCommand().equals("Remove note")) {
          // checks if the note fields have all been filled in
          if (pitch.getSelectedIndex() == -1 || octave.getSelectedIndex() == -1 ||
              startTime.getText().equals("") || endTime.getText().equals("") ||
              instrument.getText().equals("") || volume.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Note fields have not been filled in");
          } else {
            try {
              controller.removeNote(startTime.getText(), endTime.getText(), instrument.getText(),
                  pitch.getSelectedItem().toString(), octave.getSelectedItem().toString(),
                  volume.getText());

              model.setCurrentBeat(0);
              scrolls.getHorizontalScrollBar().
                  setValue(scrolls.getHorizontalScrollBar().getMinimum());
              scrolls.getVerticalScrollBar().
                  setValue(scrolls.getVerticalScrollBar().getMinimum());
              musicPanel.revalidate();
              musicPanel.repaint();
              setCanvasSize(beatLength(model), pitchLength(model));
            } catch (IllegalArgumentException i) {
              JOptionPane.showMessageDialog(frame, "Invalid input");
            }
          }
        }
      }

      private ActionListener init(JFrame f, IController c) {
        this.frame = f;
        this.controller = c;
        return this;
      }
    }.init(this, controller));
  }

  /**
   * scrolls forward based on the keyboard input
   */
  @Override
  public void scrollForward() {
    this.scrolls.getHorizontalScrollBar().setValue(this.scrolls.getHorizontalScrollBar().getValue()
        + this.scrolls.getHorizontalScrollBar().getUnitIncrement());
  }

  /**
   * scrolls backward based on the keyboard input
   */
  @Override
  public void scrollBackward() {
    this.scrolls.getHorizontalScrollBar().setValue(this.scrolls.getHorizontalScrollBar().getValue()
        - this.scrolls.getHorizontalScrollBar().getUnitIncrement());
  }

  /**
   * scrolls upwards based on the keyboard input
   */
  @Override
  public void scrollUpwards() {
    this.scrolls.getVerticalScrollBar().setValue(this.scrolls.getVerticalScrollBar().getValue()
        - this.scrolls.getVerticalScrollBar().getUnitIncrement());
  }

  /**
   * scrolls downward based on the keyboard input
   */
  @Override
  public void scrollDownwards() {
    this.scrolls.getVerticalScrollBar().setValue(this.scrolls.getVerticalScrollBar().getValue()
        + this.scrolls.getVerticalScrollBar().getUnitIncrement());
  }

  /**
   * getter method for scrolls
   *
   * @return JScrollPane representing the scroll pane
   */
  @Override
  public JScrollPane getScrolls() {
    return this.scrolls;
  }

  /**
   * getter method for the timer
   *
   * @return a timer to update the auto scrolling
   */
  @Override
  public Timer getTimer() {
    return this.timer;
  }

  /**
   * getter method for the music panel
   *
   * @return a panel that displays the music
   */
  @Override
  public JPanel getPanel() {
    return this.musicPanel;
  }


}
