package StockMarketTests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import stockmarketsim.Client;
import stockmarketsim.Portfolio;

/**
 * Test the Client class.
 * @author jc570
 */
public class ClientTest {
    
    Client client;
    Portfolio portfolio;
    Object risk;
    
    /**
     * Constructor for test.
     */
    public ClientTest() {
        portfolio = new Portfolio(new ArrayList(),0);
        risk = new Object();
        client = new Client(portfolio,"Test Client",risk);
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
        assertEquals("Test Client",client.getName());
    }
    
    /**
     * Tests the getPortfolio method.
     */
    @Test
    public void testGetPortfolio(){
        assertSame(portfolio,client.getPortfolio());
    }
    
    /**
     * Tests the getRisk method.
     */
    @Test
    public void testGetRisk(){
        assertSame(risk,client.getRisk());
    }
    
    /**
     * Tests the setName method.
     */
    @Test
    public void testSetName(){
        String newName = "New name";
        client.setName(newName);
        assertEquals(newName,client.getName());
    }
    
    /**
     * Tests the setRisk method.
     */
    @Test
    public void testSetRisk(){
        Object newRisk = new Object();
        client.setRisk(newRisk);
        assertSame(newRisk,client.getRisk());
    }
}
