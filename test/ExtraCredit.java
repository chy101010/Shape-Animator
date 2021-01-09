import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.animator.controller.EditController;
import cs3500.animator.controller.Features;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IEditView;
import cs3500.animator.view.IView;
import cs3500.animator.view.MockEditView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.ViewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * The tests for changed and added functionality of layer and orientation.
 */
public class ExtraCredit {
  AnimatorModel model;
  SimpleModelImpl.Builder builder1;

  @Before
  public void textFixture() {
    this.builder1 = new SimpleModelImpl.Builder();
    this.model = new SimpleModelImpl();
  }

  // Testing Shapes
  @Test
  public void testEqualHashCode() {
    IShape rec = new RectangleImpl(10, 15, 20, 25, 30, 35, 40, 100);
    IShape rec1 = new RectangleImpl(10, 15, 20, 25, 30, 35, 40, 100);
    IShape rec2 = new RectangleImpl(10, 15, 20, 25, 30, 35, 40, 110);
    IShape cir = new EllipseImpl(10, 15, 20, 25, 30, 35, 40, 100);
    IShape cir1 = new EllipseImpl(10, 15, 20, 25, 30, 35, 40, 100);
    IShape cir2 = new EllipseImpl(10, 15, 20, 25, 30, 35, 40, 110);
    // Same
    assertEquals(rec, rec1);
    assertEquals(rec.hashCode(), rec1.hashCode());
    // Different Orientation
    assertNotEquals(rec, rec2);
    assertNotEquals(rec.hashCode(), rec2.hashCode());
    // Different Orientation
    assertNotEquals(rec2, rec1);
    assertNotEquals(rec2.hashCode(), rec1.hashCode());
    // Different Shape
    assertNotEquals(rec, cir);
    assertEquals(rec.hashCode(), cir.hashCode());
    // Same
    assertEquals(cir, cir1);
    assertEquals(cir.hashCode(), cir1.hashCode());
    // Different Orientation
    assertNotEquals(cir, cir2);
    assertNotEquals(cir.hashCode(), cir2.hashCode());
    // Different Orientation
    assertNotEquals(cir2, cir1);
    assertNotEquals(cir2.hashCode(), cir1.hashCode());
    // Different Shape but same everything
    assertEquals(cir2.hashCode(), rec2.hashCode());
  }

  // Testing Shapes
  @Test
  public void testGetDegreeToString() {
    IShape rec = new RectangleImpl(10, 15, 20, 25, 30, 35, 40, 10);
    IShape rec1 = new RectangleImpl(10, 15, 20, 25, 30, 35, 40, 50);
    IShape rec2 = new RectangleImpl(10, 15, 20, 25, 30, 35, 40, 110);
    IShape cir = new EllipseImpl(10, 15, 20, 25, 30, 35, 40, -50);
    IShape cir1 = new EllipseImpl(10, 15, 20, 25, 30, 35, 40, 1000);
    IShape cir2 = new EllipseImpl(10, 15, 20, 25, 30, 35, 40, 360);
    assertEquals(10, rec.getDegree());
    assertEquals(50, rec1.getDegree());
    assertEquals(110, rec2.getDegree());
    assertEquals(-50, cir.getDegree());
    assertEquals(1000, cir1.getDegree());
    assertEquals(360, cir2.getDegree());

    assertEquals("10 15 20 25 30 35 40 10", rec.toString());
    assertEquals("10 15 20 25 30 35 40 50", rec1.toString());
    assertEquals("10 15 20 25 30 35 40 110", rec2.toString());
    assertEquals("10 15 20 25 30 35 40 -50", cir.toString());
    assertEquals("10 15 20 25 30 35 40 1000", cir1.toString());
    assertEquals("10 15 20 25 30 35 40 360", cir2.toString());
  }

  // Testing Shapes
  @Test
  public void testCreateOwnCopy() {
    IShape rec = new RectangleImpl(90, 95, 100, 105, 130, 35, 40, 20);
    IShape cir = new EllipseImpl(10, 25, 40, 65, 30, 35, 100, -50);
    assertEquals(rec, rec.copy());
    assertEquals(cir, cir.copy());
    assertNotEquals(rec, cir.copy());
    assertTrue(rec.sameType(rec.createOwnType(10, 10, 10, 10,
            10, 10, 10, 10)));
    assertTrue(cir.sameType(cir.createOwnType(10, 10, 10, 10,
            10, 10, 10, 10)));
    assertTrue(rec.sameType(rec.createOwnType(10, 10, 10, 10,
            10, 10, 10, 10)));
    assertFalse(cir.sameType(rec.createOwnType(10, 10, 10, 10,
            10, 10, 10, 10)));
  }


