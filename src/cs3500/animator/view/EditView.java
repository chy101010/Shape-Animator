package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;

/**
 * Represents an user interactive visualization of the AnimatorModel. It will display the animations
 * of the model and provides buttons to allow the user to interact with the visualization. Every
 * interaction will request an action react from the {@code features}.
 */
public class EditView extends JFrame implements IEditView {
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

  // Right Side Panel For Buttons In Instruction 3
  private UtilPanel utilPanel;
  // Center Side Panel To Display The Animations
  private ViewPanel drawPanel;
  // Bottom Side Panel To Hold the TextArea For Displaying Keyframes And The Edit Panel
  private JPanel southPanel;
  // Bottom Right Panel To Edit The Animations
  private JPanel editPanel;
  // Bottom Left Area To Display Keyframes
  private JTextArea textArea;
  // JComboBox To Display All The Identities In The Animations
  private JComboBox identities;

  private Features features;
  private List<IKeyFrame> keyFrames;

  //////////////////////////////////////
  // JSlider
  private JSlider slider;

  private List<Integer> layers;

  /**
   * Constructs an EditView, setting up the buttons panel, edit panel, display panel, and display
   * textarea.
   */
  public EditView() {
    super();
    this.setTitle("Editable");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setResizable(false);

    // Sets Up Buttons Panel.
    this.setUpUtilPanel();
    this.add(new JScrollPane(this.utilPanel), BorderLayout.EAST);

    // Set Up the South Panel
    this.setUpSouthPanel();
    // Add The South Panel
    this.add(this.southPanel, BorderLayout.SOUTH);

    // Make It Resizeable
    this.setResizable(true);

    JTextArea noteToUser = new JTextArea();
    noteToUser.setText(" Note To User:\n  A user can select an animation object using the JComboBox"
            + " provide"
            + " at bottom-right corner. \n Once an animation object is selected, it will be "
            + " high-lighted(transparent red),"
            + " and its keyframes\n will be displayed in the left JTextArea and it can be "
            + " unhigh-lighted by choosing 'No Select' or another animation object.\n\n"
            + " The user can modify/add to its keyframes or remove the selected animation object.\n"
            + " The user can add a new object into by giving it a name and select its type.");
    noteToUser.setEditable(false);
    this.add(noteToUser, BorderLayout.NORTH);
  }


  /**
   * Set ups the scrollable utilPanel along with its button. Every Button will be disabled until the
   * 'start' button is pressed.
   */
  private void setUpUtilPanel() {
    this.utilPanel = new UtilPanel();
    this.utilPanel.setPreferredSize(new Dimension(210, 300));

    this.startButton = new JButton("Start");
    this.setStartButton();
    this.utilPanel.add(this.startButton);

    this.restartButton = new JButton("Restart");
    this.restartButton.setEnabled(false);
    this.setRestartButton();
    this.utilPanel.add(this.restartButton);

    this.pauseButton = new JButton("Pause");
    this.pauseButton.setEnabled(false);
    this.utilPanel.add(this.pauseButton);

    this.resumeButton = new JButton("Resume");
    this.resumeButton.setEnabled(false);

    this.enableReaction(this.pauseButton, this.resumeButton);
    this.utilPanel.add(this.resumeButton);

    this.increaseButton = new JButton("Speed Up 2X");
    this.increaseButton.setEnabled(false);
    this.utilPanel.add(this.increaseButton);

    this.decreaseButton = new JButton("Slow Down 2X");
    this.decreaseButton.setEnabled(false);
    this.utilPanel.add(this.decreaseButton);

    this.enableLoopButton = new JButton("Enable Loop");
    this.enableLoopButton.setEnabled(false);
    this.utilPanel.add(this.enableLoopButton);

    this.disableLoopButton = new JButton("Disable Loop");
    this.disableLoopButton.setEnabled(false);
    this.enableReaction(this.disableLoopButton, this.enableLoopButton);
    this.utilPanel.add(this.disableLoopButton);

    this.slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
    this.slider.setMajorTickSpacing(10);
    this.slider.setMinorTickSpacing(1);
    this.slider.setPaintTicks(true);
    this.utilPanel.add(this.slider);

    // adding the button into the compose panel
    this.utilPanel.setBorder(BorderFactory.createLineBorder(Color.black));
  }

