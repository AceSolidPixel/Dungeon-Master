/**Magical is an interface class used by MagicalEnemy and Hero class*/
public interface Magical
{
  /** Displays menu for types of magic */
  public static final String MAGIC_MENU = "1. Magic Missile\n2. Fireball\n3. Thunderclap ";
  /** Displays magicMissile attack */
  public String magicMissile(Entity e);
  /** Displays fireball attack */
  public String fireball(Entity e);
  /** Displays thunderclap attack */
  public String thunderclap(Entity e);
  
}