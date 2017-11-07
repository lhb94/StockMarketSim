package stockmarketsim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A stock market for holding exchanges and traders
 *
 * @author lh340, jc570
 */
public class StockMarket {

  private Exchange[] exchanges;
  private Trader[] traders;
  private MarketState state = MarketState.STABLE;
  private List<Integer> closingIndices = new ArrayList<>();

  /**
   *
   * @param exchanges An array of exchanges which are on the market
   * @param traders An array of traders which trade on the market
   */
  public StockMarket(Exchange[] exchanges, Trader[] traders) {
    this.exchanges = exchanges;
    this.traders = traders;
  }

  /**
   *
   * @param exchanges An array of exchanges which are on the market
   */
  public StockMarket(Exchange[] exchanges) {
    this.exchanges = exchanges;
  }

  /**
   * Calculates any pending offers the traders have made
   * @param closingTime Is it at the end of a market day?
   * @throws java.lang.Exception Throws an error if the period could not be simulated
   */
  public void simulateInterval(boolean closingTime) throws Exception {

    for (Trader t : traders) {
      t.simulatePeriod();
      
      //Update the random trader mode at the end of the day
      if(closingTime && t instanceof RandomTrader) {
        ((RandomTrader)t).updateMode();
      }
    }

    //Calculate all of the offers and the new share prices
    calculateOffers();

    updateTraders();
    
    calculatePrices();
    
    calculateMarketState(closingTime);
  }

  private void calculateMarketState(boolean closingTime) {
    if(!closingTime) {
      return;
    }
    
    int numCompanies = 0;
    int valueTotal = 0;
    
    for(Exchange e : exchanges) {
      for(Company c : e.getTradedCompanies()) {
        valueTotal += c.getPrice();
        numCompanies++;
      }
    }
    
    int dayIndex = valueTotal / numCompanies;
    
    closingIndices.add(dayIndex);
    
    if(closingIndices.size() < 3) {
      state = MarketState.STABLE;
      return;
    }
    
    int indexA = closingIndices.get(closingIndices.size() - 3);
    int indexB = closingIndices.get(closingIndices.size() - 2);
    int indexC = closingIndices.get(closingIndices.size() - 1);
    
    if(indexC > indexB && indexB > indexA)
      state = MarketState.BULL;
    else if(indexA > indexB && indexB > indexC)
      state = MarketState.BEAR;
    else
      state = MarketState.STABLE;
  }

  /**
   * Gets the state the market is in e.g. stable/bull/bear
   *
   * @return The market state
   */
  public MarketState getMarketState() {
    return state;
  }

  private void updateTraders() {
    for (Trader t : traders) {
      //boolean isNorb = t.getClient().getName().equals("Norbert DaVinci");
      List<Stock> newStocks = new ArrayList<>(t.getClient().getPortfolio().getStocks());
      int oldTotal = 0;
      
      for(Stock s : newStocks) {
        oldTotal += s.getValue();
      }

      //First add any stocks that don't exist yet in the trader's portfolio
      for (Offer o : t.getOffers()) {
        boolean exists = false;
        for (Stock s : newStocks) {
          if (s.getCompany() == o.getCompany()) {
            exists = true;
            break;
          }
        }

        if (!exists) {
          newStocks.add(new Stock(o.getCompany(), Math.abs(o.getFulfilled())));
        }
      }

      //Now run through and calculate all the changes in stocks and set the new
      //values in the stocks
      for (Stock s : newStocks) {
        int newOwnership = 0;
        for (Offer o : t.getOffers()) {
          if (o.getCompany() == s.getCompany()) {
            newOwnership += Math.abs(o.getFulfilled());
          }
        }

        if (newOwnership != 0) {
          s.setQuantity(newOwnership);
        }
      }
      
      int newTotal = 0;
      
      for(Stock s : newStocks) {
        newTotal += s.getValue();
      }
      
      t.getClient().getPortfolio().setCash(t.getClient().getPortfolio().getCash() + oldTotal - newTotal);
      
      t.getClient().getPortfolio().setStocks(newStocks);
    }
  }

