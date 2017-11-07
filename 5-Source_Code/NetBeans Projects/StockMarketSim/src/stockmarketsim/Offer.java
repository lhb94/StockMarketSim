package stockmarketsim;

/**
 * Represents an offer made by a Trader to purchase or sell Stocks.
 *
 * @author lh340, jc570
 */
public class Offer {

  private final Company company;
  private final int quantity;
  private int numFulfilled;

  /**
   * Constructor for Offer object.
   *
   * @param company The company whose shares are to be bought/sold.
   * @param quantity The amount of shares to buy (negative value for sell).
   */
  public Offer(Company company, int quantity) {
    this.company = company;
    this.quantity = quantity;
  }

  /**
   *
   * @return The company the offer is associated with.
   */
  public Company getCompany() {
    return company;
  }

  /**
   *
   * @return The quantity of shares to be bought (Negative value to sell).
   */
  public int getDesiredQuantity() {
    return quantity;
  }


  /**
   *
   * @param numFulfilled The number of shares that can be fulfilled.
   */
  public void setFulfilled(int numFulfilled) {
    this.numFulfilled = numFulfilled;
  }
  
  /**
   * 
   * @return The number of fulfilled orders
   */
  public int getFulfilled() {
    return this.numFulfilled;
  }
}
