package cs3500.animator.model;

import java.util.List;


/**
 * This interface represents the available operations on a KeyFrame of an animation object. A
 * KeyFrame represents the space in time of a {@code IShape}. Allows the client to query about the
 * tick and shape at this keyframe. Allows the client to add a keyframe into a list of keyframes or
 * remove from it. Override equals/hashcode - Two KeyFrames are the same if they have the same time
 * tick and same {@code IShape}.
 */
public interface IKeyFrame {

  /**
   * Returns a copy of the {@code IShape} in this this KeyFrame.
   *
   * @return a copy {@code IShape}.
   */
  IShape getShape();

  /**
   * Returns the time tick in this KeyFrame.
   *
   * @return the time tick.
   */
  int getTime();

  /**
   * Inserts this KeyFrame into the given list of KeyFrames.
   *
   * @param list of {@code IKeyFrame}
   */
  void addInto(List<IKeyFrame> list);

  /**
   * Removes this KeyFrame from the given list of KeyFrame.
   *
   * @param list of KeyFrame
   * @throws IllegalArgumentException if this KeyFrame isn't in the given list.
   */
  void removeFrom(List<IKeyFrame> list);

  /**
   * Returns a string representation of this KeyFrame.
   *
   * @return a string representation of this KeyFrame
   */
  String toString();

  /**
   * Determines whether the given {@code Object} is physically or logically equal to this KeyFrame.
   *
   * @param obj an {@code Object}
   * @return true if they are logically and physically equal, else false.
   */
  boolean equals(Object obj);

  /**
   * Hashing the attributes of this KeyFrame.
   *
   * @return hashCode.
   */
  int hashCode();

}
