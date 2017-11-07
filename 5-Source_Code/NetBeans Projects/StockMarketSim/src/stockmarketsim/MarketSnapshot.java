/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarketsim;

import java.util.ArrayList;
import java.util.Date;

/**
 * Stores a snapshot of the data in the market at a specific time
 *
 * @author lh340, jc570
 */
public class MarketSnapshot {

  private Date dateTime;
  private MarketState marketState;

  private String[] companyNames;
  private Integer[] companyValues;

  private String[] clientNames;
  private Integer[] clientValues;

  private String[] exchangeNames;
  private Integer[] exchangeIndicies;

  /**
   * Gets the date the snapshot was taken at
   * @return A date representing when the snapshot was taken
   */
  public Date getDate() {
    return dateTime;
  }

  /**
   * Gets the state of the market at the point the snapshot was taken
   * @return The state of the market
   */
  public MarketState getMarketState() {
    return marketState;
  }

  /**
   * Gets an array of the company names
   * @return An array of company names
   */
  public String[] getCompanyNames() {
    return companyNames;
  }

  /**
   * Gets the evaluation of each company at the snapshot time, indexed the same
   * as company names.
   * @return An array of company values in pennies
   */
  public Integer[] getCompanyValues() {
    return companyValues;
  }

  /**
   * Gets an array of the client names
   * @return An array of client names
   */
  public String[] getClientNames() {
    return clientNames;
  }

  /**
   * Gets an array of the net worth of each client at the snapshot time, indexed
   * the same as the client names
   * @return An array of client values in pennies
   */
  public Integer[] getClientValues() {
    return clientValues;
  }

  /**
   * Gets an array of the exchange names (type of company they serve)
   * @return An array for the exchange names
   */
  public String[] getExchangeNames() {
    return exchangeNames;
  }

  /**
   * Gets an array of indices for  the exchanges, indexed the same as the
   * exchange names
   * @return An array for each index of each exchange, in pennies
   */
  public Integer[] getExchangeIndicies() {
    return exchangeIndicies;
  }

  /**
   * Generates a snapshot of the current state of a market
   * @param market The market to take a snapshot of
   * @param dateTime The date and time to assign to the snapshot
   * @return A snapshot of the given market at the current simulation time
   */
  public static MarketSnapshot generateFromStockMarket(StockMarket market, Date dateTime) {
    ArrayList<String> companyNames = new ArrayList<>();
    ArrayList<Integer> companyValues = new ArrayList<>();

    //Get all the companies from all the exchanges and then get their name and
    //current net worth
    for (Exchange e : market.getExchanges()) {
      for (Company c : e.getTradedCompanies()) {
        companyNames.add(c.getName());
        companyValues.add(c.getNetWorth());
      }
    }

    ArrayList<String> clientNames = new ArrayList<>();
    ArrayList<Integer> clientValues = new ArrayList<>();

    //Get the portfolio of each of the clients and then gets the clients name
    //and their current net worth
    for (Trader t : market.getTraders()) {
      Portfolio p = t.getClient().getPortfolio();
      clientNames.add(t.getClient().getName());
      clientValues.add(p.getNetWorth());
    }

    ArrayList<String> exchangeNames = new ArrayList<>();
    ArrayList<Integer> exchangeIndicies = new ArrayList<>();

    //Gets each exchange and their name and the current index
    for (Exchange e : market.getExchanges()) {
      exchangeNames.add(e.getType().name());
      exchangeIndicies.add(e.getExchangeIndex());
    }

    //Built a snapshot
    MarketSnapshot snapshot = new MarketSnapshot();
    snapshot.companyNames = companyNames.toArray(new String[companyNames.size()]);
    snapshot.companyValues = companyValues.toArray(new Integer[companyValues.size()]);

    snapshot.clientNames = clientNames.toArray(new String[clientNames.size()]);
    snapshot.clientValues = clientValues.toArray(new Integer[clientValues.size()]);

    snapshot.exchangeNames = exchangeNames.toArray(new String[exchangeNames.size()]);
    snapshot.exchangeIndicies = exchangeIndicies.toArray(new Integer[exchangeIndicies.size()]);

    snapshot.marketState = market.getMarketState();
    snapshot.dateTime = dateTime;

    return snapshot;
  }

  /**
   * A private constructor so that only this class can create nre instances of
   * MarketSnapshot
   */
  private MarketSnapshot() {

  }
}
