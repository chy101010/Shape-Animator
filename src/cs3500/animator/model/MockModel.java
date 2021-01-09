package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A mockup model to test the communication between Model and Controller, checking whether the
 * controller is calling the methods with the correct inputs.
 */
public class MockModel implements AnimatorModel {
  final StringBuilder log;

  /**
   * Constructs a MockModel.
   *
   * @param log the StringBuilder that to be edited with the inputs once the Controller called
   *            Move.
   */
  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Appends this "getShape called" into the {@code log}.
   *
   * @return an empty arraylist
   */
  @Override
  public List<String> getShapes() {
    this.log.append("getShape called");
    return new ArrayList<>();
  }

  /**
   * Appends this "getFramesFor called" into the {@code log}.
   *
   * @param identity the identity of the animation object
   * @return an empty arraylist
   */
  @Override
  public List<IKeyFrame> getFramesFor(String identity) {
    this.log.append("getFramesFor called");
    return new ArrayList<>();
  }

  /**
   * Appends the given {@code identity} and the given {@code t} into the {@code log}.
   *
   * @param identity the identity of the animation object
   * @param t        tick
   * @return null
   */
  @Override
  public IShape getShapeAt(String identity, int t) {
    return new RectangleImpl(1, 1, 1, 1, 1, 1, 1, 1);
  }

  /**
   * Appends the "isAnimationOver called" into the {@code log}.
   *
   * @param t tick
   * @return false
   */
  @Override
  public boolean isAnimationOver(int t) {
    return false;
  }

  /**
   * Appends the given {@code identity} and the to-String of {@code frame} into the {@code log}.
   *
   * @param identity the identity of the animation object
   * @param frame    the keyframe to be removed
   */
  @Override
  public void addFrame(String identity, IKeyFrame frame) {
    log.append(identity + " " + frame.toString());
  }

  /**
   * Appends the given {@code identity} and the to-String of {@code frame} into the {@code log}.
   *
   * @param identity the identity of the animation object
   * @param frame    a new Keyframe
   */
  @Override
  public void removeFrame(String identity, IKeyFrame frame) {
    log.append(identity + " " + frame.toString());
  }

  /**
   * Appends the given {@code identity} into the {@code log}.
   *
   * @param identity of the target shape in the animation
   */
  @Override
  public void removeShapes(String identity) {
    log.append(identity);
  }

  /**
   * Appends "getViews called" into the {@code log}.
   *
   * @return nothing.
   */
  @Override
  public int[] getViews() {
    this.log.append("getViews called");
    return new int[0];
  }

  /**
   * Returns "rectangle" to pass the exception in controller.
   *
   * @param identity the identity of the animation object
   * @return "rectangle"
   */
  @Override
  public String getTypes(String identity) {
    return "rectangle";
  }

  /**
   * Appends the inputs into the {@code log}.
   *
   * @param x      the leftmost x value
   * @param y      the topmost y value
   * @param width  the width of the bounding box.
   * @param height the height of the bounding box
   */
  @Override
  public void setBounds(int x, int y, int width, int height) {
    this.log.append(x + " " + " " + y + " " + width + " " + height);
    return;
  }

  ///////////////////////////////////////////////////
  @Override
  public int lastTick() {
    this.log.append("lastTick called");
    return 0;
  }

  @Override
  public void addShapes(String identity, String type, int layer) {
    this.log.append("addShapes Called" + " " + identity + " " + type + " " + layer);
  }

  @Override
  public int getLayer(String identity) {
    this.log.append("getLayer Called" + " " + identity);
    return 0;
  }

  @Override
  public void removeLayer(int layer) {
    this.log.append("changeLayer removeLayer" + " " + layer);
  }

  @Override
  public void swapLayer(int first, int second) {
    this.log.append("swapLayerCalled" + " " + first + " " + second);
  }
}
