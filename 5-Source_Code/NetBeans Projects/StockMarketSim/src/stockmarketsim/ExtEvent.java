/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarketsim;

import java.util.Date;

/**
 * An event which effects the simulation at some point in time
 * @author lh340, jc570
 */
public abstract class ExtEvent {

  /**
   * Run the event, should be called every interval
   * @param currentTime The current time it is in the simulation
   */
  public abstract void Run(Date currentTime);
}
