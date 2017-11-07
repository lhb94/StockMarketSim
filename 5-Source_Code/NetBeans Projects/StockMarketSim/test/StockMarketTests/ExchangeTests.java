package StockMarketTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import stockmarketsim.Company;
import stockmarketsim.Exchange;
import stockmarketsim.ExchangeType;

/**
 * Tests the Exchange class.
 * @author jc570
 */
public class ExchangeTests {
    
    Exchange exchange;
    Company[] companies;
    
    /**
     * Constructor for the test.
     */
    public ExchangeTests() {
        try {
            Company[] companies = {
            new Company("Company A", 10, 10),
            new Company("Company B", 20, 20)
            };
            this.companies = companies;
            exchange = new Exchange(ExchangeType.PROPERTY,companies);
        } catch (Exception e){fail("Test failed when constructing company");}
        
    }
    
    // @Test
    // public void hello() {}

    /**
     * Tests the getExchangeIndex method.
     */
    @Test
    public void testGetExchangeIndex(){
        assertEquals(15,exchange.getExchangeIndex());
    }
    
    /**
     * Tests the getTradedCompanies method.
     */
    @Test
    public void testGetTradedCompanies(){
        assertArrayEquals(companies,exchange.getTradedCompanies());
    }
    
    /**
     * Tests the getType method.
     */
    @Test
    public void testGetType(){
        assertEquals(ExchangeType.PROPERTY,exchange.getType());
    }
}
