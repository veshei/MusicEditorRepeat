package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

/**
 * Created by veronicashei on 6/22/16.
 */

/**
 * Class to set the text for the combo box to either the default
 * title or the correct String based on the index
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
  private String title;

  public ComboBoxRenderer(String title) {
    this.title = title;
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value,
                                                int index, boolean isSelected, boolean hasFocus) {
    if (index == -1 && value == null)
      setText(this.title);
    else
      setText(value.toString());
    return this;
  }
}

