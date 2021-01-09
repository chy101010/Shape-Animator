package cs3500.animator.model;


/**
 * This interface represents the available querying of a shape in an animation. A shape in an
 * animation is configured within a box with width and height. Therefore, implementations of this
 * interface are expected to have a height and width. Override equals/hashcode - Two {@code IShape}
 * are the same if they have the same attributes.
 */
public interface IShape {
  /**
   * Returns the x coordinate of this shape.
   *
   * @return x coordinate
   */
  int getX();


  /**
   * Returns the y coordinate of this shape.
   *
   * @return y coordinate
   */
  int getY();


  /**
   * Returns the R color index of this shape.
   *
   * @return R index
   */
  int getR();

  /**
   * Returns the G color index of this shape.
   *
   * @return G index
   */
  int getG();

  /**
   * Returns the B color index of this shape.
   *
   * @return B index
   */
  int getB();


  /**
   * Returns the width of this shape.Assuming that a shape in the animation must be in a box, and
   * thus it has a width.
   *
   * @return width
   */
  int getWidth();

  /**
   * Returns the height of this shape. Assuming that a shape in the animation must be in a box, and
   * thus it has a height.
   *
   * @return height
   */
  int getHeight();

  /**
   * Returns the name of this shape. Ex: "Rectangle", "Ellipse", "Star", "Square", etc....
   *
   * @return type
   */
  String typeName();

  /**
   * Determines whether this shape is the same type as the given shape.
   *
   * @param other another Shape
   * @return true if the this shape and the given shape are the same type.
   */
  boolean sameType(IShape other);

  /**
   * Returns a copy of this shape.
   *
   * @return copy of this shape
   */
  IShape copy();

  /**
   * Returns the string representation of this shape.
   *
   * @return string representation
   */
  String toString();

  /**
   * Determines whether this shape is logically or physically equal to the given {@code Object}.
   *
   * @param obj another Object
   * @return true if they are logically or physically equal, else false.
   */
  boolean equals(Object obj);

  /**
   * Hashing the attributes of this shape.
   *
   * @return hashCode
   */
  int hashCode();

  /**
   * Creates a shape which is the same type as the shape invoking this method with the given
   * attributes.
   *
   * @param x      coordinate
   * @param y      coordinate
   * @param width  width
   * @param height height
   * @param r      R index
   * @param g      G index
   * @param b      B index
   * @return IShape with same type as the one that it is getting called on
   */
  IShape createOwnType(int x, int y, int width, int height, int r, int g, int b, int o);


  /**
   * Returns the orientation of this shape.
   *
   * @return the orientation
   */
  int getDegree();
}
