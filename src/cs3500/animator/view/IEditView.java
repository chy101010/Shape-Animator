package cs3500.animator.view;

import java.util.List;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;

/**
 * Represents an user interactive visualization for the AnimationModel. It provides the
 * functionalities that allow a client to adjust the bounds/dimensions. It can displays animation,
 * textual keyframes, identities, current tick, current delay, and looping status. It can request
 * functionalities from the given {@code listener}.
 */
public interface IEditView extends IView {
  /**
   * Renders the visualization of the AnimatorModel in this IView. The type of visualization is
   * delegated to the class that implements this interface.
   */
  void display();

  /**
   * Takes in a bundle of action reacts, encapsulated in the {@code listener}, that views can
   * request service from when user interactions occurred.
   *
   * @param listener that offers the action reacts.
   */
  void setFeatures(Features listener);

  /**
   * Sets up the displaying JFrame's and JPanel's bounds/dimension using the given {@code canvas}.
   *
   * @param canvas an array that holds the offsets of the animation.
   */
  void setCanvas(int[] canvas);

  /**
   * Refresh the animation.
   */
  void repaint();

  /**
   * Takes in a list of shapes and displays them.
   *
   * @param shapes list of shapes to be displayed.
   */
  void displayShapes(List<IShape> shapes);

  /**
   * Takes in the current tick value and display it.
   *
   * @param tick current tick
   */
  void displayTick(int tick);


  /**
   * Takes in the current timer's delay and display it.
   *
   * @param delay current timer's delay
   */
  void displayDelay(int delay);

  /**
   * Takes in the status of the loop back and display it.
   *
   * @param loop current loop back status. true means the loop back is enabled and false other
   *             wise.
   */
  void displayLoop(boolean loop);


  /**
   * Takes in a list of keyframes and displays them in a textual form.
   *
   * @param frames a list of keyframes
   */
  void displayKeyframes(List<IKeyFrame> frames);

  /**
   * Takes in a list of identities and displays them.
   *
   * @param identities a list of identities
   */
  void displayIdentity(List<String> identities);

  /**
   * Takes in an error message and displays it.
   *
   * @param message an error message.
   */
  void errorBox(String message);


  /**
   * Takes in the last tick of the entire animation and adjust the slider bar.
   *
   * @param t last tick of the animation
   */
  void lastTick(int t);

  /**
   * Takes in the last of existing layers and display them.
   *
   * @param layers of the animation objects.
   */
  void setLayers(List<Integer> layers);
}