  /**
   * Sets up the southPanel that holds a JTextArea on the left side to display all the Keyframes of
   * the selected Animation Object and an EditPanel that allows the user to select the Animation
   * Objects and modify their animations and to remove and delete Animation Objects.
   */
  private void setUpSouthPanel() {
    this.southPanel = new JPanel();
    this.southPanel.setLayout(new BorderLayout());
    this.southPanel.setPreferredSize(new Dimension(100, 160));
    this.setUpTextArea();
    this.setUpEditPanel();
    this.southPanel.add(new JScrollPane(this.textArea), BorderLayout.CENTER);
    this.southPanel.add(new JScrollPane(this.editPanel), BorderLayout.EAST);
  }

  /**
   * Sets up the EditPanel to hold all the editing buttons and the identities JComboBox, and set up
   * the rules how they react to action.
   */
  private void setUpEditPanel() {
    this.editPanel = new JPanel();
    this.editPanel.setPreferredSize(new Dimension(300, 170));
    JLabel select = new JLabel("Select the Object>");
    select.setFont(new Font("TimesRoman", Font.PLAIN, 14));
    this.editPanel.add(select);
    this.identities = new JComboBox();
    this.editPanel.add(this.identities);

    // Remove Shape Button
    // Remove Shape Button
    JButton removeShapeButton = new JButton("Remove Shape");
    removeShapeButton.addActionListener(evt -> this.removeShapeButton());
    this.editPanel.add(removeShapeButton);

    // Add Shape Button
    // Add Shape Button
    JButton addShapeButton = new JButton("Add Shape");
    addShapeButton.addActionListener(evt -> this.addShapeButton());
    this.editPanel.add(addShapeButton);

    // Remove KeyFrame Button
    // Remove KeyFrame Button
    JButton removeKeyFrame = new JButton("Remove Keyframe");
    removeKeyFrame.addActionListener(evt -> this.removeKeyFrameButton());
    this.editPanel.add(removeKeyFrame);

    // Add KeyFrame Button
    // Add KeyFrame Button
    JButton addKeyFrame = new JButton("Add Keyframe");
    addKeyFrame.addActionListener(evt -> this.addKeyFrameButton());
    this.editPanel.add(addKeyFrame);

    // Edit KeyFrame Button
    // Edit KeyFrame Button
    JButton editKeyFrame = new JButton("Edit Keyframe");
    editKeyFrame.addActionListener(evt -> this.editKeyFrameButton());
    this.editPanel.add(editKeyFrame);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    JButton removeLayer = new JButton("Remove Layer");
    removeLayer.addActionListener(evt -> this.removeLayer());
    this.editPanel.add(removeLayer);

    JButton swapLayer = new JButton("Swap Layers");
    swapLayer.addActionListener(evt -> this.swapLayer());
    this.editPanel.add(swapLayer);
  }

  /**
   * Sets up the TextArea for displaying keyframes.
   */
  private void setUpTextArea() {
    this.textArea = new JTextArea();
    this.textArea.setEditable(false);
  }

  /**
   * Sets up the action react of the first/second Button. Once the first Button is clicked, it will
   * enable the second Button, but it will disable itself, vice versa.
   *
   * @param first  the first JButton
   * @param second the second JButton
   */
  private void enableReaction(JButton first, JButton second) {
    first.addActionListener(evt -> second.setEnabled(true));
    first.addActionListener(evt -> first.setEnabled(false));
    second.addActionListener(evt -> first.setEnabled(true));
    second.addActionListener(evt -> second.setEnabled(false));
  }

  /**
   * Sets up the action react of the Start Button. Once it is clicked, it will enable the Enable
   * Button, Restart Button, Pause Button, Decrease Speed Button, and Increase Speed Button, but it
   * will disable itself.
   */
  private void setStartButton() {
    this.startButton.addActionListener(evt -> this.enableLoopButton.setEnabled(true));
    this.startButton.addActionListener(evt -> this.restartButton.setEnabled(true));
    this.startButton.addActionListener(evt -> this.pauseButton.setEnabled(true));
    this.startButton.addActionListener(evt -> this.decreaseButton.setEnabled(true));
    this.startButton.addActionListener(evt -> this.increaseButton.setEnabled(true));
    this.startButton.addActionListener(evt -> this.startButton.setEnabled(false));
  }

