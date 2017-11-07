package stockmarketsim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of a random trader, designed to emulate the random noise in
 * a stock market
 *
 * @author lh340, jc570
 */
public class RandomTrader extends Trader {

  private RandomTraderMode mode;
  private Random rng;

  /**
   * A RandomTrader object.
   *
   * @param market The market the trader works in.
   * @param client The client the trader represents.
   * @param employer The name of the employer of the trader.
   */
  public RandomTrader(StockMarket market, Client client, String employer) {
    super(market, client, employer);
    mode = RandomTraderMode.BALANCED;
    rng = new Random();
  }

  /**
   *
   * Simulates a period of trade. The trader will calculate offers based on his
   * mode, and update his offers accordingly.
   */
  @Override
  public void simulatePeriod() {
    ArrayList<Offer> proposedOffers = new ArrayList<Offer>();

    //The percent of a clients net worth that will be traded
    //Always falls between 0-2% depending on mode
    double sellValuePercentage;
    double buyValuePercentage;

    //Calculate random percentages       
    switch (mode) {
      case SELLER:
        sellValuePercentage = rng.nextDouble() * 0.02;
        buyValuePercentage = rng.nextDouble() * 0.005;
        break;
      case PURCHASER:
        sellValuePercentage = rng.nextDouble() * 0.005;
        buyValuePercentage = rng.nextDouble() * 0.02;
        break;
      default: //Balanced
        sellValuePercentage = rng.nextDouble() * 0.01;
        buyValuePercentage = rng.nextDouble() * 0.01;
        break;
    }

    //Set sellValue to the chosen percentage of assets available to this trader
    int sellValue = (int) Math.round(sellValuePercentage * (double) client.getPortfolio().getNetWorth());
    int buyValue = (int) Math.round(buyValuePercentage * (double) client.getPortfolio().getNetWorth());
    
    //Temporary variables to record how much has been offered to buy/sell
    int sellOffer = 0;
    int buyOffer = 0;

    //Start making random sale offers
    while (sellValue > sellOffer) {

      //Pick a random stock to sell
      List<Stock> stocks = client.getPortfolio().getStocks();
      int randomIndex = rng.nextInt(stocks.size());
      Stock toSell = stocks.get(randomIndex);
      
      int maxPossible = (int)Math.round((double)sellValue / toSell.getCompany().getPrice());

      if (toSell.getQuantity() == 0 || toSell.getCompany().getPrice() <= 0 || maxPossible <= 0) {
        continue;
      }
      
      int maxToSell = Math.min(toSell.getQuantity(), maxPossible);

      //Pick a random amount to sell
      int amountToSell = rng.nextInt(maxToSell);
      
      proposedOffers.add(new Offer(toSell.getCompany(), -amountToSell));
      
      sellOffer += amountToSell * toSell.getCompany().getPrice();
      
      //sellValue--;
    }
    
    List<Company> companies = new ArrayList<>();
    
    for(Exchange e : stockMarket.getExchanges()) {
      for(Company c : e.getTradedCompanies()) {
        companies.add(c);
      }
    }

    //Start making random buy offers
    while (buyValue > buyOffer) {
      int randomIndex = rng.nextInt(companies.size());
      
      Company company = companies.get(randomIndex);
      
      int maxPossible = (int)Math.round((double)buyValue / company.getPrice());
      
      if(company.getPrice() <= 0 || maxPossible <= 0)
        continue;
      
      int maxToBuy = Math.min(company.getNumShares(), maxPossible);

      //Pick a random amount to sell
      int amountToBuy = rng.nextInt(maxToBuy);
      
      //while(amountToBuy * company.getPrice() > buyValue)
        //amountToBuy--;// = (int)Math.round(buyValue / company.getPrice());
      
      proposedOffers.add(new Offer(company, amountToBuy));
      
      buyOffer += amountToBuy * company.getPrice();
      
      //buyValue--;
    }

    //Update offers
    setOffers(proposedOffers);
  }

  /**
   * Updates the seller mode at the end of the day
   */
  public void updateMode() {
    int value = rng.nextInt(100);
    switch (mode) {
      case SELLER:
        if (value < 60) {
          mode = RandomTraderMode.BALANCED;
        }
        //Else, remain as Seller
        break;
      case BALANCED:
        if (value < 10) {
          mode = RandomTraderMode.SELLER;
        } else if (value < 20) {
          mode = RandomTraderMode.PURCHASER;
        }
        //Else, remain as Balanced
        break;
      case PURCHASER:
        if (value < 70) {
          mode = RandomTraderMode.BALANCED;
        }
        //Else, remain as Purchaser
        break;
    }
  }
}

/**
 * The buying/selling modes a random trader can be in
 *
 * @author lh340
 */
enum RandomTraderMode {
  BALANCED,
  PURCHASER,
  SELLER;
}
