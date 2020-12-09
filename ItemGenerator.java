import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
/** 
* ItemGenerator class makes a random item from the itemList.txt 
*/
public class ItemGenerator
{
  /** Stores the items into an ArrayList */
  private ArrayList<Item> itemList;
  /** 
  * The contructor of ItemGenerator reads from the text file of items and puts them into an ArrayList
  */
  public ItemGenerator()
  {
    itemList = new ArrayList<Item>();
    try
    {
      Scanner read = new Scanner(new File ("ItemList.txt"));
      while (read.hasNext())
      {
        String line = read.nextLine();
        Item i = new Item(line);
        itemList.add(i);
      }
      read.close();
    }
    catch (FileNotFoundException fnf)
    {
      System.out.println("File not found");
    }
  }
  /** 
  * Item generateItem creates a random item 
  *
  * @return - returns a randomly selected item from the itemList ArrayList
  */
  public Item generateItem()
  {
    Random rand = new Random();
    int item = rand.nextInt(itemList.size());
    return itemList.get(item);
  }
}