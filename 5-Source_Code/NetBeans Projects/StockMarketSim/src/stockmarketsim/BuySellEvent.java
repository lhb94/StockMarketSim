/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarketsim;

import java.util.Date;

/**
 * Used to handle buying and selling events that happen throughout the year
 *
 * @author lh340, jc570
 */
public class BuySellEvent extends ExtEvent {

  private Date eventStart;
  private Date eventEnd;

  /**
   *
   * @param market The market in which the events take place
   * @param eventStart When the event starts
   * @param eventEnd When the event ends
   */
  public BuySellEvent(StockMarket market, Date eventStart, Date eventEnd) {
    this.eventStart = eventStart;
    this.eventEnd = eventEnd;
  }

  /**
   * Runs the event, this should be called even if the time is outside of the
   * event
   *
   * @param currentTime The current time in the simulation
   */
  @Override
  public void Run(Date currentTime) {
    //If we're not inside the event immediately return
    if (eventStart.after(currentTime) || eventEnd.before(eventEnd)) {
      return;
    }

  }

}
