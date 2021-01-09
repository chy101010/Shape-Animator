import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Test cases for the IKeyFrame interface. Verifying that the methods are functioning as intended.
 */
public class KeyFrameTest {
  IKeyFrame tick10;
  IKeyFrame tick15;
  IKeyFrame tick20;
  IKeyFrame tick40;

  IKeyFrame tickO30;
  IKeyFrame tickO50;
  IKeyFrame tickO42;

  IShape rectangleOne;
  IShape rectangleTwo;
  IShape rectangleThree;
  IShape rectangleFour;
  IShape ellipseOne;
  IShape ellipseTwo;
  IShape ellipseThree;

  //creates initial versions of IKeyFrame objects for being used in other tests
  @Before
  public void testFixture() {
    this.rectangleOne = new RectangleImpl(20, 30, 10, 10,
            0, 0, 0, 0);
    this.rectangleTwo = new RectangleImpl(20, 10, 10, 10,
            0, 0, 0, 0);
    this.rectangleThree = new RectangleImpl(50, 40, 10, 17,
            0, 8, 0, 10);
    this.rectangleFour = new RectangleImpl(100, 200, 102, 10,
            0, 0, 0, 20);
    this.ellipseOne = new EllipseImpl(50, 40, 10, 17,
            0, 8, 0, 30);
    this.ellipseTwo = new EllipseImpl(20, 50, 14, 12,
            1, 2, 0, 30);
    this.ellipseThree = new EllipseImpl(20, 50, 14, 12,
            1, 2, 0, 30);

    this.tick10 = new KeyFrame(10, this.rectangleOne);
    this.tick15 = new KeyFrame(15, this.rectangleTwo);
    this.tick20 = new KeyFrame(20, this.rectangleThree);
    this.tick40 = new KeyFrame(40, this.rectangleFour);
    this.tickO50 = new KeyFrame(50, this.ellipseOne);
    this.tickO42 = new KeyFrame(42, this.ellipseTwo);
    this.tickO30 = new KeyFrame(30, this.ellipseThree);
  }

