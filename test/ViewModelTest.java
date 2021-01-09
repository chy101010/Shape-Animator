import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EllipseImpl;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.RectangleImpl;
import cs3500.animator.model.SimpleModelImpl;
import cs3500.animator.view.IViewModel;
import cs3500.animator.view.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Test cases for the IViewModel Interface. Verifying that the methods are functioning as intended.
 */
public class ViewModelTest {

  //testing: having a null IViewModel will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelError() {
    IViewModel viewModel = new ViewModel(null);
  }

  //testing: getShapes method of ViewModel's update on an empty model input
  @Test
  public void testGetShapesOnEmptyModel() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    assertEquals(Arrays.asList(), model.getShapes());
  }

  //testing: display method of ViewModel's update on a non-empty model input
  @Test
  public void testGetShapesOnNonEmptyModel() {
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("hello", "hello", 0);
    IViewModel viewModel = new ViewModel(model);
    assertEquals(new ArrayList<>(Arrays.asList("hello")), viewModel.getShapes());
    model.addShapes("h", "hello", 0);
    IViewModel viewModel1 = new ViewModel(model);
    assertEquals(new ArrayList<>(Arrays.asList("hello", "h")), viewModel1.getShapes());
    model.addShapes("he", "hello", 0);
    IViewModel viewModel2 = new ViewModel(model);
    assertEquals(new ArrayList<>(Arrays.asList("hello", "h", "he")), viewModel2.getShapes());
  }


  //testing: getShape at of ViewModel has to place IKeyFrames in order
  @Test
  public void getShapeAt() {
    AnimatorModel mod = new SimpleModelImpl();
    IShape rectangleOne = new RectangleImpl(200, 200,
            50, 100, 255, 0, 0,0 );

    IShape rectangleFour = new RectangleImpl(300, 300,
            50, 100, 255, 0, 0, 0);

    IKeyFrame tick100 = new KeyFrame(100, rectangleOne);
    IKeyFrame tick200 = new KeyFrame(200, rectangleFour);
    mod.addShapes("R", "Rectangle", 0);
    mod.addFrame("R", tick100);
    mod.addFrame("R", tick200);
    IViewModel ivm = new ViewModel(mod);
    assertEquals(rectangleOne, ivm.getShapeAt("R", 100));
    assertEquals(rectangleFour, ivm.getShapeAt("R", 200));

    assertNull(mod.getShapeAt("R", 210));
    assertNull(mod.getShapeAt("R", 99));

    // Position Testing
    IShape inBetween = new RectangleImpl(250, 250,
            50, 100, 255, 0, 0,0);
    IShape inBetween1 = new RectangleImpl(290, 290,
            50, 100, 255, 0, 0,0);
    ivm = new ViewModel(mod);
    assertEquals(inBetween, ivm.getShapeAt("R", 150));
    assertEquals(inBetween1, ivm.getShapeAt("R", 190));

    // More Test on Positions, Colors, and Dimensions
    IShape rectangle = new RectangleImpl(250, 210,
            24, 170, 255, 20, 90,0);
    IShape rectangle1 = new RectangleImpl(800, 1010,
            24, 100, 200, 210, 100,0);
    IKeyFrame tick240 = new KeyFrame(240, rectangle);
    IKeyFrame tick300 = new KeyFrame(300, rectangle1);
    mod.addFrame("R", tick240);
    mod.addFrame("R", tick300);
    IShape rectangle3 = new RectangleImpl(625, 756,
            24, 122, 217, 149, 96,0);
    ivm = new ViewModel(mod);
    assertEquals(rectangle, ivm.getShapeAt("R", 240));
    assertEquals(rectangle1, ivm.getShapeAt("R", 300));
    assertEquals(rectangle3, ivm.getShapeAt("R", 281));
  }

  //testing: trying to get a shape that doesn't exist will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void getShapeAt3() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel ivm = new ViewModel(mod);
    ivm.getShapeAt("R", 10);
  }

  //trying to get a shape at a tick that doesn't exist will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void getShapeAt4() {
    AnimatorModel mod = new SimpleModelImpl();

    mod.addShapes("O", "Ellipse",0);
    IShape ovalOne = new EllipseImpl(440, 70, 120,
            60, 0, 0, 255,0);
    IKeyFrame tickO6 = new KeyFrame(6, ovalOne);
    mod.addFrame("O", tickO6);
    IViewModel ivm = new ViewModel(mod);
    ivm.getShapeAt("R", 0);
  }


  //trying to get a shape at a tick that doesn't exist will cause IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void getShapeAt5() {
    // Invalid tick
    AnimatorModel mod = new SimpleModelImpl();
    mod.addShapes("O", "Ellipse",0);
    IShape ovalOne = new EllipseImpl(440, 70, 120,
            60, 0, 0, 255,0);
    IKeyFrame tickO6 = new KeyFrame(6, ovalOne);
    mod.addFrame("O", tickO6);
    IViewModel ivm = new ViewModel(mod);
    ivm.getShapeAt("O", -34);
  }

  //testing: getShape at of ViewModel to get the bounds, width and height
  @Test
  public void testGetViews() {
    List<String> identities = Collections.singletonList("R");
    AnimatorModel model = new SimpleModelImpl();
    model.setBounds(13, 23, 65, 87);
    IViewModel ivm = new ViewModel(model);
    int[] views = ivm.getViews();
    assertEquals(13, views[0]);
    assertEquals(23, views[1]);
    assertEquals(65, views[2]);
    assertEquals(87, views[3]);
  }

  //testing: to check if the animation is over
  @Test
  public void testIsAnimationOver() {
    AnimatorModel animate = new SimpleModelImpl();
    IViewModel ivm = new ViewModel(animate);
    assertTrue(ivm.isAnimationOver(1));
    assertTrue(ivm.isAnimationOver(2));
    IShape ovalOne = new EllipseImpl(440, 70, 120,
            60, 0, 0, 255,0);
    IKeyFrame tickO6 = new KeyFrame(6, ovalOne);
    animate.addShapes("C", "Ellipse",0);
    animate.addFrame("C", tickO6);
    assertFalse(ivm.isAnimationOver(6));
    assertFalse(ivm.isAnimationOver(5));
    animate.addShapes("C2", "Ellipse",0);
    IShape oval3 = new EllipseImpl(440, 70, 120,
            60, 0, 0, 255,0);
    IKeyFrame tickO50 = new KeyFrame(50, oval3);
    animate.addFrame("C2", tickO50);
    assertFalse(ivm.isAnimationOver(49));
    assertTrue(ivm.isAnimationOver(51));
  }

  //testing: while checking if the animation is over or not, we can't have a negative time.
  @Test(expected = IllegalArgumentException.class)
  public void invalidIsAnimationOver1() {
    AnimatorModel mod = new SimpleModelImpl();
    IViewModel model = new ViewModel(mod);
    assertTrue(model.isAnimationOver(-1));
  }

  @Test
  public void testGetFramesFor() {
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("hello", "rectangle",0);
    IKeyFrame first = new KeyFrame(1,
            new RectangleImpl(2, 2, 2, 2, 2, 2, 2,0));
    model.addFrame("hello", first);
    assertEquals(new ArrayList<>(Arrays.asList(first)),
            model.getFramesFor("hello"));
    IViewModel view = new ViewModel(model);
    assertEquals(new ArrayList<>(Arrays.asList(first)),
            view.getFramesFor("hello"));
    IKeyFrame second = new KeyFrame(2,
            new RectangleImpl(3, 3, 3, 3, 3, 3, 3,0));
    IKeyFrame third = new KeyFrame(3,
            new RectangleImpl(4, 4, 4, 4, 4, 4, 4,0));
    model.addFrame("hello", second);
    model.addFrame("hello", third);
    assertEquals(new ArrayList<>(Arrays.asList(first, second, third)),
            model.getFramesFor("hello"));
    IViewModel view1 = new ViewModel(model);
    assertEquals(new ArrayList<>(Arrays.asList(first, second, third)),
            view1.getFramesFor("hello"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFramesFor() {
    // Identity doesn't exist
    AnimatorModel model = new SimpleModelImpl();
    IViewModel view = new ViewModel(model);
    view.getFramesFor("hello");
  }

  @Test
  public void testGetType() {
    AnimatorModel model = new SimpleModelImpl();
    model.addShapes("hello", "rectangle",0);
    model.addShapes("hello1", "oval",0);
    model.addShapes("hello2", "tri",0);
    model.addShapes("hello3", "oct",0);
    IViewModel view = new ViewModel(model);
    assertEquals("rectangle", view.getTypes("hello"));
    assertEquals("oval", view.getTypes("hello1"));
    assertEquals("tri", view.getTypes("hello2"));
    assertEquals("oct", view.getTypes("hello3"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetType() {
    // Identity doesn't exist
    AnimatorModel model = new SimpleModelImpl();
    IViewModel view = new ViewModel(model);
    view.getTypes("hello");
  }
}