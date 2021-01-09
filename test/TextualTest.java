import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewModel;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.ViewModel;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

/**
 * Test cases for the Textual implementation. Verifying that the methods are functioning as
 * intended.
 */
public class TextualTest {

  //testing: having a null appendable will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableError() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    IView view = new TextualView(model, null);
  }

  //testing: having a null IViewModel will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelError() {
    IView view = new TextualView(null, new StringBuilder());
  }

  //testing: having a null IViewModel and null appendable cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNullError1() {
    IView view = new TextualView(null, null);
  }

  //testing: display method of TextualView's update on an empty model input
  @Test
  public void testDisplayOnEmptyModel() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    StringBuilder builder = new StringBuilder();
    IView view = new TextualView(model, builder);
    view.display();
    assertEquals(new StringBuilder("canvas 0 0 0 0 " + "\n").toString(), builder.toString());
  }

  //testing: display method of TextualView's update on a non-empty model input
  @Test
  public void testDisplayOnNonEmptyModel() {
    IShape rectangle = new RectangleImpl(300, 300,
            50, 100, 255, 0, 0, 10);
    IShape rectangle1 = new RectangleImpl(200, 200,
            25, 100, 255, 0, 0, 10);
    IShape rectangle2 = new RectangleImpl(400, 200,
            25, 100, 255, 0, 0, 10);
    IKeyFrame tick = new KeyFrame(50, rectangle);
    IKeyFrame tick1 = new KeyFrame(100, rectangle1);
    IKeyFrame tick2 = new KeyFrame(500, rectangle2);
    // Constructing the AnimatorModel
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("R", "rectangle", 3);
    model.addFrame("R", tick);
    model.addFrame("R", tick1);
    model.addFrame("R", tick2);
    // Wrapping it into a IViewModel
    IViewModel viewModel = new ViewModel(model);
    StringBuilder builder = new StringBuilder();
    // Creates Textual View
    IView view = new TextualView(viewModel, builder);
    view.display();
    assertEquals("canvas 0 0 0 0 \n" +
                    "Shape R Rectangle Layer3\n" +
                    "motion R 50 300 300 50 100 255 0 0 10  100 200 200 25 100 255 0 0 10\n" +
                    "motion R 100 200 200 25 100 255 0 0 10  500 400 200 25 100 255 0 0 10",
            builder.toString());
    // More tests
    IShape ellipse = new EllipseImpl(10, 200,
            25, 100, 255, 0, 0, 0);
    IShape ellipse1 = new EllipseImpl(80, 200,
            20, 100, 25, 2, 0, 0);
    IShape ellipse2 = new EllipseImpl(85, 250,
            20, 100, 25, 20, 10, 0);
    IKeyFrame tick4 = new KeyFrame(50, ellipse);
    IKeyFrame tick5 = new KeyFrame(11, ellipse1);
    IKeyFrame tick6 = new KeyFrame(55, ellipse2);
    model.addShapes("C", "ellipse", 0);
    model.addFrame("C", tick4);
    model.addFrame("C", tick5);
    model.addFrame("C", tick6);
    // New IViewModel
    IViewModel viewModel1 = new ViewModel(model);
    // New StringBuilder
    StringBuilder builder1 = new StringBuilder();
    IView view1 = new TextualView(viewModel1, builder1);
    view1.display();
    assertEquals("canvas 0 0 0 0 \n" +
                    "Shape C Ellipse Layer0\n" +
                    "motion C 11 80 200 20 100 25 2 0 0  50 10 200 25 100 255 0 0 0\n" +
                    "motion C 50 10 200 25 100 255 0 0 0  55 85 250 20 100 25 20 10 0\n" +
                    "\n" +
                    "Shape R Rectangle Layer3\n" +
                    "motion R 50 300 300 50 100 255 0 0 10  100 200 200 25 100 255 0 0 10\n" +
                    "motion R 100 200 200 25 100 255 0 0 10  500 400 200 25 100 255 0 0 10",
            builder1.toString());
    IShape rectangle4 = new RectangleImpl(450, 200,
            25, 100, 255, 0, 0, 0);
    IShape rectangle5 = new RectangleImpl(400, 200,
            25, 100, 255, 200, 100, 0);
    IKeyFrame tick7 = new KeyFrame(50, rectangle);
    IKeyFrame tick8 = new KeyFrame(55, rectangle5);
    model.addShapes("O", "rectangle", 0);
    model.addFrame("O", tick7);
    model.addFrame("O", tick8);
    // New IViewModel
    IViewModel viewModel2 = new ViewModel(model);
    // New StringBuilder
    StringBuilder builder2 = new StringBuilder();
    IView view2 = new TextualView(viewModel1, builder2);
    view2.display();
    assertEquals("canvas 0 0 0 0 \n" +
                    "Shape C Ellipse Layer0\n" +
                    "motion C 11 80 200 20 100 25 2 0 0  50 10 200 25 100 255 0 0 0\n" +
                    "motion C 50 10 200 25 100 255 0 0 0  55 85 250 20 100 25 20 10 0\n" +
                    "\n" +
                    "Shape O Rectangle Layer0\n" +
                    "motion O 50 300 300 50 100 255 0 0 10  55 400 200 25 100 255 200 100 0\n" +
                    "\n" +
                    "Shape R Rectangle Layer3\n" +
                    "motion R 50 300 300 50 100 255 0 0 10  100 200 200 25 100 255 0 0 10\n" +
                    "motion R 100 200 200 25 100 255 0 0 10  500 400 200 25 100 255 0 0 10",
            builder2.toString());
  }

  @Test
  public void testDisplayUsingFile() throws FileNotFoundException {
    AnimatorModel model = AnimationReader.parseFile(new FileReader("pop.txt"),
            new SimpleModelImpl.Builder());
    IViewModel viewModel = new ViewModel(model);
    StringBuilder builder = new StringBuilder();
    IView view = new TextualView(viewModel, builder);
    view.display();
    assertEquals("canvas 200 70 360 360 \n" +
                    "Shape R Rectangle Layer1\n" +
                    "motion R 1 200 200 50 100 255 0 0 0  10 440 70 100 100 255 0 0 0\n" +
                    "motion R 10 440 70 100 100 255 0 0 0  50 440 250 50 100 255 0 0 0\n" +
                    "motion R 50 440 250 50 100 255 0 0 0  51 440 370 50 100 255 0 0 0\n" +
                    "motion R 51 440 370 50 100 255 0 0 0  70 440 370 25 100 255 0 0 0\n" +
                    "motion R 70 440 370 25 100 255 0 0 0  110 440 370 25 100 255 0 0 0\n" +
                    "\n" +
                    "Shape C Ellipse Layer2\n" +
                    "motion C 1 200 200 60 100 0 255 0 0  10 440 70 60 100 0 255 0 0\n" +
                    "motion C 10 440 70 60 100 0 255 0 0  50 440 250 60 100 0 255 0 0\n" +
                    "motion C 50 440 250 60 100 0 255 0 0  51 440 370 60 100 0 255 0 0\n" +
                    "motion C 51 440 370 60 100 0 255 0 0  70 440 370 60 100 0 255 0 0\n" +
                    "motion C 70 440 370 60 100 0 255 0 0  110 440 370 60 100 0 255 0 0\n" +
                    "\n" +
                    "Shape C3 Ellipse Layer3\n" +
                    "motion C3 1 200 80 60 100 255 0 255 0  10 440 80 60 100 255 0 255 90\n" +
                    "motion C3 10 440 80 60 100 255 0 255 90  50 440 80 60 100 255 0 255 0\n" +
                    "motion C3 50 440 80 60 100 255 0 255 0  51 440 80 60 100 255 0 255 0\n" +
                    "motion C3 51 440 80 60 100 255 0 255 0  70 440 80 60 100 255 0 255 -360\n" +
                    "motion C3 70 440 80 60 100 255 0 255 -360  110 440 80 60 100 255 0 255 50\n" +
                    "\n" +
                    "Shape C1 Ellipse Layer4\n" +
                    "motion C1 1 200 100 60 100 0 0 0 0  10 440 100 60 100 0 0 0 0\n" +
                    "motion C1 10 440 100 60 100 0 0 0 0  50 440 100 60 100 0 0 0 0\n" +
                    "motion C1 50 440 100 60 100 0 0 0 0  51 440 100 60 100 0 0 0 0\n" +
                    "motion C1 51 440 100 60 100 0 0 0 0  70 440 100 60 100 0 0 0 0\n" +
                    "motion C1 70 440 100 60 100 0 0 0 0  110 440 100 60 100 0 0 0 0\n" +
                    "\n" +
                    "Shape C2 Ellipse Layer5\n" +
                    "motion C2 1 200 90 60 100 255 0 0 0  10 440 90 60 100 255 0 0 0\n" +
                    "motion C2 10 440 90 60 100 255 0 0 0  50 440 90 60 100 255 0 0 0\n" +
                    "motion C2 50 440 90 60 100 255 0 0 0  51 440 90 60 100 255 0 0 0\n" +
                    "motion C2 51 440 90 60 100 255 0 0 0  70 440 90 60 100 255 0 0 0\n" +
                    "motion C2 70 440 90 60 100 255 0 0 0  110 440 90 60 100 255 0 0 0",
            builder.toString());
  }

  @Test
  public void testDisplayUsingFile1() throws FileNotFoundException {
    AnimatorModel model = AnimationReader.parseFile(new FileReader("toh-3.txt"),
            new SimpleModelImpl.Builder());
    IViewModel viewModel = new ViewModel(model);
    StringBuilder builder = new StringBuilder();
    IView view = new TextualView(viewModel, builder);
    view.display();
    assertEquals("canvas 145 50 410 220 \n" +
                    "Shape disk1 Rectangle Layer0\n" +
                    "motion disk1 1 190 180 20 30 0 49 90 0  25 190 180 20 30 0 49 90 0\n" +
                    "motion disk1 25 190 180 20 30 0 49 90 0  35 190 50 20 30 0 49 90 0\n" +
                    "motion disk1 35 190 50 20 30 0 49 90 0  36 190 50 20 30 0 49 90 0\n" +
                    "motion disk1 36 190 50 20 30 0 49 90 0  46 490 50 20 30 0 49 90 0\n" +
                    "motion disk1 46 490 50 20 30 0 49 90 0  47 490 50 20 30 0 49 90 0\n" +
                    "motion disk1 47 490 50 20 30 0 49 90 0  57 490 240 20 30 0 49 90 0\n" +
                    "motion disk1 57 490 240 20 30 0 49 90 0  89 490 240 20 30 0 49 90 0\n" +
                    "motion disk1 89 490 240 20 30 0 49 90 0  99 490 50 20 30 0 49 90 0\n" +
                    "motion disk1 99 490 50 20 30 0 49 90 0  100 490 50 20 30 0 49 90 0\n" +
                    "motion disk1 100 490 50 20 30 0 49 90 0  110 340 50 20 30 0 49 90 0\n" +
                    "motion disk1 110 340 50 20 30 0 49 90 0  111 340 50 20 30 0 49 90 0\n" +
                    "motion disk1 111 340 50 20 30 0 49 90 0  121 340 210 20 30 0 49 90 0\n" +
                    "motion disk1 121 340 210 20 30 0 49 90 0  153 340 210 20 30 0 49 90 0\n" +
                    "motion disk1 153 340 210 20 30 0 49 90 0  163 340 50 20 30 0 49 90 0\n" +
                    "motion disk1 163 340 50 20 30 0 49 90 0  164 340 50 20 30 0 49 90 0\n" +
                    "motion disk1 164 340 50 20 30 0 49 90 0  174 190 50 20 30 0 49 90 0\n" +
                    "motion disk1 174 190 50 20 30 0 49 90 0  175 190 50 20 30 0 49 90 0\n" +
                    "motion disk1 175 190 50 20 30 0 49 90 0  185 190 240 20 30 0 49 90 0\n" +
                    "motion disk1 185 190 240 20 30 0 49 90 0  217 190 240 20 30 0 49 90 0\n" +
                    "motion disk1 217 190 240 20 30 0 49 90 0  227 190 50 20 30 0 49 90 0\n" +
                    "motion disk1 227 190 50 20 30 0 49 90 0  228 190 50 20 30 0 49 90 0\n" +
                    "motion disk1 228 190 50 20 30 0 49 90 0  238 490 50 20 30 0 49 90 0\n" +
                    "motion disk1 238 490 50 20 30 0 49 90 0  239 490 50 20 30 0 49 90 0\n" +
                    "motion disk1 239 490 50 20 30 0 49 90 0  249 490 180 20 30 0 49 90 0\n" +
                    "motion disk1 249 490 180 20 30 0 49 90 0  257 490 180 20 30 0 255 0 0\n" +
                    "motion disk1 257 490 180 20 30 0 255 0 0  302 490 180 20 30 0 255 0 0\n" +
                    "\n" +
                    "Shape disk2 Rectangle Layer0\n" +
                    "motion disk2 1 167 210 65 30 6 247 41 0  57 167 210 65 30 6 247 41 0\n" +
                    "motion disk2 57 167 210 65 30 6 247 41 0  67 167 50 65 30 6 247 41 0\n" +
                    "motion disk2 67 167 50 65 30 6 247 41 0  68 167 50 65 30 6 247 41 0\n" +
                    "motion disk2 68 167 50 65 30 6 247 41 0  78 317 50 65 30 6 247 41 0\n" +
                    "motion disk2 78 317 50 65 30 6 247 41 0  79 317 50 65 30 6 247 41 0\n" +
                    "motion disk2 79 317 50 65 30 6 247 41 0  89 317 240 65 30 6 247 41 0\n" +
                    "motion disk2 89 317 240 65 30 6 247 41 0  185 317 240 65 30 6 247 41 0\n" +
                    "motion disk2 185 317 240 65 30 6 247 41 0  195 317 50 65 30 6 247 41 0\n" +
                    "motion disk2 195 317 50 65 30 6 247 41 0  196 317 50 65 30 6 247 41 0\n" +
                    "motion disk2 196 317 50 65 30 6 247 41 0  206 467 50 65 30 6 247 41 0\n" +
                    "motion disk2 206 467 50 65 30 6 247 41 0  207 467 50 65 30 6 247 41 0\n" +
                    "motion disk2 207 467 50 65 30 6 247 41 0  217 467 210 65 30 6 247 41 0\n" +
                    "motion disk2 217 467 210 65 30 6 247 41 0  225 467 210 65 30 0 255 0 0\n" +
                    "motion disk2 225 467 210 65 30 0 255 0 0  302 467 210 65 30 0 255 0 0\n" +
                    "\n" +
                    "Shape disk3 Rectangle Layer0\n" +
                    "motion disk3 1 145 240 110 30 11 45 175 0  121 145 240 110 30 11 45 175 0\n" +
                    "motion disk3 121 145 240 110 30 11 45 175 0  131 145 50 110 30 11 45 175 0\n" +
                    "motion disk3 131 145 50 110 30 11 45 175 0  132 145 50 110 30 11 45 175 0\n" +
                    "motion disk3 132 145 50 110 30 11 45 175 0  142 445 50 110 30 11 45 175 0\n" +
                    "motion disk3 142 445 50 110 30 11 45 175 0  143 445 50 110 30 11 45 175 0\n" +
                    "motion disk3 143 445 50 110 30 11 45 175 0  153 445 240 110 30 11 45 175 0\n" +
                    "motion disk3 153 445 240 110 30 11 45 175 0  161 445 240 110 30 0 255 0 0\n" +
                    "motion disk3 161 445 240 110 30 0 255 0 0  302 445 240 110 30 0 255 0 0",
            builder.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testFailAppend() {
    IShape rectangle = new RectangleImpl(300, 300,
            50, 100, 255, 0, 0, 0);
    IKeyFrame tick = new KeyFrame(50, rectangle);
    // Constructing the AnimatorModel
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("R", "rectangle", 0);
    model.addFrame("R", tick);
    // Wrapping it into a IViewModel
    IViewModel viewModel = new ViewModel(model);
    Appendable builder = new FailingAppendable();
    // Creates Textual View
    IView view = new TextualView(viewModel, builder);
    view.display();
  }
}
