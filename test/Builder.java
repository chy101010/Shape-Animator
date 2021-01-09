import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the Builder in the SimpleModelImpl. Verifying that the methods are functioning as
 * intended.
 */
public class Builder {

  private SimpleModelImpl.Builder builder1;

  @Before
  public void textFixture() {
    this.builder1 = new SimpleModelImpl.Builder();
  }

  @Test
  public void testBuild() {
    AnimatorModel model = this.builder1.build();
    assertEquals(new ArrayList<>(), model.getShapes());
    assertEquals(0, model.getViews()[0]);
    assertEquals(0, model.getViews()[1]);
    assertEquals(0, model.getViews()[2]);
    assertEquals(0, model.getViews()[3]);
  }

  @Test
  public void testSetBounds() {
    AnimatorModel model = this.builder1.build();
    assertEquals(0, model.getViews()[0]);
    assertEquals(0, model.getViews()[1]);
    assertEquals(0, model.getViews()[2]);
    assertEquals(0, model.getViews()[3]);
    this.builder1.setBounds(10, 20, 30, 50);
    AnimatorModel model1 = this.builder1.build();
    assertEquals(10, model1.getViews()[0]);
    assertEquals(20, model1.getViews()[1]);
    assertEquals(30, model1.getViews()[2]);
    assertEquals(50, model1.getViews()[3]);
  }

  @Test
  public void testDeclareShape() {
    AnimatorModel model = this.builder1.build();
    assertEquals(new ArrayList<>(), model.getShapes());
    this.builder1.declareShape("Hello", "r", 0);
    assertEquals(new ArrayList<>(Arrays.asList("Hello")), model.getShapes());
    this.builder1.declareShape("Bye", "O", 0);
    assertEquals(new ArrayList<>(Arrays.asList("Hello", "Bye")), model.getShapes());
    assertEquals("O", model.getTypes("Bye"));
    assertEquals("r", model.getTypes("Hello"));
    this.builder1.declareShape("morning", "O", 0);
    assertEquals(new ArrayList<>(Arrays.asList("Hello", "Bye", "morning")), model.getShapes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDeclareShape() {
    this.builder1.declareShape("h", "l", 0);
    this.builder1.declareShape("h", "lq", 0);
  }

  @Test
  public void testAddMotion() {
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addMotion("h", 1, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
    this.builder1.addMotion("h", 7, 10, 80, 100, 12,
            90, 212, 123, 0,
            22, 31, 441, 121, 15, 123, 55, 0, 0);
    AnimatorModel model = this.builder1.build();
    IKeyFrame first = new KeyFrame(1, new RectangleImpl(0, 0, 0, 2,
            2, 2, 2, 2));
    IKeyFrame second = new KeyFrame(2, new RectangleImpl(2, 2, 2, 2,
            2, 2, 0, 0));
    IKeyFrame third = new KeyFrame(7, new RectangleImpl(10, 80, 100, 12,
            90, 212, 123, 0));
    IKeyFrame four = new KeyFrame(22, new RectangleImpl(31, 441, 121, 15,
            123, 55, 0, 0));
    assertEquals(new ArrayList<>(Arrays.asList(first, second, third, four)),
            model.getFramesFor("h"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddMotion() {
    // t2 < t1
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addMotion("h", 10, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddMotion1() {
    // identity doesn't exist
    this.builder1.addMotion("h", 10, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddMotion2() {
    // Mapping two different motions
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addMotion("h", 10, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
    this.builder1.addMotion("h", 10, 0, 0, 0, 2, 2, 2, 2,
            2, 1, 1, 1, 1, 1, 1, 1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddMotion3() {
    // Mapping two different motions
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addMotion("h", 10, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
    this.builder1.addMotion("h", 10, 0, 1, 0, 2, 2, 2, 2,
            3, 1, 1, 1, 1, 1, 1, 1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddMotion4() {
    // Mapping two different motions
    this.builder1.declareShape("h", "rectangle", 0);
    this.builder1.addMotion("h", 9, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
    this.builder1.addMotion("h", 10, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddMotion5() {
    // unsupported shape
    this.builder1.declareShape("h", "haha", 0);
    this.builder1.addMotion("h", 9, 0, 0, 0, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 0, 0);
  }

  @Test
  public void testAddKeyFrame() {
    this.builder1.declareShape("h", "ellipse", 0);
    this.builder1.addKeyframe("h", 2, 2, 2, 2, 2, 2, 2, 2, 0);
    AnimatorModel model = this.builder1.build();
    IShape first = new EllipseImpl(2, 2, 2, 2, 2, 2, 2, 0);
    assertEquals(first, model.getFramesFor("h").get(0).getShape());
    this.builder1.addKeyframe("h", 3, 3, 3, 3, 3, 3, 3, 3, 0);
    IShape second = new EllipseImpl(3, 3, 3, 3, 3, 3, 3, 0);
    assertEquals(second, model.getFramesFor("h").get(1).getShape());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddKeyFrame() {
    // identity doesn't exist
    this.builder1.addKeyframe("h", 2, 2, 2, 2, 2, 2, 2, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAddKeyFrame1() {
    // mapping two different keyframes
    this.builder1.declareShape("h", "ellipse", 0);
    this.builder1.addKeyframe("h", 2, 2, 2, 2, 2, 2, 2, 2, 0);
    this.builder1.addKeyframe("h", 2, 2, 2, 3, 2, 2, 2, 2, 0);
  }
}
