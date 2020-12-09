// Prince Angulo
// Giovanni Contreras
// CECS 277
// Project 1
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

/**Main class: Makes use of all of the other classes to run the game */
class Main 
{
  public static void main(String[] args)
  {
    Random rand = new Random(); //Object for randomizing
    Scanner in = new Scanner(System.in);
    System.out.println("What is your name, traveler?");
    String name = in.nextLine();
    int mapNum = 0; //Will keep track of which map the player is on
    Map m = new Map();
    m.loadMap(mapNum);
    Hero h = new Hero(name, m);
    ItemGenerator ig = new ItemGenerator();
    EnemyGenerator eg = new EnemyGenerator(ig);
    int lvl = 1; //Used to pass into the enemyGenerator
    boolean flee = false;  //Check to see if running
    char currentChar = 'n'; //Checks what type of room the player is in

    boolean playing = true; 
    while(playing == true) //This will run until either the player dies or the player decides to quit in the menus
    {
      System.out.println(h.toString());
      m.displayMap(h.getLocation());
      boolean move = true;
      if(flee == true && currentChar == 'm')
        move = false;
      
      if(move == true) //Check which direction to move in
      {
        System.out.println("1. Go North");
        System.out.println("2. Go South");
        System.out.println("3. Go East");
        System.out.println("4. Go West");
        System.out.println("5. Quit");
        int input = getIntRange(1,5);
        switch(input)
        {
          case 1:
            currentChar = h.goNorth();
            break;
          case 2:
            currentChar = h.goSouth();
            break;
          case 3:
            currentChar = h.goEast();
            break;
          case 4:
            currentChar = h.goWest();
            break;
          case 5:
            System.out.println("Game Over!");
            playing = false;
            break;
        }
      }
      if(currentChar == 'm' && playing == true) //Checks for a monster room
      {
        boolean cleared = monsterRoom(h, m, eg, lvl);
        if(cleared == false)
        {
          boolean north = true; 
          boolean south = true;
          boolean east = true;
          boolean west = true;
          if (h.getLocation().y == 4) 
            east = false;
          if (h.getLocation().y == 0) 
            west = false;
          if (h.getLocation().x == 4) 
            south = false;
          if (h.getLocation().x == 0) 
            north = false;
          boolean [] validRunDirections = {north, south, east, west};
          boolean validRun = false;
          while(validRun == false) // Will loop until a valid run direction is chosen
          {
            int dir = rand.nextInt(validRunDirections.length);
            if(dir == 0 && validRunDirections[0] == true)
            {
              h.goNorth();
              validRun = true;
            }
            else if(dir == 1 && validRunDirections[1] == true)
            {
              h.goSouth();
              validRun = true;
            }
            else if(dir == 2 && validRunDirections[2] == true)
            {
              h.goEast();
              validRun = true;
            }
            else if(dir == 3 && validRunDirections[3] == true)
            {
              h.goWest();
              validRun = true;
            }
          }
        }
      }
      else if(currentChar == 'i' && playing == true) //Checks for an item room
        itemRoom(h,m,ig);
      else if(currentChar == 'n' && playing == true) // Checks for an empty room
        System.out.println("There is nothing in this room.");
      else if(currentChar == 's' && playing == true) //Checks to see if the player returned to the start of the floor
				System.out.println("You're back at the start.");
      System.out.println("------------------------------");
      if(m.getCharAtLoc(h.getLocation()) == 'f' && playing == true) //Checks to see if the player finished this floor
      {
        lvl += 1;
        mapNum += 1;
        h.heal(h.getMaxHp());
        System.out.println("Welcome to level " + lvl);
        m.loadMap(mapNum);
        m.findStart();
      }

      if(h.getHp() == 0) //Checks to see if the player died
      {
        System.out.println( h.getName() + " has died! Game Over");
        playing = false;
      }
    }
  }
  /**
  * monsterRoom calls fight() and loops until either the player runs, dies, or if the enemd died. 
  * Mostly just prompts the player 
  *
  * @param Hero h - the hero object 
  * @param Map m - the current map object
  * @param EnemyGenerator eg - the enemy generator object that will randomly generate a premade enemy
  * @param level - the current level of the enemy decided by how many floors the player has cleared
  * @return cleared - returns true when any of the previous conditions are met
  */
  public static boolean monsterRoom(Hero h, Map m, EnemyGenerator eg, int level)
  {
    Enemy e = eg.generateEnemy(level);
    System.out.println("You've encountered a(n) " + e.getName());
    boolean run = false;
    boolean cleared = false;
    while (h.getHp() != 0 && run == false && cleared == false) 
    {
      System.out.println(h.toString());
      System.out.println(e.getName() + "\nHP: " + e.getHp() + "/" + e.getMaxHp());
      System.out.println("1. Fight\n2. Run");
      int in = getIntRange(1, 2);
      if (in == 1) 
      {
        cleared = fight(h, e);
      } 
      else 
      {
        System.out.println("You escaped!");
        run = true;
      }
      
      if (cleared == true) 
      {
        m.removeCharAtLoc(h.getLocation());
      }
    
    }
	  return cleared;
  }
  /**
  * The fight methods represents the actual battle between the hero and the monster. 
  * 
  * @param Hero h - the hero object 
  * @param Enemy e - the monster object
  * @return win - true if the hero wins, false will be trurned otherwise
  */
  public static boolean fight(Hero h, Enemy e)
  {
    boolean win = false;
		System.out.println(h.attack(e));
		if(e.getHp() == 0) 
    {
			win = true;
			System.out.println("You defeated the " + e.getName());
			System.out.println("You received a " + e.getItem().getName() + " from its corpse.");
			if (h.pickUpItem(e.getItem()) == false)
      {
				System.out.println("Your inventory is full\n1. Drop an item from inventory and replace with new item\n2. Drop new item");
				int choice = getIntRange(1, 2);
				if (choice == 1) 
        {
					System.out.println("Choose an item to drop");
					System.out.println(h.itemsToString());
					int dropChoice = getIntRange(1, h.getNumItems());
					h.dropItem(dropChoice - 1);
					h.pickUpItem(e.getItem());
				}
			}
		} 
    else 
    {
			System.out.println(e.attack(h));
		}
		return win;
  }

  /**
  * The itemRoom method adds a randomly generated item to the hero's inventory
  * 
  * @param Hero h  - the hero object
  * @param Map m  - the current map object
  * @param ItemGenerator ig - the item generator object
  */
  public static void itemRoom(Hero h, Map m, ItemGenerator ig)
  {
    Item i = ig.generateItem();
    System.out.println("You've found a " + i.getName() + "!");
    if(h.getNumItems() < 5)
      h.pickUpItem(i);
    else
    {
      System.out.println("Your inventory is already full\n1. Drop an item from inventory and replace with new item\n2. Drop new item");
      int choice = getIntRange(1,2);
      if (choice == 1)
      {
        System.out.println("Choose an item to drop");
        System.out.println(h.itemsToString());
        int dropChoice = getIntRange(1,h.getNumItems());
        h.dropItem(dropChoice - 1);
        h.pickUpItem(i);
      }
    }
    m.removeCharAtLoc(h.getLocation());
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