  // Testing Animation Reader without Layer/Orientation
  @Test
  public void testReader() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("smalldemo.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
    assertEquals("canvas 200 70 360 360 \n" +
                    "Shape R Rectangle Layer0\n" +
                    "motion R 1 200 200 50 100 255 0 0 0  10 200 200 50 100 255 0 0 0\n" +
                    "motion R 10 200 200 50 100 255 0 0 0  50 300 300 50 100 255 0 0 0\n" +
                    "motion R 50 300 300 50 100 255 0 0 0  51 300 300 50 100 255 0 0 0\n" +
                    "motion R 51 300 300 50 100 255 0 0 0  70 300 300 25 100 255 0 0 0\n" +
                    "motion R 70 300 300 25 100 255 0 0 0  100 200 200 25 100 255 0 0 0\n" +
                    "\n" +
                    "Shape C Ellipse Layer0\n" +
                    "motion C 6 440 70 120 60 0 0 255 0  20 440 70 120 60 0 0 255 0\n" +
                    "motion C 20 440 70 120 60 0 0 255 0  50 440 250 120 60 0 0 255 0\n" +
                    "motion C 50 440 250 120 60 0 0 255 0  70 440 370 120 60 0 170 85 0\n" +
                    "motion C 70 440 370 120 60 0 170 85 0  80 440 370 120 60 0 255 0 0\n" +
                    "motion C 80 440 370 120 60 0 255 0 0  100 440 370 120 60 0 255 0 360",
            append.toString());
  }

  // Testing Animation Reader With Layer
  @Test
  public void testReaderLayer() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("readTest1.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
    assertEquals("canvas 200 70 360 360 \n" +
                    "Shape R Rectangle Layer1\n" +
                    "motion R 1 200 200 50 100 255 0 0 0  10 440 100 50 100 255 0 0 0\n" +
                    "\n" +
                    "Shape C Ellipse Layer2\n" +
                    "motion C 1 200 200 60 100 0 255 0 0  10 440 70 60 100 0 255 0 0\n" +
                    "\n" +
                    "Shape C3 Ellipse Layer3\n" +
                    "motion C3 1 200 80 60 100 255 0 255 0  10 440 80 60 100 255 0 255 0\n" +
                    "\n" +
                    "Shape C1 Ellipse Layer4\n" +
                    "motion C1 1 200 100 60 100 0 0 0 0  10 440 100 60 100 0 0 0 0\n" +
                    "\n" +
                    "Shape C2 Ellipse Layer5\n" +
                    "motion C2 1 200 90 60 100 0 0 200 0  10 440 90 60 100 0 0 200 0",
            append.toString());
  }

  // Testing Animation Reader With Orientation
  @Test
  public void testReaderOrientation() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("readTest2.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
    assertEquals("canvas 200 70 360 360 \n" +
                    "Shape R Rectangle Layer1\n" +
                    "motion R 1 200 200 50 100 255 0 0 0  10 440 100 50 100 255 0 0 360\n" +
                    "motion R 10 440 100 50 100 255 0 0 360  50 440 250 50 100 255 0 0 0\n" +
                    "motion R 50 440 250 50 100 255 0 0 0  51 440 370 50 100 255 0 0 0\n" +
                    "motion R 51 440 370 50 100 255 0 0 0  70 440 370 25 100 255 0 0 0\n" +
                    "motion R 70 440 370 25 100 255 0 0 0  110 440 370 25 100 255 0 0 0\n" +
                    "\n" +
                    "Shape C3 Ellipse Layer3\n" +
                    "motion C3 1 200 80 60 100 255 0 255 0  10 440 80 60 100 255 0 255 90\n" +
                    "motion C3 10 440 80 60 100 255 0 255 90  50 440 80 60 100 255 0 255 0\n" +
                    "motion C3 50 440 80 60 100 255 0 255 0  51 440 80 60 100 255 0 255 0\n" +
                    "motion C3 51 440 80 60 100 255 0 255 0  70 440 80 60 100 255 0 255 -360\n" +
                    "motion C3 70 440 80 60 100 255 0 255 -360  110 440 80 60 100 255 0 255 50",
            append.toString());
  }

