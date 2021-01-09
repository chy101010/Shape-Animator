package cs3500.animator.view;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;

/**
 * This class represents the SVG visualization implementation of the view. It allows the clients to
 * write an SVG visualization into an svg file, other files, or to the console directed by the
 * Appendable in its constructor.
 */
public class SvgView implements IView {
  private Appendable writer = System.out;
  private int rate;
  private IViewModel model;

  /**
   * Constructs a SvgView with the given view only {@code model}, Appendable {@code writer}, and the
   * {@code rate}.
   *
   * @param model  a view only model
   * @param writer an Appendable to display the output
   * @param rate   frames per second
   * @throws IllegalArgumentException if the {@code model} or {@code writer} is null, or if the
   *                                  {@code rate} is less than or equal to 0.
   */
  public SvgView(IViewModel model, Appendable writer, int rate) {
    if (rate <= 0) {
      throw new IllegalArgumentException("The given rate must be positive!");
    }
    if (writer == null) {
      throw new IllegalArgumentException("Must pass in non null Appendable");
    }
    if (model == null) {
      throw new IllegalArgumentException("Must pass in non null Model");
    }
    this.writer = writer;
    this.rate = rate;
    this.model = model;
  }

  /**
   * Outputs the SVG visualization of the view only {@code model} in this SvgView using its {@code
   * rate} into the its appendable object {@code writer}.
   *
   * @throws IllegalArgumentException if the appendable append failed.
   */
  @Override
  public void display() {
    try {
      StringBuilder str = new StringBuilder();
      // Sets up the dimension of the window <svg
      this.svgHeader(str);
      // Looping through every Identity
      for (String identity : this.model.getShapes()) {
        // Obtains the list of Keyframes pointed by this identity.
        List<IKeyFrame> frames = this.model.getFramesFor(identity);
        // Obtains type of the shape pointed by this identity.
        String type = this.model.getTypes(identity);
        // Only invokes formatter if there is at least one frame.
        if (frames.size() > 0) {
          this.formatter(str, identity, type, frames);
        }
      }
      // </svg>
      this.svgEnd(str);
      // Append the svg into the appendable
      this.writer.append(str.toString());
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Append failed", ioe);
    }
  }

  /**
   * Appends the SVG header into the given {@code str}. It sets up the SVG header and provides a
   * view box of the dimensions width and height and off set its x and y bounds accordingly, ex:
   * <"svg ...../">.
   *
   * @param str a stringBuilder to accumulate the SVG header.
   */
  private void svgHeader(StringBuilder str) {
    str.append(String.format("<svg width=\"%d\" height=\"%d\" "
                    + "version=\"1.1\" \n \txmlns=\"http://www.w3.org/2000/svg\" >\n"
                    + "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"none\""
                    + " stroke=\"black\" stroke-width=\"1\" /> \n\n",
            this.model.getViews()[2], this.model.getViews()[3],
            this.model.getViews()[0] - this.model.getViews()[0],
            this.model.getViews()[1] - this.model.getViews()[1],
            this.model.getViews()[2], this.model.getViews()[3]));
  }


  /**
   * Appends the SVG end into the given {@code str}, ex: <"/svg">.
   *
   * @param str a stringBuilder to accumulate the SVG end.
   */
  private void svgEnd(StringBuilder str) {
    str.append("</svg>");
  }


  /**
   * Appends the SVG animation commands and disappear command, using the {@code frames}, of an
   * animation object pointed by the {@code identity} into the given {@code str}. The attribute
   * specifiers("x" or "cx", "width" or "rx") are determined by the {@code type}
   *
   * @param str      a stringBuilder to accumulate the SVG shape instantiation, shape animations and
   *                 shape close commands
   * @param identity of the animation object
   * @param frames   the list of KeyFrames of the animation object
   * @param type     the type of shape of the animation object
   * @throws IllegalArgumentException if the type of shape is undocumented in the attributesName
   *                                  method.
   */
  private void formatter(StringBuilder str, String identity, String type, List<IKeyFrame> frames) {
    // Get list of attributes's name for this given type of shape x,y,width,height,rect for
    // rectangle, and cx,cy,rx,ry,ellipse for ellipse
    List<String> attributes = this.attributesName(type);
    // Only proceed if this given type of shape's attributes is documented
    if (attributes != null) {
      // <rect or <ellipse using the shape in the first keyframe
      this.starter(str, identity, frames.get(0), attributes, type);
      // <animate using all the shapes in the list of keyframes
      this.animate(str, frames, attributes, type);
      // disappear the animation object
      this.disappear(str, frames.get(frames.size() - 1));
      // </rect> or </ellipse>
      this.closer(str, type);
    } else {
      throw new IllegalArgumentException("Please document the shape's attributes' name in svg!");
    }
  }

