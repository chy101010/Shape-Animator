import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewModel;
import cs3500.animator.view.SvgView;
import cs3500.animator.view.ViewModel;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

/**
 * Test cases for the SVG implementation. Verifying that the methods are functioning as intended.
 */
public class SVGTest {

  //testing: rate being equal to 0 (implies not positive) will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testZeroRateError() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    IView view = new SvgView(model, new StringBuilder(), 0);
  }

  //testing: rate being negative will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRateError() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    IView view = new SvgView(model, new StringBuilder(), -23);
  }

  //testing: having a null appendable will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableError() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    IView view = new SvgView(model, null, 2);
  }

  //testing: having a null IViewModel will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelError() {
    IView view = new SvgView(null, new StringBuilder(), 3);
  }

  //testing: display method of SVG's update on an empty model input
  @Test
  public void testDisplayOnEmptyModel() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    StringBuilder builder = new StringBuilder();
    IView view = new SvgView(model, builder, 2);
    view.display();
    assertEquals(new StringBuilder("<svg width=\"0\" height=\"0\" version=\"1.1\" \n"
            + " \txmlns=\"http://www.w3.org/2000/svg\" >\n"
            + "<rect x=\"0\" y=\"0\" width=\"0\" height=\"0\" fill=\"none\" stroke=\"black\" "
            + "stroke-width=\"1\" /> \n"
            + "\n"
            + "</svg>").toString(), builder.toString());

  }

  //testing: display method of SVG's update on a non-empty model input
  @Test
  public void testDisplayOnNonEmptyModel() {
    IShape rectangle = new RectangleImpl(300, 300,
            50, 100, 255, 0, 0,0);
    IShape rectangle1 = new RectangleImpl(200, 200,
            25, 100, 255, 0, 0,0);
    IShape rectangle2 = new RectangleImpl(400, 200,
            25, 100, 255, 0, 0,0);
    IKeyFrame tick = new KeyFrame(50, rectangle);
    IKeyFrame tick1 = new KeyFrame(100, rectangle1);
    IKeyFrame tick2 = new KeyFrame(500, rectangle2);
    // Constructing the AnimatorModel
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("R", "rectangle",0);
    model.addFrame("R", tick);
    model.addFrame("R", tick1);
    model.addFrame("R", tick2);
    // Wrapping it into a IViewModel
    IViewModel viewModel = new ViewModel(model);
    StringBuilder builder = new StringBuilder();
    // Creates Textual View
    IView view = new SvgView(viewModel, builder, 10);
    view.display();
    assertEquals("<svg width=\"0\" height=\"0\" version=\"1.1\" \n"
                    + " \txmlns=\"http://www.w3.org/2000/svg\" >\n"
                    + "<rect x=\"0\" y=\"0\" width=\"0\" height=\"0\" fill=\"none\" "
                    + "stroke=\"black\" stroke-width=\"1\" /> \n"
                    + "\n"
                    + "<rect id=\"R\" x=\"300\" y=\"300\" width=\"50\" height=\"100\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" > \n"
                    + " \t <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" "
                    + "begin=\"5000ms\" fill=\"freeze\" /> \n"
                    + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"5000ms\" "
                    + "attributeName=\"x\" from=\"300\" to=\"200\" fill=\"freeze\" /> \n"
                    + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"5000ms\" "
                    + "attributeName=\"y\" from=\"300\" to=\"200\" fill=\"freeze\" /> \n"
                    + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"5000ms\" "
                    + "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" /> \n"
                    + "\t<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"40000ms\" "
                    + "attributeName=\"x\" from=\"200\" to=\"400\" fill=\"freeze\" /> \n"
                    + "\t <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"hidden\" "
                    + "begin=\"50000ms\" fill=\"freeze\" /> \n"
                    + "</rect>\n"
                    + "</svg>",
            builder.toString());
  }

  @Test
  public void testDisplayUsingFile() throws FileNotFoundException {
    AnimatorModel model = AnimationReader.parseFile(new FileReader("pop.txt"),
            new SimpleModelImpl.Builder());
    IViewModel viewModel = new ViewModel(model);
    StringBuilder builder = new StringBuilder();
    IView view = new SvgView(viewModel, builder, 100);
    view.display();
    assertEquals("<svg width=\"360\" height=\"360\" version=\"1.1\" \n" +
                    " \txmlns=\"http://www.w3.org/2000/svg\" >\n" +
                    "<rect x=\"0\" y=\"0\" width=\"360\" height=\"360\" fill=\"none\"" +
                    " stroke=\"black\" stroke-width=\"1\" /> \n" +
                    "\n" +
                    "<rect id=\"R\" x=\"0\" y=\"130\" width=\"50\" height=\"100\" " +
                    "fill=\"rgb(255,0,0)\" visibility=\"hidden\" > \n" +
                    " \t <set attributeName=\"visibility\" attributeType=\"CSS\"" +
                    " to=\"visible\" begin=\"10ms\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms\" " +
                    "attributeName=\"x\" from=\"0\" to=\"240\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms\" " +
                    "attributeName=\"y\" from=\"130\" to=\"0\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"100\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"100ms\" dur=\"400ms\" " +
                    "attributeName=\"y\" from=\"0\" to=\"180\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"100ms\" dur=\"400ms\" " +
                    "attributeName=\"width\" from=\"100\" to=\"50\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"10ms\" " +
                    "attributeName=\"y\" from=\"180\" to=\"300\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"510ms\" dur=\"190ms\"" +
                    " attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" /> \n" +
                    "\t <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"hidden\" " +
                    "begin=\"1100ms\" fill=\"freeze\" /> \n" +
                    "</rect>\n" +
                    "<ellipse id=\"C\" cx=\"30\" cy=\"180\" rx=\"30\" ry=\"50\" " +
                    "fill=\"rgb(0,255,0)\" visibility=\"hidden\" > \n" +
                    " \t <set attributeName=\"visibility\" attributeType=\"CSS\"" +
                    " to=\"visible\" begin=\"10ms\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms\" " +
                    "attributeName=\"cx\" from=\"30\" to=\"270\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms\" " +
                    "attributeName=\"cy\" from=\"180\" to=\"50\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"100ms\" dur=\"400ms\"" +
                    " attributeName=\"cy\" from=\"50\" to=\"230\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"10ms\"" +
                    " attributeName=\"cy\" from=\"230\" to=\"350\" fill=\"freeze\" /> \n" +
                    "\t <set attributeName=\"visibility\" attributeType=\"CSS\" " +
                    "to=\"hidden\" begin=\"1100ms\" fill=\"freeze\" /> \n" +
                    "</ellipse>\n" +
                    "<ellipse id=\"C3\" cx=\"30\" cy=\"60\" rx=\"30\" ry=\"50\" " +
                    "fill=\"rgb(255,0,255)\" visibility=\"hidden\" > \n" +
                    " \t <set attributeName=\"visibility\" attributeType=\"CSS\"" +
                    " to=\"visible\" begin=\"10ms\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms\" " +
                    "attributeName=\"cx\" from=\"30\" to=\"270\" fill=\"freeze\" /> \n" +
                    "\t<animateTransform attributeName=\"transform\" attributeTy" +
                    "pe=\"XML\" type=\"rotate\" from=\"0 30 60\" to=\"90 270 60\"" +
                    " begin=\"10ms\" dur=\"90ms\" />\n" +
                    "\t<animateTransform attributeName=\"transform\" attributeType" +
                    "=\"XML\" type=\"rotate\" from=\"90 270 60\" to=\"0 270 60\" beg" +
                    "in=\"100ms\" dur=\"400ms\" />\n" +
                    "\t<animateTransform attributeName=\"transform\" attributeType=\"" +
                    "XML\" type=\"rotate\" from=\"0 270 60\" to=\"-360 270 60\" be" +
                    "gin=\"510ms\" dur=\"190ms\" />\n" +
                    "\t<animateTransform attributeName=\"transform\" attributeType" +
                    "=\"XML\" type=\"rotate\" from=\"-360 270 60\" to=\"50 27" +
                    "0 60\" begin=\"700ms\" dur=\"400ms\" />\n" +
                    "\t <set attributeName=\"visibility\" attributeType=\"CSS\" t" +
                    "o=\"hidden\" begin=\"1100ms\" fill=\"freeze\" /> \n" +
                    "</ellipse>\n" +
                    "<ellipse id=\"C1\" cx=\"30\" cy=\"80\" rx=\"30\" ry=\"50\" fi" +
                    "ll=\"rgb(0,0,0)\" visibility=\"hidden\" > \n" +
                    " \t <set attributeName=\"visibility\" attributeType=\"CSS\" " +
                    "to=\"visible\" begin=\"10ms\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=\"90ms" +
                    "\" attributeName=\"cx\" from=\"30\" to=\"270\" fill=\"freeze\" /> \n" +
                    "\t <set attributeName=\"visibility\" attributeType=\"CSS\"" +
                    " to=\"hidden\" begin=\"1100ms\" fill=\"freeze\" /> \n" +
                    "</ellipse>\n" +
                    "<ellipse id=\"C2\" cx=\"30\" cy=\"70\" rx=\"30\" ry=" +
                    "\"50\" fill=\"rgb(255,0,0)\" visibility=\"hidden\" > \n" +
                    " \t <set attributeName=\"visibility\" attributeType" +
                    "=\"CSS\" to=\"visible\" begin=\"10ms\" fill=\"freeze\" /> \n" +
                    "\t<animate attributeType=\"xml\" begin=\"10ms\" dur=" +
                    "\"90ms\" attributeName=\"cx\" from=\"30\" to=\"270\" fill=\"freeze\" /> \n" +
                    "\t <set attributeName=\"visibility\" attributeType" +
                    "=\"CSS\" to=\"hidden\" begin=\"1100ms\" fill=\"freeze\" /> \n" +
                    "</ellipse>\n" +
                    "</svg>",
            builder.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void appendFailed() {
    IShape rectangle = new RectangleImpl(300, 300, 50, 100, 255,
            0, 0,0);
    IKeyFrame tick = new KeyFrame(50, rectangle);
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("R", "rectangle",0);
    IViewModel viewModel = new ViewModel(model);
    IView view = new SvgView(viewModel, new FailingAppendable(), 10);
    view.display();
  }

}