  // Testing Animation Reader With Orientation/Layer
  @Test
  public void testReaderOrientationLayer() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("readTest3.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
    assertEquals("canvas 200 70 360 360 \n" +
                    "Shape R Rectangle Layer1\n" +
                    "motion R 1 200 200 50 100 255 0 0 0  10 440 100 50 100 255 0 0 360\n" +
                    "motion R 10 440 100 50 100 255 0 0 360  50 440 250 50 100 255 0 0 0\n" +
                    "\n" +
                    "Shape C Ellipse Layer2\n" +
                    "motion C 1 200 200 60 100 0 255 0 0  10 440 70 60 100 0 255 0 0\n" +
                    "motion C 10 440 70 60 100 0 255 0 0  50 440 250 60 100 0 255 0 0\n" +
                    "\n" +
                    "Shape C3 Ellipse Layer3\n" +
                    "motion C3 1 200 80 60 100 255 0 255 0  10 440 80 60 100 255 0 255 90\n" +
                    "motion C3 10 440 80 60 100 255 0 255 90  50 440 80 60 100 255 0 255 0\n" +
                    "\n" +
                    "Shape C1 Ellipse Layer4\n" +
                    "motion C1 1 200 100 60 100 0 0 70 0  10 440 100 60 100 0 0 60 0\n" +
                    "motion C1 10 440 100 60 100 0 0 60 0  50 440 100 60 100 0 0 100 0\n" +
                    "\n" +
                    "Shape C2 Ellipse Layer5\n" +
                    "motion C2 1 200 90 60 100 0 0 200 60  10 440 90 60 100 0 0 200 60\n" +
                    "motion C2 10 440 90 60 100 0 0 200 60  50 440 90 60 100 255 0 200 100",
            append.toString());
  }

  // Testing incorrect input for Orientation there must be an ending orientation if
  // there is an initial.
  @Test(expected = IllegalArgumentException.class)
  public void testReaderWrongOrientation() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("readTest4.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
  }

  // Testing incorrect input for Orientation there must be an initial orientation if
  // there is an ending.
  @Test(expected = IllegalArgumentException.class)
  public void testReaderWrongOrientation1() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("readTest5.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
  }