  /**
   * Appends the SVG command for the instantiation of a shape into the given {@code str}. The
   * instantiation is created using the first keyframe {@code frame}.
   *
   * @param str        a stringBuilder to accumulate the SVG shape instantiation.
   * @param identity   of the animation object.
   * @param frame      the first KeyFrame of the animation object.
   * @param attributes the list of attributes specifiers for the type of this animation object.
   * @param type       type of shape of the animation object.
   * @throws IllegalArgumentException if the given {@code type} isn't documented in this SvgView
   */
  private void starter(StringBuilder str, String identity, IKeyFrame frame, List<String> attributes,
                       String type) {
    IShape shape = frame.getShape();
    int width = this.adjustDimension(type, shape.getWidth());
    int height = this.adjustDimension(type, shape.getHeight());
    int boundX = this.adjustOffset(type, shape.getX(), shape.getWidth())
            - this.model.getViews()[0];
    int boundY = this.adjustOffset(type, shape.getY(), shape.getHeight())
            - this.model.getViews()[1];
    str.append(String.format("<%s id=\"%s\" %s=\"%d\" %s=\"%d\" %s=\"%d\" %s=\"%d\""
                    + " fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" > \n"
                    + " \t <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
                    + " begin=\"%dms\" fill=\"freeze\" /> \n",
            attributes.get(4), identity, attributes.get(0), boundX, attributes.get(1),
            boundY, attributes.get(2), width, attributes.get(3),
            height, shape.getR(), shape.getG(), shape.getB(), frame.getTime() * 1000 / this.rate));
  }


  /**
   * Appends the SVG commands for the animations in the given list of {@code frames}. An animation
   * command <"animate" will only be written if the starting attribute is different from the end
   * attribute. It uses the list of {@code attributes} to specify the attributes name(x or cx,
   * height or ry) for different type of shape.
   *
   * @param str        a stringBuilder to accumulate the SVG animations.
   * @param frames     list of keyframes
   * @param attributes list of attributes specifiers
   * @param type       type of the shape
   * @throws IllegalArgumentException if the given {@code type} isn't documented in this SvgView
   */
  private void animate(StringBuilder str, List<IKeyFrame> frames, List<String> attributes,
                       String type) {
    for (int i = 0; i < frames.size() - 1; i++) {
      IKeyFrame first = frames.get(i);
      IKeyFrame second = frames.get(i + 1);
      IShape firstShape = first.getShape();
      IShape secShape = second.getShape();
      int ms = first.getTime() * 1000 / this.rate;
      int dur = (second.getTime() - first.getTime()) * 1000 / this.rate;
      // Checking the X coordinate
      if (firstShape.getX() != secShape.getX()) {
        this.eachAnimated(str, attributes.get(0),
                ms, dur,
                this.adjustOffset(type, firstShape.getX(),
                        firstShape.getWidth()) - this.model.getViews()[0],
                this.adjustOffset(type, secShape.getX(),
                        secShape.getWidth()) - this.model.getViews()[0]);
      }
      // Checking the Y coordinate
      if (firstShape.getY() != secShape.getY()) {
        this.eachAnimated(str, attributes.get(1),
                ms, dur,
                this.adjustOffset(type, firstShape.getY(),
                        firstShape.getHeight()) - this.model.getViews()[1],
                this.adjustOffset(type, secShape.getY(),
                        secShape.getHeight()) - this.model.getViews()[1]);
      }
      // Checking the Width dimension
      if (firstShape.getWidth() != secShape.getWidth()) {
        this.eachAnimated(str, attributes.get(2), ms, dur,
                this.adjustDimension(type, firstShape.getWidth()),
                this.adjustDimension(type, secShape.getWidth()));
      }
      // Checking the Height dimension
      if (firstShape.getHeight() != secShape.getHeight()) {
        this.eachAnimated(str, attributes.get(3), ms, dur,
                this.adjustDimension(type, firstShape.getHeight()),
                this.adjustDimension(type, secShape.getHeight()));
      }
      // Checking the RGB color index
      if (firstShape.getR() != secShape.getR() || firstShape.getG() != secShape.getG()
              || firstShape.getB() != secShape.getB()) {
        this.eachAnimatedColor(str, ms, dur, firstShape.getR(), firstShape.getG(),
                firstShape.getB(), secShape.getR(), secShape.getG(), secShape.getB());
      }
      if (firstShape.getDegree() != secShape.getDegree()) {
        this.eachAnimatedRotate(str, firstShape, secShape, ms, dur);
      }
    }
  }

  /**
   * Append the SVG commands for an animation object rotation.
   *
   * @param str    a stringBuilder to accumulate the SVG disappear command for an animation object.
   * @param first  the initial shape
   * @param second the final shape
   * @param ms     the starting time
   * @param dur    the duration
   */
  private void eachAnimatedRotate(StringBuilder str, IShape first, IShape second,
                                  int ms, int dur) {
    str.append(String.format("\t<animateTransform attributeName=\"transform\" "
                    + "attributeType=\"XML\" type=\"rotate\" from=\"%d %d %d\" to=\"%d %d %d\" " +
                    "begin=\"%dms\" dur=\"%dms\" />\n",
            first.getDegree(),
            first.getX() + first.getWidth() / 2 - this.model.getViews()[0],
            first.getY() + first.getHeight() / 2 - this.model.getViews()[1],
            second.getDegree(),
            second.getX() + second.getWidth() / 2 - this.model.getViews()[0],
            second.getY() + second.getHeight() / 2 - this.model.getViews()[1],
            ms,
            dur));
  }

