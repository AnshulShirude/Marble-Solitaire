package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents the abstract class EnglishEuropeanAbstract that implements the methods of the
 * MarbleSolitaireModel. Essentially, this is creating this abstract class and whichever methods
 * are common from the 2 classes are pulled out and put in this class to prevent code redundancy.
 */
public abstract class EnglishEuropeanAbstract implements MarbleSolitaireModel {

  protected int armThickness;
  protected SlotState[][] board;

  /**
   * Main constructor that does all of the invalid checking. A constructor with three parameters
   * (side length, row, col), to specify the size of the board and the initial position of the
   * empty slot. If there are invalid things, then you throw an IllegalArgumentException.
   *
   * @param armThickness represents the armThickness (side length) of each of the sides in the
   *                     model.
   * @param row          represents the emptyX spot in the model.
   * @param col          represents the emptyY spot in the model.
   * @throws IllegalArgumentException in the case that one of the fields (armThickness, row, or
   *                                  column) are negative and the board is not being correctly
   *                                  made due to some invalid states for the row or the col too.
   */
  public EnglishEuropeanAbstract(int armThickness, int row, int col) throws
          IllegalArgumentException {
    if (armThickness <= 0 || row < 0 || col < 0) {
      throw new IllegalArgumentException("One of the fields are not positive.");
    } else if (armThickness % 2 != 1) {
      throw new IllegalArgumentException("Arm thickness is not an odd number.");
    } else {
      this.armThickness = armThickness;
      int totalLength = this.getBoardSize();
      this.board = new SlotState[totalLength][totalLength];
      this.board = drawBoard(armThickness, row, col);
    }
  }

  /**
   * A constructor with a single parameter (the side length) that creates a game with the specified
   * side length, and the empty slot in the center of the board.
   *
   * @param armThickness represents the armThickness (side length) of each of the sides in the
   *                     model.
   * @throws IllegalArgumentException in the case that the armThickness is invalid specified by the
   *                                  main constructor.
   */
  public EnglishEuropeanAbstract(int armThickness) throws IllegalArgumentException {
    this(armThickness, (armThickness * 3 - 2) / 2, (armThickness * 3 - 2) / 2);
  }

  /**
   * A constructor with two parameters (row, col), to specify the initial position of the empty
   * slot, in a board of default size 3.
   *
   * @param row represents the emptyX spot in the model.
   * @param col represents the emptyY spot in the model.
   * @throws IllegalArgumentException in the case that the empty row and the column are invalid.
   */
  public EnglishEuropeanAbstract(int row, int col) throws IllegalArgumentException {
    this(3, row, col);
  }

  /**
   * A default constructor (no parameters) that creates a default board whose sides have length
   * 3, with the empty slot in the center of the board.
   */
  public EnglishEuropeanAbstract() {
    this(3);
  }

  protected abstract SlotState[][] drawBoard(int armThickness, int row, int col);

  @Override
  public int getBoardSize() {
    return this.armThickness * 3 - 2;
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    int pegs = 0;
    for (int i = 0; i < this.getBoardSize(); i = i + 1) {
      for (int j = 0; j < this.getBoardSize(); j = j + 1) {
        if (this.getSlotAt(i, j) == SlotState.Marble) {
          pegs = pegs + 1;
        }
      }
    }
    return pegs;
  }

  /**
   * Get the state of the slot at a given position on the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the state of the slot at the given row and column
   * @throws IllegalArgumentException if the row or the column are beyond
   *                                  the dimensions of the board
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    int totalLength = this.getBoardSize();
    if (row < 0 || row >= totalLength || col < 0 || col >= totalLength) {
      throw new IllegalArgumentException("Invalid cell position (" + row + ", " + col + ")");
    } else {
      return this.board[row][col];
    }
  }

  @Override
  public boolean isGameOver() {
    int startingValue = this.armThickness - 1;
    int endingValue = getBoardSize() - this.armThickness + 1;

    // If the game is over to the right in the row?
    for (int i = 0; i < getBoardSize(); i = i + 1) {
      for (int j = 0; j < endingValue; j = j + 1) {
        if ((this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i, j + 1).equals(SlotState.Marble)) &&
                (this.getSlotAt(i, j + 2).equals(SlotState.Empty))) {
          return false;
        }
      }
    }

    // If the game is over in terms of the columns going down?
    for (int i = 0; i < endingValue; i = i + 1) {
      for (int j = 0; j < getBoardSize(); j = j + 1) {
        if ((this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i + 1, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i + 2, j).equals(SlotState.Empty))) {
          return false;
        }
      }
    }

    // If the game is over to the left in the row?
    for (int i = 0; i < getBoardSize(); i = i + 1) {
      for (int j = startingValue; j < getBoardSize(); j = j + 1) {
        if ((this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i, j - 1).equals(SlotState.Marble)) &&
                (this.getSlotAt(i, j - 2).equals(SlotState.Empty))) {
          return false;
        }
      }
    }

    // If the game is over to the top in terms of the column?
    for (int i = startingValue; i < getBoardSize(); i = i + 1) {
      for (int j = 0; j < getBoardSize(); j = j + 1) {
        if ((this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i - 1, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i - 2, j).equals(SlotState.Empty))) {
          return false;
        }
      }
    }
    return true;
  }

  // I had worked on abstracting move and trying to make them the same implementations with
  // different methods for the invalid checking. However, for some reason, they could not be
  // abstracted and the code for one of the models was not working for the other and vice versa.
  // Therefore, the code for move was not abstracted. This was my design decision and I have
  // documented it here.
}
