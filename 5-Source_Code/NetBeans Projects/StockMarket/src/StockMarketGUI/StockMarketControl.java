/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockMarketGUI;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockmarketsim.*;

/**
 * Controls the stock market on the GUI
 * @author lhb94
 */
public class StockMarketControl implements Runnable {

  Simulation marketSim;
  ControlMode mode = ControlMode.STOP;
  Main gui;
  
  /**
   *
   * @param gui The GUI the market is being displayed on
   */
  public StockMarketControl(Main gui) {
    this.gui = gui;
  }
  
  /**
   *
   * @param mode The mode to set the simulation to
   */
  public void setControlMode(ControlMode mode) {
    this.mode = mode;
  }

  /**
   * Loads a market from a path
   * @param path The path to the csv file
   * @throws Exception If the file could not be loaded
   */
  public void loadMarket(String path) throws Exception {
    //"C:\\Users\\lhb94\\Documents\\Software Engineering\\Project\\StockMarketSim\\src\\init.csv";
    MarketIO loader = new MarketIO();
    loader.loadFromFile(path);

    marketSim = new Simulation(loader.getMarket(), loader.getEvents(), loader.getStartDate());
  }
  
  /**
   * Initializes the market data after a market has been loaded
   */
  public void initData() {
    gui.initSim(marketSim.getClientNames(), marketSim.getCompanyNames(), marketSim.getExchangeNames());
  }

  @Override
  public void run() {
    final int frameTime = 8;
    int timeSinceLastUpdate = 0;
    for (;;) {
      try {
        Thread.sleep(frameTime);
      } catch (InterruptedException ex) {
        Logger.getLogger(StockMarketControl.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(mode == ControlMode.STOP)
        continue;
      
      if(mode == ControlMode.SINGLE) {
        try {
          marketSim.RunSimFrame();
          gui.updateChart(marketSim.getLastSnapshot());
          mode = ControlMode.STOP;
        } catch (Exception ex) {
          Logger.getLogger(StockMarketControl.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      if(mode == ControlMode.PROGRESSIVE) {
        double intervalsPerFrame = gui.getIntervalsPerFrame();
        int everyMS = (int)(1.0 / (intervalsPerFrame * 120.0) * 1000.0);
        
        if(timeSinceLastUpdate > everyMS) {
          try {
            marketSim.RunSimFrame();
            gui.updateChart(marketSim.getLastSnapshot());
          } catch (Exception ex) {
            Logger.getLogger(StockMarketControl.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          timeSinceLastUpdate = 0;
        }
      }
      
      timeSinceLastUpdate += frameTime;
    }
  }

}
