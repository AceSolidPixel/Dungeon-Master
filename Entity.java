public abstract class Entity
{
   /** Stores the name of the entity */
   private String name;
   /** Stores the maximum possible amount of Hit Points the entity may have */
   private int maxHp;
   /** Stores the current HP of the entity */
   private int hp;
   /**
   * Constructor for this class. Sets the name and maximum HP of the entity 
   * 
   * @param String n - a string used for the naming of the entity
   * @param int mHp - an integer that is used to set both the hp and maxHp since all entities start at full HP 
   */
   public Entity(String n, int mHp)
   {
      name = n;
      hp = mHp;
      maxHp = mHp;
   }

   /** Abstract method to be overridden by subclasses */
   public abstract String attack(Entity e);

   /*
   * Returns the entity's name
   * @return name - Returns the entity's name
   */
   public String getName()
   {
      return name;
   }
   
   /*
   * Returns the entity's HP
   * @return hp - Returns the entity's HP
   */
   public int getHp()
   {
      return hp;
   }
   /*
   * Returns the entity's maximum HP
   * @return maxHp - Returns the entity's maximum HP
   */
   public int getMaxHp()
   {
      return maxHp;
   }
   /*
   * Increases the entity's HP by a specified amount
   * @param int h - the value by which the HP will increase
   */
   public void heal(int h)
   {
      hp += h;
      if(hp > maxHp)// Doesn't allow an entity's current hp go higher than its Max HP
        hp = maxHp;
   }
   /*
   * Decreases the entity's HP by a specified amount
   * @param int h - the value by which the HP will decrease
   */
   public void takeDamage(int h)
   {
      hp -= h;
      if(hp < 0) // An entity's HP will never be negative
        hp = 0;
   }
   /*
   * Returns the name, current hp, and the max hp of the entity
   * @return - Returns the name, current hp, and the max hp of the entity
   */
   @Override
   public String toString()
   {
      return getName() + "\nHP: " + getHp() + "/" + getMaxHp(); 
   }
}