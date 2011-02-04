package org.mcwebmin.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Joe Stein
 */
public class MineQueryCommunicator
{
   private int mineQueryPort;
   private String ip;
   private Socket sock;

   public MineQueryCommunicator()
   {
      // do nothing
   }

   public MineQueryCommunicator(String ipAddress)
   {
      ip = ipAddress;
      mineQueryPort = Integer.valueOf(Utilities.getPref("minequery_port","network"));
   }

   public MineQueryCommunicator(String ipAddress, int port)
   {
      ip = ipAddress;
      mineQueryPort = port;
   }

   protected void setPort(int port)
   {
      mineQueryPort = port;
   }

   protected int getPort()
   {
      return mineQueryPort;
   }

   protected void setIp(String host)
   {
      ip = host;
   }

   protected String getIp()
   {
      return ip;
   }

   public String[] getPlayers() throws IOException
   {
      String output = query();
      int firstBracket = output.indexOf("[") + 1;
      int secondBracket = output.indexOf("]",firstBracket);
      String playerStr = output.substring(firstBracket,secondBracket).replace(" ", "");
      String[] players = playerStr.split(",");
      return players;
   }

   public String query() throws IOException
   {
      BufferedReader in = null;
      try {
         sock = new Socket();
         sock.connect(new InetSocketAddress(ip, mineQueryPort), 5000);
         sock.getOutputStream().write("QUERY\n".getBytes());
         in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
         String input = "";
         String line = "";
         line = in.readLine();
         while (line != null)
         {
            input += line;
            line = in.readLine();
         }
         return input;
      } catch (IOException ex) {
         throw ex;
      } finally {
         if (in != null)
         {
            in.close();
         }
      }
   }

   public static void main(String[] args) throws IOException
   {
      MineQueryCommunicator mql = new MineQueryCommunicator("localhost");
      String[] players = mql.getPlayers();
      for (String player: players)
      {
         System.out.println(player);
      }
   }
}
