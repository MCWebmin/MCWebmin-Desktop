package mcadmin;

/**
 *
 * @author Joe Stein
 */
public class Player implements Comparable
{
   private String name;

   public Player(String n)
   {
      name = n;
   }

   @Override
   public String toString()
   {
      return name;
   }

   public String getName()
   {
      return name;
   }

   public int compareTo(Object o)
   {
      if (o instanceof Player)
      {
         if (((Player) o).getName().equals(this.name))
         {
            return 0;
         } else {
            return -1;
         }
      } else if (o instanceof String)
      {
         if (this.name.equals((String) o))
         {
            return 0;
         } else {
            return -1;
         }
      } else
      {
         throw new IllegalArgumentException("Must compare to another Player or String");
      }
   }

   @Override
   public boolean equals(Object o)
   {
      if (o instanceof Player || o instanceof String)
      {
         return (((Player) o).compareTo(this) == 0) ? true:false ;
      } else
      {
         throw new IllegalArgumentException("Must check if equals Player or String");
      }
   }

   @Override
   public int hashCode()
   {
      int hash = 3;
      hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
      return hash;
   }
}
