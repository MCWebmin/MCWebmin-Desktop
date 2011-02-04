package org.mcwebmin.desktop;

/**
 *
 * @author Joe Stein
 */
public interface DataHandler
{
   public static int END_OF_STREAM = -999;
   public static int BAD_PASSWORD = -1;
   public static int PASSWORD_PROMPT = 0;
   public static int CONNECTED = 1;
   public static int TIMEOUT = 2;
   public static int AUTHENTICATED = 3;
   public static int MESSAGE = 5;

   public void handle(int code, String... message);
   public void send (String command);
}