  /**
   * Appends the SVG commands for an animation object disappearance.
   *
   * @param str   a stringBuilder to accumulate the SVG disappear command for an animation object.
   * @param frame the last KeyFrame of a animation object.
   */
  private void disappear(StringBuilder str, IKeyFrame frame) {
    str.append(String.format("\t <set attributeName=\"visibility\" attributeType=\"CSS\" "
            + "to=\"hidden\""
            + " begin=\"%dms\" fill=\"freeze\" /> \n", frame.getTime() * 1000 / this.rate));
  }

  /**
   * Appends the SVG end for an animation object; ex: <"/rect"> or <"/ellipse">.
   *
   * @param str  a stringBuilder to accumulate the SVG end command.
   * @param type the type of the animation object.
   */
  private void closer(StringBuilder str, String type) {
    if (type.equalsIgnoreCase("rectangle")) {
      str.append("</rect>\n");
      return;
    } else if (type.equalsIgnoreCase("ellipse")) {
      str.append("</ellipse>\n");
      return;
    }
  }

  /**
   * Appends an animation commands(Not for fill="x,x,x") into the given {@code str} with the given
   * {@code attributeName} and starting value {@code from} and ending value {@code to} from the
   * given start time {@code begin} to the duration {@code dur}.
   *
   * @param str           a stringBuilder to accumulate the SVG animation command.
   * @param attributeName the attribute that is changing
   * @param begin         the starting time
   * @param dur           the duration
   * @param from          the starting value
   * @param to            the ending value
   */
  private void eachAnimated(StringBuilder str, String attributeName, int begin, int dur,
                            int from, int to) {
    str.append(String.format("\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\""
                    + " attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" /> \n",
            begin, dur, attributeName, from, to));
  }

  /**
   * Appends an animation commands (for fill="x,x,x") into the given {@code str} with the given
   * {@code attributeName} and starting value {@code from} and ending value {@code to} from the
   * given start time {@code begin} to the duration {@code dur}.
   *
   * @param str   a stringBuilder to accumulate the SVG animation command.
   * @param begin the starting time
   * @param dur   the duration
   * @param r1    the starting R index
   * @param g1    the starting G index
   * @param b1    the starting B index
   * @param r2    the ending R index
   * @param g2    the ending G index
   * @param b2    the ending B index
   */
  private void eachAnimatedColor(StringBuilder str, int begin, int dur, int r1, int g1, int b1,
                                 int r2, int g2, int b2) {
    str.append(String.format("\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                    + "attributeName=\"%s\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" fill="
                    + "\"freeze\" /> \n",
            begin, dur, "fill", r1, g1, b1, r2, g2, b2));
  }


  /**
   * Returns the list of attribute specifiers of the given {@code type}.
   *
   * @param type the type of the shape
   * @return a list of attributes specifiers if the type if documented.
   * @throws IllegalArgumentException if the given type isn't documented
   */
  private List<String> attributesName(String type) {
    if (type.equalsIgnoreCase("rectangle")) {
      return new ArrayList<>(Arrays.asList("x", "y", "width", "height", "rect"));
    } else if (type.equalsIgnoreCase("ellipse")) {
      return new ArrayList<>(Arrays.asList("cx", "cy", "rx", "ry", "ellipse"));
    }
    throw new IllegalArgumentException("The given type of shape isn't supported!");
  }

  /**
   * Adjusts the {@code dim} accordingly to the given {@code type}.
   *
   * @param type the type of the shape
   * @param dim  the dimension to be adjusted
   * @return the adjusted dimension
   * @throws IllegalArgumentException if the given type isn't documented
   */
  private int adjustDimension(String type, int dim) {
    if (type.equalsIgnoreCase("rectangle")) {
      return dim;
    } else if (type.equalsIgnoreCase("ellipse")) {
      return dim / 2;
    }
    throw new IllegalArgumentException("The given type of shape isn't supported!");
  }

  /**
   * Adjusts the {@code bound} accordingly to the given {@code type} and {@code dim}.
   *
   * @param type  the type of the shape
   * @param bound the x/y bound
   * @param dim   the dimension/radius
   * @return the adjusted bound
   * @throws IllegalArgumentException if the given type isn't documented
   */
  private int adjustOffset(String type, int bound, int dim) {
    if (type.equalsIgnoreCase("rectangle")) {
      return bound;
    } else if (type.equalsIgnoreCase("ellipse")) {
      return bound + dim / 2;
    }
    throw new IllegalArgumentException("The given type of shape isn't supported!");
  }
}
