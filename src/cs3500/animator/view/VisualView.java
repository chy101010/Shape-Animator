package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cs3500.animator.model.IShape;

/**
 * This class represents the visual visualization implementation of the view. It allows the clients
 * to displays the animation of the data of a view-only-model with a desired frame per second in the
 * JFrame.
 */
public class VisualView extends JFrame implements IView {
  private IViewModel model;
  private Timer timer;
  private ViewPanel panel;

  /**
   * Constructs a VisualView with the given view only model {@code model} and frame per second
   * {@code rate}. This constructors set up the timer, scrollable JPanel with a not resizeable
   * JFrame.
   *
   * @param model the view only model
   * @param rate  the frame per second
   * @throws IllegalArgumentException if the given model is null.
   * @throws IllegalArgumentException if the given rate is 0 or less than 0.
   */
  public VisualView(IViewModel model, int rate) {
    if (model == null) {
      throw new IllegalArgumentException("Must pass in a non null Model!!");
    }
    if (rate <= 0) {
      throw new IllegalArgumentException("Must pass in a positive rate!!");
    }
    this.model = model;
    this.setTitle("ExCELlence");
    // Sets up the panel and pass in the offsets
    this.panel = new ViewPanel(this.model.getViews());
    this.panel.setPreferredSize(new Dimension(model.getViews()[2], model.getViews()[3]));
    this.setSize(new Dimension(model.getViews()[2], model.getViews()[3]));
    JScrollPane scroll = new JScrollPane(panel);
    // Declare JFrame not resizable
    this.setResizable(false);
    // Sets up the delay
    int delay = (int) 1000 / rate;
    // Sets the ticking with the delay
    this.timer = new Timer(delay, new ViewLoop());
    this.add(scroll);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }


  /**
   * Renders the visualization and starts the timer, so the visualization updates every other tick.
   */
  @Override
  public void display() {
    this.setVisible(true);
    this.timer.start();
  }


  /**
   * Represents the ticking handling and updating the animations of this VisualView.
   */
  private class ViewLoop implements ActionListener {
    int tick = 0;

    /**
     * Listens to the timer, and for every action it increment the tick and obtain the list of
     * {@code IShape} at that given tick in the model. It will pass the list of the JPanel, and tell
     * the JPanel to repaint those shapes.
     *
     * @param e action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      tick++;
      if (model.isAnimationOver(tick)) {
        return;
      }
      List<IShape> shapes = new ArrayList<>();
      List<String> names = model.getShapes();
      for (String name : names) {
        IShape shape = model.getShapeAt(name, tick);
        if (shape != null) {
          shapes.add(shape);
        }
      }
      panel.setShapes(shapes);
      panel.repaint();
    }
  }
}