  // Testing incomplete inputs
  @Test(expected = IllegalArgumentException.class)
  public void testReaderIncomplete() {
    AnimationReader reader = new AnimationReader();
    StringBuilder append = new StringBuilder();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader("readTest6.txt"),
              new SimpleModelImpl.Builder());
      IView text = new TextualView(new ViewModel(model), append);
      text.display();
    } catch (FileNotFoundException ieo) {
      throw new IllegalArgumentException("File not read");
    }
  }


  // Testing Builder AddMotion/DeclareShape/
  @Test
  public void testBuilder() {
    AnimatorModel model = this.builder1.build();
    assertEquals(new ArrayList<>(), model.getShapes());
    this.builder1.declareShape("Hello", "r", 1);
    assertEquals(new ArrayList<>(Arrays.asList("Hello")), model.getShapes());
    this.builder1.declareShape("Bye", "O", 2);
    assertEquals(new ArrayList<>(Arrays.asList("Hello", "Bye")), model.getShapes());
    assertEquals("O", model.getTypes("Bye"));
    assertEquals("r", model.getTypes("Hello"));
    this.builder1.declareShape("morning", "O", 2);
    this.builder1.declareShape("good", "O", 4);
    this.builder1.declareShape("bye", "O", 3);
    assertEquals(new ArrayList<>(Arrays.asList("Hello", "Bye", "morning",
            "bye", "good")), model.getShapes());
  }

  // Invalid declare Shape
  @Test(expected = IllegalArgumentException.class)
  public void invalidDeclareShape() {
    this.builder1.declareShape("h", "l", 0);
    this.builder1.declareShape("h", "lq", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDeclareShape1() {
    this.builder1.declareShape("h", "l", 1);
    this.builder1.declareShape("h", "lq", 1);
  }

  // Testing Builder add Motion
  @Test
  public void testAddMotion() {
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addMotion("h", 1, 0, 0, 0, 2, 2, 2, 2,
            90, 2, 2, 2, 2, 2, 2, 2, 0, 30);
    this.builder1.addMotion("h", 7, 10, 80, 100, 12,
            90, 212, 123, 50,
            22, 31, 441, 121, 15, 123, 55, 0, 0);
    AnimatorModel model = this.builder1.build();
    IKeyFrame first = new KeyFrame(1, new RectangleImpl(0, 0, 0, 2,
            2, 2, 2, 90));
    IKeyFrame second = new KeyFrame(2, new RectangleImpl(2, 2, 2, 2,
            2, 2, 0, 30));
    IKeyFrame third = new KeyFrame(7, new RectangleImpl(10, 80, 100, 12,
            90, 212, 123, 50));
    IKeyFrame four = new KeyFrame(22, new RectangleImpl(31, 441, 121, 15,
            123, 55, 0, 0));
    assertEquals(new ArrayList<>(Arrays.asList(first, second, third, four)),
            model.getFramesFor("h"));
  }

  // Testing Builder add Keyframe
  @Test
  public void testAddKeyframe() {
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addKeyframe("h", 10, 10, 20, 20, 30, 40, 50, 60, 10);
    IKeyFrame keyFrame = new KeyFrame(10, new RectangleImpl(10, 20, 20, 30,
            40, 50, 60, 10));
    AnimatorModel model = this.builder1.build();
    assertEquals(new ArrayList<>(Arrays.asList(keyFrame)), model.getFramesFor("h"));
  }

  // Testing Model/lastTick/addShape/getLayer/removeLayer/swapLayer
  @Test
  public void testAddShape() {
    // The returned list should have the Shape in the order of ascending layers.
    this.model.addShapes("R1", "Rectangle", 1);
    this.model.addShapes("R11", "Rectangle", 1);
    this.model.addShapes("R2", "Rectangle", 2);
    this.model.addShapes("R5", "Rectangle", 5);
    this.model.addShapes("R4", "Rectangle", 4);
    this.model.addShapes("E3", "Ellipse", 3);
    this.model.addShapes("E33", "Ellipse", 3);
    this.model.addShapes("E333", "Ellipse", 3);
    this.model.addShapes("R3333", "Rectangle", 3);
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R11", "R2", "E3", "E33", "E333",
            "R3333", "R4", "R5")),
            this.model.getShapes());
  }

  @Test
  public void testLastTick() {
    this.model.addShapes("R", "Rectangle", 1);
    assertEquals(0, this.model.lastTick());
    this.model.addFrame("R", new KeyFrame(10,
            new RectangleImpl(1, 0, 1, 1, 1, 1, 1, 1)));
    assertEquals(10, this.model.lastTick());
    this.model.addFrame("R", new KeyFrame(100,
            new RectangleImpl(1, 0, 1, 1, 1, 1, 1, 1)));
    assertEquals(100, this.model.lastTick());
    this.model.addShapes("E", "Ellipse", 10);
    this.model.addFrame("E", new KeyFrame(101,
            new EllipseImpl(1, 0, 1, 1, 1, 1, 1, 1)));
    assertEquals(101, this.model.lastTick());
    this.model.removeLayer(10);
    assertEquals(100, this.model.lastTick());
    this.model.removeLayer(1);
    assertEquals(0, this.model.lastTick());
  }

  @Test
  public void testGetLayer() {
    this.model.addShapes("R", "Rectangle", 10);
    this.model.addShapes("R1", "Rectangle", -1);
    this.model.addShapes("R2", "Rectangle", 74);
    assertEquals(10, this.model.getLayer("R"));
    assertEquals(-1, this.model.getLayer("R1"));
    assertEquals(74, this.model.getLayer("R2"));
  }

  @Test
  public void testRemovelayer() {
    this.model.addShapes("R", "Rectangle", 10);
    this.model.addShapes("R1", "Rectangle", -1);
    this.model.addFrame("R", new KeyFrame(10,
            new RectangleImpl(1, 0, 1, 1, 1, 1, 1, 1)));
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R")), this.model.getShapes());
    assertEquals(new ArrayList<>(Arrays.asList(new KeyFrame(10,
                    new RectangleImpl(1, 0, 1, 1, 1, 1, 1, 1)))),
            this.model.getFramesFor("R"));
    this.model.removeLayer(10);
    assertEquals(new ArrayList<>(Arrays.asList("R1")), this.model.getShapes());
    this.model.addShapes("R", "Rectangle", 10);
    this.model.addShapes("R10", "Rectangle", 10);
    assertNotEquals(new ArrayList<>(Arrays.asList(new KeyFrame(10,
                    new RectangleImpl(1, 0, 1, 1, 1, 1, 1, 1)))),
            this.model.getFramesFor("R"));
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R", "R10")), this.model.getShapes());
    this.model.removeLayer(10);
    this.model.removeLayer(-1);
    assertEquals(new ArrayList<>(), this.model.getShapes());
  }

  @Test
  public void testSwapLayer() {
    this.model.addShapes("R1", "Rectangle", 1);
    this.model.addShapes("R11", "Rectangle", 1);
    this.model.addShapes("R3", "Rectangle", 3);
    this.model.addShapes("R4", "Rectangle", 4);
    this.model.addShapes("R44", "Rectangle", 4);
    this.model.addShapes("R5", "Rectangle", 5);
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R11", "R3", "R4", "R44", "R5")),
            this.model.getShapes());
    this.model.swapLayer(1, 4);
    assertEquals(new ArrayList<>(Arrays.asList("R4", "R44", "R3", "R1", "R11", "R5")),
            this.model.getShapes());
    assertEquals(4, this.model.getLayer("R1"));
    assertEquals(4, this.model.getLayer("R11"));
    assertEquals(1, this.model.getLayer("R4"));
    assertEquals(1, this.model.getLayer("R44"));
    this.model.swapLayer(3, 7);
    assertEquals(new ArrayList<>(Arrays.asList("R4", "R44", "R1", "R11", "R5", "R3")),
            this.model.getShapes());
    assertEquals(7, this.model.getLayer("R3"));
  }


  // Testing Features/removeLayer/swapLayer/addKeyFrame/addShape/editKeyFrame
  @Test
  public void testFeature() {
    StringBuilder log = new StringBuilder();
    IEditView mock = new MockEditView(log);
    Features controller = (Features) (new EditController(mock, this.model));
    ///////////////////Testing addShape
    controller.addShape("R1", "Rectangle", 1);
    controller.addShape("R2", "Rectangle", 2);
    controller.addShape("R11", "Rectangle", 1);
    controller.addShape("E1", "Ellipse", 1);
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R11", "E1", "R2")),
            this.model.getShapes());
    ////////////////Testing remove Layer
    controller.addShape("R111", "Rectangle", 1);
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R11", "E1", "R111", "R2")),
            this.model.getShapes());
    controller.removeLayer(1);
    assertEquals(new ArrayList<>(Arrays.asList("R2")),
            this.model.getShapes());
    controller.removeLayer(2);
    assertEquals(new ArrayList<>(),
            this.model.getShapes());
    //////////////////Testing swapLayer
    controller.addShape("R1", "Rectangle", 1);
    controller.addShape("R11", "Rectangle", 1);
    controller.addShape("R2", "Rectangle", 2);
    controller.addShape("R3", "Rectangle", 3);
    assertEquals(new ArrayList<>(Arrays.asList("R1", "R11", "R2", "R3")),
            this.model.getShapes());
    controller.swapLayer(1, 2);
    assertEquals(new ArrayList<>(Arrays.asList("R2", "R1", "R11", "R3")),
            this.model.getShapes());
    //////////////Testing add/edit keyframe
    controller.selectShape("R1");
    assertEquals(new ArrayList<>(), this.model.getFramesFor("R1"));
    controller.addKeyFrame(1, 1, 1, 1, 1, 1, 1, 1, 1);
    IKeyFrame frame = new KeyFrame(1, new RectangleImpl(1, 1, 1, 1,
            1, 1, 1, 1));
    assertEquals(new ArrayList<>(Arrays.asList(frame)), this.model.getFramesFor("R1"));
    IKeyFrame frame1 = new KeyFrame(1, new RectangleImpl(1, 2, 2, 1,
            1, 1, 1, 2));
    controller.editKeyFrame(1, 2, 2, 1, 1, 1, 1, 1, 2);
  }
}
