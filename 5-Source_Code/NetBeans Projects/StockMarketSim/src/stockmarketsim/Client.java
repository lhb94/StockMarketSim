package stockmarketsim;

/**
 * Represents a Client in a Stock Market Simulation.
 *
 * @author lh340, jc570
 * @version 1.0
 * @since 2017-05-04
 */
public class Client {

  private Portfolio portfolio;
  private String name;
  private Object risk;

  /**
   * Constructor for new Client objects. Initialises the class fields to the
   * supplied parameters.
   *
   * @param portfolio The portfolio associated with this Client
   * @param name The name of the client
   * @param risk The risk object associated with this Client
   */
  public Client(Portfolio portfolio, String name, Object risk) {
    this.portfolio = portfolio;
    this.name = name;
    this.risk = risk;
  }

  /**
   * Gets the name of this client.
   *
   * @return The name of the client as a String.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the portfolio associated with this client.
   *
   * @return A portfolio object.
   */
  public Portfolio getPortfolio() {
    return portfolio;
  }

  /**
   * The risk object associated with the client.
   *
   * @return A risk object.
   */
  public Object getRisk() {
    return risk;
  }

  /**
   * Sets the name of this Client. Does not apply any validation to the supplied
   * String, so use with care.
   *
   * @param name The String to set the name of this client to.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the risk of this Client. Currently accepts any Object so use with
   * extreme care.
   *
   * @param newRisk The object to set as this clients risk.
   */
  public void setRisk(Object newRisk) {
    risk = newRisk;
  }
}
