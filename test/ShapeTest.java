import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IShape;
import cs3500.animator.model.RectangleImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the IShape interface. Verifying that the methods are functioning as intended.
 */
public class ShapeTest {
  IShape one;
  IShape two;
  IShape three;
  IShape four;
  IShape five;
  IShape six;

  //creates initial versions of IShape objects for being used in other tests
  @Before
  public void testFixture() {
    this.one = new RectangleImpl(20, 30, 10,
            17, 0, 0, 0, 60);
    this.two = new RectangleImpl(20, 30, 10,
            15, 0, 198, 30, 60);
    this.three = new RectangleImpl(25, -1, 5,
            10, 0, 5, 0, 20);
    this.four = new EllipseImpl(20, -1, 10,
            10, 0, 0, 177, 20);
    this.five = new EllipseImpl(20, 50, 14,
            12, 1, 2, 10, 30);
    this.six = new EllipseImpl(20, 50, 14,
            12, 1, 2, 205, -100);
  }

  //testing: to show negative input for width of a rectangle object will
  //create IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNegWidthError() {
    IShape shape = new RectangleImpl(20, 30, -10,
            10, 0, 0, 0, 0);
  }

  //testing: to show negative input for width of an ellipse object will
  //create IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNegWidthError2() {
    IShape shape = new EllipseImpl(20, 30, -98,
            10, 0, 0, 0, 0);
  }

  //testing: to show negative input for height of a rectangle object will
  //create IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNegHeightError() {
    IShape shape = new RectangleImpl(20, 30, 10,
            -10, 0, 0, 0, 0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidthHeightError() {
    // testing invalid height and width
    IShape shape = new RectangleImpl(20, 30, -20,
            -10, 0, 0, 0, 0);
  }

  //testing: to show negative input for height of a ellipse object will
  //create IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNegHeightError2() {
    IShape shape = new EllipseImpl(20, 30, 98,
            -34, 0, 0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectColor() {
    // testing incorrect color index
    IShape shape = new EllipseImpl(20, 30, 98,
            10, -10, 0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectColor1() {
    // testing incorrect color index
    IShape shape = new EllipseImpl(20, 30, 98,
            10, -10, -20, 0, 0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectColor2() {
    // testing incorrect color index
    IShape shape = new EllipseImpl(20, 30, 98,
            10, 10, 20, -10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectColor3() {
    // testing incorrect color index
    IShape shape = new EllipseImpl(20, 30, 98,
            10, 10, -20, 10, 0);
  }

  //testing: getting x value of an IShape's place as cartesian coordinate system
  @Test
  public void testGetX() {
    assertEquals(20, this.one.getX());
    assertEquals(20, this.two.getX());
    assertEquals(25, this.three.getX());
    assertEquals(20, this.four.getX());
    assertEquals(20, this.five.getX());
    assertEquals(20, this.six.getX());
  }

  //testing: getting y value of an IShape's place as cartesian coordinate system
  @Test
  public void testGetY() {
    assertEquals(30, this.one.getY());
    assertEquals(30, this.two.getY());
    assertEquals(-1, this.three.getY());
    assertEquals(-1, this.four.getY());
    assertEquals(50, this.five.getY());
    assertEquals(50, this.six.getY());
  }

  //testing: getting r value of an IShape's color as RGB color coding
  @Test
  public void testGetR() {
    assertEquals(0, this.one.getR());
    assertEquals(0, this.two.getR());
    assertEquals(0, this.three.getR());
    assertEquals(0, this.four.getR());
    assertEquals(1, this.five.getR());
    assertEquals(1, this.six.getR());
  }

  //testing: getting g value of an IShape's color as RGB color coding
  @Test
  public void testGetG() {
    assertEquals(0, this.one.getG());
    assertEquals(198, this.two.getG());
    assertEquals(5, this.three.getG());
    assertEquals(0, this.four.getG());
    assertEquals(2, this.five.getG());
    assertEquals(2, this.six.getG());
  }

  //testing: getting b value of an IShape's color as RGB color coding
  @Test
  public void testGetB() {
    assertEquals(0, this.one.getB());
    assertEquals(30, this.two.getB());
    assertEquals(0, this.three.getB());
    assertEquals(177, this.four.getB());
    assertEquals(10, this.five.getB());
    assertEquals(205, this.six.getB());
  }

  //testing: getting width of an IShape
  @Test
  public void testGetWidth() {
    assertEquals(10, this.one.getWidth());
    assertEquals(10, this.two.getWidth());
    assertEquals(5, this.three.getWidth());
    assertEquals(10, this.four.getWidth());
    assertEquals(14, this.five.getWidth());
    assertEquals(14, this.six.getWidth());
  }

  //testing: getting height of an IShape
  @Test
  public void testGetHeight() {
    assertEquals(17, this.one.getHeight());
    assertEquals(15, this.two.getHeight());
    assertEquals(10, this.three.getHeight());
    assertEquals(10, this.four.getHeight());
  }

  //testing: getting the type of an IShape as a String
  @Test
  public void testNameType() {
    assertEquals("Rectangle", this.one.typeName());
    assertEquals("Rectangle", this.two.typeName());
    assertEquals("Rectangle", this.three.typeName());
    assertEquals("Ellipse", this.four.typeName());
    assertEquals("Ellipse", this.five.typeName());
    assertEquals("Ellipse", this.six.typeName());
  }

  //testing: to check if 2 IShape's are same type (example: both rectangle?)
  @Test
  public void testSameType() {
    IShape two = new RectangleImpl(20, 30, 10, 17, 0, 0, 0, 0);
    assertTrue(this.one.sameType(this.two));
    assertTrue(this.one.sameType(this.three));
    assertTrue(this.three.sameType(this.two));
    assertFalse(this.one.sameType(this.four));
    assertFalse(this.three.sameType(this.four));
    assertTrue(this.four.sameType(this.five));
    assertTrue(this.five.sameType(this.six));
  }


  //testing: to check if 2 IShapes are is exactly same (as in shape type, size, color and place)
  @Test
  public void testEquals() {
    IShape one = new RectangleImpl(20, 30, 10, 17, 0, 0, 0, 60);
    IShape six = new EllipseImpl(20, 50, 14, 12, 1, 2, 205, -100);
    assertEquals(this.one, one);
    assertNotEquals(this.one, this.three);
    assertNotEquals(this.one, this.four);
    assertNotEquals(this.four, this.three);
    assertNotEquals(this.four, this.five);
    assertNotEquals(this.four, this.six);
    assertNotEquals(this.six, this.five);
    assertEquals(this.six, six);
  }

  //testing: to check if hashcodes of 2 IShapes are equal.
  @Test
  public void testHashCode() {
    IShape six = new EllipseImpl(20, 50, 14, 12, 1, 2, 205, -100);
    assertNotEquals(this.one.hashCode(), this.three.hashCode());
    assertNotEquals(this.one.hashCode(), this.four.hashCode());
    assertNotEquals(this.four.hashCode(), this.three.hashCode());
    assertNotEquals(this.four.hashCode(), this.five.hashCode());
    assertEquals(this.six.hashCode(), six.hashCode());
  }

  //testing: to check String representation of an IShape
  @Test
  public void testToString() {
    assertEquals("20 30 10 17 0 0 0 60", this.one.toString());
    assertEquals("20 30 10 15 0 198 30 60", this.two.toString());
    assertEquals("25 -1 5 10 0 5 0 20", this.three.toString());
    assertEquals("20 -1 10 10 0 0 177 20", this.four.toString());
  }

  //testing: to check if the copy method will create an IShape that is exactly same
  //of the original IShape(as in shape type, size, color and place)
  @Test
  public void testCopy() {
    assertEquals(this.one, this.one.copy());
    assertEquals(this.four, this.four.copy());
    assertEquals(this.five, this.five.copy());
  }

  //testing: if createOwnType will create the same type of IShape
  //with the values given to the method
  @Test
  public void testCreateOwnType() {
    IShape seven = new RectangleImpl(23, 15, 8,
            10, 23, 234, 96, 0);
    assertEquals(seven, this.one.createOwnType(23, 15, 8,
            10, 23, 234, 96, 0));
    IShape eight = new EllipseImpl(22, 17, 14,
            12, 23, 39, 63, 0);
    IShape nine = new EllipseImpl(22, 17, 14,
            12, 23, 39, 63, 0);
    assertEquals(eight, nine);
    assertNotEquals(seven, eight);
  }

}
