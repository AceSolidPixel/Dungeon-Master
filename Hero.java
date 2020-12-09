import java.util.ArrayList;
import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Hero extends Entity implements Magical
{
  /** ArrayList to store the items of the Hero */
  private ArrayList <Item> items;
  /** Will import the map for that level */
  private Map map;
  /** Will store the Hero's position */
  private Point location;
  
  /**
  * An overloaded contstructor for the Hero class. It initializes the hero's name, map, and sets the starting position of the Hero
  *
  * @param String n - name of the player.
  * @param Mapn m - the map the player is playing on.
  */
  public Hero(String n, Map m)
  {
    super(n, 25);
    items = new ArrayList <Item> ();
    map = m;
    location = map.findStart();
  }

  /**
  * Overriden toString method from Object. This method returns the Hero's name, HP, and any items the Hero may have
  *
  * @return s - returns a string which can be different depending on whether or not the Hero has any items
  */
  @Override
  public String toString()
  {
    String s = "";
    if(getNumItems() > 0) //If the hero has items, print out the Inventory along with the Hero's name and HP
      s += super.toString() + "\n" + itemsToString();
    else //If the Hero has no items, only return the Hero's name and HP
      s += super.toString();
    return s;
  }
  
  /**
  * This method returns the Hero's Inventory
  *
  * @return inv - returns a String with the list of items in the Hero's inventory
  */
  public String itemsToString()
  {
    String inv = "Inventory: ";
    for(int i = 0; i < getNumItems(); i++)
    {
      inv += "\n" + (i + 1) + ". " + items.get(i).getName();
    }
    return inv;
  }
  
  /**
  * Returns how many items the Hero has.
  *
  * @return - returns the size of the items ArrayList
  */
  public int getNumItems()
  {
    return items.size();
  }
  
  /**
  * This method adds any items passed to it to the Hero's inventory if it isn't alreadt full
  *
  * @return invFull - returns true if the inventory is full, returns false otherwise
  */

  public boolean pickUpItem(Item i)
  {
    boolean invFull = false;
    if(items.size() < 5) 
    {
      items.add(i);
      invFull = true;
    }
    return invFull;
  }
  
  /*
  * The drinkPotion method fully heals the Hero and removes the Health Potion from the Hero's inventory
  */
  public void drinkPotion()
  {
    heal(25);
    for(int i = 0; i < items.size(); i++)
    {
      if(items.get(i).getName().equals("Health Potion"))
      {
        dropItem(i);
        break;
      }
    }
  }
  
  /*
  * Removes the item at the specified index from the Hero's inventory
  * 
  * @param int index - the index where the item should be removed from
  */
  public void dropItem(int index)
  {
    items.remove(index);
  }
  
  /**
  * Checks to see if the hero has at least 1 Health Potion in their inventory
  */
  public boolean hasPotion()
  {
    for(int i = 0; i < getNumItems(); i++)
    {
      if((items.get(i).getName()).equals("Health Potion"))
        return true; 
    }
    return false;
  }

  /**
  * Returns the player's position on the map
  */
  public Point getLocation()
  {
    return location;
  }

  /**
  * Moves the Hero upwards on the map
  *
  * @return - returns the character at the new location 
  */
  public char goNorth()
  {
    map.removeCharAtLoc(location);
    if(location.x > 0)
    {
      location.x -= 1;
      map.reveal(location);
    }
    else
    {
      System.out.println("Cannot move outside of the dungeon!");
    }
    return map.getCharAtLoc(location);
  }

  /**
  * Moves the Hero downwards on the map
  *
  * @return - returns the character at the new location 
  */
  public char goSouth()
  {
    map.removeCharAtLoc(location);
    if(location.x < 4)
    {
      location.x += 1;
      map.reveal(location);
    }
    else
    {
      System.out.println("Cannot move outside of the dungeon!");
    }
    return map.getCharAtLoc(location);
  }

  /**
  * Moves the Hero to the right on the map
  *
  * @return - returns the character at the new location 
  */
  public char goEast()
  {
    map.removeCharAtLoc(location);
    if(location.y < 4)
    {
      location.y += 1;
      map.reveal(location);
    }
    else
    {
      System.out.println("Cannot move outside of the dungeon!");
    }
    return map.getCharAtLoc(location);
  }

  /**
  * Moves the Hero to the left on the map
  *
  * @return - returns the character at the new location 
  */
  public char goWest()
  {
    map.removeCharAtLoc(location);
    if(location.y > 0)
    {
      location.y -= 1;
      map.reveal(location);
    }
    else
    {
      System.out.println("Cannot move outside of the dungeon!");
    }
      return map.getCharAtLoc(location);
  }

  /**
  * Overridden method from Entity. The attack method passes an entity that will be attacked. A physical attack is performed against the entity passed.
  *
  * @param Entity e - The Entity that will be attacked
  * @return - Returns a String that states how much damage the enemy dealt to the entity passed.
  */ 
  @Override
  public String attack(Entity e)
  {
    Random rand = new Random();
    int dmgTaken = rand.nextInt(5);
    System.out.println("1. Physical Attack\n2. Magic Attack");
    String out = "";
    if (hasPotion() == true) //Will only print if the Hero has a Health Potion
    {
      System.out.println("3. Drink Health Potion");
    }
    
    int input = 0;
    if(hasPotion() == true)
      input = getIntRange(1, 3);
    else  
      input = getIntRange(1, 2);

    if(input == 1)
    {
      e.takeDamage(dmgTaken);
      out += this.getName() + " attacks " + e.getName() + " for " + dmgTaken + " damage.";
    }
    else if(input == 2)
    {
      System.out.println(MAGIC_MENU);
      int magicChoice = getIntRange(1, 3);
      switch(magicChoice)
      {
        case 1:
          out += magicMissile(e);
          break;
        case 2:
          out += fireball(e);
          break;
        case 3:
          out +=thunderclap(e);
          break;
      }
    }
    else if(input == 3) 
    {
      drinkPotion();
      out += this.getName() + " drinks a Health Potion. heals to full HP!";
    }
    return out;
  }

  /** 
  * The magicMissile method is passed an Entity that will be attacked by the Magic Missle Spell. 
  *
  * @param Entity e - The entity that will be attacked
  * @return - returns a string indicating how much damage was dealt
  */
  @Override
  public String magicMissile(Entity e)
  {
    Random rand = new Random();
    int dmgTaken = rand.nextInt(9); //All magic attacks can deal between 1-10 dmg and cannot miss
    e.takeDamage(dmgTaken + 1);
    return this.getName() + " uses Magic Missle on " + e.getName() + " for " + dmgTaken + " damage.";
  }

  /** 
  * The fireBall method is passed an Entity that will be attacked by the fireball Spell. 
  *
  * @param Entity e - The entity that will be attacked
  * @return - returns a string indicating how much damage was dealt
  */
  @Override
  public String fireball(Entity e)
  {
    Random rand = new Random();
    int dmgTaken = rand.nextInt(9); //All magic attacks can deal between 1-10 dmg and cannot miss
    e.takeDamage(dmgTaken + 1);
    return this.getName() + "uses Fireball on " + e.getName() + " for " + dmgTaken + " damage.";
  }

  /** 
  * The thunderlap method is passed an Entity that will be attacked by the thunderclap Spell. 
  *
  * @param Entity e - The entity that will be attacked
  * @return - returns a string indicating how much damage was dealt
  */
  @Override
  public String thunderclap(Entity e)
  {
    Random rand = new Random();
    int dmgTaken = rand.nextInt(9); //All magic attacks can deal between 1-10 dmg and cannot miss
    e.takeDamage(dmgTaken + 1);
    return this.getName() + " uses Thunderclap on " + e.getName() + " for " + dmgTaken + " damage.";
  }

  /**
	 * Checks if the inputted value is an integer and 
	 * within the specified range (ex: 1-10)
	 * @param low lower bound of the range.
	 * @param high upper bound of the range.
	 * @return the valid input.
	 */
	public static int getIntRange( int low, int high ) 
  {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while(valid == false) 
    {
			if(in.hasNextInt()) 
      {
				input = in.nextInt();
				if( input <= high && input >= low ) 
        {
					valid = true;
				} 
        else 
        {
					System.out.println( "Invalid Range." );
				}
			} 
      else 
      {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
  }
}