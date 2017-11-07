package StockMarketTests;

import org.junit.Test;
import static org.junit.Assert.*;
import stockmarketsim.Company;
import stockmarketsim.Exchange;
import stockmarketsim.ExchangeType;
import stockmarketsim.StockMarket;
import stockmarketsim.Trader;

/**
 * Tests the StockMarket class.
 * @author jc570
 */
public class StockMarketTest {
    
    Company companyA;
    Company companyB;
    Company[] companies;
    
    Exchange[] exchanges;
    
    Trader traderA;
    Trader traderB;
    Trader[] traders;
    StockMarket market;
    
    /**
     * Constructor for the test.
     */
    public StockMarketTest() {
        try {
            companyA = new Company("Company A", 10, 10);
            companyB = new Company("Company B", 20, 20);
        } catch (Exception e) {fail("Test failed when constructing company");}
        
        Company[] tempCompanies = {companyA,companyB};
        companies = tempCompanies;
        
        Exchange[] tempExchanges = {new Exchange(ExchangeType.PROPERTY,companies)};
        exchanges = tempExchanges;
        
        Trader[] tempTraders = {traderA, traderB};
        traders = tempTraders;
        
        market = new StockMarket(exchanges,traders);
        
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /**
     * Tests the getExchanges method.
     */
    @Test
    public void testGetExchanges(){
        assertSame(exchanges,market.getExchanges());
    }
    
    /**
     * Tests the getTraders method.
     */
    @Test
    public void testGetTraders(){
        assertSame(traders,market.getTraders());
    }
    
}
