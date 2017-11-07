package stockmarketsim;

/**
 * Represents a collection of stocks.
 *
 * @author lh340, jc570
 */
public class Stock {

  private Company company;
  private int quantity;

  /**
   * Constructor for stock objects.
   *
   * @param company The company associated with thee stocks.
   * @param quantity The amount of stocks represented by this object.
   */
  public Stock(Company company, int quantity) {
    this.company = company;
    this.quantity = quantity;
  }

  /**
   *
   * @return The company the stocks belong to.
   */
  public Company getCompany() {
    return company;
  }

  /**
   *
   * @return The amount of stocks represented by this object.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   *
   * @return The value of these stocks.
   */
  public int getValue() {
    return (int)Math.round(company.getPrice() * (double)quantity);
  }

  /**
   * Sets the quantity of stocks represented by this object.
   *
   * @param newQuantity The new quantity.
   */
  public void setQuantity(int newQuantity) {
    quantity = newQuantity;
  }
}
