import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.animator.controller.EditController;
import cs3500.animator.controller.Features;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.MockModel;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.view.EditView;
import cs3500.animator.view.IEditView;

/**
 * Test cases for the features implementation in the EditController. Verifying the methods that
 * mutate the model are functioning as intended and the communication between the controller and the
 * model is correct.
 */
public class FeaturesAndModelTest {
  private Features editController;
  private IEditView view;
  private AnimatorModel model;
  private Features mockController;
  private StringBuilder log;

  @Before
  public void testFixture() {
    this.view = new EditView();
    this.model = new SimpleModelImpl();
    this.model.addShapes("R", "rectangle",0);
    this.model.addShapes("C", "ellipse",0);
    this.log = new StringBuilder();
    MockModel mock = new MockModel(log);
    this.editController = new EditController(this.view, this.model);
    this.mockController = new EditController(this.view, mock);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    this.editController = new EditController(null, this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    this.editController = new EditController(this.view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    this.editController = new EditController(null, null);
  }


  // Note: Invalid Remove Shape Will Results In A Pop Up Of JOptionPanel So It Can't Be Rested.
  @Test
  public void removeShapeTest() {
    List<String> arr = new ArrayList<>(Arrays.asList("R", "C"));
    assertEquals(arr, this.model.getShapes());
    this.editController.selectShape("R");
    this.editController.removeShape();
    arr.remove("R");
    assertEquals(arr, this.model.getShapes());
    this.editController.selectShape(null);
    this.editController.selectShape("C");
    this.editController.removeShape();
    arr.remove("C");
    assertEquals(arr, this.model.getShapes());
  }

  // Note: Invalid Add Shape Will Results In A Pop Up Of JOptionPanel So It Can't Be Rested.
  @Test
  public void addShape() {
    List<String> arr = new ArrayList<>(Arrays.asList("R", "C"));
    assertEquals(arr, this.model.getShapes());
    this.editController.addShape("B", "ellipse",0);
    arr.add("B");
    assertEquals(arr, this.model.getShapes());
    this.editController.addShape("L", "ellipse",0);
    arr.add("L");
    assertEquals(arr, this.model.getShapes());
  }

  // Note: Invalid Add Keyframe Will Results In A Pop Up Of JOptionPanel So It Can't Be Rested.
  @Test
  public void addKeyFrame() {
    List<IKeyFrame> arr = new ArrayList<>();
    assertEquals(arr, this.model.getFramesFor("R"));
    this.editController.selectShape("R");
    this.editController.addKeyFrame(1, 1, 1, 1, 1, 1, 1, 1,0);
    IKeyFrame key1 = new KeyFrame(1, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    arr.add(key1);
    assertEquals(arr, this.model.getFramesFor("R"));

    this.editController.addKeyFrame(2, 1, 1, 1, 1, 1, 1, 1,0);
    IKeyFrame key2 = new KeyFrame(2, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    arr.add(key2);
    assertEquals(arr, this.model.getFramesFor("R"));

    this.editController.addKeyFrame(3, 1, 1, 1, 1, 1, 1, 1,0);
    IKeyFrame key3 = new KeyFrame(3, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    arr.add(key3);
    assertEquals(arr, this.model.getFramesFor("R"));

    List<IKeyFrame> arr1 = new ArrayList<>();
    assertEquals(arr1, this.model.getFramesFor("C"));
    this.editController.selectShape("C");
    this.editController.addKeyFrame(1, 1, 1, 1, 1, 1, 1, 1,0);
    IKeyFrame keyO1 = new KeyFrame(1, new EllipseImpl(1, 1, 1,
            1, 1, 1, 1,0));
    arr1.add(keyO1);
    assertEquals(arr1, this.model.getFramesFor("C"));

    this.editController.addKeyFrame(2, 3, 4, 5, 6, 7, 8, 9,0);
    IKeyFrame keyO2 = new KeyFrame(2, new EllipseImpl(3, 4, 5,
            6, 7, 8, 9,0));
    arr1.add(keyO2);
    assertEquals(arr1, this.model.getFramesFor("C"));

    this.editController.addKeyFrame(4, 4, 4, 4, 4, 4, 4, 4,0);
    IKeyFrame key03 = new KeyFrame(4, new EllipseImpl(3, 4, 5,
            6, 7, 8, 9,0));
    IKeyFrame key033 = new KeyFrame(4, new EllipseImpl(4, 4, 4,
            4, 4, 4, 4,0));
    arr1.add(key03);
    assertNotEquals(arr1, this.model.getFramesFor("C"));
    arr1.remove(key03);
    arr1.add(key033);
    assertEquals(arr1, this.model.getFramesFor("C"));
  }

  // Note: Invalid Remove Keyframe Will Results In A Pop Up Of JOptionPanel So It Can't Be Rested.
  @Test
  public void removeKeyFrame() {
    List<IKeyFrame> arr = new ArrayList<>();
    assertEquals(arr, this.model.getFramesFor("R"));
    IKeyFrame key1 = new KeyFrame(1, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    IKeyFrame key2 = new KeyFrame(2, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    IKeyFrame key3 = new KeyFrame(3, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    this.model.addFrame("R", key1);
    this.model.addFrame("R", key2);
    this.model.addFrame("R", key3);
    arr.add(key1);
    arr.add(key2);
    arr.add(key3);
    assertEquals(arr, this.model.getFramesFor("R"));
    this.editController.selectShape("R");
    this.editController.removeKeyFrame(1);
    arr.remove(key1);
    assertEquals(arr, this.model.getFramesFor("R"));
    this.editController.removeKeyFrame(2);
    arr.remove(key2);
    assertEquals(arr, this.model.getFramesFor("R"));
    this.editController.removeKeyFrame(3);
    arr.remove(key3);
    assertEquals(arr, this.model.getFramesFor("R"));
    assertEquals(0, arr.size());
  }

  // Note: Invalid Edit Keyframe Will Results In A Pop Up Of JOptionPanel So It Can't Be Rested.
  @Test
  public void editKeyFrame() {
    List<IKeyFrame> arr = new ArrayList<>();
    assertEquals(arr, this.model.getFramesFor("R"));
    IKeyFrame key1 = new KeyFrame(1, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    IKeyFrame key2 = new KeyFrame(2, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    IKeyFrame key3 = new KeyFrame(3, new RectangleImpl(1, 1, 1,
            1, 1, 1, 1,0));
    this.model.addFrame("R", key1);
    this.model.addFrame("R", key2);
    this.model.addFrame("R", key3);
    IKeyFrame key33 = new KeyFrame(3, new RectangleImpl(3, 3, 3,
            3, 3, 3, 3,0));
    assertNotEquals(key33.getShape(), this.model.getShapeAt("R", 3));
    this.editController.selectShape("R");
    this.editController.editKeyFrame(3, 3, 3, 3, 3, 3, 3, 3,0);
    assertEquals(key33.getShape(), this.model.getShapeAt("R", 3));

    IKeyFrame key22 = new KeyFrame(2, new RectangleImpl(2, 2, 2,
            2, 2, 2, 2,0));
    assertNotEquals(key22.getShape(), this.model.getShapeAt("R", 2));
    this.editController.editKeyFrame(2, 2, 2, 2, 2, 2, 2, 2,0);
    assertEquals(key22.getShape(), this.model.getShapeAt("R", 2));
  }

  @Test
  public void mockRemoveShape() {
    // Testing whether the identity and the tpye is passed to the model correctly
    this.mockController.selectShape("Something");
    this.mockController.removeShape();
    assertEquals("getFramesFor calledSomethinggetShape calledgetShape called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.selectShape(" ");
    this.mockController.removeShape();
    assertEquals("getFramesFor called getShape calledgetShape called"
            , this.log.toString());
  }

  @Test
  public void mockAddShape() {
    // Testing whether the identity and the type is passed to the model correctly
    this.mockController.addShape("R", "rectangle",0);
    assertEquals("addShapes Called R rectangle 0getShape calledgetShape called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.addShape("C", "rectangle",0);
    assertEquals("addShapes Called C rectangle 0getShape calledgetShape called"
            , this.log.toString());
    log.delete(0, log.length());
    this.mockController.addShape("O", "ellipse",0);
    assertEquals("addShapes Called O ellipse 0getShape calledgetShape called"
            , this.log.toString());
  }


  @Test
  public void mockRemoveFrame() {
    // Testing whether the identity and the tick is passed to the model correctly.
    this.mockController.selectShape("R");
    this.mockController.removeKeyFrame(1);
    assertEquals("getFramesFor calledR 1 1 1 1 1 1 1 1 1getFramesFor called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.selectShape("R");
    this.mockController.removeKeyFrame(2);
    assertEquals("getFramesFor calledR 2 1 1 1 1 1 1 1 1getFramesFor called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.selectShape("R");
    this.mockController.removeKeyFrame(10);
    assertEquals("getFramesFor calledR 10 1 1 1 1 1 1 1 1getFramesFor called",
            this.log.toString());
  }

  @Test
  public void mockAddKeyFrame() {
    // Testing whether the identity and the tick is passed to the model correctly.
    this.mockController.selectShape("C");
    this.mockController.addKeyFrame(1, 1, 1, 1, 1, 1, 1, 1,0);
    assertEquals("getFramesFor calledC 1 1 1 1 1 1 1 1 0getFramesFor called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.selectShape("R");
    this.mockController.addKeyFrame(1, 1, 10, 1, 1, 30, 1, 4,0);
    assertEquals("getFramesFor calledR 1 1 10 1 1 30 1 4 0getFramesFor called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.selectShape("R");
    this.mockController.addKeyFrame(2, 10, 2, 251, 2, 2, 5, 2,0);
    assertEquals("getFramesFor calledR 2 10 2 251 2 2 5 2 0getFramesFor called",
            this.log.toString());
  }

  @Test
  public void mockEditKeyFrame() {
    // Testing whether the identity and attributes are passed to the model correctly.
    this.mockController.selectShape("C");
    this.mockController.editKeyFrame(1, 1, 1, 1, 1, 1, 1, 1,0);
    // Remove is called and Add is Called
    assertEquals("getFramesFor calledC 1 1 1 1 1 1 1 1 1C 1 1 " +
                    "1 1 1 1 1 1 0getFramesFor called"
            , this.log.toString());
    this.mockController.selectShape("C");
    log.delete(0, log.length());
    this.mockController.editKeyFrame(2, 4, 30, 1, 4, 1, 1, 1,0);
    // Remove is called and Add is Called
    assertEquals("C 2 1 1 1 1 1 1 1 1C 2 4 30 1 4 1 1 1 0getFramesFor called",
            this.log.toString());
    log.delete(0, log.length());
    this.mockController.editKeyFrame(2, 10, 1, 15, 1, 255, 1, 1,0);
    // Remove is called and Add is Called
    assertEquals("C 2 1 1 1 1 1 1 1 1C 2 10 1 15 1 255 1 1 0getFramesFor called",
            this.log.toString());
  }
}
