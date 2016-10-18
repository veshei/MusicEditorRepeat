package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a {@code KeyboardHandler} class.
 */
public final class KeyboardHandler implements KeyListener {
  private Map<Integer, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  private Map<Integer, Runnable> keyReleasedMap;
  private List<Integer> keysPressed;

  /**
   * Constructs an instance of a {@code KeyboardHandler}.
   */
  public KeyboardHandler() {
    this.keyTypedMap = new HashMap<>();
    this.keyPressedMap = new HashMap<>();
    this.keyReleasedMap = new HashMap<>();
    this.keysPressed = new ArrayList<>();
  }

  /**
   * runs the key if it has been typed
   *
   * @param e the key event
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (this.keyTypedMap.containsKey(e.getKeyCode()))
      this.keyTypedMap.get(e.getKeyCode()).run();
  }

  /**
   * runs the key if the key has been pressed
   *
   * @param e the key event
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (this.keyPressedMap.containsKey(e.getKeyCode()))
      this.keyPressedMap.get(e.getKeyCode()).run();
  }

  /**
   * runs the key if it has been released
   *
   * @param e the key event
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (this.keyReleasedMap.containsKey(e.getKeyCode()))
      this.keyReleasedMap.get(e.getKeyCode()).run();
  }

  /**
   * Gets the map of keys typed of the {@code KeyboardHandler}.
   *
   * @return a map of keys typed
   */
  public Map<Integer, Runnable> getKeyTypedMap() {
    return this.keyTypedMap;
  }

  /**
   * Gets the map of keys pressed of the {@code KeyboardHandler}.
   *
   * @return a map of keys pressed
   */
  public Map<Integer, Runnable> getKeyPressedMap() {
    return this.keyPressedMap;
  }

  /**
   * Gets the map of keys released of the {@code KeyboardHandler}.
   *
   * @return a map of keys released
   */
  public Map<Integer, Runnable> getKeyReleasedMap() {
    return this.keyReleasedMap;
  }

  /**
   * Installs a {@code Runnable} by adding a key event to the map of keys typed.
   *
   * @param keyCode the key code of the key event
   * @param r       the Runnable
   */
  public void addKeyTyped(int keyCode, Runnable r) {
    this.keyTypedMap.putIfAbsent(keyCode, r);
  }

  /**
   * Installs a {@code Runnable} by adding a key event to the map of keys pressed.
   *
   * @param keyCode the key code of the key event
   * @param r       the Runnable
   */
  public void addKeyPressed(int keyCode, Runnable r) {
    this.keyPressedMap.putIfAbsent(keyCode, r);
  }

  /**
   * Installs a {@code Runnable} by adding a key event to the map of keys released.
   *
   * @param keyCode the key code of the key event
   * @param r       the Runnable
   */
  public void addKeyReleased(int keyCode, Runnable r) {
    this.keyReleasedMap.putIfAbsent(keyCode, r);
  }
}
