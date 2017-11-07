package stockmarketsim;

import java.util.ArrayList;
import java.util.List;

/**
 * Trader type is for simulated traders such as intelligent or random traders
 *
 * @author lh340, jc570, jc570
 */
public abstract class Trader {

  protected Client client;
  protected List<Offer> offers;
  protected String employer;
  protected StockMarket stockMarket;

  /**
   *
   * @param market The market which the traders will be trading on
   * @param client Client that this trader will trade for and manage
   * @param employer The entity this trader works for
   */
  public Trader(StockMarket market, Client client, String employer) {
    this.stockMarket = market;
    this.client = client;
    this.employer = employer;
    this.offers = new ArrayList<Offer>();
  }

  /**
   * Returns the client that the trader trades for
   *
   * @return Trader's client
   */
  public Client getClient() {
    return client;
  }

  /**
   * Returns the company this trader works for
   *
   * @return A string representing the employer
   */
  public String getEmployer() {
    return employer;
  }

  /**
   * Gets the offers that the trader wants to make
   *
   * @return a list of offers made
   */
  public List<Offer> getOffers() {
    return offers;
  }

  /**
   * Sets the offers once they've been fully/partially/etc fulfilled
   *
   * @param offers A list of fulfilled offers
   */
  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  /**
   * Simulates the period
   */
  public abstract void simulatePeriod();
}
