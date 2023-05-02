package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * This represents the MarbleSolitaireTextView class that implements the MarbleSolitaireView
 * interface. It is used to see the view of the marbles that are present on the board with the
 * appropriate methods.
 */
public class MarbleSolitaireTextView extends AbstractView implements MarbleSolitaireView {
  private final MarbleSolitaireModelState msms;

  /**
   * This represents the MarbleSolitaireTextView constructor that initializes the private
   * variable to the MarbleSolitaireModelState variable that is passed in to the argument of the
   * constructor.
   *
   * @param msms is the argument that is used to represent the functionality of the
   *             MarbleSolitaireModelState class so that it can be used to view the baord in this
   *             class.
   * @throws IllegalArgumentException in the case that the msms is null.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState msms) throws IllegalArgumentException {
    this(msms, System.out);
  }

  /**
   * This represents the MarbleSolitaireTextView constructor which instantiates the variables in
   * the case that they are not null.
   *
   * @param msms is the argument that is used to represent the functionality of the
   *              MarbleSolitaireModelState class so that it can be used to view the baord in this
   *              class.
   * @param ap is the argument that is used to represent the Appendable which puts everything
   *           together.
   * @throws IllegalArgumentException in the case that the msms is null.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState msms, Appendable ap) throws
          IllegalArgumentException {
    super(ap);
    if (msms == null) {
      throw new IllegalArgumentException("The MarbleSolitaireModelState or the appendable object " +
              "were null.");
    }
    this.msms = msms;
  }

  /**
   * Return a string that represents the current state of the board. The
   * string should have one line per row of the game board. Each slot on the
   * game board is a single character (O, _ or space for a marble, empty and
   * invalid position respectively). Slots in a row should be separated by a
   * space. Each row has no space before the first slot and after the last slot.
   * @return the game state as a string
   */
  @Override
  public String toString() {
    int totalLength = this.msms.getBoardSize();
    int leftsRowCol = totalLength / 2 - 1;
    int rightsRowCol = totalLength - leftsRowCol;

    String answer = "";
    for (int i = 0; i < totalLength; i = i + 1) {
      String rowAnswer = "";
      String rowAnswerStripped = "";

      for (int j = 0; j < totalLength; j = j + 1) {
        MarbleSolitaireModelState.SlotState currState = this.msms.getSlotAt(i, j);
        String add;
        if ((currState.equals(MarbleSolitaireModelState.SlotState.Invalid)) && j >= rightsRowCol) {
          add = "";
        } else if (currState.equals(MarbleSolitaireModelState.SlotState.Marble)) {
          add = "O ";
        } else if (currState.equals(MarbleSolitaireModelState.SlotState.Empty)) {
          add = "_ ";
        } else {
          add = "  ";
        }
        rowAnswer = rowAnswer + add;
        rowAnswerStripped = rowAnswer.stripTrailing();
      }
      if (i < totalLength - 1) {
        rowAnswerStripped = rowAnswerStripped + "\n";
      }
      answer = answer + rowAnswerStripped;
    }
    return answer;
  }
}
