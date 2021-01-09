package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.model.IKeyFrame;

/**
 * This class represents the textual visualization implementation of the view. It allows the clients
 * to render the textual visualization of the view only model into the console.
 */
public class TextualView implements IView {
  private IViewModel model;
  private Appendable append = System.out;

  /**
   * Constructors a TextualView with the given view only mode {@code model} and {@code append}. The
   * This allows the to take in an appendable solely for testing purpose, and the default appendable
   * is System.out
   *
   * @param model  the view only model
   * @param append the appendable
   */
  public TextualView(IViewModel model, Appendable append) {
    if (append == null || model == null) {
      throw new IllegalArgumentException("ViewModel and Appendable can't be null");
    }
    this.model = model;
    this.append = append;
  }


  /**
   * Renders the textual visualization of the view only model into the Appendable.
   *
   * @throws IllegalArgumentException if the appendable appending failed.
   */
  @Override
  public void display() {
    try {
      // Create the string representation of the shapes and their keyframes
      String outPut = this.getDescription();
      // Create the canvas bounds
      String canvas = String.format("canvas %d %d %d %d \n", this.model.getViews()[0],
              this.model.getViews()[1], this.model.getViews()[2],
              this.model.getViews()[3]);
      StringBuilder builder = new StringBuilder(canvas);
      builder.append(outPut);
      this.append.append(builder);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }


  /**
   * Returns the string representation of the all animation objects in this View-Only Animator
   * {@code model} with their respective KeyFrames, identity, and shape's type("Rectangle" or
   * "Ellipse"). The keyframes will be displayed in every two adjacent pairs.
   *
   * @return a string representation.
   */
  private String getDescription() {
    List<String> list = this.model.getShapes();
    String[] each = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      each[i] = this.motionDisplay(this.model.getFramesFor(list.get(i)), list.get(i));
    }
    return String.join("\n\n", each);
  }

  /**
   * Returns a String that represents the set of motions of this animation object pointed by the
   * given {@code identity} by converting/chaining adjacent {@code frames} into Strings.
   *
   * @param frames   list of keyFrames.
   * @param identity the identity of the shape
   * @return a representation of the given {@code identity}: identity, type of the shape,motions.
   */
  private String motionDisplay(List<IKeyFrame> frames, String identity) {
    String[] motions = new String[frames.size() - 1];
    String header = "Shape" + " " + identity + " " + frames.get(0).getShape().typeName() + " "
            + "Layer" + this.model.getLayer(identity);
    if (motions.length == 0) {
      return header;
    } else {
      for (int i = 0; i < frames.size() - 1; i++) {
        String motion = "motion" + " " + identity + " "
                + frames.get(i).toString() + "  " + frames.get(i + 1).toString();
        motions[i] = motion;
      }
      return header + "\n" + String.join("\n", motions);
    }
  }
}
