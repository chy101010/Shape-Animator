package cs3500.animator.model;

import java.util.List;

/**
 * This interface represents an animation and the operations that allow the user to add {@code
 * IShape} along with its {@code tick} into the animation. A shape with a time tick is an animation
 * object{@code IKeyframe}. It allows the user to remove an animation object from the animation, to
 * know when the animation is over after a given tick. An animation is over if there is no more
 * keyframes at a given tick or after. It allows the user to query the list of keyframes of an
 * animation object pointed by an unique {@code identity}, and to query the instantaneous {@code
 * IShape} pointed by the identity at a given tick. It allows the user to set the bounds of the
 * animation, and thus, to query about the bounds of the animation, the type of shape pointed by
 * identity.
 */
public interface AnimatorModel {

  /**
   * Returns the list of identities(String) in this animation.
   *
   * @return the list of name, else empty list if there isn't any
   */
  List<String> getShapes();

  /**
   * Returns the list of keyframes of the shape pointed by the given {@code identity} in this
   * animation.
   *
   * @param identity the identity of the animation object
   * @return list of keyframes
   * @throws IllegalArgumentException if the given identity doesn't exist.
   */
  List<IKeyFrame> getFramesFor(String identity);

  /**
   * Return the {@code IShape} pointed by the given {@code identity} at the given {@code t}. The
   * returned shape will have the instantaneous attributes at the given time tick: attributes during
   * its transition from the keyframe before to the keyframe after.
   *
   * @param identity the identity of the animation object
   * @param t        tick
   * @return correct shape with the correct attributes, else return null if the shape disappeared.
   * @throws IllegalArgumentException if the given identity doesn't exist or the given tick is less
   *                                  than 1.
   */
  IShape getShapeAt(String identity, int t);

  /**
   * Determine whether the animation is over or not at the given {@code t}. The animation is over
   * when there is no frame after or at the given {@code t}.
   *
   * @param t tick
   * @return true if the animation is over, else false.
   * @throws IllegalArgumentException if the given {@code t} is less than 1.
   */
  boolean isAnimationOver(int t);


  /**
   * Add this given {@code frame} into the list of keyframes of the shape pointed by the given
   * {@code identity}.
   *
   * <p>Invariants: A list of keyframes can contains only one type of shape.</p>
   *
   * @param identity the identity of the animation object
   * @param frame    the keyframe to be removed
   * @throws IllegalArgumentException if the identity doesn't exist.
   */
  void addFrame(String identity, IKeyFrame frame);


  /**
   * Remove this given {@code frame} from the list of keyframes of the shape pointed by the given
   * {@code identity}.
   * <p>Invariants: The a list of keyframes must contains only the same type of shape.</p>
   *
   * @param identity the identity of the animation object
   * @param frame    a new Keyframe
   * @throws IllegalArgumentException if the given frame isn't in the the list, or the given
   *                                  identity doesn't not exist.
   */
  void removeFrame(String identity, IKeyFrame frame);


  /**
   * Add this given {@code identity} into this animation along with its first appearance {@code
   * firstFrame}, which holds the time tick it should appears and the attributes of the shape.
   *
   * @param identity the identity of the animation object
   * @param type     the type of fo the shape in the animation
   * @throws IllegalArgumentException if the given identity already exists.
   */
  void addShapes(String identity, String type, int layer);

  /**
   * Remove the given {@code identity} from this animation.
   *
   * @param identity of the target shape in the animation
   * @throws IllegalArgumentException if the given identity doesn't exist.
   */
  void removeShapes(String identity);


  /**
   * Returns the bounds of this animation. These bounds would be stored in an array of length 4. The
   * first index is the leftmost x value, the second index is the topmost y value, the third index
   * is the width of the bounding box, and the last index is the height of the bounding box.
   *
   * @return an array of length 4 storing the bounds this animation.
   */
  int[] getViews();

  /**
   * Returns the type of shape of the animation object pointed by this given {@code identity}.
   *
   * @param identity the identity of the animation object
   * @return type of shape
   * @throws IllegalArgumentException if the given identity doesn't exist
   */
  String getTypes(String identity);


  /**
   * Set the bounds of the animation.
   *
   * @param x      the leftmost x value
   * @param y      the topmost y value
   * @param width  the width of the bounding box.
   * @param height the height of the bounding box
   */
  void setBounds(int x, int y, int width, int height);


  /**
   * Returns longest tick of the animation.
   *
   * @return the last tick
   */
  int lastTick();

  /**
   * Returns the layer of the animation object pointed by the given {@code identity}.
   *
   * @param identity of the animation object
   * @return layer of the animation object
   * @throws IllegalArgumentException if the given identity doesn't exist.
   */
  int getLayer(String identity);


  /**
   * Removes all the animation objects in the given {@code layer}.
   *
   * @param layer of the animation objects.
   */
  void removeLayer(int layer);

  /**
   * Swaps the shapes in the given {@code first} layer to given {@code second} layer. And shapes
   * from {@code first} layer to {@code second} layer.
   *
   * @param first  the first layer of the swapping layers
   * @param second the second layer of the swapping layers
   */
  void swapLayer(int first, int second);
}
