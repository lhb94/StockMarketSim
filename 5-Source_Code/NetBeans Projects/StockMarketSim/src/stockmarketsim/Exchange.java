package stockmarketsim;

/**
 * An exchange in which companies of a certain type are traded
 *
 * @author lh340, jc570
 */
public class Exchange {

  private ExchangeType type;
  private Company[] companies;

  /**
   *
   * @param type The type of companies that should be traded on this market
   * @param companies An array of companies that will be traded on this market
   */
  public Exchange(ExchangeType type, Company[] companies) {
    this.type = type;
    this.companies = companies;
  }

  /**
   * Returns the stock market index of the companies on this exchange
   *
   * @return The index of the exchange in pennies
   */
  public int getExchangeIndex() {
    int numCompanies = companies.length;

    //Sum up all of the prices of each companies stock
    int totalPrice = 0;
    for (int i = 0; i < numCompanies; i++) {
      totalPrice += companies[i].getPrice();
    }

    //Take the average to get the index. The index is the average
    //price of the companies traded on the exchange
    int index = totalPrice / numCompanies;

    return index;
  }

  /**
   * Gets an array of the companies that're traded on this exchange
   *
   * @return An array of Company that are part of this exchange
   */
  public Company[] getTradedCompanies() {
    return companies;
  }

  /**
   * Returns the type of companies that should be traded on this market
   *
   * @return The type of exchange
   */
  public ExchangeType getType() {
    return type;
  }
}
