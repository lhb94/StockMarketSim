/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarketsim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Is used to load all simulation initialization data from a .csv file
 * @author lh340, jc570
 */
public class MarketIO {

  private Client[] clients;
  private ExtEvent[] events;
  private StockMarket market;
  private boolean hasLoaded = false;
  private Date startDate;

  /**
   * Loads market data from the selected .csv file
   *
   * @param file the location to the initialization file to open
   * @throws Exception Any IO exception or exception in the way the csv is setup
   */
  public void loadFromFile(String file) throws Exception {
    //Read in the entire csv file as raw lines using the buffered reader's
    //line reading functionality
    ArrayList<String> rawData = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        rawData.add(line);
      }
    }

    //Remove empty lines
    for (int i = 0; i < rawData.size(); i++) {
      if (rawData.get(i).replace(",", "").equals("")) {
        rawData.remove(i);
        i--;
      }
    }

    //Parse the date and time init data and then remove it from the raw data
    //as it's no longer needed
    parseTableLine(rawData.get(0));
    rawData.remove(0);
    
    //Find the offset the the events start location
    //This is done by finding the line that starts with the keyword "EVENTS"
    int eventsStart = -1;
    for (int i = 0; i < rawData.size(); i++) {
      if (rawData.get(i).startsWith("EVENTS,")) {
        eventsStart = i;
        rawData.remove(i);
        break;
      }
    }

    //If there's no event locator then exit
    if (eventsStart == -1) {
      throw new Exception("No events locator in csv file " + file);
    }

    //The first line must be the longest as it contains all of the client names
    int tableWidth = rawData.get(0).split(",").length;

    //Store the market table and events table in two dimensional string arrays
    //Each element represents one cell in the table
    String[][] marketTable = new String[eventsStart][tableWidth];
    String[][] eventsTable = new String[rawData.size() - eventsStart + 1][tableWidth];

    //Load each of the cells from the raw data, loading into the correct table
    //depending on upon position in the rawdata
    for (int i = 0; i < rawData.size(); i++) {
      String[] tableRow = Arrays.copyOf(rawData.get(i).split(","), tableWidth);
      if (i < eventsStart) {
        marketTable[i] = tableRow;
      } else {
        eventsTable[i - eventsStart] = tableRow;
      }
    }

    //Parse the market table
    parseMarketTable(marketTable);

    //We've loaded the market now
    hasLoaded = true;
  }

  private void parseMarketTable(String[][] table) throws Exception {
    //The clients offset is the 4th horizontal position across
    int clientsOffset = 4;
    int numClients = table[0].length - clientsOffset;
    if (numClients < 2) {
      throw new Exception("There must be at least two clients in a simulation");
    }

    String[] clientNames = new String[numClients];
    int[] cashHoldings = new int[numClients];
    String[] traderTypes = new String[numClients];

    //Read in all of the client data, except stock data
    for (int i = 0; i < numClients; i++) {
      int tableOffset = clientsOffset + i;
      clientNames[i] = table[0][tableOffset];
      cashHoldings[i] = Integer.parseInt(table[1][tableOffset]);
      traderTypes[i] = table[2][tableOffset];
    }

    //Create an enum map so we can store all of the similar companies in the
    //correct list for the correct exchange
    Map<ExchangeType, ArrayList<Company>> exchanges = new EnumMap<>(ExchangeType.class);

    for (ExchangeType type : ExchangeType.values()) {
      exchanges.put(type, new ArrayList<Company>());
    }

    int companyOffset = 5;
    int numCompanies = table.length - companyOffset;

    ExchangeType[] companyTypes = new ExchangeType[numCompanies];
    Company[] companies = new Company[numCompanies];

    //Load all company data in
    for (int i = 0; i < numCompanies; i++) {
      int tableOffset = companyOffset + i;
      String companyName = table[tableOffset][0];
      String companyType = table[tableOffset][1].toUpperCase();
      companyTypes[i] = ExchangeType.valueOf(companyType);

      int stockPrice = Integer.parseInt(table[tableOffset][2]);
      int numIssuedShares = Integer.parseInt(table[tableOffset][3]);

      Company c = new Company(companyName, numIssuedShares, stockPrice);
      companies[i] = c;
    }
    
    ArrayList<ArrayList<Stock>> clientStocks = new ArrayList<ArrayList<Stock>>();

    for (int i = 0; i < numClients; i++) {
      int tableOffsetX = clientsOffset + i;
      ArrayList<Stock> stocks = new ArrayList<>();
      
      for (int j = 0; j < numCompanies; j++) {
        int tableOffsetY = companyOffset + j;
        
        int ownedShares = Integer.parseInt(table[tableOffsetY][tableOffsetX]);
        if(ownedShares == 0)
          continue;
        
        Stock s = new Stock(companies[j], ownedShares);
        stocks.add(s);
      }
      clientStocks.add(stocks);
    }
    
    for(int i = 0; i < numCompanies; i++)
      exchanges.get(companyTypes[i]).add(companies[i]);
    
    Exchange[] exchangesFinal = new Exchange[exchanges.size()];
    int index = 0;
    for(EnumMap.Entry<ExchangeType, ArrayList<Company>> entry : exchanges.entrySet()) {
      Exchange ex = new Exchange(entry.getKey(), entry.getValue().toArray(new Company[0]));
      exchangesFinal[index] = ex;
      index++;
    }
    
    StockMarket sm = new StockMarket(exchangesFinal);
    
    Trader[] traders = new Trader[numClients];
    
    for(int i = 0; i < numClients; i++) {
      List<Stock> stocksList = clientStocks.get(i);
      Portfolio portfolio = new Portfolio(stocksList, cashHoldings[i]);
      Client client = new Client(portfolio, clientNames[i], null);
      
      switch(traderTypes[i]) {
        case "INT":
          traders[i] = new RandomTrader(sm, client, "W&G");//new IntelligentTrader(sm, client, "W&G");
          break;
        case "RAND":
          traders[i] = new RandomTrader(sm, client, "W&G");
          break;
      }
    }
    
    sm.setTraders(traders);

    this.market = sm;
    this.events = new ExtEvent[0];
    //StockMarket sm = new StockMarket(exchanges, traders);
  }

  private void parseTableLine(String line) throws ParseException {
    //The first cell should have the date in date month year format
    //and the second cell should have the time of the day on that date
    String[] firstLine = line.split(",");
    String dateTime = firstLine[0] + " " + firstLine[1];
    DateFormat df = new SimpleDateFormat("dd/MM/YYYY kk:mm");
    startDate = df.parse(dateTime);
  }

  /**
   * Returns the loaded clients once a file has been opened
   *
   * @return An array of the simulations clients
   * @throws Exception Throws exception if a file hasn't been loaded
   */
  public Client[] getClients() throws Exception {
    if (!hasLoaded) {
      throw new Exception("No file has been loaded, please load a file first.");
    }
    return clients;
  }

  /**
   * Returns the loaded events once a file has been opened
   *
   * @return An array of the simulation events
   * @throws Exception Throws exception if a file hasn't been loaded
   */
  public ExtEvent[] getEvents() throws Exception {
    if (!hasLoaded) {
      throw new Exception("No file has been loaded, please load a file first.");
    }
    return events;
  }

  /**
   * Returns the loaded market once a file has been opened
   *
   * @return The stock market defined in the csv
   * @throws Exception Throws exception if a file hasn't been loaded
   */
  public StockMarket getMarket() throws Exception {
    if (!hasLoaded) {
      throw new Exception("No file has been loaded, please load a file first.");
    }
    return market;
  }
  
  /**
   * Returns the date the simulation should start on
   * @return The date the simulation should start on
   */
  public Date getStartDate() {
    return startDate;
  }
}
