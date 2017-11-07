package StockMarketTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Runs all JUnit Tests
 * @author jc570
 */
@RunWith(Suite.class)
@SuiteClasses({
                ClientTest.class,
                CompanyTest.class,
                ExchangeTests.class,
                OfferTest.class,
                PortfolioTest.class,
                StockTest.class,
                StockMarketTest.class,
                SimulationTest.class
})

public class AllTests {

}