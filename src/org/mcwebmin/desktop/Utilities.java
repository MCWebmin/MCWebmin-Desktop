package org.mcwebmin.desktop;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Joe Stein
 */
public class Utilities
{
   public static JFrame INVISIFRAME = new JFrame();
   private static final String ROOT_NODE = "/org/mcwebmin/desktop";
   private static Preferences prefs = Preferences.userRoot().node(ROOT_NODE);
   private static ResourceBundle resources;

   static
   {
      resources = ResourceBundle.getBundle("org.mcwebmin.desktop.resources.preferences");
   }

   public static boolean setLookAndFeel()
   {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         return true;
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
      } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
      }
      return false;
   }

   public static void setPref(String field, String value)
   {
      prefs.put(field, value);
      try {
         prefs.flush();
      } catch (BackingStoreException ex) {
         Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public static void setPref(String field, String node, String value)
   {
      prefs.node(node).put(field, value);
      try {
         prefs.flush();
      } catch (BackingStoreException ex) {
         Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public static String getPref(String field)
   {
      return getPref(field,ROOT_NODE);
   }

   public static String getPref(String field, String node)
   {
      try {
         if (prefs.nodeExists(node)) {
            String value = prefs.node(node).get(field, null);
            if (value == null)
            {
               // fall back on preferences properties file
               return resources.getString(field);
            } else
            {
               return value;
            }
         } else
         {
            try
            {
               return resources.getString(field);
            } catch (Exception e)
            {
               // couldn't find it in the properties file either :(
               return null;
            }
         }
      } catch (BackingStoreException ex) {
         ex.printStackTrace();
         return null;
      } catch (NullPointerException npe)
      {
         npe.printStackTrace();
         return null;
      }
   }

   protected static void initPrefs()
   {
      try {
         if (!prefs.nodeExists(ROOT_NODE)) {
            prefs.node("network").putInt("minequery_port", 25570);
            prefs.node("network").putInt("multi_port", 9001);
         }
      } catch (BackingStoreException ex) {
         
      }
   }

   public static void main(String[] args)
   {
      initPrefs();
      System.out.println(getPref("minequery_port","network"));
      System.out.println(getPref("multi_port","network"));
      System.out.println(getPref("multrt","nasdfwork"));
   }
}
