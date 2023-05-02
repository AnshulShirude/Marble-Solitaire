package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Represents the TriangleSolitaireTextView class that extends the AbstractView and implements
 * the MarbleSolitaireView. This is the method that toString's the triangle model.
 */
public class TriangleSolitaireTextView extends AbstractView implements MarbleSolitaireView {

  private final MarbleSolitaireModelState tsm;

  /**
   * Represents the TriangleSolitaireTextView constructor that takes in a MarbleSolitaireModelState.
   *
   * @param tsm represents the MarbleSolitaireModelState that you take in for rendering the view.
   * @throws IllegalArgumentException in the case that the tsm is null.
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState tsm) throws IllegalArgumentException {
    this(tsm, System.out);
  }

  /**
   * Represents the TriangleSolitaireTextView constructor that takes in a
   * MarbleSolitaireModelState and the Appendable.
   * @param tsm represents the MarbleSolitaireModelState.
   * @param ap represents the Appendable.
   *
   * @throws IllegalArgumentException in the case that the tsm is null.
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState tsm, Appendable ap) throws
          IllegalArgumentException {
    super(ap);
    if (tsm == null) {
      throw new IllegalArgumentException("The MarbleSolitaireModelState or the appendable object " +
              "were null.");
    }
    this.tsm = tsm;
  }

  @Override
  public String toString() {
    String output = "";
    int dimension = this.tsm.getBoardSize();

    for (int i = 0; i < dimension; i = i + 1) {
      int numInRow = i + 1;
      String rowOutput = "";

      for (int j = 0; j < numInRow; j = j + 1) {
        MarbleSolitaireModelState.SlotState currState = tsm.getSlotAt(i, j);
        String add;

        if (currState == MarbleSolitaireModelState.SlotState.Empty) {
          add = "_ ";
        } else if (currState == MarbleSolitaireModelState.SlotState.Invalid) {
          add = " ";
        } else {
          add = "O ";
        }

        rowOutput = rowOutput + add;
        rowOutput = emptyLeft(rowOutput, dimension - i + 1);
      }

      String ridtrailing = rowOutput.stripTrailing();

      if (i < (dimension - 1)) {
        ridtrailing = ridtrailing + "\n";
      }
      output = output + ridtrailing;
    }
    return output;
  }

  private String emptyLeft(String input, int len) {
    if (input.length() >= len) {
      return input;
    }
    StringBuilder sb = new StringBuilder();
    while (sb.length() < len - input.length()) {
      sb.append(" ");
    }
    sb.append(input);

    return sb.toString();
  }
}
