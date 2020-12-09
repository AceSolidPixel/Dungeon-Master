public class Item
{
  /** Stores the name of the item */
  private String name;
  /**
  * Constructor for this class. Sets the name of the item
  */
  public Item (String n)
  {
    name = n;
  }

  /*
  * Returns the item's name
  * @return name - Returns the item's name
  */
  public String getName()
  {
    return name;
  }
}