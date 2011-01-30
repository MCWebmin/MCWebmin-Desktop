/*
 *  Copyright 2010 Joe Stein.
 * 
 */

/*dis
 * MainWindow.java
 *
 * Created on Jan 18, 2011, 10:26:07 PM
 */

package mcadmin;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author joe
 */
public class MainWindow extends javax.swing.JFrame implements DataHandler {

   private static final int REROUTE_NONE = 0;
   private static final int REROUTE_PLAYERSLISTENER = 1;
   
   private Connection cxn;
   private volatile int reroute = REROUTE_NONE;
   private PlayersListener playersListener = new PlayersListener();
   private volatile Thread playersListenerThread;
   private DefaultListModel playerListModel;
   
    /** Creates new form MainWindow */
    public MainWindow() {
        initComponents();
        playerListModel = new DefaultListModel();
        playerList.setModel(playerListModel);
        playersListenerThread = new Thread(playersListener);
        playersListenerThread.setDaemon(true);
        updateTitle("MC Remote Admin");
        setComponentsEnabled(false);
        this.setVisible(true);
    }

   protected synchronized void setReroute(int type)
   {
       reroute = type;
   }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jScrollPane1 = new javax.swing.JScrollPane();
      logArea = new javax.swing.JTextArea();
      commandField = new javax.swing.JTextField();
      sendButton = new javax.swing.JButton();
      jScrollPane2 = new javax.swing.JScrollPane();
      playerList = new javax.swing.JList();
      jLabel1 = new javax.swing.JLabel();
      jTabbedPane1 = new javax.swing.JTabbedPane();
      jMenuBar1 = new javax.swing.JMenuBar();
      jMenu1 = new javax.swing.JMenu();
      connectMenuItem = new javax.swing.JMenuItem();
      quitMenuItem = new javax.swing.JMenuItem();
      jMenu2 = new javax.swing.JMenu();
      jMenu3 = new javax.swing.JMenu();
      jMenu4 = new javax.swing.JMenu();
      jMenu5 = new javax.swing.JMenu();

      logArea.setColumns(20);
      logArea.setRows(5);
      jScrollPane1.setViewportView(logArea);

