package cs3500.animator.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.util.AnimationBuilder;


/**
 * This class allows the user to add shapes along with their keyframe(an animation object) into this
 * animation. It allows the user to remove animation objects from the animation.It allows the user
 * to know when the animation is over after a given tick. An animation is over if there is no more
 * keyframes at a given tick or after. It allows the user to query the list of sorted keyframes of
 * an animation object pointed by an unique {@code identity}. Each identity is mapped onto a list of
 * sorted keyframes, and the type of shape of the animation object. It allows the user to query the
 * instantaneous shape, the list of Keyframes, and the type of shape of the animation object pointed
 * by the identity at a given tick
 * <p> Invariant: The list of KeyFrames is sorted.</p>
 * <p> Invariant: The shapes in a list of KeyFrames are the same type.</p>
 * <p> Invariant: If an identity is in the {@code stamps} then it must be in {@code types} and
 * {@code identities} and vice versa</p>
 * <p> Invariant: There is no duplicated identity.</p>
 * <p> Invariant: The time tick in a keyframe must be at least 0.</p>
 * <p> Invariant: The color indexes of a shape must be between 0 to 255.</p>
 */
public class SimpleModelImpl implements AnimatorModel {
  // Identity -> List of Keyframes
  private Map<String, List<IKeyFrame>> stamps;
  // Identity -> type
  private Map<String, String> types;
  // The identities are stored in order, the earliest added identity at the front.
  private List<String> identities;
  private int boundX;
  private int boundY;
  private int width;
  private int height;

  private Map<String, Integer> layer;

  /**
   * Constructs an animation with the given {@code stamps}, {@code types}, {@code identities},
   * {@code boundX}, {@code boundY}, {@code width}, and {@code height}.
   *
   * @param stamps     a Map that hash identities onto Keyframes.
   * @param types      a Map that hash identities onto type of shape.
   * @param identities a list of identities that are in the order in which they are added.
   * @param boundX     the leftmost x value of the animation
   * @param boundY     the topmost y value of the animation
   * @param width      the width of the bounding box of the animation
   * @param height     the height of the bounding box of the animation
   * @throws IllegalArgumentException if the given {@code stamps} {@code types} or {@code
   *                                  identities} is null.
   */
  public SimpleModelImpl(Map<String, List<IKeyFrame>> stamps, Map<String, String> types,
                         Map<String, Integer> layer, List<String> identities, int boundX,
                         int boundY, int width, int height) {
    if (stamps == null || types == null || identities == null) {
      throw new IllegalArgumentException("Must pass in a list, and two maps for the identities!");
    }
    this.stamps = stamps;
    this.types = types;
    this.layer = layer;
    this.identities = identities;
    this.boundX = boundX;
    this.boundY = boundY;
    this.width = width;
    this.height = height;
  }


  /**
   * Convenient Constructor to constructs an animation model.
   */
  public SimpleModelImpl() {
    this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>(), 0, 0,
            0, 0);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    this.boundX = x;
    this.boundY = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public int[] getViews() {
    int[] array = new int[4];
    array[0] = this.boundX;
    array[1] = this.boundY;
    array[2] = this.width;
    array[3] = this.height;
    return array;
  }

  @Override
  public String getTypes(String identity) {
    // checking whether this identity exist
    this.hasIdentity(identity);
    return this.types.get(identity);
  }

  @Override
  public void addShapes(String identity, String type, int layer) {
    if (this.stamps.containsKey(identity)) {
      throw new IllegalArgumentException("The Identity already exist!");
    }
    // Add into the name list
    this.addSortedly(identity, layer);
    // Add into the types map, identity -> type
    this.types.put(identity, type);
    // Add into the stamps map, and instantiate a Map for identity -> list of keyframes
    this.stamps.put(identity, new ArrayList<IKeyFrame>());
    //
    this.layer.put(identity, layer);
  }

  ///////////////////////////////////////////////////////////
  @Override
  public int getLayer(String identity) {
    this.hasIdentity(identity);
    return this.layer.get(identity);
  }

