package cs3500.animator.model;

import java.util.Objects;


/**
 * Represents the shape rectangle. Supports the users to query about its attributes and allows the
 * user to return a copy of itself. Override equals/hashcode. This class shouldn't be extended.
 */
public final class RectangleImpl extends AbstractShape {


  /**
   * Constructs an rectangle using the super constructor.
   */
  public RectangleImpl(int x, int y, int width, int height, int r, int g, int b, int o) {
    super(x, y, width, height, r, g, b, o);
  }


  /**
   * Returns the string name of this shape, "Rectangle".
   *
   * @return string name
   */
  @Override
  public String typeName() {
    return "Rectangle";
  }


  /**
   * Determines whether the given shape is an {@code RectangleImpl}.
   *
   * @param other another shape
   * @return return true if the given shape is an {@code RectangleImpl}, else false.
   */
  @Override
  public boolean sameType(IShape other) {
    return other instanceof RectangleImpl;
  }

  /**
   * Determines whether the given {@code Object} is an {@code RectangleImpl} and has the same
   * attributes of this rectangle.
   *
   * @param obj another Object
   * @return true if they are logically or physically equal, else false.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RectangleImpl)) {
      return false;
    }
    RectangleImpl other = (RectangleImpl) obj;
    return this.x == other.getX() && this.y == other.getY() && this.width == other.width
            && this.height == other.height
            && this.r == other.getR() && this.g == other.getG() && this.b == other.getB()
            && this.o == other.getDegree();
  }

  /**
   * Hashing its attributes.
   *
   * @return hashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y, this.width, this.height, this.r, this.g, this.b, this.o);
  }

  /**
   * Return an {@code RectangleImpl} copy of this oval.
   *
   * @return a copy
   */
  @Override
  public IShape copy() {
    return new RectangleImpl(this.x, this.y, this.width, this.height, this.r,
            this.g, this.b, this.o);
  }

  @Override
  public IShape createOwnType(int x, int y, int width, int height, int r, int g, int b, int o) {
    return new RectangleImpl(x, y, width, height, r, g, b, o);
  }
}