      commandField.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyReleased(java.awt.event.KeyEvent evt) {
            commandFieldKeyReleased(evt);
         }
      });

      sendButton.setText("Send");
      sendButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            sendButtonActionPerformed(evt);
         }
      });

      jScrollPane2.setViewportView(playerList);

      jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
      jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel1.setText("Connected Users");

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      jMenu1.setText("File");

      connectMenuItem.setText("Connect");
      connectMenuItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            connectMenuItemActionPerformed(evt);
         }
      });
      jMenu1.add(connectMenuItem);

      quitMenuItem.setText("Quit");
      quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            quitMenuItemActionPerformed(evt);
         }
      });
      jMenu1.add(quitMenuItem);

      jMenuBar1.add(jMenu1);

      jMenu2.setText("Tools");
      jMenuBar1.add(jMenu2);

      jMenu3.setText("Server");
      jMenuBar1.add(jMenu3);

      jMenu4.setText("Advanced");
      jMenuBar1.add(jMenu4);

      jMenu5.setText("Help");
      jMenuBar1.add(jMenu5);

      setJMenuBar(jMenuBar1);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitMenuItemActionPerformed
    {//GEN-HEADEREND:event_quitMenuItemActionPerformed
       System.exit(0);
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void connectMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_connectMenuItemActionPerformed
    {//GEN-HEADEREND:event_connectMenuItemActionPerformed
       if (cxn == null)
       {
          new ConnectWindow(this);
       } else
       {
          disconnect();
       }
    }//GEN-LAST:event_connectMenuItemActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_sendButtonActionPerformed
    {//GEN-HEADEREND:event_sendButtonActionPerformed
       send(commandField.getText());
       SwingUtilities.invokeLater(new Runnable() {
         public void run()
         {
            commandField.setText("");
         }
       });
    }//GEN-LAST:event_sendButtonActionPerformed

    private void commandFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_commandFieldKeyReleased
    {//GEN-HEADEREND:event_commandFieldKeyReleased
       if (evt.getKeyCode() == KeyEvent.VK_ENTER)
       {
          send(commandField.getText());
          SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
               commandField.setText("");
            }
          });
       }
    }//GEN-LAST:event_commandFieldKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

   private void updateTitle(final String newTitle)
   {
      SwingUtilities.invokeLater(new Runnable() {
         public void run()
         {
            setTitle(newTitle);
         }
      });
   }

    public synchronized void setConnection(Connection c)
    {
       cxn = c;
       updateTitle(cxn.getIp() + ":" + cxn.getPort());
       logArea.setText("");
       log("[INFO] Connected to " + cxn.getIp() + " at port " + cxn.getPort());
       setComponentsEnabled(true);
       SwingUtilities.invokeLater(new Runnable() {
         public void run()
         {
            connectMenuItem.setText("Disconnect");
         }
       });

       // start the player list thread
       if (playersListenerThread.isAlive())
       {
         playersListenerThread.notify();
       } else
       {
          playersListenerThread.start();
       }
    }

   private void setComponentsEnabled(final boolean enabled)
   {
      SwingUtilities.invokeLater(new Runnable() {
         public void run()
         {
            logArea.setEnabled(enabled);
            commandField.setEnabled(enabled);
            sendButton.setEnabled(enabled);
         }
      });
   }

   private synchronized void disconnect()
   {
      playersListener.kill();
      cxn.close();
      cxn = null;
      updateTitle("MC Remote Admin");
      setComponentsEnabled(false);
      SwingUtilities.invokeLater(new Runnable() {
         public void run()
         {
            connectMenuItem.setText("Connect");
         }
       });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JTextField commandField;
   private javax.swing.JMenuItem connectMenuItem;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JMenu jMenu1;
   private javax.swing.JMenu jMenu2;
   private javax.swing.JMenu jMenu3;
   private javax.swing.JMenu jMenu4;
   private javax.swing.JMenu jMenu5;
   private javax.swing.JMenuBar jMenuBar1;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JTabbedPane jTabbedPane1;
   private javax.swing.JTextArea logArea;
   private javax.swing.JList playerList;
   private javax.swing.JMenuItem quitMenuItem;
   private javax.swing.JButton sendButton;
   // End of variables declaration//GEN-END:variables

   private void log(String message)
   {
      message = message.trim();
      //System.out.println("logging: " + message);
      logArea.append(message + "\n");
   }

   public void handle(int code, String... messages)
   {
      if (code == DataHandler.MESSAGE)
      {
         if (reroute == REROUTE_NONE)
         {
            String message = (messages.length == 0) ? null : messages[0];
            log(message);
         } else if (reroute == REROUTE_PLAYERSLISTENER)
         {
            playersListener.handle(messages[0]);
         }
      } else if (code == DataHandler.END_OF_STREAM)
      {
         if (cxn != null)
         {
            JOptionPane.showMessageDialog(this, "Disconnected.","Error", JOptionPane.WARNING_MESSAGE);
            disconnect();
         }
      } else if (code == DataHandler.TIMEOUT)
      {
         log("Connection timed out.");
         setComponentsEnabled(false);
      }
   }

   public void send(String command)
   {
      cxn.sendCommand(command);
   }

   private void updatePlayerList(String[] players)
   {
      //System.out.println("Players: " + Arrays.deepToString(players));

      String[] currPlayerStrs = new String[playerListModel.size()];
      playerListModel.copyInto(currPlayerStrs);
      ArrayList<String> currentList = new ArrayList<String>();
      currentList.addAll(Arrays.asList(currPlayerStrs));
      //System.out.println("Current Players: " + Arrays.deepToString(currPlayerStrs));
      
      ArrayList<String> updatedList = new ArrayList<String>();
      updatedList.addAll(Arrays.asList(players));

      ArrayList<String> concurrentList = new ArrayList<String>();

      for (String player : updatedList)
      {
         for (String currentPlayer : updatedList)
         {
            //System.out.println(player + "=" + currentPlayer + "?");
            if (player.equals(currentPlayer))
            {
               concurrentList.add(player);
            }
         }
      }

      //System.out.print("Concurrent list: " + concurrentList.toString());
      //System.out.println(" Updated list: " + updatedList.toString());

      if (concurrentList.size() > currentList.size()) // player(s) joined
      {
         for (String currentPlayer : concurrentList)
         {
            if (!currentList.contains(currentPlayer))
            {
               // the currentPlayer joined
               //System.out.println("Adding: " + currentPlayer);
               playerListModel.addElement(currentPlayer);
            }
         }
      } else if (currentList.size() < concurrentList.size()) // player left
      {
         for (String currentPlayer : currentList)
         {
            if (!concurrentList.contains(currentPlayer))
            {
               // the currentPlayer joined
               //System.out.println("Removing: " + currentPlayer);
               playerListModel.removeElement(currentPlayer);
            }
         }
      } else {
         //System.out.println("No users to add or remove");
      }


   }

   private class PlayersListener implements Runnable
   {
      private boolean keepRunning = true;

      protected void kill()
      {
         keepRunning = false;
      }

      protected void handle(String message)
      {
         message = message.trim();
         final String SEARCH_STR = "Connected players:";
         //System.out.println("listener thread handling: " + message);
         int playersListIndex = message.indexOf(SEARCH_STR) + SEARCH_STR.length();
         String playersList = message.substring(playersListIndex).trim();
         //System.out.println("Players: {" + playersList + "}");
         String[] playersArray = playersList.split(",");
         updatePlayerList(playersArray);
         setReroute(REROUTE_NONE);
      }

      public void run()
      {
         while (keepRunning && cxn != null)
         {
            try {
               Thread.sleep(3000);
               setReroute(REROUTE_PLAYERSLISTENER);
               send("list");
            } catch (InterruptedException ex) {
               Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         }
      }
   }

}