  @Override
  public void removeLayer(int layer) {
    List<String> names = this.nameList();
    System.out.print(layer + "Model");
    for (String identity : names) {
      if (this.layer.get(identity) == layer) {
        this.removeShapes(identity);
      }
    }
  }

  @Override
  public void swapLayer(int first, int second) {
    List<String> names = this.nameList();
    for (String identity : names) {
      if (this.layer.get(identity) == first) {
        this.layer.replace(identity, second);
        this.identities.remove(identity);
        this.addSortedly(identity, second);
      } else if (this.layer.get(identity) == second) {
        this.layer.replace(identity, first);
        this.identities.remove(identity);
        this.addSortedly(identity, first);
      }
    }
  }

  /**
   * Sorts the given {@code identity} into {@code identities} according the given {@code layer}.
   *
   * @param identity of the animation object
   * @param layer    of the animation object
   */
  private void addSortedly(String identity, int layer) {
    for (int i = 0; i < this.identities.size(); i++) {
      if (layer < this.layer.get(this.identities.get(i))) {
        this.identities.add(i, identity);
        return;
      }
    }
    this.identities.add(identity);
  }
  /////////////////////////////////////////////////////////

  /**
   * Invariants: A list of keyframes can contains only one type of shape. And the list of keyframes
   * is sorted in a chronologically order, from first to last.
   *
   * @throws IllegalArgumentException if the given KeyFrame doesn't hold the same {@code IShape}
   *                                  type as the KeyFrames in the list or the given KeyFrame maps
   *                                  on an existing different KeyFrame.
   */
  @Override
  public void addFrame(String identity, IKeyFrame frame) {
    // Checking whether the identity exist
    this.hasIdentity(identity);
    // Checking whether the types of shapes are the same
    if (!frame.getShape().typeName().equalsIgnoreCase(this.types.get(identity))) {
      throw new IllegalArgumentException("The types of shapes doesn't match");
    }
    // Add Into the List of IKeyframes of this identity
    frame.addInto(stamps.get(identity));
  }

  @Override
  public void removeFrame(String identity, IKeyFrame frame) {
    // Checking whether the identity exist
    this.hasIdentity(identity);
    List<IKeyFrame> keyFrames = stamps.get(identity);
    frame.removeFrom(stamps.get(identity));
  }

  @Override
  public void removeShapes(String identity) {
    this.hasIdentity(identity);
    this.stamps.remove(identity);
    this.identities.remove(identity);
    this.types.remove(identity);
    this.layer.remove(identity);
  }

  @Override
  public List<String> getShapes() {
    return this.nameList();
  }


  /**
   * Additional Information: The list of keyframes returned will be in a chronologically order, from
   * first to last.
   */
  @Override
  public List<IKeyFrame> getFramesFor(String identity) {
    this.hasIdentity(identity);
    List<IKeyFrame> frames = stamps.get(identity);
    List<IKeyFrame> arr = new ArrayList<>();
    for (IKeyFrame frame : frames) {
      arr.add(frame);
    }
    return arr;
  }

  @Override
  public IShape getShapeAt(String identity, int t) {
    this.validTick(t);
    this.hasIdentity(identity);
    List<IKeyFrame> keyFrames = this.stamps.get(identity);
    for (int i = 0; i < keyFrames.size(); i++) {
      if (i + 1 < keyFrames.size()) {
        IKeyFrame start = keyFrames.get(i);
        IKeyFrame end = keyFrames.get(i + 1);
        double startTime = (double) (start.getTime());
        double endTime = (double) (end.getTime());
        if (start.getTime() < t && t < end.getTime()) {
          double multiplicationConstant = (t - startTime) / (endTime - startTime);
          return this.frameWithIn(multiplicationConstant, start.getShape(), end.getShape());
        }
      }
      if (keyFrames.get(i).getTime() == t) {
        return keyFrames.get(i).getShape().copy();
      }
    }
    return null;
  }

  //
  @Override
  public boolean isAnimationOver(int t) {
    this.validTick(t);
    return t > this.endTick();
  }

