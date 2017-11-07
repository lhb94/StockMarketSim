package stockmarketsim;

/**
 * A class for holding data about individual companies, can be used on
 * exchanges.
 *
 * @author lh340, jc570
 */
public class Company {

  private String name;
  private int issuedShares;
  private double price;

  /**
   * 
   * @param name Name of the company.
   * @param issuedShares The number of shares the company has currently issued.
   * @param price The current price of each share, in
   * @throws Exception Throws an exception for an invalid name or price
   */
  public Company(String name, int issuedShares, double price) throws Exception {
    setName(name);
    this.issuedShares = issuedShares;
    setPrice(price);
  }

  /**
   * Returns the companies name
   *
   * @return Company name as a string
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the companies name
   *
   * @param name The new company name as a string
   */
  public void setName(String name) {
    //todo: add some verification for valid names?
    this.name = name;
  }

  /**
   * Returns current price per share
   *
   * @return The price as an double in pennies
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the new price per share
   *
   * @param newPrice The new price per share, in pennies
   * @throws Exception The share price must be positive and valid or an exception will be thrown
   */
  public void setPrice(double newPrice) throws Exception {
    //Don't allow negative prices for obvious reasons
    if (newPrice < 0.0) {
      throw new Exception("Shares cannot have a negative value.");
    }
    this.price = newPrice;
  }

  /**
   * Returns the number of issued shares
   *
   * @return an integer representing the number of issues shares
   */
  public int getNumShares() {
    return issuedShares;
  }

  /**
   * Gets the net worth of the company (issued shares * share price)
   *
   * @return An integer representing the net worth in pennies
   */
  public int getNetWorth() {
    return (int)Math.round((double)issuedShares * price);
  }
}
