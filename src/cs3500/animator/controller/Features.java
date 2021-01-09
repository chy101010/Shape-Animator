package cs3500.animator.controller;

/**
 * This interface represents all the action reacts(each provide some functionality when a view
 * client request upon) that a controller implementation can offer.
 */
public interface Features {
  /**
   * Start the Animation.
   */
  void start();

  /**
   * Restart the Animation.
   */
  void restart();

  /**
   * Resume the Animation.
   */
  void resume();

  /**
   * Pause the Animation.
   */
  void pause();

  /**
   * Increase the speed of data flow between the Controller and the View.
   */
  void increase();

  /**
   * Decrease the speed of data flow between the Controller and the View.
   */
  void decrease();

  /**
   * Switch on or off the looping of the Animation.
   */
  void loopSwitch();

  /**
   * Allows the View client to tell the controller which Animation Object is current selected and do
   * something with it.
   *
   * @param identity of the Animation Object
   */
  void selectShape(String identity);

  /**
   * Remove the selected Animation Object from the model.
   */
  void removeShape();

  /**
   * Adds in an Animation Object into the model with given {@code identity}, the given {@code type}
   * and the given {@code layer}.
   *
   * @param identity of the Animation Object
   * @param type     of the Animation Object
   * @param layer    of the Animation Object
   */
  void addShape(String identity, String type, int layer);

  /**
   * Adds in a Keyframe of {@code t} into the selected the selected Animation Object with the given
   * attributes {@code x,y,width,height,r,g,b,o}.
   *
   * @param t      tick
   * @param x      coordinate
   * @param y      coordinate
   * @param width  dimension
   * @param height dimension
   * @param r      red color index
   * @param g      green color index
   * @param b      blue color index
   * @param o      orientation
   */
  void addKeyFrame(int t, int x, int y, int width, int height, int r, int g, int b, int o);

  /**
   * Removes the Keyframe at the given {@code tick} of the selected Animation Object.
   *
   * @param tick of the keyframe to be removed.
   */
  void removeKeyFrame(int tick);

  /**
   * Replaces the Keyframe at the given {@code t} of the selected Animation Object with a new
   * keyframe with the attributes of {@code x,y,width,height,r,g,b,o}.
   *
   * @param t      tick of the keyframe to be edited
   * @param x      coordinate
   * @param y      coordinate
   * @param width  dimension
   * @param height dimension
   * @param r      red color index
   * @param g      green color index
   * @param b      blue color index
   * @param o      orientation
   */
  void editKeyFrame(int t, int x, int y, int width, int height, int r, int g, int b, int o);

  /**
   * Select change the current tick to the given {@code t}.
   *
   * @param t new tick
   */
  void selctTick(int t);


  /**
   * Removes all the animation objects in the given {@code layer}.
   *
   * @param layer of the animation objects
   */
  void removeLayer(int layer);

  /**
   * Swaps the shapes in the first layer in to the second layer and the shapes in the second layer
   * in to the first layer.
   *
   * @param first  layer
   * @param second layer
   */
  void swapLayer(int first, int second);
}
