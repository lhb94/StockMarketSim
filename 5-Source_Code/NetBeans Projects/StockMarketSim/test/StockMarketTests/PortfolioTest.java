package StockMarketTests;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import stockmarketsim.Company;
import stockmarketsim.Portfolio;
import stockmarketsim.Stock;

/**
 * Tests the Portfolio class.
 * @author jc570
 */
public class PortfolioTest {
    
    ArrayList<Stock> stocks;
    Company company;
    Portfolio portfolio;
    
    /**
     * Constructor for the test.
     */
    public PortfolioTest() {
        try {
            Company tempC = new Company("Company A",100,100);
            company = tempC;
        } catch (Exception e) {}
        Stock s = new Stock(company,100);
        stocks = new ArrayList<Stock>();
        stocks.add(s);
        portfolio = new Portfolio(stocks,100);
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    /**
     * Tests the getCash method.
     */
    @Test
    public void testGetCash(){
        assertEquals(100,portfolio.getCash());
    }
    
    /**
     * Tests the getNetWorth method.
     */
    @Test
    public void testGetNetWorth(){
        assertEquals(10100,portfolio.getNetWorth());
    }
    
    /**
     * Tests the setStocks method.
     */
    @Test
    public void testSetStocks(){
        ArrayList<Stock> newStocks = new ArrayList<Stock>();
        portfolio.setStocks(newStocks);
        assertSame(newStocks,portfolio.getStocks());
    }
    
    /**
     * Tests the getStocks method.
     */
    @Test
    public void testGetStocks(){
        assertSame(stocks,portfolio.getStocks());
    }
}
