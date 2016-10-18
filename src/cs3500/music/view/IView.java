package cs3500.music.view;

/**
 * An extra level of abstraction for all the different views
 */
public interface IView {
  void initialize();

  void updateTime(int beat);
}