import java.util.Random;

/** MagicalEnemy class is a subclass of Enemy and uses the Magical interface */
public class MagicalEnemy extends Enemy implements Magical
{
  /** Overloaded Constructor of MagicalEnemy that sets the name, max health, and item  */
  public MagicalEnemy(String name, int mHp, Item i)
  {
    super(name, mHp, i);
  }

  /** 
  * String attack chooses a random magical attack on the hero
  *
  * @param Entity e - the entity that will be attacked by the MagicalEnemy
  * @return calls one of the three magical attack methods
  */
  @Override
  public String attack(Entity e)
  {
    Random rand = new Random();
    int attack = rand.nextInt(3);
    switch(attack)
    {
      case 0:
        return magicMissile(e);
      case 1:
        return fireball(e);
      case 2:
        return thunderclap(e);
      default:
        return "";
    }
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
}
