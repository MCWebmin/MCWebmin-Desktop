package org.mcwebmin.desktop;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joe Stein
 */
public class Connection
{
   private Socket sock;
   private Thread listener;
   private DataHandler handler;

   public Connection(String host, int port, DataHandler dh) throws UnknownHostException, IOException
   {
      handler = dh;
      sock = new Socket(host,port);
      listener = new Thread(new DataListener());
      listener.start();
   }

   public Connection(String host, int port, DataHandler dh, int timeout) throws SocketException, UnknownHostException, IOException
   {
      handler = dh;
      sock = new Socket();
      //sock.setSoTimeout(timeout);
      sock.connect(new InetSocketAddress(host,port),timeout);
      listener = new Thread(new DataListener());
      listener.start();
   }

   public boolean close()
   {
      if (!sock.isClosed())
      {
         try
         {
            sock.close();
            return true;
         } catch (IOException ex)
         {
            return false;
         }
      }
      return true;
   }

   public boolean sendCommand(String message)
   {
      try
      {
         System.out.println("[LOCAL] Sending: " + message);
         byte[] data = message.getBytes();
         sock.getOutputStream().write(data);
         return true;
      } catch (IOException ex)
      {
         return false;
      }
   }

   protected void receive(String message)
   {
      if (message.indexOf("enter the password") > -1)
      {
         handler.handle(DataHandler.PASSWORD_PROMPT);
      } else if (message.indexOf("Bad password") > -1)
      {
         handler.handle(DataHandler.BAD_PASSWORD);
      } else if (message.indexOf("Access granted") > -1)
      {
         handler.handle(DataHandler.AUTHENTICATED);
      } else {
         handler.handle(DataHandler.MESSAGE, message);
      }
      System.out.println("[REMOTE] " + message);
   }

   protected void receive(byte[] data)
   {
      String message = "";
      for (byte datum: data)
      {
         message += (char) datum;
      }
      receive(message);
   }

   public boolean isAlive()
   {
      System.out.println("closed: " + sock.isClosed());
      System.out.println("connected: " + sock.isConnected());
      System.out.println("read closed: " + sock.isInputShutdown());
      return !sock.isClosed();
   }

   public void setHandler(DataHandler newHandler)
   {
      handler = newHandler;
   }

   public String getIp()
   {
      if (sock != null)
      {
         return sock.getInetAddress().getHostName();
      } else
      {
         return null;
      }
   }

   public int getPort()
   {
      if (sock != null)
      {
         return sock.getPort();
      } else
      {
         return -1;
      }
   }

   private class DataListener implements Runnable
   {
      private boolean keepRunning = true;
      public void kill()
      {
         keepRunning = false;
      }
      public void run()
      {
         InputStreamReader is = null;
         try {
            is = new InputStreamReader(sock.getInputStream());
         } catch (IOException ex) {
            keepRunning = false;
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
         }
         while (keepRunning)
         {
            try
            {
               //byte[] data = new byte[1024];
              // if (sock.getInputStream().read(data) == -1)
               char[] buffer = new char[1024];
               int read = is.read(buffer);
               if (read == -1)
               {
                  throw new IOException("EOS reached");
               }
               String data = "";
               for (char ch : buffer)
               {
                  data += ch;
               }
               data = data.trim();
               if (data == null || data == "")
               {
                  System.out.println("@Connection: data is null, EOS");
                  handler.handle(DataHandler.END_OF_STREAM);
               } else
               {
                  receive(data);
               }
            } catch (IOException ex)
            {
               //ex.printStackTrace();
               System.out.println("@Connection: IOException, sending EOS");
               handler.handle(DataHandler.END_OF_STREAM);
               keepRunning = false;
            }
         }
      }
   }
}
