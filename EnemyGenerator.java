import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

/** The EnemyGenerator class reads the information of an enemy from a text file and can pick one at random to generate */
public class EnemyGenerator
{
  /** An ArrayList of Enemy objects that will store all of the possible enemeies' stats */
  private ArrayList <Enemy> enemyList;
  /** An ItemGenerator object that will generate a random item for the enemy to carry */
  private ItemGenerator ig;
  /**
  * The EnemyGenerator method is the constuctor of the class
  * Initializes enemyList and ig. The method reads all of the enemies' stats from a text file to create templates of enemies and stores them in enemyList.
  *
  * @param ItemGenerator ig - item generator object
  */
  public EnemyGenerator(ItemGenerator iG)
  {
    enemyList = new ArrayList <Enemy>();
    ig = new ItemGenerator();
    ig = iG;
    try
    {
      Scanner read = new Scanner(new File ("EnemyList.txt")); //Check to see if the text file with all of the enemy info is present in the folder
      while (read.hasNext()) // Loops until there are no more lines of enemy info in the EnemyList text file.
      {
        Enemy e;
        String line = read.nextLine();
        String [] enemyInfo = line.split(","); //The first elemnt in the array is the name of the enemy, second is the HP, and the third is its type
        if(enemyInfo[2].equals("p")) // Creates a physical enemy
          e = new Enemy(enemyInfo[0], Integer.parseInt(enemyInfo[1]), ig.generateItem()); 
        else // Creates a magical enemy
          e = new MagicalEnemy(enemyInfo[0], Integer.parseInt(enemyInfo[1]), ig.generateItem());
        enemyList.add(e);
      }
      read.close();
    }
    catch (FileNotFoundException fnf)// Will run if the file is not present in the folder 
    {
      System.out.println("File not found");
    }
  }

  /**
  * The generateEnemy method picks an enemy at random within the enemyList which was created in the constructor 
  *
  * @param int level - used to augemnt the enemy's HP.
  */
  public Enemy generateEnemy(int level)
  {
    Random rand = new Random();
    int enemy = rand.nextInt(enemyList.size());
    Enemy e = enemyList.get(enemy);
    if(e instanceof MagicalEnemy) //Checks for if the enemy that was picked is a magical enemy
      e = new MagicalEnemy(e.getName(),(e.getMaxHp()+level),e.getItem()); //Magical enemies will only recieve 1 extra HP for every level they are
    else //If the enemy ISN'T a magical enemy, then it must be a physical enemy
      e = new Enemy(e.getName(), (e.getMaxHp()+(level * 2)),e.getItem()); // Physical enemies will gain 2 HP per level

    return e;
  }
}