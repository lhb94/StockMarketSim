package StockMarketTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import stockmarketsim.Company;

/**
 * Tests the Company class.
 * @author jc570
 */
public class CompanyTest {
    
    Company company;
    
    /**
     * Constructor for the test.
     */
    public CompanyTest() {
        try {
            company = new Company("Test Company",100,100);
        } catch (Exception e) {fail("Test failed in Company constructor");}
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName(){
        assertEquals("Test Company",company.getName());
    }
    
    /**
     * Tests the setName method.
     */
    @Test
    public void testSetName(){
        String newName = "New Company Name";
        company.setName(newName);
        assertEquals(newName,company.getName());
    }
    
    /**
     * Tests the getPrice method.
     */
    @Test
    public void testGetPrice(){
        assertEquals(100,company.getPrice());
    }
    
    /**
     * Tests the setPrice method.
     */
    @Test
    public void testSetPrice(){
        try {
            company.setPrice(200);
        } catch (Exception e) {}
        assertEquals(200,company.getPrice());
    }
    
    /**
     * Tests the setPrice method when given a negative price.
     * Expected behaviour is a thrown exception.
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testSetNegativePrice() throws Exception {
        try {
            company.setPrice(-1);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Tests the getNumShares method.
     */
    @Test
    public void testGetNumShares(){
        assertEquals(100,company.getNumShares());
    }
    
    /**
     * Tests the getNetWorth method.
     */
    @Test
    public void testGetNetWorth(){
        assertEquals((100*100),company.getNetWorth());
    }
}
