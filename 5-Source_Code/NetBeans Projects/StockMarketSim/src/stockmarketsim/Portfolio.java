package stockmarketsim;

import java.util.List;

/**
 * Class to represent a clients portfolio of owned assets.
 *
 * @author lh340, jc570
 */
public class Portfolio {

  private int cash;
  private List<Stock> stocks;

  /**
   * Constructor for Portfolio object.
   *
   * @param stocks A list of stocks owned by the Client.
   * @param cash The amount of cash owned by the Client.
   */
  public Portfolio(List<Stock> stocks, int cash) {
    this.stocks = stocks;
    this.cash = cash;
  }

  /**
   *
   * @return The amount of cash owned by the client.
   */
  public int getCash() {
    return cash;
  }
  
  /**
   * Sets the new total amount of cash in the account
   * @param cash The new cash value in pence
   */
  public void setCash(int cash) {
    this.cash = cash;
  }

  /**
   *
   * @return The net worth, in pounds, of the client.
   */
  public int getNetWorth() {
    int value = 0;
    for (int i = 0; i < stocks.size(); i++) {
      value += stocks.get(i).getValue();
    }
    value += cash;
    return value;
  }

  /**
   * Sets the stocks owned by the client.
   *
   * @param stocks The list of stocks to set as the clients stocks.
   */
  public void setStocks(List<Stock> stocks) {
    this.stocks = stocks;
  }

  /**
   *
   * @return The list of stocks owned by the client.
   */
  public List<Stock> getStocks() {
    return stocks;
  }
}
