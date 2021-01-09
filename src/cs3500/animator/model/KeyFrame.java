package cs3500.animator.model;

import java.util.List;
import java.util.Objects;


/**
 * A representation of a KeyFrame in an animation. Each keyframe has an {@code IShape} and a time
 * {@code tick}. Therefore it represents the space in time of a shape. A shape in the animation can
 * have a list of keyframes, in which each keyframe represents the shape's status at that given time
 * tick.
 */
public class KeyFrame implements IKeyFrame {
  private final int tick;
  private final IShape shape;

  /**
   * Constructs a KeyFrame with the given {@code tick} and {@code IShape}.
   *
   * @param tick  time tick of the KeyFrame
   * @param shape the shape in the animation
   * @throws IllegalArgumentException if the given tick is less than 0. Assuming there wouldn't be
   *                                  any animation at tick zero. If the given IShape is a null.
   */
  public KeyFrame(int tick, IShape shape) {
    if (tick < 0) {
      throw new IllegalArgumentException("The tick must be positive!");
    }
    if (shape == null) {
      throw new IllegalArgumentException("The shape can't be null!");
    }
    this.tick = tick;
    this.shape = shape;
  }

  @Override
  public IShape getShape() {
    return this.shape.copy();
  }

  @Override
  public int getTime() {
    return this.tick;
  }

  /**
   * Additional Information: Chronologically Inserts this KeyFrame into the given list of KeyFrames,
   * from first to last.
   *
   * @throws IllegalArgumentException if this KeyFrame maps onto a different KeyFrame in the given
   *                                  list.
   */
  @Override
  public void addInto(List<IKeyFrame> list) {
    for (int i = 0; i < list.size(); i++) {
      IKeyFrame other = list.get(i);
      if (this.tick == other.getTime() && !this.equals(other)) {
        throw new IllegalArgumentException("A Different KeyFrame already exist at the given tick");
      }
      if (this.equals(other)) {
        return;
      }
      if (this.addBefore(other)) {
        list.add(i, this);
        return;
      }
    }
    list.add(this);
  }

  @Override
  public void removeFrom(List<IKeyFrame> list) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals(this)) {
        list.remove(i);
        return;
      }
    }
    throw new IllegalArgumentException("The given frame doesn't exist!");
  }


  @Override
  public String toString() {
    return this.tick + " " + this.shape.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof KeyFrame)) {
      return false;
    }
    KeyFrame other = (KeyFrame) obj;
    return other.getShape().equals(this.shape)
            && this.tick == other.tick;
  }


  @Override
  public int hashCode() {
    return Objects.hash(this.tick, this.shape);
  }


  /**
   * Determines whether this KeyFrame comes before the given KeyFrame.
   *
   * @param other KeyFrame
   * @return true if this KeyFrame comes before the given KeyFrame, else false.
   */
  private boolean addBefore(IKeyFrame other) {
    return this.tick < other.getTime();
  }
}
