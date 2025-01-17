package cs3500.animator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A helper to read animation data and construct an animation from it.
 */
public class AnimationReader {
  /**
   * A factory for producing new animations, given a source of shapes and a builder for constructing
   * animations.
   *
   * <p>
   * The input file format consists of two types of lines:
   * <ul>
   * <li>Shape lines: the keyword "shape" followed by two identifiers (i.e.
   * alphabetic strings with no spaces), giving the unique name of the shape,
   * and the type of shape it is.</li>
   * <li>Motion lines: the keyword "motion" followed by an identifier giving the name
   * of the shape to move, and 16 integers giving the initial and final conditions of the motion:
   * eight numbers giving the time, the x and y coordinates, the width and height,
   * and the red, green and blue color values at the start of the motion; followed by
   * eight numbers for the end of the motion.  See {@link AnimationBuilder#addMotion}</li>
   * </ul>
   * </p>
   *
   * @param readable The source of data for the animation
   * @param builder  A builder for helping to construct a new animation
   * @param <Doc>    The main model interface type describing animations
   * @return
   */
  public static <Doc> Doc parseFile(Readable readable, AnimationBuilder<Doc> builder) {
    Objects.requireNonNull(readable, "Must have non-null readable source");
    Objects.requireNonNull(builder, "Must provide a non-null AnimationBuilder");
    Scanner s = new Scanner(readable);
    // Split at whitespace, and ignore # comment lines
    s.useDelimiter(Pattern.compile("(\\p{Space}+|#.*)+"));
    while (s.hasNext()) {
      String word = s.next();
      switch (word) {
        case "canvas":
          readCanvas(s, builder);
          System.out.print(s.nextLine());
          break;
        case "shape":
          readShape(s, builder);
          break;
        case "motion":
          readMotion(s, builder);
          break;
        default:
          throw new IllegalStateException("Unexpected keyword: " + word + s.nextLine());
      }
    }
    return builder.build();
  }

  private static <Doc> void readCanvas(Scanner s, AnimationBuilder<Doc> builder) {
    int[] vals = new int[4];
    String[] fieldNames = {"left", "top", "width", "height"};
    for (int i = 0; i < 4; i++) {
      vals[i] = getInt(s, "Canvas", fieldNames[i]);
    }
    builder.setBounds(vals[0], vals[1], vals[2], vals[3]);
  }

  private static <Doc> void readShape(Scanner s, AnimationBuilder<Doc> builder) {
    String name;
    String type;
    int layer = 0;
    if (s.hasNext()) {
      name = s.next();
    } else {
      throw new IllegalStateException("Shape: Expected a name, but no more input available");
    }
    if (s.hasNext()) {
      type = s.next();
    } else {
      throw new IllegalStateException("Shape: Expected a type, but no more input available");
    }
    if (s.hasNextInt()) {
      layer = s.nextInt();
    }
    builder.declareShape(name, type, layer);
  }

  private static <Doc> void readMotion(Scanner s, AnimationBuilder<Doc> builder) {
    String[] fieldNames = new String[]{
      "initial time", "initial x-coordinate", "initial y-coordinate" ,
      "initial width", "initial height",
      "initial red value", "initial green value", "initial blue value", "initial degree",
      "final time",
      "final x-coordinate", "final y-coordinate",
      "final width", "final height",
      "final red value", "final green value", "final blue value", "final degree"
    };
    String name;
    List<Integer> arr = new ArrayList<>();

    if (s.hasNext()) {
      name = s.next();
    } else {
      throw new IllegalStateException("Motion: Expected a shape name, but no more input available");
    }
    for (int i = 0; i < 18; i++) {
      arr.add(getInt(s, "Motion", fieldNames[i]));
      if (i == 15 && !s.hasNextInt()) {
        break;
      }
    }
    if (arr.size() == 16) {
      builder.addMotion(name,
              arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), arr.get(5), arr.get(6),
              arr.get(7), 0, arr.get(8), arr.get(9), arr.get(10), arr.get(11), arr.get(12),
              arr.get(13), arr.get(14), arr.get(15), 0);
    } else if (arr.size() == 18) {
      builder.addMotion(name,
              arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), arr.get(5), arr.get(6),
              arr.get(7), arr.get(8), arr.get(9), arr.get(10), arr.get(11), arr.get(12),
              arr.get(13), arr.get(14), arr.get(15), arr.get(16), arr.get(17));
    } else {
      throw new IllegalArgumentException("Either 16 integers of 18 integers!");
    }

  }


  private static int getInt(Scanner s, String label, String fieldName) {
    if (s.hasNextInt()) {
      return s.nextInt();
    } else if (s.hasNext()) {
      throw new IllegalStateException(
              String.format("%s: expected integer for %s, got: %s", label, fieldName, s.next()));
    } else {
      throw new IllegalStateException(
              String.format("%s: expected integer for %s, but no more input available",
                      label, fieldName));
    }
  }

}
