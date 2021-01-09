package cs3500.animator.model;


/**
 * An Abstract class for the shapes in the animation. It supports methods that allow users to query
 * about its attributes and returns a copy of itself.
 */
abstract class AbstractShape implements IShape {
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  protected int r;
  protected int g;
  protected int b;
  protected int o;

  /**
   * Constructs a shape with the given {@code x} coordinate, {@code y} coordinate, {@code width},
   * {@code height} dimensions, {@code r}, {@code g}, and {@code b} color indexes.
   *
   * @param x      coordinate
   * @param y      coordinate
   * @param width  width
   * @param height height
   * @param r      R index of the color
   * @param g      G index of the color
   * @param b      B index of the color
   * @throws IllegalArgumentException if either of the given color indexes is invalid that they must
   *                                  be integers between 0-255, or the width and height is less
   *                                  than 0;
   */
  public AbstractShape(int x, int y, int width, int height, int r, int g, int b, int o) {
    if (r < 0 || 255 < r || g < 0 || 255 < g || b < 0 || 255 < b) {
      throw new IllegalArgumentException("RGB must integer between 0-255!");
    }
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Invalid width or height!");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.r = r;
    this.g = g;
    this.b = b;
    this.o = o;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getG() {
    return this.g;
  }

  @Override
  public int getB() {
    return this.b;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  // delegate to subclass
  @Override
  public abstract String typeName();

  // delegate to subclass
  @Override
  public abstract boolean sameType(IShape other);

  // delegate to subclass
  @Override
  public abstract boolean equals(Object obj);

  // delegate to subclass
  @Override
  public abstract int hashCode();

  @Override
  public String toString() {
    return this.x + " " + this.y + " " + this.width + " " + this.height
            + " " + this.r + " " + this.g + " " + this.b + " " + this.o;
  }

  // delegate to subclass
  @Override
  public abstract IShape copy();


  // delegate to subclass
  @Override
  public abstract IShape createOwnType(int x, int y, int width, int height,
                                       int r, int g, int b, int o);

  @Override
  public int getDegree() {
    return this.o;
  }
}
