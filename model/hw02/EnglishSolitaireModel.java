package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.EnglishEuropeanAbstract;

/**
 * The EnglishSolitaireModel class implements the MarbleSolitaireModel interface which holds all
 * the methods of the MarbleSolitaireModel interface. The class holds the major functionality of
 * the game with all of the constructors and the methods.
 */
public class EnglishSolitaireModel extends EnglishEuropeanAbstract implements MarbleSolitaireModel {

  /**
   * The first constructor should take no parameters, and initializes the game board with the arm
   * thickness of 3 and the empty slot to be the center of the board.
   */
  public EnglishSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * A second constructor should take two parameters: sRow and sCol. It should initialize the
   * game board so that the arm thickness is 3 and the empty slot is at the position (sRow, sCol).
   * If this specified position is invalid, it should throw an IllegalArgumentException with a
   * message "Invalid empty cell position (r,c)" with  and  replaced with the row and column
   * passed to it.
   *
   * @param sRow represents the row of the marble.
   * @param sCol represents the column of the marble.
   * @throws IllegalArgumentException in the case that the sRow or sCol for the empty are invalid.
   */
  public EnglishSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    this(3, sRow, sCol);
  }

  /**
   * The third constructor should take the arm thickness as its only parameter and initializes a
   * game board with the empty slot at the center. It should throw an IllegalArgumentException
   * if the arm thickness is not a positive odd number.
   *
   * @param armThickness is used to represent the thickness of the arm in the board. This means
   *                     the square that is supposed to be in the center of the board will be of
   *                     armThickness width.
   * @throws IllegalArgumentException in the case that the armThickness is invalid.
   */
  public EnglishSolitaireModel(int armThickness) throws IllegalArgumentException {
    this(armThickness, (armThickness + 2 * (armThickness - 1)) / 2,
            (armThickness + 2 * (armThickness - 1)) / 2);
  }

  /**
   * Finally a fourth constructor should take the arm thickness, row and column of the empty slot
   * in that order. It should throw an IllegalArgumentException if the arm thickness is not a
   * positive odd number, or the empty cell position is invalid.
   *
   * @param armThickness represents the thickness of the arm in the board. This means
   *                     the square that is supposed to be in the center of the board will be of
   *                     armThickness width.
   * @param sRow         represents the row of the marble.
   * @param sCol         represents the column of the marble.
   * @throws IllegalArgumentException in the case that one of the fields (armThickness, row, or
   *                                  column) are negative and the board is not being correctly
   *                                  made due to some invalid states for the row or the col too.
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) throws
          IllegalArgumentException {
    super(armThickness, sRow, sCol);
  }

  /**
   * Instantiates the board with the appropriate Slotstates given to the invalid, valid,
   * and the marble states.
   *
   * @return the board of Slotstates with each of the spots of the baord instantiated to a
   *         slotstatew
   */
  protected SlotState[][] drawBoard(int armThickness, int sRow, int sCol) throws
          IllegalArgumentException {
    int totalLength = this.getBoardSize();
    int lowerBound = armThickness - 1;
    int upperBound = armThickness * 2 - 2;

    if ((sRow < lowerBound && (sCol < lowerBound || sCol > upperBound))
            || (sRow > upperBound && (sCol < lowerBound || sCol > upperBound))) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + ", " + sCol +
              ")");
    }

    for (int i = 0; i < totalLength; i = i + 1) {
      for (int j = 0; j < totalLength; j = j + 1) {

        if ((i == sRow) && (j == sCol)) {
          this.board[i][j] = SlotState.Empty;
        } else if (((i < lowerBound) && (j < lowerBound || j > upperBound)) ||
                ((i > upperBound) && (j < lowerBound || j > upperBound))) {
          this.board[i][j] = SlotState.Invalid;
        } else {
          this.board[i][j] = SlotState.Marble;
        }
      }
    }
    return this.board;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    checkOnBoard(fromRow, fromCol, toRow, toCol);
    checkSameMove(fromRow, fromCol, toRow, toCol);
    checkIfEmpty(toRow, toCol);
    checkMarbleInBetweenAndValid(fromRow, fromCol, toRow, toCol);
  }

  /**
   * Check if you are on the board.
   *
   * @param fromRow represents the from row.
   * @param fromCol represents the from column.
   * @param toRow   represents the to row.
   * @param toCol   represents the to column.
   * @throws IllegalArgumentException if there is an invalid cell position for the position
   *                                  as to where the marble is and where it is going to
   */
  protected boolean checkOnBoard(int fromRow, int fromCol, int toRow, int toCol) throws
          IllegalArgumentException {
    int totalLength = this.getBoardSize();
    int leftsRowCol = (this.armThickness / 2) + 1;
    int rightsRowCol = totalLength - (this.armThickness / 2) - 1;

    if (((fromRow < leftsRowCol) && (fromCol < leftsRowCol || fromCol >= rightsRowCol)) ||
            ((fromRow >= rightsRowCol) && (fromCol < leftsRowCol || fromCol >= rightsRowCol))) {
      throw new IllegalArgumentException("Invalid initial cell position (" + fromRow + ", "
              + fromCol + ")");
    } else if (((toRow < leftsRowCol) && (toCol < leftsRowCol || toCol >= rightsRowCol)) ||
            ((toRow >= rightsRowCol) && (toCol < leftsRowCol || toCol >= rightsRowCol))) {
      throw new IllegalArgumentException("Invalid final cell position (" + toRow + ", "
              + toCol + ")");
    } else {
      return true;
    }
  }

  /**
   * Check if the same move occurred. Is the toRow and the fromRow equal and the toRow and the
   * toCol equal? This means that you are not moving at all.
   *
   * @param fromRow represents the from row.
   * @param fromCol represents the from column.
   * @param toRow   represents the to row.
   * @param toCol   represents the to column.
   * @throws IllegalArgumentException if you are not moving from one spot to the other
   */
  private void checkSameMove(int fromRow, int fromCol, int toRow, int toCol) throws
          IllegalArgumentException {
    if ((fromRow == toRow) && (fromCol == toCol)) {
      throw new IllegalArgumentException("You are not moving.");
    } else {
      return;
    }
  }

  /**
   * Checks if the toRow, toCol spot is empty.
   *
   * @param toRow represents the to row.
   * @param toCol represents the to column.
   * @throws IllegalArgumentException if the slot that you are going to is not empty.
   */
  private void checkIfEmpty(int toRow, int toCol) throws IllegalArgumentException {
    if (getSlotAt(toRow, toCol).equals((SlotState.Empty))) {
      return;
    } else {
      throw new IllegalArgumentException("There is a marble present at the given coordinates.");
    }
  }

  /**
   * Needs to check if there is a marble in between.
   * Needs to check for if it is a valid move (this would mean 2 in either direction up, down,
   * left, right.
   * Need to remove the marble from the (fromRow, fromCol) and make it visible to the (toRow,
   * toCol).
   * Make sure to remove the marble that you are jumping over.
   *
   * @param fromRow represents the from row.
   * @param fromCol represents the from column.
   * @param toRow   represents the to row.
   * @param toCol   represents the to column.
   * @throws IllegalArgumentException if the marble is not moving to a valid location on the baord.
   */
  private void checkMarbleInBetweenAndValid(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {

    int middleMarbleX;
    int middleMarbleY;
    // determine the spot that the marble would be
    if (fromRow == toRow && (((toCol - fromCol) == 2) || ((toCol - fromCol) == -2))) {
      if (toCol > fromCol) {
        middleMarbleY = toCol - 1;
        // adding the middleMarble to be empty
        board[fromRow][middleMarbleY] = SlotState.Empty;
      } else {
        middleMarbleY = fromCol - 1;
        // adding the middleMarble to be empty
        board[fromRow][middleMarbleY] = SlotState.Empty;
      }

      // adding the initial spot to be empty
      board[fromRow][fromCol] = SlotState.Empty;
      // adding the new marble
      board[fromRow][toCol] = SlotState.Marble;
    } else if (fromCol == toCol && (((toRow - fromRow) == 2) || ((toRow - fromRow) == -2))) {
      //if (toRow > fromRow && ((toRow - fromRow) == 2)) {
      if (toRow > fromRow) {
        middleMarbleX = toRow - 1;
        board[middleMarbleX][fromCol] = SlotState.Empty;
      }
      // else if ((toRow < fromRow) && ((toRow - fromRow) == -2)) {
      else if (toRow < fromRow) {
        middleMarbleX = fromRow - 1;
        // adding the middleMarble to be empty
        board[middleMarbleX][fromCol] = SlotState.Empty;
      }
      // adding the initial spot to be empty
      board[fromRow][fromCol] = SlotState.Empty;
      // adding the new marble
      board[toRow][toCol] = SlotState.Marble;
    } else {
      throw new IllegalArgumentException("There is no marble in between or it is an invalid jump.");
    }
  }
}