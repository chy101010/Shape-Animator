package cs3500.animator.model;

import java.util.Objects;


/**
 * Represents the shape Ellipse. Supports the users to query about its attributes and allows the
 * user to return a copy of itself. Override equals/hashcode. This class shouldn't be extended.
 */
public final class EllipseImpl extends AbstractShape {

  /**
   * Constructs an Ellipse using the super constructor.
   */
  public EllipseImpl(int x, int y, int width, int height, int r, int g, int b, int o) {
    super(x, y, width, height, r, g, b, o);
  }

  /**
   * Returns the string name of this shape, "Ellipse".
   *
   * @return string name
   */
  @Override
  public String typeName() {
    return "Ellipse";
  }

  /**
   * Determines whether the given shape is an {@code EllipseImpl}.
   *
   * @param other another shape
   * @return return true if the given shape is an {@code EllipseImpl}, else false.
   */
  @Override
  public boolean sameType(IShape other) {
    return other instanceof EllipseImpl;
  }

  /**
   * Determines whether the given {@code Object} is an {@code EllipseImpl} and has the same
   * attributes of this Ellipse.
   *
   * @param obj another Object
   * @return true if they are physically or logically equal, else false.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof EllipseImpl)) {
      return false;
    }
    EllipseImpl other = (EllipseImpl) obj;
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
   * Return an {@code EllipseImpl} copy of this Ellipse.
   *
   * @return a copy
   */
  @Override
  public IShape copy() {
    return new EllipseImpl(this.x, this.y, this.width, this.height, this.r, this.g, this.b, this.o);
  }

  @Override
  public IShape createOwnType(int x, int y, int width, int height, int r, int g, int b, int o) {
    return new EllipseImpl(x, y, width, height, r, g, b, o);
  }
}
