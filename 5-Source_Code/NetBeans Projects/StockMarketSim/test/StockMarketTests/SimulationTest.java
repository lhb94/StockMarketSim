package StockMarketTests;


import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Test;
import stockmarketsim.Client;
import stockmarketsim.Company;
import stockmarketsim.Exchange;
import stockmarketsim.ExchangeType;
import stockmarketsim.ExtEvent;
import stockmarketsim.Portfolio;
import stockmarketsim.RandomTrader;
import stockmarketsim.Simulation;
import stockmarketsim.Stock;
import stockmarketsim.StockMarket;
import stockmarketsim.Trader;

/**
 * Tests the Simulation class.
 * @author jc570
 */
public class SimulationTest {
    
    Simulation simulation;
    
    ExtEvent eventA;
    ExtEvent eventB;
    ExtEvent eventC;
    ExtEvent[] events;
    
    StockMarket stockMarket;
    
    Date startDate;
    
    /**
    * Constructor for the test.
    */
    public SimulationTest() {
        startDate = new Date();
        ExtEvent[] tempEvents = {eventA,eventB,eventC};
        events = tempEvents;
        
        Company companyA;
        try {
            companyA = new Company("Company A", 10, 10);
        } catch (Exception e) {fail("Test failed in Company constructor");return;}
        Company[] companies = {companyA};
        
        Exchange[] exchanges = {new Exchange(ExchangeType.PROPERTY,companies)};
        
        ArrayList<Stock> stocks = new ArrayList();
        Client client = new Client(new Portfolio(stocks,100),"Client",new Object());
        Trader traderA = new RandomTrader(stockMarket,client,"Employer");
        Trader[] traders = {traderA};
        
        stockMarket = new StockMarket(exchanges,traders);
        
        simulation = new Simulation(stockMarket,events,startDate);
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /**
     * Tests the getStockMarket method.
     */
    @Test
    public void testGetStockMarket() {
        assertSame(stockMarket,simulation.getStockMarket());
    }
}