  /**
   * Sets up the action react of the Restart Button. Once it is clicked, it will enable will the
   * pause Button, but it will disable the resume Button.
   */
  private void setRestartButton() {
    this.restartButton.addActionListener(evt -> this.pauseButton.setEnabled(true));
    this.restartButton.addActionListener(evt -> this.resumeButton.setEnabled(false));
  }


  /**
   * Makes the JFrame visible.
   */
  @Override
  public void display() {
    this.setVisible(true);
  }

  /**
   * Sets up the action react of the Buttons to the {@code features}. This allows View client to
   * request an action from the Controller when a button is clicked. The type of action is to be
   * decided by the Controller.
   *
   * @param features represents the actions that the view client can request.
   */
  @Override
  public void setFeatures(Features features) {
    this.features = features;
    // Informs The Controller Which Animation Object Is Selected At The Moment.
    this.identities.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        String identity = (String) identities.getSelectedItem();
        if (identity.equals("No Select")) {
          features.selectShape(null);
        } else {
          features.selectShape(identity);
        }
      }
    });
    this.slider.addChangeListener(evt -> {
      int tick = (int) slider.getValue();
      if (!slider.getValueIsAdjusting()) {
        features.selctTick(tick);
      } else {
        features.selctTick(tick);
        this.pauseButton.doClick();
      }
    });
    this.disableLoopButton.addActionListener(evt -> features.loopSwitch());
    this.enableLoopButton.addActionListener(evt -> features.loopSwitch());
    this.startButton.addActionListener(evt -> features.start());
    this.restartButton.addActionListener(evt -> features.restart());
    this.pauseButton.addActionListener(evt -> features.pause());
    this.resumeButton.addActionListener(evt -> features.resume());
    this.increaseButton.addActionListener(evt -> features.increase());
    this.decreaseButton.addActionListener(evt -> features.decrease());
  }


  /**
   * Sets up the animations displaying {@code drawPanel} with the given {@code canvas}
   * bounds/dimensions, makes it scrollable and add it into the JFrame. Using the given {@code
   * canvas} to mutate the dimension of the JFrame to incorporate the displaying panel.
   *
   * @param canvas the bounds/dimensions of the displaying panel.
   */
  @Override
  public void setCanvas(int[] canvas) {
    this.drawPanel = new ViewPanel(canvas);
    this.drawPanel.setPreferredSize(new Dimension(canvas[2], canvas[3]));
    this.drawPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    JScrollPane scroll = new JScrollPane(this.drawPanel);
    this.add(scroll, BorderLayout.CENTER);
    this.setSize(new Dimension(canvas[2] + 400, canvas[3] + 360));
  }

  /**
   * Refresh the animations displaying {@code drawPanel}, and the {@code utilPanel} that holds the
   * buttons(From Instruction #3) and displays the loop back status, current tick, and current
   * delay.
   */
  @Override
  public void repaint() {
    drawPanel.repaint();
    utilPanel.repaint();
  }

  /**
   * Displays the current tick in the {@code utilPanel}.
   *
   * @param tick current tick
   */
  @Override
  public void displayTick(int tick) {
    this.utilPanel.setTick(tick);
    this.slider.setValue(tick);
  }


  /**
   * Displays the current delay in the {@code delay}.
   *
   * @param delay current delay
   */
  @Override
  public void displayDelay(int delay) {
    this.utilPanel.setDelay(delay);
  }

  /**
   * Displays status of the loop back option whether it is enabled or not in the {@code utilPanel}.
   *
   * @param loop a boolean value indicating the loop back, 'true' indicates that the  loop back is
   *             enabled, and false otherwise.
   */
  @Override
  public void displayLoop(boolean loop) {
    utilPanel.setLoop(loop);
  }


  /**
   * Sets the list of keyframes stored into {@code frames}, and renders the list of keyframes in the
   * {@code textArea}.
   *
   * @param frames a list of keyframes of the selected animation object.
   */
  @Override
  public void displayKeyframes(List<IKeyFrame> frames) {
    this.keyFrames = frames;
    this.displayTextualFrames();
  }

  /**
   * Displays/Repaints the list of keyframes in the {@code textArea} of the selected animation
   * object in the JComboBox {@code identities}.
   */
  private void displayTextualFrames() {
    StringBuilder str = new StringBuilder("KeyFrame Info: Tick-Coordinates(X, Y)"
            + "-Dimensions(Width, Height)-Color(R, G, B)\n");
    int size = 0;
    for (IKeyFrame frame : this.keyFrames) {
      str.append("KeyFrame: " + frame.toString() + "\n");
      size++;
    }
    this.textArea.setTabSize(size);
    this.textArea.setText(str.toString());
    this.textArea.repaint();
  }


  /**
   * Updates the identities in the JComboBox {@code identities} into the given list of {@code
   * names}.
   *
   * @param names the list of identities to be updated into the JComboBox
   */
  @Override
  public void displayIdentity(List<String> names) {
    String[] arr = new String[names.size()];
    this.identities.removeAllItems();
    this.identities.addItem("No Select");
    for (int i = 0; i < arr.length; i++) {
      this.identities.addItem(names.get(i));
    }
  }

  /**
   * Draws the given list of {@code shapes} onto the animation displaying {@code drawPanel}.
   *
   * @param shapes the list of shapes to be drawn.
   */
  @Override
  public void displayShapes(List<IShape> shapes) {
    this.drawPanel.setShapes(shapes);
  }

  /**
   * Sets up the react action of the button {@code addShapeButton}. This allows a JOptionPanel to
   * pop up when the addShape Button is clicked. This JOptionPanel has one JTextField for the client
   * to input shape's identity, and it has JComboBox that allows the user to choose the shape's
   * type. Once the client clicked the Ok button in that JOptionPanel, an add Shape request will be
   * sent to the controller.
   */
  private void addShapeButton() {
    JTextField field1 = new JTextField();
    String[] types = new String[]{"rectangle", "ellipse"};
    JComboBox field2 = new JComboBox(types);
    JTextField field3 = new JTextField();
    Object[] fields = {"Input Shape's Identity", field1, "Select Shape's  Type",
                       field2, "Input Layer", field3};
    if (JOptionPane.showConfirmDialog(null, fields,
            "Add Shape", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
      try {
        this.features.addShape(field1.getText(), (String) field2.getSelectedItem(),
                Integer.parseInt(field3.getText()));
      } catch (IllegalArgumentException ieo) {
        this.errorBox(ieo.getMessage());
      }
    }
  }

  /**
   * Sets up the react action of the button {@code removeShapeButton}. This allows the client to
   * remove the selected animation object int the JComboBox {@code identities}. A JOptionPanel will
   * pop up to allow the user to confirm the action.
   */
  private void removeShapeButton() {
    if (JOptionPane.showConfirmDialog(
            null,
            "You Want To Remove The Selected Shape?",
            "Question",
            JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
      this.features.removeShape();
    }
  }

  /**
   * Sets up the react action of the button {@code removeKeyFrame}. This allows a JOptionPanel to
   * pop up when the remove Keyframe button is clicked. This JOptionPanel has a JTextField for the
   * client to input the tick(an integer) of the keyframe of the selected animation object he/she
   * wants to remove.
   */
  private void removeKeyFrameButton() {
    JTextField field = new JTextField();
    Object[] fields = {"Input Tick To Be Removed", field};
    if (JOptionPane.showConfirmDialog(null, fields,
            "Remove KeyFrame", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
      try {
        this.features.removeKeyFrame(Integer.parseInt(field.getText()));
      } catch (NumberFormatException iea) {
        this.errorBox(iea.getMessage() + ", (Pass In An Integer)");
      }
    }
  }

  /**
   * Sets up the react action of the button {@code addKeyFrame}. This allows a JOptionPanel to pop
   * up when the add Keyframe button is clicked. This JOptionPanel has eight JTextFields for the
   * client input the attributes of a {@code IKeyframe}, then these attributes will be passed to the
   * Controller to add a {@code IKeyframe} to the selected animation object in the JComboBox {@code
   * identities}.
   */
  //TOdo
  private void addKeyFrameButton() {
    JTextField[] atr = this.keyframeAttributes();
    Object[] fields = {"Tick", atr[0], "X Position", atr[1], "Y Position", atr[2], "Width", atr[3],
                       "Height", atr[4], "R Color", atr[5], "G Color", atr[6],
                       "B Color", atr[7], "Degree", atr[8]};
    if (JOptionPane.showConfirmDialog(null, fields, "Add KeyFrame",
            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
      try {
        this.features.addKeyFrame(Integer.parseInt(atr[0].getText()),
                Integer.parseInt(atr[1].getText()), Integer.parseInt(atr[2].getText()),
                Integer.parseInt(atr[3].getText()), Integer.parseInt(atr[4].getText()),
                Integer.parseInt(atr[5].getText()), Integer.parseInt(atr[6].getText()),
                Integer.parseInt(atr[7].getText()), Integer.parseInt(atr[8].getText()));
      } catch (NumberFormatException nfe) {
        this.errorBox(nfe.getMessage() + ", (Pass In Integers)");
      }
    }
  }

  /**
   * Sets up react action of the button {@code editKeyFrame}. This allows a JOptionPanel to pop up
   * when the edit KeyFrame button is clicked. This JOptionPanel has a JComboBox that allows the
   * user to select one of the existing keyframes of the selected animation object in {@code
   * identities} to be edited. Once a keyframe is selected, seven JTextFields would separately
   * display the current attribute of the Shape. Then the client would be able to edit them by
   * editing each JTextField.
   */
  private void editKeyFrameButton() {
    JComboBox box = new JComboBox();
    box.addItem("No Select");
    for (IKeyFrame frame : this.keyFrames) {
      box.addItem(frame.getTime());
    }
    final int[] tick = {-1};
    JTextField[] atr = this.keyframeAttributes();
    box.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          if (!box.getSelectedItem().equals("" +
                  "No Select")) {
            tick[0] = (int) box.getSelectedItem();
            for (IKeyFrame frame : keyFrames) {
              if (frame.getTime() == tick[0]) {
                IShape shape = frame.getShape();
                atr[1].setText(Integer.toString(shape.getX()));
                atr[2].setText(Integer.toString(shape.getY()));
                atr[3].setText(Integer.toString(shape.getWidth()));
                atr[4].setText(Integer.toString(shape.getHeight()));
                atr[5].setText(Integer.toString(shape.getR()));
                atr[6].setText(Integer.toString(shape.getG()));
                atr[7].setText(Integer.toString(shape.getB()));
                atr[8].setText(Integer.toString(shape.getDegree()));
              }
            }
          }
        }
      }
    });
    Object[] fields = {"Select The KeyFrame", box, "X Position", atr[1], "Y Position", atr[2],
                       "Width", atr[3], "Height", atr[4], "R Color", atr[5], "G Color", atr[6],
                       "B Color", atr[7], "Degree", atr[8]};
    if (JOptionPane.showConfirmDialog(null, fields, "Edit KeyFrame",
            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
      try {
        if (tick[0] != -1) {
          this.features.editKeyFrame(tick[0], Integer.parseInt(atr[1].getText()),
                  Integer.parseInt(atr[2].getText()), Integer.parseInt(atr[3].getText()),
                  Integer.parseInt(atr[4].getText()), Integer.parseInt(atr[5].getText()),
                  Integer.parseInt(atr[6].getText()), Integer.parseInt(atr[7].getText()),
                  Integer.parseInt(atr[8].getText()));
        } else {
          this.errorBox("Must Select A KeyFrame!");
        }
      } catch (NumberFormatException nfe) {
        this.errorBox(nfe.getMessage() + ", (Pass In Integers)");
      }
    }
  }

  /**
   * Sets up the react action of the button {@code removeLayer}. This allows a JOptionPanel to pop
   * up when that button clicked. It will have a JComboBox that allows the user to select the layer
   * of shapes of be removed after clicking the 'Yes' button.
   */
  private void removeLayer() {
    JComboBox box = new JComboBox();
    box.addItem("No Select");
    for (Integer layer : this.layers) {
      box.addItem(layer);
    }
    final Integer[] layer = {null};
    box.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          if (!box.getSelectedItem().equals("" +
                  "No Select")) {
            layer[0] = (int) box.getSelectedItem();
          }
        }
      }
    });
    if (JOptionPane.showConfirmDialog(null, box, "Remove Layer",
            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
      if (layer[0] != null) {
        features.removeLayer(layer[0]);
      }
    }
  }

  /**
   * Sets up the react action of the button {@code swapLayer}. This allows a JOptionPanel to pop
   * up when that button clicked. It will display the existing layers with two input JTextArea for
   * the user to choose the layers to be swapped.
   */
  private void swapLayer() {
    JTextArea area = new JTextArea();
    area.setSize(2, this.layers.size());
    for (Integer layer : this.layers) {
      area.append(Integer.toString(layer) + "\n");
    }
    area.setEditable(false);
    JTextField field = new JTextField();
    JTextField field1 = new JTextField();
    Object[] fields = {"Layers", area, "First Layer", field, "Second layer", field1};
    if (JOptionPane.showConfirmDialog(null, fields, "Edit KeyFrame",
            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
      try {
        this.features.swapLayer(Integer.parseInt(field.getText()),
                Integer.parseInt(field1.getText()));
      } catch (NumberFormatException nfe) {
        this.errorBox(nfe.getMessage() + ", (Pass In Integers)");
      }
    }
  }

  /**
   * Returns an array of eights JTextFields to be displayed in JOptionPanel.
   *
   * @return an array of JTextFields.
   */
  private JTextField[] keyframeAttributes() {
    JTextField tick = new JTextField();
    JTextField x = new JTextField();
    JTextField y = new JTextField();
    JTextField width = new JTextField();
    JTextField height = new JTextField();
    JTextField r = new JTextField();
    JTextField g = new JTextField();
    JTextField b = new JTextField();
    JTextField o = new JTextField();
    return new JTextField[]{tick, x, y, width, height, r, g, b, o};
  }


  /**
   * Pops up a JOptionPanel displaying the given {@code message}.
   *
   * @param message to display
   */
  public void errorBox(String message) {
    JFrame frame = new JFrame();
    JOptionPane.showMessageDialog(frame, message);
  }

  /**
   * Change the length of the {@code slider} into the given {@code t}.f
   *
   * @param t last tick of the animation
   */
  @Override
  public void lastTick(int t) {
    this.slider.setMaximum(t);
  }

  /**
   * Updates the {@code layers} to the given layers.
   *
   * @param layers of the animation objects
   */
  @Override
  public void setLayers(List<Integer> layers) {
    this.layers = layers;
  }

  /**
   * Representing the JPanel that could displays the current tick, status of the loop back, and the
   * delay. This JPanel is used to hold start/resume/decrease/increase/enable&disable loop buttons.
   */
  private class UtilPanel extends JPanel {
    int tick = 0;
    int delay = 1000;
    boolean loop = false;


    /**
     * Constructs a UtilPanel.
     */
    private UtilPanel() {
    }

    /**
     * Draws the current tick, loop back status and current timer's delay on the JPanel.
     *
     * @param g graphics
     */
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.drawString("Current Tick:" + " " + this.tick, 65, 240);
      g2d.drawString("Current Delay:" + " " + this.delay + "ms", 45, 260);
      g2d.drawString("Current Loop:" + " " + this.loop, 55, 280);
    }

    /**
     * Sets the tick to the current {@code t}.
     *
     * @param t current tick.
     */
    private void setTick(int t) {
      this.tick = t;
    }

    /**
     * Sets the delay to the current {@code delay}.
     *
     * @param d current delay
     */
    private void setDelay(int d) {
      this.delay = d;
    }

    /**
     * Sets the loop back status to the current {@code loop}.
     *
     * @param loop current loop back status.
     */
    private void setLoop(boolean loop) {
      this.loop = loop;
    }
  }
}
