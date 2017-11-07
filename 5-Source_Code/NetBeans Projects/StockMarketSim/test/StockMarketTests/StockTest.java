package StockMarketTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import stockmarketsim.Company;
import stockmarketsim.Stock;

/**
 * Test the Stock class.
 * @author jc570
 */
public class StockTest {
    
    Company company;
    Stock stock;
    
    /**
     * Constructor for the tests.
     */
    public StockTest() {
        try {
            Company tempC = new Company("Company A",100,100);
            company = tempC;
        } catch (Exception e) {}
        stock = new Stock(company,100);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /**
     * Tests the getCompany method.
     */
    @Test
    public void testGetCompany(){
        assertSame(company,stock.getCompany());
    }
    
    /**
     * Tests the getQuantity method.
     */
    @Test
    public void testGetQuantity(){
        assertEquals(100,stock.getQuantity());
    }
    
    /**
     * Tests the getValue method.
     */
    @Test
    public void testGetValue(){
        assertEquals(10000,stock.getValue());
    }
    
    /**
    * Tests the setQuantity method.
    */
    @Test
    public void testSetQuantity(){
        stock.setQuantity(300);
        assertEquals(300,stock.getQuantity());
    }
}
