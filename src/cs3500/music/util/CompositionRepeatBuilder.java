package cs3500.music.util;

import java.util.List;

import cs3500.music.model.Repeat;

/**
 * Created by veronicashei on 6/27/16.
 */
public interface CompositionRepeatBuilder<T> extends CompositionBuilder<T> {
  CompositionRepeatBuilder<T> addRepeat(int repeatBeat);
}
