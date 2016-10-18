package cs3500.music.model;

import java.util.List;

/**
 * Created by veronicashei on 6/27/16.
 */
public interface IRepeatEditor extends IMusicEditor {
  List<Repeat> getRepeats();

  void addRepeat(Repeat repeat);

  void removeRepeat(Repeat repeat);
}
