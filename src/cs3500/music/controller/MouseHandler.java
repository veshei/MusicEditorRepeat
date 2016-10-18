package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * a class to represent the different mouse handlers
 */
public class MouseHandler implements MouseListener {
  private Map<Integer, Runnable> mouseClickedMap;
  private Map<Integer, Runnable> mousePressedMap;
  private Map<Integer, Runnable> mouseReleasedMap;
  private int x, y;

  public MouseHandler() {
    this.mouseClickedMap = new HashMap<>();
    this.mousePressedMap = new HashMap<>();
    this.mouseReleasedMap = new HashMap<>();
  }

  /**
   * runs the mouse if it has been clicked
   *
   * @param e the mouse event
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    this.x = e.getX();
    this.y = e.getY();

    if (this.mouseClickedMap.containsKey(e.getButton()))
      this.mouseClickedMap.get(e.getButton()).run();
  }

  /**
   * runs the mouse if it has been pressed
   *
   * @param e the mouse event
   */
  @Override
  public void mousePressed(MouseEvent e) {
    this.x = e.getX();
    this.y = e.getY();

    if (this.mousePressedMap.containsKey(e.getButton()))
      this.mousePressedMap.get(e.getButton()).run();
  }

  /**
   * runs the mouse if it has been released
   *
   * @param e the mouse event
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    this.x = e.getX();
    this.y = e.getY();

    if (this.mouseReleasedMap.containsKey(e.getButton()))
      this.mouseReleasedMap.get(e.getButton()).run();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  public Map<Integer, Runnable> getMouseClickedMap() {
    return this.mouseClickedMap;
  }

  public Map<Integer, Runnable> getMousePressedMap() {
    return this.mousePressedMap;
  }

  public Map<Integer, Runnable> getMouseReleasedMap() {
    return this.mouseReleasedMap;
  }

  /**
   * Installs a {@code Runnable} by adding a mouse event to the map of mouse clicked.
   *
   * @param mouseCode the mouse code of the mouse event
   * @param r         the Runnable
   */
  public void addMouseClicked(int mouseCode, Runnable r) {
    this.mouseClickedMap.putIfAbsent(mouseCode, r);
  }

  /**
   * Installs a {@code Runnable} by adding a mouse event to the map of mouse pressed.
   *
   * @param mouseCode the mouse code of the mouse event
   * @param r         the Runnable
   */
  public void addMousePressed(int mouseCode, Runnable r) {
    this.mousePressedMap.putIfAbsent(mouseCode, r);
  }

  /**
   * Installs a {@code Runnable} by adding a mouse event to the map of mouse released.
   *
   * @param mouseCode the mouse code of the mouse event
   * @param r         the Runnable
   */
  public void addMouseReleased(int mouseCode, Runnable r) {
    this.mouseReleasedMap.putIfAbsent(mouseCode, r);
  }

  /**
   * Gets the X of the {@code MouseHandler}.
   *
   * @return the X of the MouseHandler
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the Y of the {@code MouseHandler}
   *
   * @return the Y of the MouseHandler
   */
  public int getY() {
    return this.y;
  }
}
