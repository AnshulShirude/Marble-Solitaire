package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents the EuropeanSolitaireModel class that extends the EnglishEuropeanAbstract and
 * implements the MarbleSolitaireModel. This class creates the EuropeanSolitaireModel with the
 * appropriate constructors and by inheriting the methods of the MarbleSolitaireModel.
 */
public class EuropeanSolitaireModel extends EnglishEuropeanAbstract implements
        MarbleSolitaireModel {

  /**
   * A default constructor (no parameters) that creates an octagonal board whose sides have length
   * 3, with the empty slot in the center of the board.
   */
  public EuropeanSolitaireModel() {
    this(3, 3, 3);
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
  public EuropeanSolitaireModel(int armThickness) throws IllegalArgumentException {
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
  public EuropeanSolitaireModel(int row, int col) throws IllegalArgumentException {
    this(3, row, col);
  }

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
  public EuropeanSolitaireModel(int armThickness, int row, int col) throws
          IllegalArgumentException {
    super(armThickness, row, col);
  }

  protected SlotState[][] drawBoard(int armThickness, int row, int col) {
    int length = this.getBoardSize();
    int leftRowsCol;
    int rightRowsCol;

    for (int i = 0; i < length; i = i + 1) {
      if (i < armThickness) {
        leftRowsCol = armThickness - 1 - i;
        rightRowsCol = armThickness * 2 - 2 + i;
      } else if (i > 2 * armThickness - 2) {
        leftRowsCol = i - (2 * armThickness - 2);
        rightRowsCol = 3 * armThickness - leftRowsCol - 3;
      } else {
        leftRowsCol = 0;
        rightRowsCol = length - 1;
      }

      for (int j = 0; j < length; j = j + 1) {
        if (j < leftRowsCol || j > rightRowsCol) {
          this.board[i][j] = SlotState.Invalid;
        } else {
          this.board[i][j] = SlotState.Marble;
        }
      }
    }
    if (this.getSlotAt(row, col).equals(SlotState.Invalid)) {
      throw new IllegalArgumentException("Cannot create empty since it is invalid.");
    } else {
      this.board[row][col] = SlotState.Empty;
    }
    return this.board;
  }

  /**
   * Move a single marble from a given position to another given position.
   * A move is valid only if the from and to positions are valid. Specific
   * implementations may place additional constraints on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {

    int totalLength = getBoardSize();
    int midX = ((fromRow) + (toRow)) / 2;
    int midY = ((fromCol) + (toCol)) / 2;

    if (fromRow < 0 || fromCol < 0 || toRow < 0 || toCol < 0 || fromRow >= totalLength ||
            fromCol >= totalLength || toRow >= totalLength || toCol >= totalLength) {
      throw new IllegalArgumentException("Invalid spots for the move");
    } else if (checkOnBoard(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("To and from are invalid.");
    } else if (!getSlotAt((fromRow), (fromCol)).equals(SlotState.Marble)) {
      throw new IllegalArgumentException("From is not a marble");
    } else if (!getSlotAt((toRow), (toCol)).equals(SlotState.Empty)) {
      throw new IllegalArgumentException("To is not empty");
    } else if (Math.sqrt(Math.pow((fromCol - toCol), 2) + Math.pow((fromRow - toRow), 2)) != 2) {
      throw new IllegalArgumentException("Distance is not 2");
    } else if (!getSlotAt(midX, midY).equals(SlotState.Marble)) {
      throw new IllegalArgumentException("Middle is not a marble");
    } else {
      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[midX][midY] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;
    }
  }

  protected boolean checkOnBoard(int fromRow, int fromCol, int toRow, int toCol) {
    int center = this.getBoardSize() / 2;

    if (this.getSlotAt(fromRow, fromCol).equals(SlotState.Invalid) || this.getSlotAt(toRow,
            toCol).equals(SlotState.Invalid)) {
      throw new IllegalArgumentException("The move is invalid as it is not on the board.");
    } else {
      return false;
    }
  }
}