  @Override
  public int lastTick() {
    return this.endTick();
  }

  private int endTick() {
    int tick = 0;
    for (String identity : this.identities) {
      List<IKeyFrame> keyFrames = stamps.get(identity);
      if (keyFrames.size() != 0) {
        int last = keyFrames.get(keyFrames.size() - 1).getTime();
        if (last > tick) {
          tick = last;
        }
      }
    }
    return tick;
  }


  /**
   * Ensures the given {@code identity} is within the {@code stamps} map.
   *
   * @param identity of the target shape in the animation
   * @throws IllegalArgumentException if the given identity does not exist.
   */
  private void hasIdentity(String identity) {
    if (!this.stamps.containsKey(identity)) {
      throw new IllegalArgumentException("The Identity doesn't exist!");
    }
  }

  /**
   * Ensures the given {@code t} is positive.
   *
   * @param t tick
   * @throws IllegalArgumentException if the given t is less than zero.
   */
  private void validTick(int t) {
    if (t < 0) {
      throw new IllegalArgumentException("Time tick should not be less than 0");
    }
  }

  /**
   * Returns the copied list of identities.
   *
   * @return list of the identities.
   */
  private List<String> nameList() {
    List<String> arr = new ArrayList<>();
    for (String str : this.identities) {
      arr.add(str);
    }
    return arr;
  }

  /**
   * Returns a {@code IShape} that is between {@code start}: version of it and {@code end} version
   * of it by rate of change per tick between each frame for {@code start} and {@code end} shapes as
   * {@code Constant}. This is an Interpolation
   *
   * @param constant rate of change of values as {@code tick} of {@code start} - {@code tick} of new
   *                 {@code IShape} we want to have divided by {@code tick} of {@code end} - {@code
   *                 tick} of {@code end}.
   * @param start    the initial shape that we use to find the shapes in between this one and {@code
   *                 end} shape.
   * @param end      the other shape that we use to find the shapes in between this one and {@code
   *                 start} shape.
   * @return IShape that is between {@code start} and {@code end} for an specific tick.
   */
  private IShape frameWithIn(double constant, IShape start, IShape end) {
    int newX = newIntValuesCalculator(constant, start.getX(), end.getX());
    int newY = newIntValuesCalculator(constant, start.getY(), end.getY());
    int newR = newIntValuesCalculator(constant, start.getR(), end.getR());
    int newG = newIntValuesCalculator(constant, start.getG(), end.getG());
    int newB = newIntValuesCalculator(constant, start.getB(), end.getB());
    int newWidth = this.newIntValuesCalculator(constant, start.getWidth(), end.getWidth());
    int newHeight = this.newIntValuesCalculator(constant, start.getHeight(), end.getHeight());
    int newDegree = this.newIntValuesCalculator(constant, start.getDegree(), end.getDegree());
    return start.createOwnType(newX, newY, newWidth, newHeight, newR, newG, newB, newDegree);
  }

  /**
   * Returns a int that is between {@code in1}: version of it and {@code in2} of it by rate of
   * change per tick between each frame for {@code in1} and {@code in2} integers as {@code
   * Constant}. This is the interpolation calculator
   *
   * @param multiplicationConstant is the constant rate of change of values as {@code tick} of
   *                               {@code in1} - {@code tick} of new to be created integer we want
   *                               to have divided by {@code tick} of {@code in2} - {@code tick} of
   *                               {@code in2}.
   * @param in1                    the initial integer that we use to find the integer in between
   *                               this one and {@code in2}.
   * @param in2                    the other integer that we use to find the color in between this
   *                               one and {@code in1}.
   * @return an integer that is between {@code in1} and {@code in2} for an specific tick.
   */
  private int newIntValuesCalculator(double multiplicationConstant, int in1, int in2) {
    int differences = in2 - in1;
    return (int) (differences * multiplicationConstant + in1);
  }


