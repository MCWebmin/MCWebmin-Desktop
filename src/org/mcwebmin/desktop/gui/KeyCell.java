/*
 *  Copyright 2010 Joe Stein.
 * 
 */

package org.mcwebmin.desktop.gui;

/**
 *
 * @author Joe Stein
 */
public class KeyCell
{
   private String name;
   private String key;
   private String node;

   public KeyCell(String name, String key)
   {
      this.name = name;
      this.key = key;
   }

   public KeyCell(String name, String key, String node)
   {
      this.name = name;
      this.key = key;
      this.node = node;
   }
   
   public String getKey()
   {
      return key;
   }

   public void setKey(String key)
   {
      this.key = key;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return name;
   }

   public String getNode()
   {
      return node;
   }
}
