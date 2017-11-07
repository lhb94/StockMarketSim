/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockMarketGUI;

import java.awt.BorderLayout;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import stockmarketsim.MarketSnapshot;
import stockmarketsim.MarketState;

/**
 *
 * @author lhb94
 */
public class Main extends javax.swing.JFrame {
  
  StockMarketControl marketControl;

  JFreeChart clientChart;
  JFreeChart companyChart;
  JFreeChart exchangeChart;
  
  XYSeriesCollection clientDataset;
  XYSeriesCollection companyDataset;
  XYSeriesCollection exchangeDataset;
  
  Date currentDate;
  
  double intervalsPerFrame = 1.0 / 120.0;
  
  /**
   * Returns the number of intervals to simulate per frame
   * @return The number of intervals to simulate per frame as a double
   */
  public double getIntervalsPerFrame() {
    return intervalsPerFrame;
  }

  /**
   * Updates the gui with a new market snapshot as reference
   * @param snapshot The snapshot to add to the gui
   */
  public void updateChart(MarketSnapshot snapshot) {
    Integer[] clientValues = snapshot.getClientValues();
    
    for(int i = 0; i < clientValues.length; i++) {
      clientDataset.getSeries(i).add(snapshot.getDate().getTime(), clientValues[i]);
    }

    Integer[] companyValues = snapshot.getCompanyValues();
    
    for(int i = 0; i < companyValues.length; i++) {
      companyDataset.getSeries(i).add(snapshot.getDate().getTime(), companyValues[i]);
    }

    Integer[] exchangeIndices = snapshot.getExchangeIndicies();
    
    for(int i = 0; i < exchangeIndices.length; i++) {
      exchangeDataset.getSeries(i).add(snapshot.getDate().getTime(), exchangeIndices[i]);
    }
    
    jLabelMarketState.setText(snapshot.getMarketState().toString());
    jLabelSimulationDate.setText(snapshot.getDate().toString());
  }
  
  /**
   * Initializes the charts when nothing has been loaded yet 
   * @param clientNames An array of client names
   * @param companyNames An array of company names
   * @param exchangeNames An array of exchange names
   */
  public void initSim(String[] clientNames, String[] companyNames, String[] exchangeNames) {
    for(int i = 0; i < clientNames.length; i++) {
      XYSeries clientSeries = new XYSeries(clientNames[i]);
      clientDataset.addSeries(clientSeries);
    }
    
    for(int i = 0; i < companyNames.length; i++) {
      XYSeries companySeries = new XYSeries(companyNames[i]);
      companyDataset.addSeries(companySeries);
    }
    
    for(int i = 0; i < exchangeNames.length; i++) {
      XYSeries exchangeSeries = new XYSeries(exchangeNames[i]);
      exchangeDataset.addSeries(exchangeSeries);
    }
  }

