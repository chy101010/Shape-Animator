import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//I came to getShape (didn''t test it fully! There may be errors in prev tests)

/**
 * Test cases for the Animator Interface. Verifying that the methods are functioning as intended.
 */
public class AnimatorModelTest {
  // For Rectangle R
  IKeyFrame tickR1;
  IKeyFrame tickR10;
  IKeyFrame tickR50;
  IKeyFrame tickR51;
  IKeyFrame tickR70;
  IKeyFrame tickR100;

  // For Oval C
  IKeyFrame tickO6;
  IKeyFrame tickO20;
  IKeyFrame tickO50;
  IKeyFrame tickO70;
  IKeyFrame tickO80;
  IKeyFrame tickO100;

  // For Rectangle R
  IShape rectangleOne;
  IShape rectangleTwo;
  IShape rectangleThree;
  IShape rectangleFour;
  IShape rectangleFive;
  IShape rectangleSix;

  // For Oval C
  IShape ovalOne;
  IShape ovalTwo;
  IShape ovalThree;
  IShape ovalFour;
  IShape ovalFive;
  IShape ovalSix;

  AnimatorModel animate;

  @Before
  public void testFixture() {
    this.rectangleOne = new RectangleImpl(200, 200,
            50, 100, 255, 0, 0, 0);
    this.rectangleTwo = new RectangleImpl(200, 200,
            50, 100, 255, 0, 0, 0);
    this.rectangleThree = new RectangleImpl(300, 300,
            50, 100, 255, 0, 0, 0);
    this.rectangleFour = new RectangleImpl(300, 300,
            50, 100, 255, 0, 0, 0);
    this.rectangleFive = new RectangleImpl(300, 300,
            25, 100, 255, 0, 0, 0);
    this.rectangleSix = new RectangleImpl(200, 200,
            25, 100, 255, 0, 0, 0);
    this.ovalOne = new EllipseImpl(440, 70, 120,
            60, 0, 0, 255, 0);
    this.ovalTwo = new EllipseImpl(440, 70, 120,
            60, 0, 0, 255, 0);
    this.ovalThree = new EllipseImpl(440, 250, 120,
            60, 0, 0, 255, 0);
    this.ovalFour = new EllipseImpl(440, 370, 120,
            60, 0, 170, 0, 0);
    this.ovalFive = new EllipseImpl(440, 370, 120,
            60, 0, 255, 0, 0);
    this.ovalSix = new EllipseImpl(440, 370, 120,
            60, 0, 255, 0, 0);

    this.tickR1 = new KeyFrame(1, this.rectangleOne);
    this.tickR10 = new KeyFrame(10, this.rectangleTwo);
    this.tickR50 = new KeyFrame(50, this.rectangleThree);
    this.tickR51 = new KeyFrame(51, this.rectangleFour);
    this.tickR70 = new KeyFrame(70, this.rectangleFive);
    this.tickR100 = new KeyFrame(100, this.rectangleSix);

    this.tickO6 = new KeyFrame(6, this.ovalOne);
    this.tickO20 = new KeyFrame(20, this.ovalTwo);
    this.tickO50 = new KeyFrame(50, this.ovalThree);
    this.tickO70 = new KeyFrame(70, this.ovalFour);
    this.tickO80 = new KeyFrame(80, this.ovalFive);
    this.tickO100 = new KeyFrame(100, this.ovalSix);

    this.animate = new SimpleModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullStamps() {
    Map<String, String> types = new HashMap<>();
    Map<String, Integer> layer = new HashMap<>();
    types.put("R", "Rectangle");
    List<String> identities = Collections.singletonList("R");
    AnimatorModel model = new SimpleModelImpl(null, types, layer, identities, 13,
            23, 65, 87);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTypes() {
    Map<String, List<IKeyFrame>> stamps = new HashMap<>();
    Map<String, Integer> layer = new HashMap<>();
    stamps.put("R", new ArrayList<>(Arrays.asList(this.tickR50, this.tickR100)));
    List<String> identities = Collections.singletonList("R");
    AnimatorModel model = new SimpleModelImpl(stamps, null, layer, identities, 13,
            23, 65, 87);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullIdentities() {
    Map<String, List<IKeyFrame>> stamps = new HashMap<>();
    Map<String, Integer> layer = new HashMap<>();
    stamps.put("R", new ArrayList<>(Arrays.asList(this.tickR50, this.tickR100)));
    Map<String, String> types = new HashMap<>();
    types.put("R", "Rectangle");
    List<String> identities = Collections.singletonList("R");
    AnimatorModel model = new SimpleModelImpl(stamps, types, layer, null,
            23, 65, 87, 0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    AnimatorModel model = new SimpleModelImpl(null, null, null, null, 13,
            23, 65, 87);
  }

  @Test
  public void testConstructor() {
    // testing the constructor using the methods
    Map<String, List<IKeyFrame>> stamps = new HashMap<>();
    stamps.put("R", new ArrayList<>(Arrays.asList(this.tickR50, this.tickR100)));
    Map<String, String> types = new HashMap<>();
    Map<String, Integer> layer = new HashMap<>();
    types.put("R", "Rectangle");
    List<String> identities = Collections.singletonList("R");
    AnimatorModel model = new SimpleModelImpl(stamps, types, layer, identities, 13,
            23, 65, 87);
    assertEquals("Rectangle", model.getTypes("R"));
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR50, this.tickR100)),
            model.getFramesFor("R"));
    assertEquals(this.tickR50.getShape(), model.getShapeAt("R", 50));
    assertEquals(13, model.getViews()[0]);
    assertEquals(23, model.getViews()[1]);
    assertEquals(65, model.getViews()[2]);
    assertEquals(87, model.getViews()[3]);
    assertEquals(new ArrayList<>(Arrays.asList("R")), model.getShapes());
  }

  @Test
  public void testConstructor1() {
    AnimatorModel model = new SimpleModelImpl();
    assertEquals(new ArrayList<>(), model.getShapes());
  }

  @Test
  public void testGetViews() {
    Map<String, List<IKeyFrame>> stamps = new HashMap<>();
    stamps.put("R", new ArrayList<>(Arrays.asList(this.tickR50, this.tickR100)));
    Map<String, String> types = new HashMap<>();
    types.put("R", "Rectangle");
    Map<String, Integer> layer = new HashMap<>();
    List<String> identities = Collections.singletonList("R");
    AnimatorModel model = new SimpleModelImpl(stamps, types, layer, identities, 13,
            23, 65, 87);

    int[] views = model.getViews();
    assertEquals(13, views[0]);
    assertEquals(23, views[1]);
    assertEquals(65, views[2]);
    assertEquals(87, views[3]);

  }

  @Test
  public void testSetBoundsGetViews() {
    AnimatorModel model = new SimpleModelImpl();
    model.setBounds(45, 23, 33, 14);
    int[] views = model.getViews();
    assertEquals(45, views[0]);
    assertEquals(23, views[1]);
    assertEquals(33, views[2]);
    assertEquals(14, views[3]);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetTypesError() { //doesn't exist
    AnimatorModel model = new SimpleModelImpl();
    model.getTypes("asdfgh");
  }

  @Test
  public void testGetTypesWorking() { //doesn't exist
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("R1", "Rectangle", 0);
    model.addShapes("1", "Ellipse", 0);
    assertEquals("Rectangle", model.getTypes("R1"));
    assertEquals("Ellipse", model.getTypes("1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddShape() {
    assertEquals(new ArrayList<>(), this.animate.getShapes());
    this.animate.addShapes("R", "Rectangle", 0);
    assertEquals(new ArrayList<>(Collections.singletonList("R")), this.animate.getShapes());
    this.animate.addShapes("R", "Rectangle",0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddShape1() {
    assertEquals(new ArrayList<>(), this.animate.getShapes());
    this.animate.addShapes("C", "Ellipse", 0);
    assertEquals(new ArrayList<>(Collections.singletonList("C")), this.animate.getShapes());
    this.animate.addShapes("C", "Ellipse", 0);
  }

  @Test
  public void testAddShapes() {
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("rect1", "Rectangle",0);
    List<String> shapes = model.getShapes();
    assertEquals(1, shapes.size());
    assertEquals("rect1", shapes.get(0));
    assertTrue(model.getShapes().contains("rect1"));
    assertEquals("Rectangle", model.getTypes("rect1"));
  }

  @Test
  public void testAddFrames() {
    // adding frames and checking the lists, whether the lists are sorted.
    this.animate.addShapes("R", "Rectangle", 0);
    this.animate.addFrame("R", tickR1);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickR1)),
            this.animate.getFramesFor("R"));
    this.animate.addFrame("R", this.tickR10);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10)),
            this.animate.getFramesFor("R"));
    // adding behind
    this.animate.addFrame("R", this.tickR70);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10, this.tickR70)),
            this.animate.getFramesFor("R"));
    // adding before
    this.animate.addFrame("R", this.tickR51);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10,
            this.tickR51, this.tickR70)), this.animate.getFramesFor("R"));
  }

  @Test
  public void addFrameMappingItSelf() {
    // Mapping identical Keyframe
    this.animate.addShapes("R", "Rectangle", 0);
    this.animate.addFrame("R", tickR1);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickR1)),
            this.animate.getFramesFor("R"));
    this.animate.addFrame("R", tickR1);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickR1)),
            this.animate.getFramesFor("R"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addFrameMappingItSelf2() {
    // Mapping not identical Keyframe
    this.animate.addShapes("R", "Rectangle",0);
    IKeyFrame map = new KeyFrame(1, this.rectangleFour);
    this.animate.addFrame("R", tickR1);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickR1)),
            this.animate.getFramesFor("R"));
    this.animate.addFrame("R", map);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddFrames1() {
    // Identity doesn't exist
    this.animate.addShapes("R", "Rect",0);
    this.animate.addFrame("R", tickR1);
    this.animate.addFrame("c", this.tickR10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddFrames2() {
    // Identity doesn't exist
    this.animate.addFrame("c", this.tickR10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddFrames3() {
    // Type mis-match, adding oval into a list of rectangle Keyframe
    this.animate.addShapes("c", "Ellipse",0);
    this.animate.addFrame("c", tickR1);
  }

  @Test
  public void testRemoveFrame() {
    // adding and removing frames, and checking the list
    this.animate.addShapes("C", "Ellipse",0);
    this.animate.addFrame("C", tickO6);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickO6)),
            this.animate.getFramesFor("C"));
    this.animate.addFrame("C", this.tickO6);
    this.animate.addFrame("C", this.tickO100);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO6, this.tickO100)),
            this.animate.getFramesFor("C"));
    this.animate.addFrame("C", this.tickO20);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO6, this.tickO20, this.tickO100)),
            this.animate.getFramesFor("C"));
    this.animate.removeFrame("C", this.tickO20);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO6, this.tickO100)),
            this.animate.getFramesFor("C"));
    this.animate.removeFrame("C", this.tickO6);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO100)),
            this.animate.getFramesFor("C"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRemoveFrame() {
    // keyFrame doesn't exist
    this.animate.addShapes("C", "Ellipse",0);
    this.animate.addFrame("C", tickO6);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickO6)),
            this.animate.getFramesFor("C"));
    this.animate.removeFrame("C", this.tickO100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRemoveFrame1() {
    // Identity doesn't exist
    this.animate.removeFrame("C", this.tickO100);
  }


  @Test(expected = IllegalArgumentException.class)
  public void invalidRemoveFrame2() {
    // Identity doesn't exist
    this.animate.addShapes("R", "Rectangle",0);
    this.animate.addFrame("R", tickR1);
    this.animate.removeFrame("C", this.tickR1);
  }

  @Test
  public void removeShapes() {
    // Adding and removing shapes, and checking the list of shapes
    this.animate.addShapes("R", "Rectangle",0);
    assertEquals(new ArrayList<>(Collections.singletonList("R")), this.animate.getShapes());
    this.animate.addShapes("C", "Ellipse",0);
    assertEquals(new ArrayList<>(Arrays.asList("R", "C")), this.animate.getShapes());
    this.animate.removeShapes("C");
    assertEquals(new ArrayList<>(Collections.singletonList("R")), this.animate.getShapes());
    this.animate.addShapes("C", "Ellipse",0);
    this.animate.addShapes("D", "Ellipse",0);
    assertEquals(new ArrayList<>(Arrays.asList("R", "C", "D")), this.animate.getShapes());
    this.animate.removeShapes("C");
    assertEquals(new ArrayList<>(Arrays.asList("R", "D")), this.animate.getShapes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeShapes1() {
    // Adding and removing shapes, and checking the list of shapes
    this.animate.addShapes("R", "Rectangle",0);
    assertEquals(new ArrayList<>(Collections.singletonList("R")), this.animate.getShapes());
    assertEquals("Rectangle", this.animate.getTypes("R"));
    this.animate.removeShapes("R");
    assertEquals("Rectangle", this.animate.getTypes("R"));
  }

  @Test
  public void testGetShapes() {
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("rect1", "Rectangle",0);
    assertEquals((Collections.singletonList("rect1")), model.getShapes());
    model.addShapes("rect2", "Rectangle",0);
    assertEquals(Arrays.asList("rect1", "rect2"), model.getShapes());
    model.addShapes("rect4", "e",0);
    assertEquals(Arrays.asList("rect1", "rect2", "rect4"), model.getShapes());
  }

  @Test
  public void testGetFramesFor() {
    // Adding frames and checking the List of frames.
    this.animate.addShapes("R", "Rectangle",0);
    assertEquals(new ArrayList<>(), this.animate.getFramesFor("R"));
    this.animate.addFrame("R", this.tickR1);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickR1)),
            this.animate.getFramesFor("R"));
    this.animate.addFrame("R", this.tickR10);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10)),
            this.animate.getFramesFor("R"));
    this.animate.addFrame("R", this.tickR50);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10, this.tickR50)),
            this.animate.getFramesFor("R"));
    this.animate.addFrame("R", this.tickR100);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10,
            this.tickR50, this.tickR100)), this.animate.getFramesFor("R"));
    this.animate.addFrame("R", this.tickR70);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickR1, this.tickR10,
            this.tickR50, this.tickR70, this.tickR100)), this.animate.getFramesFor("R"));
    // This also show that list of keyFrames are sorted.
    this.animate.addShapes("C", "Ellipse",0);
    assertEquals(new ArrayList<>(),
            this.animate.getFramesFor("C"));
    this.animate.addFrame("C", this.tickO20);
    assertEquals(new ArrayList<>(Collections.singletonList(this.tickO20)),
            this.animate.getFramesFor("C"));
    this.animate.addFrame("C", this.tickO70);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO20, this.tickO70)),
            this.animate.getFramesFor("C"));
    this.animate.addFrame("C", this.tickO100);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO20, this.tickO70, this.tickO100)),
            this.animate.getFramesFor("C"));
    this.animate.addFrame("C", this.tickO50);
    assertEquals(new ArrayList<>(Arrays.asList(this.tickO20, this.tickO50,
            this.tickO70, this.tickO100)), this.animate.getFramesFor("C"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGetFramesFor1() {
    // Identity doesn't exist
    this.animate.addShapes("R", "Rectangle",0);
    assertEquals(new ArrayList<>(Collections.singletonList("R")), this.animate.getShapes());
    this.animate.getFramesFor("C");
  }

  @Test
  public void getShapeAt() {
    IKeyFrame tick100 = new KeyFrame(100, this.rectangleOne);
    IKeyFrame tick200 = new KeyFrame(200, this.rectangleFour);
    this.animate.addShapes("R", "Rectangle",0);
    this.animate.addFrame("R", tick100);
    this.animate.addFrame("R", tick200);
    assertEquals(this.rectangleOne, this.animate.getShapeAt("R", 100));
    assertEquals(this.rectangleFour, this.animate.getShapeAt("R", 200));

    assertNull(this.animate.getShapeAt("R", 210));
    assertNull(this.animate.getShapeAt("R", 99));

    // Position Testing
    IShape inBetween = new RectangleImpl(250, 250,
            50, 100, 255, 0, 0,0);
    IShape inBetween1 = new RectangleImpl(290, 290,
            50, 100, 255, 0, 0,0);
    assertEquals(inBetween, this.animate.getShapeAt("R", 150));
    assertEquals(inBetween1, this.animate.getShapeAt("R", 190));

    // More Test on Positions, Colors, and Dimensions
    IShape rectangle = new RectangleImpl(250, 210,
            24, 170, 255, 20, 90,0);
    IShape rectangle1 = new RectangleImpl(800, 1010,
            24, 100, 200, 210, 100,0);
    IKeyFrame tick240 = new KeyFrame(240, rectangle);
    IKeyFrame tick300 = new KeyFrame(300, rectangle1);
    this.animate.addFrame("R", tick240);
    this.animate.addFrame("R", tick300);
    IShape rectangle3 = new RectangleImpl(625, 756,
            24, 122, 217, 149, 96,0);
    assertEquals(rectangle, this.animate.getShapeAt("R", 240));
    assertEquals(rectangle1, this.animate.getShapeAt("R", 300));
    assertEquals(rectangle3, this.animate.getShapeAt("R", 281));
  }

  @Test
  public void getShapeAtTwo() {
    IKeyFrame tick100 = new KeyFrame(100, this.ovalOne);
    IKeyFrame tick200 = new KeyFrame(200, this.ovalThree);
    this.animate.addShapes("O", "ellipse",0);
    this.animate.addFrame("O", tick100);
    this.animate.addFrame("O", tick200);
    assertEquals(this.ovalOne, this.animate.getShapeAt("O", 100));
    assertEquals(this.ovalThree, this.animate.getShapeAt("O", 200));

    assertNull(this.animate.getShapeAt("O", 210));
    assertNull(this.animate.getShapeAt("O", 99));

    // Position Testing
    IShape inBetween = new EllipseImpl(440, 160,
            120, 60, 0, 0, 255,0);
    IShape inBetween1 = new EllipseImpl(440, 232,
            120, 60, 0, 0, 255,0);
    assertEquals(inBetween, this.animate.getShapeAt("O", 150));
    assertEquals(inBetween1, this.animate.getShapeAt("O", 190));

    // More Test on Positions, Colors, and Dimensions
    IShape oval = new EllipseImpl(120, 120,
            24, 150, 200, 50, 90,0);
    IShape ovall = new EllipseImpl(200, 240,
            48, 300, 100, 80, 200,0);
    IKeyFrame tick450 = new KeyFrame(450, oval);
    IKeyFrame tick600 = new KeyFrame(600, ovall);
    this.animate.addFrame("O", tick450);
    this.animate.addFrame("O", tick600);
    IShape ovalll = new EllipseImpl(160, 180,
            36, 225, 150, 65, 145,0);
    assertEquals(oval, this.animate.getShapeAt("O", 450));
    assertEquals(ovall, this.animate.getShapeAt("O", 600));
    assertEquals(ovalll, this.animate.getShapeAt("O", 525));
    assertEquals(null, this.animate.getShapeAt("O", 601));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeAt3() {
    // Identity doesn't exist
    this.animate.getShapeAt("R", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeAt4() {
    // Invalid tick
    this.animate.addShapes("O", "Ellipse",0);
    this.animate.addFrame("O", this.tickO6);
    this.animate.getShapeAt("R", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeAt5() {
    // Invalid tick
    this.animate.addShapes("O", "Ellipse",0);
    this.animate.addFrame("O", this.tickO6);
    this.animate.getShapeAt("O", -34);
  }


  @Test
  public void testIsAnimationOver() {
    assertTrue(this.animate.isAnimationOver(1));
    assertTrue(this.animate.isAnimationOver(2));
    this.animate.addShapes("C", "ellipse",0);
    this.animate.addFrame("C", this.tickO6);
    assertFalse(this.animate.isAnimationOver(6));
    assertFalse(this.animate.isAnimationOver(5));
    this.animate.addShapes("R", "rectangle",0);
    this.animate.addFrame("R", this.tickR50);
    assertFalse(this.animate.isAnimationOver(49));
    assertTrue(this.animate.isAnimationOver(51));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidIsAnimationOver1() {
    // Invalid tick, tick should not be less than 0.
    assertTrue(this.animate.isAnimationOver(-2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidIsAnimationOver2() {
    // Invalid tick, tick should not be less than 0.
    assertTrue(this.animate.isAnimationOver(-10));
  }
}
