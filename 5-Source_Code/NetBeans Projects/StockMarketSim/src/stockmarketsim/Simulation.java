/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarketsim;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

/**
 * Handles the stock market simulation
 * @author lh340, jc570
 */
public class Simulation {

  private StockMarket stockMarket;
  private ExtEvent[] events;
  private Date currentDateTime;
  private ArrayList<MarketSnapshot> snapshots = new ArrayList<>();

  //The time in milliseconds the simulation increases in
  static final int INTERVAL_TIME = 900000;
  //The 24 hour opening hour the market opens at
  static final int OPENING_HOUR = 9;
  //The 24 hour closing hour the market closes at
  static final int CLOSING_HOUR = 16;

  /**
   *
   * @param stockMarket The main stock market for the simulation to use
   * @param events A list of events that may run in the future
   * @param startDate The date to start the simulation on
   */
  public Simulation(StockMarket stockMarket, ExtEvent[] events, Date startDate) {
    this.stockMarket = stockMarket;
    this.events = events;
    this.currentDateTime = startDate;
  }
  
  /**
   * Gets a list of the client names that're on the market
   * @return A list of strings for each client name
   */
  public String[] getClientNames() {
    Trader[] traders = stockMarket.getTraders();
    String[] clientNames = new String[traders.length];
    
    for(int i = 0; i < traders.length; i++) {
      clientNames[i] = traders[i].getClient().getName();
    }
    
    return clientNames;
  }
  
  /**
   * Gets a list of company names on the market
   * @return A string of company names
   */
  public String[] getCompanyNames() {
    ArrayList<String> companyNames = new ArrayList<>();
    
    for(Exchange e : stockMarket.getExchanges()) {
      for(Company c : e.getTradedCompanies()) {
        companyNames.add(c.getName());
      }
    }
    
    return companyNames.toArray(new String[companyNames.size()]);
  }
  
  /**
   * Gets a list of the exchange type names on the market
   * @return An array of strings for each exchange name
   */
  public String[] getExchangeNames() {
    ArrayList<String> exchangeNames = new ArrayList<>();
    
    for (Exchange e : stockMarket.getExchanges()) {
      exchangeNames.add(e.getType().name());
    }
    
    return exchangeNames.toArray(new String[exchangeNames.size()]);
  }

  /**
   * Returns the active stock market
   *
   * @return The stock market the simulation is simulating
   */
  public StockMarket getStockMarket() {
    return stockMarket;
  }

    /**
   * Runs the simulation
   *
   * @param until The date to run the simulation until
   * @throws java.lang.Exception In case of interval error
   */
  public void RunSimUntil(Date until) throws Exception {
    while (currentDateTime.before(until)) {
      simulateInterval();
    }
  }

  /**
   * Simulates only a single simulation interval
   * @throws java.lang.Exception In case of interval error
   */
  public void RunSimFrame() throws Exception {
    simulateInterval();
  }

  /**
   * Gets the last snapshot taken of the market
   * @return Returns null if no snapshot has yet been generated
   */
  public MarketSnapshot getLastSnapshot() {
    //If there's at least one snapshot, return that, else return null
    if (snapshots.size() > 0) {
      return snapshots.get(snapshots.size() - 1);
    } else {
      return null;
    }
  }

  /**
   * Gets an array of all of the snapshots generated so far in the simulation
   * @return Am array of market snapshots
   */
  public MarketSnapshot[] getAllSnapshots() {
    return snapshots.toArray(new MarketSnapshot[snapshots.size()]);
  }

  /**
   * Gets an array of all the snapshots since a certain date im the simulation
   * @param dateSince The date to get all snapshots up to
   * @return An array of market snapshots
   */
  public MarketSnapshot[] getSnapshotsSince(Date dateSince) {
    //We don't know the size so add them all to a list and then convert the list
    //to an array
    ArrayList<MarketSnapshot> retSnaps = new ArrayList<>();
    for (MarketSnapshot snapshot : snapshots) {
      if (snapshot.getDate().after(dateSince)) {
        retSnaps.add(snapshot);
      }
    }

    return retSnaps.toArray(new MarketSnapshot[retSnaps.size()]);
  }

  private void simulateInterval() throws Exception {
    //Jump to the next interval
    incrementInterval();

    //Run all external events that will effect the market
    for (ExtEvent event : events) {
      event.Run(currentDateTime);
    }
    
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDateTime);
    
    boolean closingHour = cal.get(Calendar.HOUR_OF_DAY) == CLOSING_HOUR;
    
    //Calculate the trade offers
    stockMarket.simulateInterval(closingHour);

    snapshots.add(MarketSnapshot.generateFromStockMarket(stockMarket, currentDateTime));
  }

  private void incrementInterval() {
    //Use the calendar to calculate the new date easily
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDateTime);

    //Only increase the date if we're at closing time
    if (cal.get(Calendar.HOUR_OF_DAY) == CLOSING_HOUR) {
      //Set the hour of the day to the opening hour for the new day
      cal.set(Calendar.HOUR_OF_DAY, OPENING_HOUR);

      //Calculate the next date
      boolean isValidDate = false;
      while (!isValidDate) {
        cal.add(Calendar.DATE, 1);

        //todo: check for good Friday and easter Monday
        //Check that the new day isn't Saturday or Sunday
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          continue;
        }
        //Make sure it's not Christmas or Boxing day
        if (cal.get(Calendar.MONTH) == 11 && (cal.get(Calendar.DAY_OF_MONTH) == 25 || cal.get(Calendar.DAY_OF_MONTH) == 26)) {
          continue;
        }
        //Check for good Friday and Easter Monday
        if (cal.get(Calendar.MONTH) == 3 && (cal.get(Calendar.DAY_OF_MONTH) == 14 || cal.get(Calendar.DAY_OF_MONTH) == 17)) {
          continue;
        }
        //The date is valid otherwise
        isValidDate = true;
      }
    } else {
      //Else just jump to the next 15 minute interval
      cal.add(Calendar.MILLISECOND, INTERVAL_TIME);
    }

    //Update the date
    currentDateTime = cal.getTime();
  }
}
