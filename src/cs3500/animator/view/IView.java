package cs3500.animator.view;

/**
 * This interface represent the view implementations of the Animator. It displays the visualization
 * of a AnimatorModel using the data provided by the AnimatorModel.
 */
public interface IView {

  /**
   * Renders the visualization of the AnimatorModel in this IView. The type of visualization is
   * delegated to the class that implements this interface.
   */
  void display();
}
