/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockMarketTests;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import stockmarketsim.Client;
import stockmarketsim.Company;
import stockmarketsim.Exchange;
import stockmarketsim.ExchangeType;
import stockmarketsim.Offer;
import stockmarketsim.Portfolio;
import stockmarketsim.RandomTrader;
import stockmarketsim.StockMarket;
import stockmarketsim.Trader;

/**
 * Tests the Offer class.
 * @author jc570
 */
public class OfferTest {
    
    Client client;
    Company company;
    Offer offer;
    
    /**
     * Constructor for the test.
     */
    public OfferTest() {
        try {
            company = new Company("Company A", 10, 10);
        } catch (Exception e) {}
        
        client = new Client(new Portfolio(new ArrayList(),0),"Test Client",new Object());
        
        Company[] companies = {company};
        Exchange[] exchanges = {new Exchange(ExchangeType.PROPERTY,companies)};
                
        offer = new Offer(company,100);
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
        assertSame(company,offer.getCompany());
    }
    
    /**
     * Tests the getDesiredQuantity method.
     */
    @Test
    public void testGetDesiredQuantity(){
        assertEquals(100,offer.getDesiredQuantity());
    }
}
