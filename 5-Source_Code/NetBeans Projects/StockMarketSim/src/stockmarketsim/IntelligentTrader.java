package stockmarketsim;

import java.util.ArrayList;
import java.util.Random;

/**
 * A trader which attempts to make a profit on the market, rather than by randomly buying and selling
 * @author jc570
 */
public class IntelligentTrader extends Trader {

  private Random rng;

  /**
   * An IntelligentTrader object.
   *
   * @param market The market the trader works in.
   * @param client The client the trader represents.
   * @param employer The name of the employer of the trader.
   */
  public IntelligentTrader(StockMarket market, Client client, String employer) {
    super(market, client, employer);
    rng = new Random();
  }

  /**
   *
   * Simulates a period of trade. The trader will calculate offers based on the
   * state of the market, and update his offers accordingly.
   */
  @Override
  public void simulatePeriod() {
    ArrayList<Offer> proposedOffers = new ArrayList<Offer>();

    //The percent of a clients net worth that will be traded
    //Always falls between 0-2% depending on mode
    float sellValuePercentage;
    float buyValuePercentage;

    //The actual monetary value that will be bought or sold
    int sellValue;
    int buyValue;

    //Temporary variables to record how much has been offered to buy/sell
    int sellOffer = 0;
    int buyOffer = 0;

    MarketState state = stockMarket.getMarketState();

    if (state == MarketState.STABLE) {
      //if market state is stable, buy and sell equally
      sellValuePercentage = rng.nextInt(100) / 100;
      buyValuePercentage = rng.nextInt(100) / 100;
    } else if (state == MarketState.BULL) {
      //if bull market, buy more than sell
      sellValuePercentage = rng.nextInt(50) / 100;
      buyValuePercentage = rng.nextInt(200) / 100;
    } else {
      // if bear market, sell more than buy
      sellValuePercentage = rng.nextInt(200) / 100;
      buyValuePercentage = rng.nextInt(50) / 100;
    }

    //Set sellValue to the chosen percentage of assets available to this trader
    sellValue = Math.round(sellValuePercentage * client.getPortfolio().getNetWorth());
    buyValue = Math.round(buyValuePercentage * client.getPortfolio().getNetWorth());

    //Start making random sale offers
    while (sellValue > sellOffer) {
      //Get random owned stock
      Stock s = client.getPortfolio().getStocks().get(rng.nextInt());
      //Pick a random amount to sell - if we aren't prepared to sell
      //that much, pick the most we can
      int amountToSell = rng.nextInt(s.getQuantity());
      while ((amountToSell * s.getValue() > sellValue)) {
        amountToSell--;
      }
      //Make offer
      proposedOffers.add(new Offer(s.getCompany(), amountToSell));
      //Update amount we've proposed to sell so far
      sellOffer += (s.getValue() * amountToSell);
      //Decrement sellValue - will ensure if we can't find a proposal which
      //is worth exactly the right amount, we don't get stuck in an infinite loop
      sellValue--;
    }

    //Start making random buy offers
    while (buyValue > buyOffer) {
      //Pick a random company
      Company c = stockMarket.getExchanges()[rng.nextInt()].getTradedCompanies()[rng.nextInt()];
      //Pick a random amount to buy - if we aren't prepared to buy that
      //much, pick the most we can
      int amountToBuy = rng.nextInt(c.getNumShares());
      while ((amountToBuy * c.getPrice()) > buyValue) {
        amountToBuy--;
      }
      //Make offer
      proposedOffers.add(new Offer(c, amountToBuy));
      //Update amount we've proposed to buy so far
      buyOffer += (amountToBuy * c.getPrice());
      //Decrement buyValue - will ensure if we can't find a proposal which
      //is worth exactly the right amount, we don't get stuck in an infinite loop
      buyValue--;
    }

    //Update offers
    setOffers(proposedOffers);
  }
}
