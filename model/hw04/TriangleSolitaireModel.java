package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents the TriangleSolitaireModel class that implements the MarbleSolitaireModel. This
 * class is reponsible for creating a TriangleSolitaireModel. It will take all of the methods of
 * the MarbleSolitaireModel and implement those methods as well in the TriangleSolitaireModel class.
 */
public class TriangleSolitaireModel implements MarbleSolitaireModel {

  private final int dimension;
  private final SlotState[][] board;

  /**
   * Represents the default TriangleSolitaireModel contructor without args. A default constructor
   * (no parameters) that creates a 5-row game with the empty slot at (0,0).
   */
  public TriangleSolitaireModel() throws IllegalArgumentException {
    this(5, 0, 0);
  }

  /**
   * Represents the TriangleSolitaireModel constructor with the dimension as a parameter. A
   * constructor with a single parameter (dimensions) that creates a game with the specified
   * dimension (number of slots in the bottom-most row) and the empty slot at (0,0). This
   * constructor should throw the IllegalArgumentException exception if the specified dimension
   * is invalid (non-positive)
   *
   * @param dimension for the size of the triangle.
   * @throws IllegalArgumentException if it is invalid.
   */
  public TriangleSolitaireModel(int dimension) throws IllegalArgumentException {
    this(dimension, 0, 0);
  }

  /**
   * Represents the TriangleSolitaireModel constructor with the emptyX and emptyY as parameters.
   * A constructor with two parameters (row,col) that creates a 5-row game with the empty slot at
   * the specified position. This constructor should throw the IllegalArgumentException exception
   * if the specified position is invalid.
   *
   * @param emptyX is the emptyX for the TriangleSolitaireModel.
   * @param emptyY is the emptyY for the TriangleSolitaireModel.
   * @throws IllegalArgumentException if it is invalid.
   */
  public TriangleSolitaireModel(int emptyX, int emptyY) throws IllegalArgumentException {
    this(5, emptyX, emptyY);
  }

  /**
   * Represents the TriangleSolitaireModel constructor with the dimension, emptyX, and the emptyY
   * as the parameters. A constructor with three parameters (dimensions,row,col) that creates a
   * game with the specified dimension and an empty slot at the specified row and column. This
   * constructor should throw the IllegalArgumentException exception if the specified dimension is
   * invalid (non-positive) or the specified position is invalid.
   *
   * @param dimension is the dimension of the TriangleSolitaireModel.
   * @param emptyX    is the emptyX for the TriangleSolitaireModel.
   * @param emptyY    is the emptyY for the TriangleSolitaireModel.
   * @throws IllegalArgumentException if one of the slots are invalid.
   */
  public TriangleSolitaireModel(int dimension, int emptyX, int emptyY) throws
          IllegalArgumentException {

    if (emptyX == 0 && emptyY > 0) {
      throw new IllegalArgumentException("EmptyY is invalid");
    }
    if (emptyY > emptyX) {
      throw new IllegalArgumentException("Invalid");
    }
    for (int i = 0; i < dimension; i = i + 1) {
      if (i == (dimension - 1) && (emptyY >= (i + 1) || emptyX >= (i + 1))) {
        throw new IllegalArgumentException("Slot is invalid.");
      }
    }
    if (dimension < 0 || emptyX < 0 || emptyY < 0) {
      throw new IllegalArgumentException("One of the fields: dimension, emptyX, or emptyY are " +
              "invalid");
    } else {
      this.dimension = dimension;
      this.board = drawBoard(dimension, emptyX, emptyY);
    }
  }

  // Draw the board with it being left aligned
  private SlotState[][] drawBoard(int dimension, int emptyX, int emptyY) {
    SlotState[][] board = new SlotState[dimension][dimension];
    for (int i = 0; i < dimension; i = i + 1) {
      int sizeOfRow = i + 1;
      for (int j = 0; j < sizeOfRow; j = j + 1) {
        if (i == emptyX && j == emptyY) {
          board[i][j] = SlotState.Empty;
        } else {
          board[i][j] = SlotState.Marble;
        }
      }
    }
    return board;
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
    // check if to and from are invalid
    checkToAndFrom(fromRow, fromCol, toRow, toCol);

    // check if from has a marble
    checkFromMarble(fromRow, fromCol);

    // check if the to position is empty
    checkToEmpty(toRow, toCol);

    // check if you are 2 spaces away diagonally and there is a marble between from and to
    checkTwoSpacesDiagonallyAndMarbleBetween(fromRow, fromCol, toRow, toCol);
  }

