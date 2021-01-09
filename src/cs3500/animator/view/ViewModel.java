package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IKeyFrame;
import cs3500.animator.model.IShape;

/**
 * Represent the view only model of SimpleModelImpl. It composes an instance of SimpleModelImpl, and
 * delegates will all the functionalities to that instance.
 */
public final class ViewModel implements IViewModel {
  private AnimatorModel model;

  /**
   * Constructs a ViewModel with the given {@code model}.
   *
   * @param model an Animator Model
   * @throws IllegalArgumentException if the given {@code model} is null
   */
  public ViewModel(AnimatorModel model) {
    if (model == null) {
      throw new IllegalArgumentException("The AnimatorModel can't be null");
    }
    this.model = model;
  }

  @Override
  public List<String> getShapes() {
    return this.model.getShapes();
  }

  @Override
  public IShape getShapeAt(String identity, int t) {
    return this.model.getShapeAt(identity, t);
  }

  @Override
  public int[] getViews() {
    return this.model.getViews();
  }

  @Override
  public boolean isAnimationOver(int t) {
    return this.model.isAnimationOver(t);
  }

  @Override
  public List<IKeyFrame> getFramesFor(String identity) {
    return this.model.getFramesFor(identity);
  }

  @Override
  public String getTypes(String identity) {
    return this.model.getTypes(identity);
  }

  @Override
  public int getLayer(String identity) {
    return this.model.getLayer(identity);
  }
}
