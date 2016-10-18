package cs3500.music.view;

import java.util.Timer;

import javax.swing.*;

import cs3500.music.controller.IController;

/**
 * Created by veronicashei on 6/21/16.
 */

/**
 * interface for the methods concerning keyboard and mouse handlers
 */
public interface GuiView extends IView {

  void setParent(IController controller);

  void scrollForward();

  void scrollBackward();

  void scrollUpwards();

  void scrollDownwards();

  JScrollPane getScrolls();

  Timer getTimer();

  JPanel getPanel();

//  void setBeat(int newBeat);

//  int getWidth();

//  int getCount();

//  void setWidth(int w);

//  void setCount(int c);

//  void initRepeat();

//  void addKeyListener(KeyListener keyListener);
//
//  void addMouseListener(MouseListener mouseListener);
//
//  void jumpToBeginning();
//
//  void jumpToEnd();
}
