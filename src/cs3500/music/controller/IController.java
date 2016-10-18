package cs3500.music.controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * interface for the controller class, an extra level of abstraction
 */
public interface IController {

  void initialize();

  /**
   * Controller function to add a note.
   */
  void addNote(String start, String end, String instrument,
               String pitch, String octave, String volume);

  /**
   * Controller function to remove a note.
   */
  void removeNote(String start, String end, String instrument,
                  String pitch, String octave, String volume);

  /**
   * Gets the {@code KeyListener} of the {@code IController}.
   *
   * @return a key listener
   */
  KeyListener getKeyListener();

  /**
   * Gets the {@code MouseListener} of the {@code IController}.
   *
   * @return a mouse listener
   * @throws IllegalArgumentException if no mouse listener
   */
  MouseListener getMouseListener() throws IllegalArgumentException;

  /**
   * Adds a {@code MouseListener} to the {@code IController}.
   *
   * @param m the mouse listener to add
   */
  void addMouseListener(MouseListener m);

  /**
   * Removes a {@code MouseListener} from the {@code IController}.
   *
   * @param m the mouse listener to remove
   * @throws IllegalArgumentException if no mouse listener
   */
  void removeMouseListener(MouseListener m) throws IllegalArgumentException;
}
