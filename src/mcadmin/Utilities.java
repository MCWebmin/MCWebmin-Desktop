/*
 *  Copyright 2010 Joe Stein.
 * 
 */

package mcadmin;

import java.util.logging.Level;
import java.util.logging.Logger;
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
}
