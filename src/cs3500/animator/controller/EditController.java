package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.view.IEditView;


/**
 * Represents the controller for IEditView interface. This implementation offers a list of action
 * reacts {@code Features} that can be requested by the view client. A requests can be sent by the
 * view client {@code view}, then this controller will either do something with the given {@code
 * model} or change the speed of data flow between itself and the {@code view}.
 */
public class EditController implements IController, Features {
  // View Client
  private IEditView view;
  // Model
  private AnimatorModel model;
  // Timer
  private Timer timer;
  // Delay Of The Timer: Default 1 frame/sec
  private int delay = 1000;
  // Current tick
  private int tick = 0;
  // Looping Back Status
  private boolean loop = false;
  // The Identity Of The Selected Animation Object
  private String highLighted = null;
  // The List of Keyframes Of The Selected Animation Object
  private List<IKeyFrame> frames = null;
  // Data Flow Pause Status
  private boolean pause = false;

  /**
   * Constructs a EditController with the given {@code view} and {@code model}, and sets up a timer
   * with a timer listener that can send updated data to the {@code view} client every tick.
   *
   * @param view  an IEditView
   * @param model an AnimatorModel
   * @throws IllegalArgumentException if either of the given view of model is null.
   */
  public EditController(IEditView view, AnimatorModel model) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Must give a non null View or Model!!");
    }
    this.view = view;
    this.model = model;
    this.timer = new Timer(delay, new Ticker());
  }

  /**
   * Initialize the interactive visualization by sending a request and data to the view client.
   */
  @Override
  public void play() {
    // Sending the list of action reacts to the view client.
    this.view.setFeatures(this);
    // Sending the size information of the model so that view client can adjust its view size.
    this.view.setCanvas(model.getViews());
    // Sending the list of identities in the model so that view client can display.
    this.sendIdentities();
    // Sending a list of existing layers
    this.view.setLayers(this.getLayers());
    // Requesting the view client to show start displaying
    this.view.display();
  }

  /**
   * An action react that starts the timer, so the controller can begin sending updated data to the
   * view client.
   */
  @Override
  public void start() {
    this.timer.start();
  }

  /**
   * An action react that resets the current {@code tick} back to 0, so the controller will send
   * data starting from the beginning.
   */
  @Override
  public void restart() {
    this.pause = false;
    this.tick = 0;
  }

  /**
   * An action react that set the pause status to false, so the controller will continue to tick and
   * send data to the view client.
   */
  @Override
  public void resume() {
    this.pause = false;
  }

  /**
   * An action react that set the pause status to true, so the controller will stop to tick, but it
   * will continue to send some data to the view client.
   */
  @Override
  public void pause() {
    this.pause = true;
  }

  /**
   * An action react that increases the speed of the timer by two times by decreasing the {@code
   * delay} two times. The {@code delay} can't be lower than 1.
   */
  @Override
  public void increase() {
    if (this.delay / 2 > 1) {
      this.delay = delay / 2;
      this.timer.setDelay(delay);
    }
  }

  /**
   * An action react that decreases the speed of the timer by two times by increasing the {@code
   * delay} two times. The {@code delay} can't be higher than 8000.
   */
  @Override
  public void decrease() {
    if (this.delay * 2 < 8000) {
      this.delay = delay * 2;
      this.timer.setDelay(delay);
    } else {
      this.view.errorBox("Don't Make It Too Slow");
    }
  }

  /**
   * An action react that enables/disables looping of the visualization.
   */
  @Override
  public void loopSwitch() {
    this.loop = !this.loop;
  }

  /**
   * An action react that updates {@code highLighted} and {@code frames} according to which
   * animation object {@code identity} is selected/requested by the view client. The list of
   * keyframes of the selected object will be passed back to the view client.
   *
   * @param identity the identity of the selected animation object.
   */
  @Override
  public void selectShape(String identity) {
    if (identity == null) {
      this.highLighted = null;
      this.frames = null;
      this.view.displayKeyframes(new ArrayList<>());
      return;
    }
    this.highLighted = identity;
    this.sendKeyFrames();
  }

  /**
   * An action react that handles the removing-shape request from the view client. It will remove
   * the current selected animation object from the {@code model}. An JOptionPanel will pop up by
   * the rule of the {@code model} or no selected object.
   */
  @Override
  public void removeShape() {
    if (this.highLighted != null) {
      this.model.removeShapes(this.highLighted);
      this.sendIdentities();
      this.view.setLayers(this.getLayers());
    } else {
      this.view.errorBox("Must Select A Shape!");
    }
  }

  /**
   * An action react that handles the adding-shape request from the view client. It will add shape
   * with the given {@code identity} of the given {@code type} on the given {@code layer} into the
   * {@code model}. An JOptionPanel will pop up if the adding is unsuccessful by the rule of the
   * {@code model}.
   *
   * @param identity of the shape
   * @param type     of the shape
   * @param layer    of the shape
   */
  @Override
  public void addShape(String identity, String type, int layer) {
    try {
      this.model.addShapes(identity, type, layer);
      this.sendIdentities();
      this.view.setLayers(this.getLayers());
    } catch (IllegalArgumentException ieo) {
      this.view.errorBox(ieo.getMessage());
    }
  }

  /**
   * An action react that handles the removing-keyframe request from the view client. It will remove
   * the keyframe of the selected animation object at the given {@code tick}. An JOptionPanel will
   * pop up if the removing is unsuccessful by the rule of the {@code model} or no selected object.
   *
   * @param tick the tick of the keyframe to be removed
   */
  @Override
  public void removeKeyFrame(int tick) {
    if (this.highLighted != null) {
      try {
        this.model.removeFrame(this.highLighted,
                new KeyFrame(tick, this.model.getShapeAt(this.highLighted, tick)));
        this.sendKeyFrames();
      } catch (IllegalArgumentException iae) {
        this.view.errorBox("The KeyFrame Doesn't Exist");
      }
    } else {
      this.view.errorBox("Must Select A Shape!");
    }
  }

  /**
   * An action react that handles the adding-keyframe request from the view client. It will add a
   * keyframe to the selected animation object at the given {@code t} with a shape with these
   * attributes {@code x,y,width,height,r,g,b}. An JOptionPanel will pop up if the removing is
   * unsuccessful by the rule of the {@code model} or no selected object.
   *
   * @param t      the tick of the keyframe to be added
   * @param x      coordinate
   * @param y      coordinate
   * @param width  of the shape
   * @param height of the shape
   * @param r      red color index
   * @param g      green color index
   * @param b      blue color index
   * @param o      orientation
   */
  @Override
  public void addKeyFrame(int t, int x, int y, int width, int height, int r, int g, int b, int o) {
    if (this.highLighted != null) {
      String type = this.model.getTypes(this.highLighted);
      IKeyFrame keyframe = null;
      try {
        if (type.equalsIgnoreCase("rectangle")) {
          keyframe = new KeyFrame(t, new RectangleImpl(x, y, width, height, r, g, b, o));
        } else if (type.equalsIgnoreCase("ellipse")) {
          keyframe = new KeyFrame(t, new EllipseImpl(x, y, width, height, r, g, b, o));
        }
      } catch (IllegalArgumentException iea) {
        this.view.errorBox(iea.getMessage());
      }
      try {
        if (keyframe != null) {
          this.model.addFrame(this.highLighted, keyframe);
          this.sendKeyFrames();
        }
      } catch (IllegalArgumentException iea) {
        this.view.errorBox(iea.getMessage());
      }
    } else {
      this.view.errorBox("Must Select A Shape!");
    }
  }

  /**
   * An action react that handles the editing-keyframe request from the view client. It will edit
   * the keyframe of the selected animation object at the given {@code t}. {@code
   * x,y,width,height,r,g,b} are the new attributes of the Keyframe. An JOptionPanel will pop up if
   * the removing is unsuccessful by the rule of the {@code model} or no selected object.
   *
   * @param t      the tick of the keyframe to be removed
   * @param x      coordinate
   * @param y      coordinate
   * @param width  width of the shape
   * @param height height of the shape
   * @param r      red color index
   * @param g      green color index
   * @param b      blue color index
   * @param o      orientation
   */
  @Override
  public void editKeyFrame(int t, int x, int y, int width, int height, int r, int g, int b, int o) {
    if (this.highLighted != null) {
      IShape shape = null;
      try {
        shape = this.model.getShapeAt(this.highLighted,
                t).createOwnType(x, y, width, height, r, g, b, o);
      } catch (IllegalArgumentException iae) {
        this.view.errorBox(iae.getMessage());
      }
      if (shape != null) {
        this.model.removeFrame(this.highLighted, new KeyFrame(t,
                this.model.getShapeAt(this.highLighted, t)));
        this.model.addFrame(this.highLighted, new KeyFrame(t, shape));
        this.sendKeyFrames();
      }
    } else {
      this.view.errorBox("Must Select A Shape!");
    }
  }

  /**
   * Sends the list of identities of the {@code model} to the {@code view} client.
   */
  private void sendIdentities() {
    this.view.displayIdentity(this.model.getShapes());
  }

  /**
   * Sends the list of keyframes of the selected animation object to the {@code view} client.
   */
  private void sendKeyFrames() {
    this.view.displayKeyframes(this.model.getFramesFor(this.highLighted));
  }

  /**
   * Mutates the current {@code tick} to the given {@code t}, and sends the list of shapes at this
   * tick to the view and the tick value.
   *
   * @param t new tick
   */
  @Override
  public void selctTick(int t) {
    this.tick = t;
    this.view.displayTick(t);
    this.view.displayShapes(this.getShapes());
    this.view.repaint();
  }

  /**
   * Removes all the animation objects in the given {@code t} from the animation.
   *
   * @param t the layer to be removed.
   */
  @Override
  public void removeLayer(int t) {
    this.model.removeLayer(t);
    this.view.setLayers(this.getLayers());
    this.sendIdentities();
  }

  /**
   * Swaps the shapes in the first layer in to the second layer and the shapes in the second layer
   * in to the first layer.
   *
   * @param first  layer
   * @param second layer
   */
  @Override
  public void swapLayer(int first, int second) {
    this.model.swapLayer(first, second);
    this.view.setLayers(this.getLayers());
  }

  private List<Integer> getLayers() {
    List<String> identities = this.model.getShapes();
    List<Integer> arr = new ArrayList<>();
    for (String name : identities) {
      if (!arr.contains(this.model.getLayer(name))) {
        arr.add(this.model.getLayer(name));
      }
    }
    return arr;
  }

  /**
   * Returns the list of shape at the current {@code tick} and highlight the boxed animation object
   * if there is one.
   *
   * @return the list of shape to be drawn at current tick.
   */
  private List<IShape> getShapes() {
    List<IShape> shapes = new ArrayList<>();
    List<String> names = model.getShapes();
    for (String name : names) {
      IShape shape = model.getShapeAt(name, tick);
      if (shape != null) {
        shapes.add(shape);
        // This adds a red transparent object to high light the object if it is selected.
        if (name.equals(highLighted)) {
          IShape boxing = shape.createOwnType(shape.getX() - 5,
                  shape.getY() - 5, shape.getWidth() + 10,
                  shape.getHeight() + 10, 255, 0, 0, shape.getDegree());
          shapes.add(boxing);
          shapes.add(null);
        }
      }
    }
    return shapes;
  }


  /**
   * Represents the ticking handling and updating data and sending it to the view client.
   */
  private class Ticker implements ActionListener {

    /**
     * Listens to the timer, and for every action it increment the tick if the {@code pause} is
     * false and obtain the list of {@code IShape} at that given tick from the model. It will pass
     * the the list of {@code IShape}, and the current {@code tick}, the status of the looping
     * {@code loop}, and the current speed {@code delay} to the view client to display. It will also
     * pass along a 'faded red transparent object that is a little bigger than the size of selected
     * animation object to high-light the selected object.
     *
     * @param e action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      // Sends the view the shapes to be drawn
      view.displayShapes(getShapes());
      // Send the view the current tick
      view.displayTick(tick);
      // Send the last tick of the model
      view.lastTick(model.lastTick());
      // it will pass the status of the loop, delay, and tell the view to refresh.
      view.displayLoop(loop);
      view.displayDelay(delay);
      view.repaint();
      // If the animation is not paused
      if (!pause) {
        // If the Loop back is enabled and the animation is over, it resets the current tick
        // back to 0. If the look back is disable and the animation is over, it will freeze and
        // wait for further interaction.
        if (model.isAnimationOver(tick) && loop) {
          if (loop) {
            tick = 0;
          } else {
            return;
          }
        }
        tick++;
      }
    }
  }
}