  /**
   * Represents a Builder pattern for this SimpleModelImpl. Instance of this Builder object will be
   * used in by {@code AnimationReader} that read in a txt file and construct the intended
   * animation.
   */
  public static final class Builder implements AnimationBuilder<AnimatorModel> {
    private AnimatorModel model;

    /**
     * Constructs a Builder instance that stores a SimpleModelImpl.
     */
    public Builder() {
      this.model = new SimpleModelImpl();
    }


    @Override
    public AnimatorModel build() {
      return this.model;
    }


    @Override
    public AnimationBuilder<AnimatorModel> setBounds(int x, int y, int width, int height) {
      this.model.setBounds(x, y, width, height);
      return this;
    }


    /**
     * Additional Information: add the given {@code identity} and {@code type} into the model.
     *
     * @throws IllegalArgumentException if the given {@code identity} already exist.
     */
    @Override
    public AnimationBuilder<AnimatorModel> declareShape(String identity, String type, int layer) {
      this.model.addShapes(identity, type, layer);
      return this;
    }

    /**
     * Additional Information: add the given {@code identity} along with the two motions into the
     * model.
     *
     * @throws IllegalArgumentException will be thrown if the motions are incorrect, or the identity
     *                                  doesn't exist.
     */
    @Override
    public AnimationBuilder<AnimatorModel> addMotion(String identity, int t1, int x1, int y1,
                                                     int w1, int h1, int r1, int g1, int b1, int o1,
                                                     int t2, int x2, int y2, int w2, int h2,
                                                     int r2, int g2, int b2, int o2) {
      if (t1 > t2) {
        throw new IllegalArgumentException("Wrong motion!");
      }
      String type = this.model.getTypes(identity);
      // Creates the frames
      IKeyFrame keyFrameOne = this.buildKeyFrame(type, t1, x1, y1, w1, h1, r1, g1, b1, o1);
      IKeyFrame keyFrameTwo = this.buildKeyFrame(type, t2, x2, y2, w2, h2, r2, g2, b2, o2);
      // Add into the animation
      this.model.addFrame(identity, keyFrameOne);
      this.model.addFrame(identity, keyFrameTwo);
      return this;
    }

    /**
     * Additional Information: add the given {@code identity} along with a Keyframe into the model.
     *
     * @throws IllegalArgumentException will be thrown if the keyframe is incorrect, or the identity
     *                                  doesn't exist.
     */
    @Override
    public AnimationBuilder<AnimatorModel> addKeyframe(String identity, int t, int x, int y, int w,
                                                       int h, int r, int g, int b, int o) {
      String type = this.model.getTypes(identity);
      // Creates the frames
      IKeyFrame keyFrame = this.buildKeyFrame(type, t, x, y, w, h, r, g, b, o);
      // Add into the animation
      this.model.addFrame(identity, keyFrame);
      return this;
    }

    /**
     * Return an  {@code IKeyFrame} that represents a {@code IShape} of the given {@code type} with
     * the given attributes {@code x}, {@code y}, {@code w}, {@code h}, {@code r}, {@code g}, {@code
     * b} at the given time {@code t}.
     *
     * @param type the type for shape
     * @param t    the time tick for the KeyFrame
     * @param x    the x coordinate for the shape
     * @param y    the y coordinate for the shape
     * @param w    the width for the shape
     * @param h    the height for the shape
     * @param r    the R color index for the shape
     * @param g    the G color index for the shape
     * @param b    the B color index for the shape
     * @return a IKeyFrame
     * @throws IllegalArgumentException if the given parameters couldn't be used to construct an
     *                                  IKeyframe.
     */
    private IKeyFrame buildKeyFrame(String type, int t, int x, int y, int w, int h,
                                    int r, int g, int b, int o) {
      IKeyFrame result = null;
      if (type.equalsIgnoreCase("rectangle")) {
        result = new KeyFrame(t, new RectangleImpl(x, y, w, h, r, g, b, o));
      } else if (type.equalsIgnoreCase("ellipse")) {
        result = new KeyFrame(t, new EllipseImpl(x, y, w, h, r, g, b, o));
      }
      return result;
    }
  }

}