  private void checkToAndFrom(int fromX, int fromY, int toX, int toY) {
    if (fromX < dimension && fromY < dimension && toX < dimension && toY < dimension &&
            fromX >= 0 && fromY >= 0 && toX >= 0 && toY >= 0) {
      return;
    } else {
      throw new IllegalArgumentException("Your from or the to location are invalid.");
    }
  }

  private void checkFromMarble(int fromRow, int fromCol) {
    if (this.getSlotAt(fromRow, fromCol) == SlotState.Marble) {
      return;
    } else {
      throw new IllegalArgumentException("The from position does not have a marble.");
    }
  }

  private void checkToEmpty(int toRow, int toCol) {
    if (this.getSlotAt(toRow, toCol) == SlotState.Empty) {
      return;
    } else {
      throw new IllegalArgumentException("The to position is not empty.");
    }
  }

  private void checkTwoSpacesDiagonallyAndMarbleBetween(int fromRow, int fromCol, int toRow,
                                                        int toCol) {
    if ((fromRow - 2) == toRow && (fromCol == toCol)) {
      if (this.getSlotAt(fromRow - 1, fromCol) == SlotState.Marble) {
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[fromRow - 1][fromCol] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
      }
    } else if ((fromRow - 2) == toRow && (fromCol - 2 == toCol)) {
      if (this.getSlotAt(fromRow - 1, fromCol - 1) == SlotState.Marble) {
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[fromRow - 1][fromCol - 1] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
      }
    } else if ((fromRow + 2) == toRow && (fromCol + 2) == toCol) {
      if (this.getSlotAt(fromRow + 1, fromCol + 1) == SlotState.Marble) {
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[fromRow + 1][fromCol + 1] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
      }
    } else if ((fromRow + 2) == toRow && (fromCol == toCol)) {
      if (this.getSlotAt(fromRow + 1, fromCol) == SlotState.Marble) {
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[fromRow + 1][fromCol] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
      }
    } else if ((fromRow == toRow) && (fromCol - 2) == toCol) {
      if (this.getSlotAt(fromRow, fromCol - 1) == SlotState.Marble) {
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[fromRow][fromCol - 1] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
      }
    } else if ((fromRow == toRow) && (fromCol + 2) == toCol) {
      if (this.getSlotAt(fromRow, fromCol + 1) == SlotState.Marble) {
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[fromRow][fromCol + 1] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
      }
    } else {
      throw new IllegalArgumentException("You are not moving two spots diagonally appropriately.");
    }
  }

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < this.getBoardSize(); i = i + 1) {
      int sizeOfRow = i + 1;
      for (int j = 0; j < sizeOfRow; j = j + 1) {
        if (j < (this.getBoardSize() - 2) && (this.getSlotAt(i, j) == SlotState.Marble) &&
                (this.getSlotAt(i, j + 1) == SlotState.Marble) &&
                (this.getSlotAt(i, j + 2) == SlotState.Marble)) {
          return false;
        } else if (i < (this.getBoardSize() - 2) &&
                (this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i + 1, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i + 2, j).equals(SlotState.Empty))) {
          return false;
        } else if (j >= 2 && (this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i, j - 1).equals(SlotState.Marble)) &&
                (this.getSlotAt(i, j - 2).equals(SlotState.Empty))) {
          return false;
        } else if (i >= 2 && (this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i - 1, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i - 2, j).equals(SlotState.Empty))) {
          return false;
        } else if (i >= 2 && j >= 2 && (this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i - 1, j - 1).equals(SlotState.Marble)) &&
                (this.getSlotAt(i - 2, j - 2).equals(SlotState.Empty))) {
          return false;
        } else if (i < (this.getBoardSize() - 2) && j < (this.getBoardSize() - 2) &&
                (this.getSlotAt(i, j).equals(SlotState.Marble)) &&
                (this.getSlotAt(i + 1, j + 1).equals(SlotState.Marble)) &&
                (this.getSlotAt(i + 2, j + 2).equals(SlotState.Empty))) {
          return false;
        }
      }
    }
    return true;
  }


  /**
   * Return the size of this board. The size is roughly the longest dimension of a board.
   *
   * @return the size as an integer
   */
  @Override
  public int getBoardSize() {
    return this.dimension;
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
      throw new IllegalArgumentException();
    } else if (col > row && row < totalLength) {
      return SlotState.Invalid;
    } else {
      return this.board[row][col];
    }
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    int pegs = 0;
    for (int i = 0; i < this.dimension; i = i + 1) {
      for (int j = 0; j < (i + 1); j = j + 1) {
        if (this.getSlotAt(i, j) == SlotState.Marble) {
          pegs = pegs + 1;
        }
      }
    }
    return pegs;
  }
}