  /**
   * Creates new form Main
   */
  public Main() {
    initComponents();
    
    marketControl = new StockMarketControl(this);
    Thread t = new Thread(marketControl);
    t.start();
    
    clientDataset = new XYSeriesCollection();
    clientChart = ChartFactory.createXYLineChart("Client Net Worth", "Date", "Net Worth (pence)", clientDataset);
    
    DateAxis dateAxis = new DateAxis();
    dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MINUTE,15, DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)));
    clientChart.getXYPlot().setDomainAxis(dateAxis);
    
    ChartPanel chartPanelClient = new ChartPanel(clientChart);
    chartPanelClient.setPreferredSize(new java.awt.Dimension(560, 367));
    jScrollClient.add(chartPanelClient);
    jScrollClient.getViewport().add(chartPanelClient);
    
    companyDataset = new XYSeriesCollection();
    companyChart = ChartFactory.createXYLineChart("Company Net Worth", "Date", "Net Worth (pence)", companyDataset);
    companyChart.getXYPlot().setDomainAxis(dateAxis);
    
    ChartPanel chartPanelCompany = new ChartPanel(companyChart);
    chartPanelCompany.setPreferredSize(new java.awt.Dimension(560, 367));
    jScrollCompanies.add(chartPanelCompany);
    jScrollCompanies.getViewport().add(chartPanelCompany);
    
    exchangeDataset = new XYSeriesCollection();
    exchangeChart = ChartFactory.createXYLineChart("Exchange Indices", "Date", "Index", exchangeDataset);
    //exchangeChart.getXYPlot().setDomainAxis(dateAxis);
    
    ChartPanel chartPanelExchange = new ChartPanel(exchangeChart);
    chartPanelExchange.setPreferredSize(new java.awt.Dimension(560, 367));
    jScrollExchanges.add(chartPanelExchange);
    jScrollExchanges.getViewport().add(chartPanelExchange);
    
    updateSpeedLabel();
  }
  
  private void updateSpeedLabel() {
    intervalsPerFrame = (double)jSliderSpeed.getValue() / 900000.0;
    int minutesPerSecond = (int)(intervalsPerFrame * 120.0 * 15.0);
    jLabelSpeed.setText("Speed: " + minutesPerSecond + " minutes/second");
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    label_logo = new javax.swing.JLabel();
    jTabbedGraphs = new javax.swing.JTabbedPane();
    jPanelClient = new javax.swing.JPanel();
    jScrollClient = new javax.swing.JScrollPane();
    jPanelCompanies = new javax.swing.JPanel();
    jScrollCompanies = new javax.swing.JScrollPane();
    jPanelExchanges = new javax.swing.JPanel();
    jScrollExchanges = new javax.swing.JScrollPane();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jSliderSpeed = new javax.swing.JSlider();
    jLabel2 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabelMarketState = new javax.swing.JLabel();
    jLabelSimulationDate = new javax.swing.JLabel();
    jButtonStop = new javax.swing.JButton();
    jButtonAdvanceFrame = new javax.swing.JButton();
    jButtonComplete = new javax.swing.JButton();
    jButtonRun = new javax.swing.JButton();
    jLabelSpeed = new javax.swing.JLabel();
    jMenuBar = new javax.swing.JMenuBar();
    jMenuFile = new javax.swing.JMenu();
    jMenuLoad = new javax.swing.JMenuItem();
    jMenuQuit = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    label_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/StockMarketData/logoTransResized.png"))); // NOI18N

    javax.swing.GroupLayout jPanelClientLayout = new javax.swing.GroupLayout(jPanelClient);
    jPanelClient.setLayout(jPanelClientLayout);
    jPanelClientLayout.setHorizontalGroup(
      jPanelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
    );
    jPanelClientLayout.setVerticalGroup(
      jPanelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
    );

    jTabbedGraphs.addTab("Clients", jPanelClient);

    javax.swing.GroupLayout jPanelCompaniesLayout = new javax.swing.GroupLayout(jPanelCompanies);
    jPanelCompanies.setLayout(jPanelCompaniesLayout);
    jPanelCompaniesLayout.setHorizontalGroup(
      jPanelCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollCompanies, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
    );
    jPanelCompaniesLayout.setVerticalGroup(
      jPanelCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollCompanies, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
    );

    jTabbedGraphs.addTab("Companies", jPanelCompanies);

    javax.swing.GroupLayout jPanelExchangesLayout = new javax.swing.GroupLayout(jPanelExchanges);
    jPanelExchanges.setLayout(jPanelExchangesLayout);
    jPanelExchangesLayout.setHorizontalGroup(
      jPanelExchangesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollExchanges, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
    );
    jPanelExchangesLayout.setVerticalGroup(
      jPanelExchangesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollExchanges, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
    );

    jTabbedGraphs.addTab("Exchanges", jPanelExchanges);

    jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel1.setText("Simulation Settings");

    jSliderSpeed.setMaximum(870000);
    jSliderSpeed.setMinimum(7500);
    jSliderSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jSliderSpeedStateChanged(evt);
      }
    });

    jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel2.setText("Simulation Information");

    jLabel4.setText("Simulation Date: ");

    jLabel3.setText("Market State: ");

    jButtonStop.setText("Stop");
    jButtonStop.setMaximumSize(new java.awt.Dimension(53, 23));
    jButtonStop.setMinimumSize(new java.awt.Dimension(53, 23));
    jButtonStop.setPreferredSize(new java.awt.Dimension(52, 23));
    jButtonStop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonStopActionPerformed(evt);
      }
    });

    jButtonAdvanceFrame.setText("Advance Interval");
    jButtonAdvanceFrame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonAdvanceFrameActionPerformed(evt);
      }
    });

    jButtonComplete.setText("Run to Completion");

    jButtonRun.setText("Run");
    jButtonRun.setMaximumSize(new java.awt.Dimension(53, 23));
    jButtonRun.setMinimumSize(new java.awt.Dimension(53, 23));
    jButtonRun.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonRunActionPerformed(evt);
      }
    });

    jLabelSpeed.setText("Simulation speed: ");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1)
              .addComponent(jSliderSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelMarketState))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSimulationDate)))
            .addGap(28, 28, 28))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabelSpeed)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(jButtonComplete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jButtonAdvanceFrame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jButtonRun, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jButtonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 0, Short.MAX_VALUE))))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jLabel2))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSliderSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel3)
              .addComponent(jLabelMarketState))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel4)
              .addComponent(jLabelSimulationDate))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jLabelSpeed)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonAdvanceFrame)
          .addComponent(jButtonRun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonComplete)
          .addComponent(jButtonStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(23, 23, 23))
    );

    jMenuFile.setText("File");

    jMenuLoad.setText("Load File");
    jMenuLoad.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuLoadActionPerformed(evt);
      }
    });
    jMenuFile.add(jMenuLoad);

    jMenuQuit.setText("Exit");
    jMenuQuit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuQuitActionPerformed(evt);
      }
    });
    jMenuFile.add(jMenuQuit);

    jMenuBar.add(jMenuFile);

    setJMenuBar(jMenuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jTabbedGraphs)
          .addGroup(layout.createSequentialGroup()
            .addComponent(label_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(24, 24, 24)
            .addComponent(label_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jTabbedGraphs)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButtonAdvanceFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdvanceFrameActionPerformed
    marketControl.setControlMode(ControlMode.SINGLE);
  }//GEN-LAST:event_jButtonAdvanceFrameActionPerformed

  private void jMenuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQuitActionPerformed
    System.exit(0);
  }//GEN-LAST:event_jMenuQuitActionPerformed

  private void jMenuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLoadActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      
      try {
        marketControl.loadMarket(selectedFile.getAbsolutePath());
        marketControl.initData();
      } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    //marketControl.loadMarket(path);
  }//GEN-LAST:event_jMenuLoadActionPerformed

  private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
    marketControl.setControlMode(ControlMode.PROGRESSIVE);
  }//GEN-LAST:event_jButtonRunActionPerformed

  private void jSliderSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderSpeedStateChanged
    updateSpeedLabel();
  }//GEN-LAST:event_jSliderSpeedStateChanged

  private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
    marketControl.setControlMode(ControlMode.STOP);
  }//GEN-LAST:event_jButtonStopActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new Main().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAdvanceFrame;
  private javax.swing.JButton jButtonComplete;
  private javax.swing.JButton jButtonRun;
  private javax.swing.JButton jButtonStop;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabelMarketState;
  private javax.swing.JLabel jLabelSimulationDate;
  private javax.swing.JLabel jLabelSpeed;
  private javax.swing.JMenuBar jMenuBar;
  private javax.swing.JMenu jMenuFile;
  private javax.swing.JMenuItem jMenuLoad;
  private javax.swing.JMenuItem jMenuQuit;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanelClient;
  private javax.swing.JPanel jPanelCompanies;
  private javax.swing.JPanel jPanelExchanges;
  private javax.swing.JScrollPane jScrollClient;
  private javax.swing.JScrollPane jScrollCompanies;
  private javax.swing.JScrollPane jScrollExchanges;
  private javax.swing.JSlider jSliderSpeed;
  private javax.swing.JTabbedPane jTabbedGraphs;
  private javax.swing.JLabel label_logo;
  private javax.swing.JLabel label_settings;
  private javax.swing.JLabel label_speed;
  private javax.swing.JPanel panel_settings;
  private javax.swing.JSlider slider_speed;
  // End of variables declaration//GEN-END:variables
}
