import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.EditController;
import cs3500.animator.controller.Features;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.view.IEditView;
import cs3500.animator.view.MockEditView;

/**
 * Test cases for the communication between View and the Controller. Verifying the communication
 * between them is as intended.
 */
public class MockViewTest {
  private IEditView mock;
  private Features controller;
  private AnimatorModel model;
  StringBuilder log;
  private JButton startButton = new JButton();
  private JButton restartButton = new JButton();
  private JButton pauseButton = new JButton();
  private JButton resumeButton = new JButton();
  private JButton increaseButton = new JButton();
  private JButton decreaseButton = new JButton();
  private JButton enableLoopButton = new JButton();
  private JButton disableLoopButton = new JButton();
  private JButton addKeyFrame = new JButton();
  private JButton removeKeyFrame = new JButton();
  private JButton editKeyFrame = new JButton();
  private JButton addShapeButton = new JButton();
  private JButton removeShapeButton = new JButton();

  @Before
  public void testFixture() {
    this.log = new StringBuilder();
    this.mock = new MockEditView(this.log, this.startButton, this.restartButton, this.pauseButton,
            this.resumeButton, this.increaseButton, this.decreaseButton, this.enableLoopButton,
            this.disableLoopButton, this.addKeyFrame, this.editKeyFrame, this.removeKeyFrame,
            this.addShapeButton, this.removeShapeButton);
    this.model = new SimpleModelImpl();
    this.model.addShapes("R", "rectangle",0);
    this.model.addShapes("C", "ellipse",0);
    this.controller = new EditController(this.mock, this.model);
    // This is a test for setFeatures
    this.mock.setFeatures(this.controller);
  }

  // This is a test for setFeatures
  // * The Start feature method can not be tested since all it does is
  //   to start the private in the controller.
  // * The Restart feature method can not be tested since all it does is
  //   to mutate a private boolean field in the controller and mutate a private
  //   int field.
  // * The Pause feature method can not be tested since all its does is to
  //   mutate a private boolean field in the controller.
  // * The Resume feature method can not be tested since all its does is to
  //   mutate a private boolean field in the controller.
  // * The Increase/decrease feature method can not be tested since all it does is to
  //   mutate a private Timer and an int field.
  // * The Enable/Disable loop feature method can not be tested since all it does is
  //   to mutate a private boolean field.
  @Test
  public void setFeatures() {
    this.mock.setFeatures((Features) this.controller);
    log.delete(0, log.length());
    this.startButton.doClick();
    assertEquals("StartStart", this.log.toString());
    log.delete(0, log.length());
    this.restartButton.doClick();
    assertEquals("RestartRestart", this.log.toString());
    log.delete(0, log.length());
    this.pauseButton.doClick();
    assertEquals("PausePause", this.log.toString());
    log.delete(0, log.length());
    this.resumeButton.doClick();
    assertEquals("ResumeResume", this.log.toString());
    log.delete(0, log.length());
    this.decreaseButton.doClick();
    assertEquals("DecreaseDecrease", this.log.toString());
    log.delete(0, log.length());
    this.increaseButton.doClick();
    assertEquals("IncreaseIncrease", this.log.toString());
    log.delete(0, log.length());
    this.enableLoopButton.doClick();
    assertEquals("EnableEnable", this.log.toString());
    log.delete(0, log.length());
    this.disableLoopButton.doClick();
    assertEquals("DisableDisable", this.log.toString());
    // Add A Hard Coded KeyFrame
    List<IKeyFrame> arr = new ArrayList<>();
    assertEquals(arr, this.model.getFramesFor("R"));
    this.controller.selectShape("R");
    this.addKeyFrame.doClick();
    IKeyFrame key = new KeyFrame(2, new RectangleImpl(2, 3,
            4, 5, 6, 7, 8,0));
    arr.add(key);
    assertEquals(arr, this.model.getFramesFor("R"));
    // Edit A Hard Code KeyFrame
    this.editKeyFrame.doClick();
    IKeyFrame key1 = new KeyFrame(2,
            new RectangleImpl(4, 5, 6, 2, 20, 30, 255,0));
    arr.remove(key);
    arr.add(key1);
    assertEquals(arr, this.model.getFramesFor("R"));
    // Remove A Hard Coded KeyFrame
    this.removeKeyFrame.doClick();
    arr.remove(key1);
    assertEquals(arr, this.model.getFramesFor("R"));
    // Adding A Hard Coded Shape
    List<String> names = new ArrayList<>(Arrays.asList("R", "C"));
    assertEquals(names, this.model.getShapes());
    this.addShapeButton.doClick();
    names.add("New");
    assertEquals(names, this.model.getShapes());
    assertEquals("Rec", this.model.getTypes("New"));
    // Removing a Shape
    assertEquals(new ArrayList<>(Arrays.asList("R", "C", "New")), this.model.getShapes());
    this.removeShapeButton.doClick();
    assertEquals(new ArrayList<>(Arrays.asList("C", "New")), this.model.getShapes());
  }

  // Will show the canvas bounds, identities, and the display method should be called.
  @Test
  public void testDisplayIdentitySetCanvasDisplay() {
    this.model.setBounds(1, 2,3,4);
    ((EditController)(this.controller)).play();
    assertEquals("1 2 3 4R\n" + "C\n" + "Display Called", this.log.toString());
  }

  @Test
  public void testDisplayKeyFrame() {
    assertEquals("", this.log.toString());
    IKeyFrame key = new KeyFrame(2, new RectangleImpl(3, 3,
            4, 5, 6, 7, 8,0));
    IKeyFrame key1 = new KeyFrame(4,
            new RectangleImpl(4, 5, 6, 2, 20, 30, 255,0));
    this.model.addFrame("R", key);
    this.model.addFrame("R", key1);
    this.controller.selectShape("R");
    assertEquals("2 3 3 4 5 6 7 8\n"
            + "4 4 5 6 2 20 30 255\n", this.log.toString());
  }
}
