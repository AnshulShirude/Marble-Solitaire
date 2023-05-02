package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * Represents the MarbleSolitaireControllerImpl class that implements all of the methods of the
 * MarbleSolitaireController interface. This class will hold the methods of the interface.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final MarbleSolitaireModel msm;
  private final MarbleSolitaireView msv;
  private final Readable rd;

  /**
   * Represents the MarbleSolitaireControllerImpl constructor that instantiates the
   * MarbleSolitaireControllerImpl if any of the arguments are null.
   *
   * @param msm represents the MarbleSolitaireModel.
   * @param msv represents the MarbleSolitaireView.
   * @param rd  represents the Readable.
   * @throws IllegalArgumentException in the case that one of the parameters are null.
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel msm, MarbleSolitaireView msv,
                                       Readable rd) throws IllegalArgumentException {
    if (msm == null || msv == null || rd == null) {
      throw new IllegalArgumentException("The MarbleSolitaireModel, MarbleSolitaireView, or the " +
              "Readable object is null.");
    }
    this.msm = msm;
    this.msv = msv;
    this.rd = rd;
  }

  /**
   * This method is used to run the game. By declaring this method, you are running the game for
   * the user.
   */
  @Override
  public void playGame() throws IllegalStateException {
    Scanner s = new Scanner(this.rd);
    String[] inputsForMove = new String[4];

    try {
      while (!msm.isGameOver()) {
        this.msv.renderBoard();
        this.msv.renderMessage("Score: " + this.msm.getScore() + "\n");

        try {
          int i = 0;
          int sizeofInputs = inputsForMove.length;
          while (i < sizeofInputs) {
            String nextVal = s.next();
            if (isValueNumber(nextVal)) {
              inputsForMove[i] = nextVal;
              i = i + 1;
            } else if (nextVal.equalsIgnoreCase("q")) {
              quitGame();
              return;
            } else {
              this.msv.renderMessage("Please enter a valid number. Try again.\n");
            }
          }
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("There is no such element: " + e);
        }
        try {
          this.msm.move(Integer.parseInt(inputsForMove[0]) - 1,
                  Integer.parseInt(inputsForMove[1]) - 1,
                  Integer.parseInt(inputsForMove[2]) - 1,
                  Integer.parseInt(inputsForMove[3]) - 1);
        } catch (IllegalArgumentException e) {
          this.msv.renderMessage("Invalid move. Play again: " + e + "\n");
        }
      }
      this.msv.renderMessage("Game over!\n");
      this.msv.renderBoard();
      this.msv.renderMessage("Score: " + this.msm.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("IO Exception: " + e);
    }

  }

  private void quitGame() {
    try {
      this.msv.renderMessage("Game quit!\n");
      this.msv.renderMessage("State of game when quit:\n");
      this.msv.renderBoard();
      this.msv.renderMessage("Score: " + this.msm.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Game Quitting exception: " + e);
    }
  }

  private boolean isValueNumber(String nextVal) {
    try {
      int num = Integer.parseInt(nextVal);
      return (num > 0);
    } catch (NumberFormatException e) {
      return false;
    }
  }
}

