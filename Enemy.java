import java.util.Random;
/** The Enemy class represents a physical enemy that the player will encounter in a monster room */
public class Enemy extends Entity
{
  /** Stores what item will be dropped by the enemy */
  private Item item;

  /**
  * The Enemy method initializes enemy's name, max health points, and item.
  *
  * @param String n - name of the Enemy.
  * @param int mHp - maximum health points of the enemy.
  * @param Item i - an item in the Enemy's inventory. 
  */
  public Enemy(String n, int mHp, Item i)
  {
    super(n,mHp);
    item = i;
  }

  /**
  * The getItem method returns the item that the enemy was holding
  * @return item - the item that the enemy was holding
  */
  public Item getItem()
  {
    return item;
  }

  /**
  * The attack method passes an entity that will be attacked. A physical attack is performed against the entity passed
  *
  * @param Entity e - The Entity that will be attacked
  * @return - Returns a String that states how much damage the enemy dealt to the entity passed.
  */
  @Override
  public String attack(Entity e)
   {
     Random rand = new Random();
     int dmgTaken = rand.nextInt(6); //Physical attacks deal 0-5 damage
     e.takeDamage(dmgTaken);
     return this.getName() + " attacks " + e.getName() + " for " + dmgTaken + " damage.";
   }
}