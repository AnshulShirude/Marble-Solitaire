package cs3500.marblesolitaire.controller;

import java.io.InputStreamReader;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * Represents the main class which is necessary to run the game. This is the class that contains
 * the main method necessary to create a controller from the model and the view.
 */
public class RunGame {

  /**
   * Represents the main method necessary to run the game.
   * @param args represents the arguments of the main method.
   */
  public static void main(String[] args) {

    // model and view
    MarbleSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireView view = new MarbleSolitaireTextView(model);

    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(model, view,
            new InputStreamReader(System.in));

    controller.playGame();
  }
}