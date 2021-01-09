package cs3500.animator.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.model.IShape;

/**
 * Representing the JPanel of the visual visualization displays. It allows the clients to input a
 * list of {@code shapes}, and draws those shapes accordingly in the first to last order.
 */
public class ViewPanel extends JPanel {
  private List<IShape> shapes;
  private int[] offSet;

  /**
   * Constructs a ViewPanel with the given {@code offset} which will be used to offset the x/y
   * coordinates of the animation object.
   *
   * @param offSet list of offsets
   */
  public ViewPanel(int[] offSet) {
    this.offSet = offSet;
    this.shapes = new ArrayList<>();
  }

  /**
   * Renders the list of {@code shapes} in to the JPanel. Supporting shape rectangle and ellipse
   * currently.
   *
   * @param g Graphics
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    AffineTransform store = g2d.getTransform();
    for (int i = 0; i < this.shapes.size(); i++) {
      if (this.shapes.get(i) != null) {
        IShape shape = this.shapes.get(i);
        String type = shape.typeName();
        if (i + 1 < this.shapes.size() && this.shapes.get(i + 1) == null) {
          g2d.setColor(new Color(0.5f, 0f, 0f, .3f));
        } else {
          g2d.setColor(new Color(shape.getR(), shape.getG(), shape.getB()));
        }
        g2d.rotate(Math.toRadians(shape.getDegree()), shape.getX() + shape.getWidth() / 2
                - this.offSet[0], shape.getY() + shape.getHeight() / 2 - this.offSet[1]);
        if (type.equalsIgnoreCase("Rectangle")) {
          g2d.fillRect(shape.getX() - this.offSet[0], shape.getY() - this.offSet[1],
                  shape.getWidth(), shape.getHeight());
        } else if (type.equalsIgnoreCase("ellipse")) {
          g2d.fillOval(shape.getX() - this.offSet[0], shape.getY() - this.offSet[1],
                  shape.getWidth(), shape.getHeight());
        }
      }
      g2d.setTransform(store);
    }

  }

  /**
   * Mutates the current {@code shapes} into the given shapes.
   *
   * @param shapes list of {@code IShapde} to be rendered
   */
  public void setShapes(List<IShape> shapes) {
    this.shapes = shapes;
  }
}