  //testing: to show negative input for tick will create IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    new KeyFrame(-10, this.rectangleOne);
  }

  //testing: Can't pass a null for IShape create IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    new KeyFrame(100, null);
  }

  //testing: Can't pass a null for IShape and negative tick IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    new KeyFrame(-100, null);
  }


  //testing: getting the IShape of an IKeyFrame object
  @Test
  public void testGetShape() {
    assertEquals(this.rectangleOne, this.tick10.getShape());
    assertEquals(this.rectangleTwo, this.tick15.getShape());
    assertEquals(this.rectangleThree, this.tick20.getShape());
    assertEquals(this.rectangleFour, this.tick40.getShape());
    assertEquals(this.ellipseOne, this.tickO50.getShape());
    assertEquals(this.ellipseTwo, this.tickO42.getShape());
    assertEquals(this.ellipseThree, this.tickO30.getShape());
  }

  //testing: getting the tick of an IKeyFrame object
  @Test
  public void testGetTime() {
    assertEquals(10, this.tick10.getTime());
    assertEquals(15, this.tick15.getTime());
    assertEquals(20, this.tick20.getTime());
    assertEquals(40, this.tick40.getTime());
    assertEquals(30, this.tickO30.getTime());
    assertEquals(42, this.tickO42.getTime());
  }

  //testing: adding this IKeyFrame to the given list of IKeyFrames
  @Test
  public void testAddInto() {
    IKeyFrame tick5 = new KeyFrame(5, this.rectangleThree);
    // add in-between
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tick10, this.tick20));
    assertEquals(this.tick20, arr.get(1));
    this.tick15.addInto(arr);
    // Checking the array
    assertEquals(new ArrayList<IKeyFrame>(Arrays.asList(this.tick10, this.tick15,
            this.tick20)), arr);
    assertEquals(this.tick15, arr.get(1));
    // add behind
    assertEquals(this.tick20, arr.get(2));
    this.tick40.addInto(arr);
    // Checking the array
    assertEquals(new ArrayList<IKeyFrame>(Arrays.asList(this.tick10, this.tick15,
            this.tick20, this.tick40)), arr);
    assertEquals(this.tick20, arr.get(2));
    assertEquals(this.tick40, arr.get(3));
    // add before
    assertEquals(this.tick10, arr.get(0));
    tick5.addInto(arr);
    assertEquals(tick5, arr.get(0));
    // Checking the array
    assertEquals(new ArrayList<IKeyFrame>(Arrays.asList(tick5, this.tick10, this.tick15,
            this.tick20, this.tick40)), arr);
  }

  @Test
  public void testAddInto1() {
    // Mapping onto itself
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tick10, this.tick20));
    this.tick10.addInto(arr);
    assertEquals(new ArrayList<IKeyFrame>(Arrays.asList(this.tick10, this.tick20)), arr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddInto2() {
    // Mapping onto a different keyframe
    IKeyFrame tick10 = new KeyFrame(10, this.rectangleTwo);
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tick10, this.tick20));
    tick10.addInto(arr);
  }

  //testing: removing this IKeyFrame from the given list of IKeyFrames
  @Test
  public void testRemoveFrom() {
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tick10, this.tick20));
    assertEquals(this.tick10, arr.get(0));
    IKeyFrame tick10 = new KeyFrame(10, this.rectangleOne);
    tick10.removeFrom(arr);
    assertEquals(this.tick20, arr.get(0));
    assertEquals(1, arr.size());
  }

  //testing: to show if this IKeyFrame doesn't match with any IKeyFrames in the given list,
  //code will have IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveFrom() {
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tick10, this.tick20));
    IKeyFrame tick10 = new KeyFrame(10, this.rectangleFour);
    tick10.removeFrom(arr);
  }

  //testing: to show if this IKeyFrame doesn't exist(if the given tick doesn't exist)
  //in the given list, code will have IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveFrom1() {
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tick10, this.tick20));
    this.tickO30.removeFrom(arr);
  }

  //testing: to show if this IKeyFrame maps onto another different IKeyFrame in the given list,
  //code will have IllegalArgumentException (adding a different shape with same type)
  @Test(expected = IllegalArgumentException.class)
  public void InvalidAddInto() {
    IShape newEllipse = new EllipseImpl(79, 68, 23, 157,
            38, 97, 68, 0);
    IKeyFrame newtickO50 = new KeyFrame(50, newEllipse);
    List<IKeyFrame> arr = new ArrayList<>(Arrays.asList(this.tickO50, this.tickO30));
    newtickO50.addInto(arr);
  }

  //testing: to check String representation of an IKeyFrame
  @Test
  public void testToString() {
    assertEquals("10 20 30 10 10 0 0 0 0", this.tick10.toString());
    assertEquals("50 50 40 10 17 0 8 0 30", this.tickO50.toString());
  }

  //testing: to check if 2 IKeyFrames are is exactly same (as in shape type, size, color and place)
  @Test
  public void testEquals() {
    IKeyFrame tick10 = new KeyFrame(10, this.rectangleOne);
    IKeyFrame tick110 = new KeyFrame(10, this.ellipseThree);
    IKeyFrame tickO50 = new KeyFrame(50, this.ellipseOne);
    assertNotEquals(this.tick10, this.tickO50);
    assertNotEquals(this.tick10, this.tick20);
    assertNotEquals(this.tick10, tick110);
    assertEquals(this.tick10, tick10);
    assertEquals(tickO50, this.tickO50);
  }

  //testing: to check if hashcodes of 2 IKeyFrames are equal.
  @Test
  public void testHashCode() {
    IKeyFrame tickO50 = new KeyFrame(50, this.ellipseOne);
    IKeyFrame tick10 = new KeyFrame(10, this.rectangleOne);
    assertEquals(this.tick10.hashCode(), tick10.hashCode());
    assertEquals(this.tickO50.hashCode(), tickO50.hashCode());
    assertNotEquals(this.tick10.hashCode(), this.tick20.hashCode());
  }
}
