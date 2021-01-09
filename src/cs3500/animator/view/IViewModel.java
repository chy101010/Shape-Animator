package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;

/**
 * Represents a view-only-model interface of the AnimatorModel Interface. This interface offers all
 * the getter methods of AnimatorModel.
 */
public interface IViewModel {
  /**
   * Returns the list of identities(String) in this animation.
   *
   * @return the list of name, else empty list if there isn't any
   */
  List<String> getShapes();

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
   * Returns the bounds of this animation. These bounds would be stored in an array of length 4. The
   * first index is the leftmost x value, the second index is the topmost y value, the third index
   * is the width of the bounding box, and the last index is the height of the bounding box.
   *
   * @return an array of length 4 storing the bounds this animation.
   */
  int[] getViews();


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
   * Returns the list of keyframes of the shape pointed by the given {@code identity} in this
   * animation.
   *
   * @param identity the identity of the animation object
   * @return list of keyframes
   * @throws IllegalArgumentException if the given identity doesn't exist.
   */
  List<IKeyFrame> getFramesFor(String identity);

  /**
   * Returns the type of shape of the animation object pointed by this given {@code identity}.
   *
   * @param identity the identity of the animation object
   * @return type of shape
   * @throws IllegalArgumentException if the given identity doesn't exist
   */
  String getTypes(String identity);


  /**
   * Returns the layer of the animation object pointed by the given {@code identity}.
   *
   * @param identity of the animation object
   * @return layer of the animation object
   * @throws IllegalArgumentException if the given identity doesn't exist.
   */
  int getLayer(String identity);

}
