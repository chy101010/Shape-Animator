package cs3500.animator.controller;

/**
 * Represents the interactive controller for IEditView. The implements of this interface will serves
 * as the medium between IEditView and AnimatorModel, which means it will control the data requests
 * flow between an IEditView and an AnimatorModel. This interface offers a method that allows the
 * user to start an user interactive visualization.
 */
public interface IController {

  /**
   * Initialize the user interactive visualization.
   */
  void play();
}
