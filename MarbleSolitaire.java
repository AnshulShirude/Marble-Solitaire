package cs3500.marblesolitaire;

import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

/**
 * Represents the MarbleSolitaire class which is used to run the main. This is the final class in
 * which you are essentially creating the main and based on your specifications that you desire,
 * you are creating the controller and playing the game.
 */
public final class MarbleSolitaire {

  /**
   * Main method to run the program. This chooses between the different commands and executes
   * them accordingly as specified in the different cases and in the String[] args.
   * @param args represents the String[] arguments.
   */
  public static void main(String[] args) {

    MarbleSolitaireModel english = new EnglishSolitaireModel();
    MarbleSolitaireModel triangle = new TriangleSolitaireModel();
    MarbleSolitaireModel euro = new EuropeanSolitaireModel();

    MarbleSolitaireView englishView = new MarbleSolitaireTextView(english, System.out);
    MarbleSolitaireView triangleView = new TriangleSolitaireTextView(triangle, System.out);
    MarbleSolitaireView euroView = new MarbleSolitaireTextView(euro, System.out);

    Readable rd = new InputStreamReader(System.in);

    MarbleSolitaireController controllerEnglish = new MarbleSolitaireControllerImpl(english,
            englishView, rd);
    MarbleSolitaireController controllerTriangle = new MarbleSolitaireControllerImpl(triangle,
            triangleView, rd);
    MarbleSolitaireController controllerEuro = new MarbleSolitaireControllerImpl(euro,
            euroView, rd);

    MarbleSolitaireModel model;
    MarbleSolitaireView view;
    MarbleSolitaireController controller;

    switch (args[0]) {
      case "english":
        if (args.length == 1) {
          controllerEnglish.playGame();
        }
        if (args.length >= 3 && args[1].equals("-size")) {
          if (args.length >= 5 && args[3].equals("-hole")) {
            model = new EnglishSolitaireModel(Integer.parseInt(args[2]),
                    Integer.parseInt(args[4]), Integer.parseInt(args[5]));
          } else {
            model = new EnglishSolitaireModel(Integer.parseInt(args[2]));
          }
          view = new MarbleSolitaireTextView(model);
          controller = new MarbleSolitaireControllerImpl(model, view, rd);
          controller.playGame();
        }
        if (args.length >= 4 && args[1].equals("-hole")) {
          if (args.length >= 6 && args[4].equals("-size")) {
            model = new EnglishSolitaireModel(Integer.parseInt(args[5]),
                    Integer.parseInt(args[2]), Integer.parseInt(args[3]));
          } else {
            model = new EnglishSolitaireModel(Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]));
          }
          view = new MarbleSolitaireTextView(model);
          controller = new MarbleSolitaireControllerImpl(model, view, rd);
          controller.playGame();
        }
        break;
      case "triangular":
        if (args.length == 1) {
          controllerTriangle.playGame();
        }
        if (args.length >= 3 && args[1].equals("-size")) {
          if (args.length >= 5 && args[3].equals("-hole")) {
            model = new TriangleSolitaireModel(Integer.parseInt(args[2]), Integer.parseInt(args[4]),
                    Integer.parseInt(args[5]));
          } else {
            model = new TriangleSolitaireModel(Integer.parseInt(args[2]));
          }
          view = new TriangleSolitaireTextView(model);
          controller = new MarbleSolitaireControllerImpl(model, view, rd);
          controller.playGame();
        } else if (args.length >= 3 && args[1].equals("-hole")) {
          if (args.length >= 5 && args[4].equals("-size")) {
            model = new TriangleSolitaireModel(Integer.parseInt(args[5]), Integer.parseInt(args[2])
                    , Integer.parseInt(args[3]));
          } else {
            model = new TriangleSolitaireModel(Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]));
          }
          view = new TriangleSolitaireTextView(model);
          controller = new MarbleSolitaireControllerImpl(model, view, rd);
          controller.playGame();
        }
        break;
      case "european":
        if (args.length == 1) {
          controllerEuro.playGame();
        }
        if (args.length >= 3 && args[1].equals("-size")) {
          if (args.length >= 5 && args[3].equals("-hole")) {
            model = new EuropeanSolitaireModel(Integer.parseInt(args[2]), Integer.parseInt(args[4]),
                    Integer.parseInt(args[5]));
          } else {
            model = new EuropeanSolitaireModel(Integer.parseInt(args[2]));
          }
          view = new MarbleSolitaireTextView(model);
          controller = new MarbleSolitaireControllerImpl(model, view, rd);
          controller.playGame();
        } else if (args.length >= 3 && args[1].equals("-hole")) {
          if (args.length >= 5 && args[4].equals("-size")) {
            model = new EuropeanSolitaireModel(Integer.parseInt(args[5]), Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]));
          } else {
            model = new EuropeanSolitaireModel(Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]));
          }
          view = new MarbleSolitaireTextView(model);
          controller = new MarbleSolitaireControllerImpl(model, view, rd);
          controller.playGame();
        }
        break;
      default :
        break;
    }
  }
}