package cs3500.marblesolitaire.view;

import java.io.IOException;

/**
 * Represents the AbstractView class that implements MarbleSolitaireView for the methods that are
 * similar and pulls them out here.
 */
public abstract class AbstractView implements MarbleSolitaireView {

  private final Appendable ap;

  protected AbstractView(Appendable ap) {
    if (ap == null) {
      throw new IllegalArgumentException("The Appendable is null");
    }
    this.ap = ap;
  }

  @Override
  public abstract String toString();

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderBoard() throws IOException {
    this.ap.append(this.toString() + "\n");
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    this.ap.append(message);
  }
}
