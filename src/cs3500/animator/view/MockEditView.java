package cs3500.animator.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;

/**
 * Represents a mocking of the EditView for testing purpose. This class is used to test the
 * communication of data between the Controller and the View.
 */
public class MockEditView extends JFrame implements IEditView {
  // Start Button
  private JButton startButton;
  // Restart Button
  private JButton restartButton;
  // Pause Button
  private JButton pauseButton;

  // Resume Button
  private JButton resumeButton;
  // Increase Speed Button
  private JButton increaseButton;
  // Decrease Speed Button
  private JButton decreaseButton;
  // EnableLoop Button
  private JButton enableLoopButton;
  // DisableLoop Button
  private JButton disableLoopButton;

  // Add KeyFrame Button
  private JButton addKeyFrame;
  // Remove KeyFrame Button
  private JButton removeKeyFrame;
  // Edit KeyFrame Button
  private JButton editKeyFrame;
  // Add Shape Button
  private JButton addShapeButton;
  // Remove Shape Button
  private JButton removeShapeButton;
  // To Append Output
  private StringBuilder log;
  // To Prevent double remove
  private boolean clicked = false;
  // Features
  private Features features;

  /**
   * Constructs a MockEditView instance for testing the communication between the view and the
   * controller.
   *
   * @param log            A Stringbuilder
   * @param start          mocks the start button
   * @param restart        mocks the restart button
   * @param pause          mocks the pause button
   * @param resume         mocks the resume button
   * @param increase       mocks the increase button
   * @param decrease       mocks the decrease button
   * @param enable         mocks the enable button
   * @param disable        mocks the disable button
   * @param addKeyFrame    mocks the add keyframe button
   * @param editKeyFrame   mocks the edit keyframe button
   * @param removeKeyFrame mocks the remove keyframe button
   * @param addShape       mocks the add shape button
   * @param removeShape    mocks the remove shape button
   */
  public MockEditView(StringBuilder log, JButton start, JButton restart,
                      JButton pause, JButton resume, JButton increase,
                      JButton decrease, JButton enable, JButton disable,
                      JButton addKeyFrame, JButton editKeyFrame, JButton removeKeyFrame,
                      JButton addShape, JButton removeShape) {
    this.log = log;
    this.startButton = start;
    this.restartButton = restart;
    this.pauseButton = pause;
    this.resumeButton = resume;
    this.increaseButton = increase;
    this.decreaseButton = decrease;
    this.enableLoopButton = enable;
    this.disableLoopButton = disable;
    this.addKeyFrame = addKeyFrame;
    this.removeKeyFrame = removeKeyFrame;
    this.editKeyFrame = editKeyFrame;
    this.addShapeButton = addShape;
    this.removeShapeButton = removeShape;
  }

  public MockEditView(StringBuilder log) {
    this.log = log;
  }

  /**
   * Appends the "Display called" to the Stringbuilder, to confirm that this method is called by the
   * Controller.
   */
  @Override
  public void display() {
    this.log.append("Display Called");
  }

  /**
   * This sets up the testing of communication between the View and the Controller by hard coding
   * the request that it sends to the Controller. Some of the cases can't be tested since there is
   * no way to check the output.
   *
   * @param listener that offers the action reacts.
   */
  @Override
  public void setFeatures(Features listener) {
    this.features = listener;
    this.startButton.addActionListener(evt -> this.log.append("Start"));
    this.restartButton.addActionListener(evt -> this.log.append("Restart"));
    this.pauseButton.addActionListener(evt -> this.log.append("Pause"));
    this.resumeButton.addActionListener(evt -> this.log.append("Resume"));
    this.decreaseButton.addActionListener(evt -> this.log.append("Decrease"));
    this.enableLoopButton.addActionListener(evt -> this.log.append("Enable"));
    this.disableLoopButton.addActionListener(evt -> this.log.append("Disable"));
    this.increaseButton.addActionListener(evt -> this.log.append("Increase"));
    this.addKeyFrame.addActionListener(evt -> listener.addKeyFrame(2, 2, 3, 4,
            5, 6, 7, 8, 10));
    this.removeKeyFrame.addActionListener(evt -> listener.removeKeyFrame(2));
    this.editKeyFrame.addActionListener(evt -> listener.editKeyFrame(2, 4, 5, 6,
            2, 20, 30, 255, 10));
    this.addShapeButton.addActionListener(evt -> listener.addShape("New", "Rec", 1));
    this.removeShapeButton.addActionListener(evt -> preventDoubleRemove());
  }

  /**
   * A helper to facilitate testing, to prevent double removing a shape.
   */
  private void preventDoubleRemove() {
    if (!this.clicked) {
      features.removeShape();
      this.clicked = !this.clicked;
    }
  }

  /**
   * Appends the bounds/dimension in to the Stringbuilder, to confirm the correct input from the
   * Controller.
   *
   * @param canvas an array that holds the offsets of the animation.
   */
  @Override
  public void setCanvas(int[] canvas) {
    this.log.append(canvas[0] + " " + canvas[1] + " " + canvas[2] + " " + canvas[3]);
  }

  /**
   * Appends the list of frames in to the Stringbuilder, to confirm the correct input from the
   * Controller.
   *
   * @param frames a list of keyframes
   */
  @Override
  public void displayKeyframes(List<IKeyFrame> frames) {
    for (IKeyFrame key : frames) {
      this.log.append(key.toString() + "\n");
    }
  }

  /**
   * Appends the list of identities into the Stringbuilder, to confirm the correct input from the
   * Controller.
   *
   * @param identities a list of identities
   */
  @Override
  public void displayIdentity(List<String> identities) {
    for (String identity : identities) {
      this.log.append(identity + "\n");
    }
  }

  /**
   * Can't be tested since this method is not called by public method in the controller.
   *
   * @param shapes list of shapes to be displayed.
   */
  @Override
  public void displayShapes(List<IShape> shapes) {
    for (IShape shape : shapes) {
      this.log.append(shape.toString() + "\n");
    }
  }

  /**
   * Can't be tested since this method is not directly called by a public method in the controller.
   */
  @Override
  public void displayTick(int tick) {
    return;
  }

  /**
   * Can't be tested since this method is not directly called by a public method in the controller.
   */
  @Override
  public void displayDelay(int delay) {
    return;
  }

  /**
   * Can't be tested since this method is not directly called by a public method in the controller.
   */
  @Override
  public void displayLoop(boolean loop) {
    return;
  }

  /**
   * Gui can't be tested.
   *
   * @param message an error message.
   */
  @Override
  public void errorBox(String message) {
    return;
  }

  /**
   * Appends the {@code t} into the Stringbuilder, to confirm the correct input from the
   * Controller.
   *
   * @param t last tick of the animation
   */
  @Override
  public void lastTick(int t) {
    this.log.append(t);
  }

  /**
   * Appends the {@code layers} into the Stringbuilder, to confirm the correct input from the
   * Controller.
   *
   * @param layers of the animation objects.
   */
  @Override
  public void setLayers(List<Integer> layers) {
    this.log.append("setLayers called" + layers);
  }
}