  private void calculateOffers() throws Exception {
    //Use a map to map the total buy and sell numbers to each company
    HashMap<Company, Integer> totalBuy = new HashMap<>();
    HashMap<Company, Integer> totalSell = new HashMap<>();

    //Add all of the companies to the maps with initial values of 0
    for (Exchange e : exchanges) {
      for (Company c : e.getTradedCompanies()) {
        totalBuy.put(c, 0);
        totalSell.put(c, 0);
      }
    }

    //Go through all the offers and add to buy if the quantity is positive
    //or to sell if it's negative
    for (Trader t : traders) {
      for (Offer o : t.getOffers()) {
        if (o.getDesiredQuantity() > 0) {
          int oldValue = totalBuy.get(o.getCompany());
          int newValue = oldValue + Math.abs(o.getDesiredQuantity());
          totalBuy.put(o.getCompany(), newValue);
        } else if (o.getDesiredQuantity() < 0) {
          int oldValue = totalSell.get(o.getCompany());
          int newValue = oldValue + Math.abs(o.getDesiredQuantity());
          totalSell.put(o.getCompany(), newValue);
        }
      }
    }

    //Go through all of the offers
    for (Trader t : traders) {
      for (Offer o : t.getOffers()) {
        int numFulfilled = calculateFulfilled(totalSell.get(o.getCompany()), totalBuy.get(o.getCompany()), o.getDesiredQuantity());

        o.setFulfilled(numFulfilled);
        //boolean isNorb = t.getClient().getName().equals("Norbert DaVinci");
      }
    }
  }
  
  private void calculatePrices() throws Exception {
    //Use a map to map the total buy and sell numbers to each company
    HashMap<Company, Integer> totalBuy = new HashMap<>();
    HashMap<Company, Integer> totalSell = new HashMap<>();

    //Add all of the companies to the maps with initial values of 0
    for (Exchange e : exchanges) {
      for (Company c : e.getTradedCompanies()) {
        totalBuy.put(c, 0);
        totalSell.put(c, 0);
      }
    }

    //Go through all the offers and add to buy if the quantity is positive
    //or to sell if it's negative
    for (Trader t : traders) {
      for (Offer o : t.getOffers()) {
        if (o.getDesiredQuantity() > 0) {
          int oldValue = totalBuy.get(o.getCompany());
          int newValue = oldValue + Math.abs(o.getDesiredQuantity());
          totalBuy.put(o.getCompany(), newValue);
        } else if (o.getDesiredQuantity() < 0) {
          int oldValue = totalSell.get(o.getCompany());
          int newValue = oldValue + Math.abs(o.getDesiredQuantity());
          totalSell.put(o.getCompany(), newValue);
        }
      }
    }
    
    //Calculate supply vs demand
    for (Exchange e : exchanges) {
      for (Company c : e.getTradedCompanies()) {
        //Calculate the supply vs demand
        int buy = totalBuy.get(c);
        int sell = totalSell.get(c);
        int delta = buy - sell;

        double newPrice = calculateNewSharePrice(delta, c.getNumShares(), c.getPrice());

        //Update the price
        c.setPrice(newPrice);
      }
    }
  }

  private double calculateNewSharePrice(int supplyDemand, int numShares, double oldPrice) {
    //no change if demand met supply
    if (supplyDemand == 0) {
      return oldPrice;
    }

    //Calculate the change in price
    double priceDelta = oldPrice * (double) supplyDemand / (double) numShares;;

    //Add it to the new price
    double newPrice = oldPrice + priceDelta;
    
    if(newPrice < 0.0)
      newPrice = 0.0;

    return newPrice;
  }

  private int calculateFulfilled(int sellRequests, int buyRequests, int desired) {
    //If it's 0 then skip it
    if (desired == 0) {
      return 0;
    }

    int numFulfilled;

    //Calculate the number we can fulfill and set it
    if (desired > 0f) {
      //If there's more sell than buy then all can be 
      if (sellRequests > buyRequests) {
        numFulfilled = (int) desired;
      } else {
        numFulfilled = (int) Math.round((double) sellRequests * (double) desired / (double) buyRequests);
      }
    } else {
      //If there's more buy than sell then all can be sold, else calculate
      //the max that can be sold
      if (buyRequests > sellRequests) {
        numFulfilled = (int) desired;
      } else {
        numFulfilled = (int) Math.round((double) buyRequests * (double) desired / (double) sellRequests);
      }
    }

    return numFulfilled;
  }

  /**
   * Returns a list of exchanges on the market
   *
   * @return An array of exchanges
   */
  public Exchange[] getExchanges() {
    return exchanges;
  }

  /**
   * Returns all traders that're trading on the market
   *
   * @return An array of traders
   */
  public Trader[] getTraders() {
    return traders;
  }

  /**
   * Sets the traders that are operating on this stock market
   *
   * @param traders An array of traders
   */
  public void setTraders(Trader[] traders) {
    this.traders = traders;
  }
}
