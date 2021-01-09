package cs3500.animator;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import cs3500.animator.controller.EditController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.EditView;
import cs3500.animator.view.IEditView;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewModel;
import cs3500.animator.view.SvgView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.ViewModel;
import cs3500.animator.view.VisualView;

/**
 * Runs a AnimatorModel visualization on the console, or on a JFrames. And it can write the
 * visualization into a file. Supporting svg, textual, plain visual visualization, or an
 * interactive(editable) visual visualization. The client must specify which input file to read into
 * and the type of visualization. The client can specify the file to write into and the speed of the
 * frames.
 * <p>Example: -in "name-of-animation-file" -view "type-of-view" -out "where-output-show-go"
 * -speed "integer-ticks-per-second"</p>
 */
public final class Excellence {
  /**
   * Parse through the argument commands and renders the desired visualization. An error box will
   * pop up if the given argument commands are not in pair, no input file, can't find the input
   * file, no type of the view, the type of the view isn't supported.
   *
   * @param args argument commands
   */
  public static void main(String[] args) {
    String inputs = String.join(" ", args);
    Scanner scan = new Scanner(inputs);

    // default value
    BufferedWriter writer = null;
    String inputFile = null;
    String type = null;
    String outputFile = null;
    Integer rate = null;

    while (scan.hasNext()) {
      String str = scan.next();
      try {
        switch (str) {
          case "-in":
            inputFile = scan.next();
            break;
          case "-view":
            type = scan.next();
            break;
          case "-out":
            outputFile = scan.next();
            break;
          case "-speed":
            rate = scan.nextInt();
            break;
          default:
            break;
        }
      } catch (NoSuchElementException ime) {
        errorBox("Each pair of arguments must be ordered");
      }
    }

    if (type == null) {
      errorBox("Must Provide A View!");
    }
    if (inputFile == null) {
      errorBox("Must Provide A InputFile!");
    }

    FactoryView factory = new FactoryView();
    try {
      AnimatorModel model = AnimationReader.parseFile(new FileReader(inputFile),
              new SimpleModelImpl.Builder());
      if (!type.equals("edit")) {
        if (outputFile != null && (type.equals("svg") || type.equals("text"))) {
          writer = new BufferedWriter(new FileWriter(outputFile));
        }
        factory.setRate(rate).setModel(model).setOutput(writer).create(type).display();
        if (writer != null) {
          writer.close();
        }
      } else {
        new EditController((IEditView) factory.setModel(model).create(type), model).play();
      }
    } catch (IOException ioe) {
      errorBox(ioe.getMessage());
    }
  }

  /**
   * Represents the factory of views. That allows the client to set the rate, model, and output
   * path. And the client can create the desired view with those attributes.
   */
  private static class FactoryView {
    Appendable outPut;
    int rate;
    IViewModel model;

    /**
     * Constructs a FactoryView that stores the default attributes a of view.
     */
    private FactoryView() {
      this.outPut = System.out;
      this.rate = 1;
      IViewModel model = null;
    }

    /**
     * Returns a IView of the given {@code type}. An error box will pop up if there isn't a view
     * model or the type of the view isn't supported.
     *
     * @param type of the view.
     * @return IView
     */
    private IView create(String type) {
      if (model == null) {
        errorBox("Missing A Model!!!");
      }
      switch (type) {
        case "text":
          return new TextualView(this.model, outPut);
        case "visual":
          return new VisualView(this.model, this.rate);
        case "svg":
          return new SvgView(this.model, outPut, this.rate);
        case "edit":
          return new EditView();
        default:
          errorBox("The given type of view isn't supported!!!");
      }
      return null;
    }

    /**
     * Allows the client to set the rate of this factory instance.
     *
     * @param rate the desired frames per second
     * @return this factory instance
     */
    private FactoryView setRate(Integer rate) {
      if (rate != null) {
        this.rate = rate;
      }
      return this;
    }

    /**
     * Allows the client to read the {@code input} file and construct the desired view model and
     * store that model in this instance of factory. An error box will pop up if the input file
     * can't be found.
     *
     * @param input the name of the input file
     * @return this factory instance
     */
    private FactoryView setModel(AnimatorModel input) {
      this.model = new ViewModel(input);
      return this;
    }

    /**
     * Allows the client to set the {@code Output} appendable store that appendable object in this
     * instance of factory.
     *
     * @param output the appendable object.
     * @return this factory instance
     */
    private FactoryView setOutput(Appendable output) {
      if (output != null) {
        this.outPut = output;
      }
      return this;
    }
  }

  /**
   * Renders an error box with the given {@code message}. The program will exit once the error box
   * is closed.
   *
   * @param message error message to be output.
   */
  private static void errorBox(String message) {
    JFrame frame = new JFrame();
    JOptionPane optionPane = new JOptionPane();
    JOptionPane.showMessageDialog(frame, message);
    System.exit(-1);
  }
}
