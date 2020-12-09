import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/** The map class is used to read and display a 5x5 grid that acts as a dungeon */
public class Map
{
  /** map creates a 2 dimensional grid  */
  private char[][] map;
  /** revealed is a 2 dimensional grid for the previously discoverd path */
  private boolean[][] revealed;
  /** 
  * The Map mathod is the Constructor of this class. It initializes the size of the map and the size of the revealed map 
  */
  public Map()
  {
    map = new char [5][5];
    revealed = new boolean [5][5];
  }
  /** 
  * LoadMap loads a new map from one of the three .txt files 
  *
  * @param int mapNum - determines which map text file will load up
  */
  public void loadMap(int mapNum)
  {
    for(int i = 0; i < 5; i++)
    {
      for(int j = 0; j < 5; j++)
      {
        revealed[i][j] = false;
      }
    }
    Map m = new Map();
    String selectedMap = "Map" + ((mapNum % 3) + 1 + ".txt"); //The fourth map will have the same layout at the first map,
  try                                                         // the fifth map with be the same as the second and so on.
    {
      int row = 0; //Will keep track of which row the map is on
      Scanner readFile = new Scanner(new File(selectedMap));
      while(readFile.hasNext()) // This will loop until there are no more lines of text to add into the array
      {
        String line = readFile.nextLine();
        char [] info = line.toCharArray();;
        for(int i = 0; i < 10; i += 2) // Have to use every other char since every even char is a ' '
          map[row][i/2] = info[i];
        row++;
      } 
      readFile.close();
    }catch(FileNotFoundException e) // Checks to see if the file is in the same folder as this program
      {                             // Also Checks to see if the text file has the right name
        System.out.println("File Not Found - place in project folder");
      } 
  }

  /** 
  * getCharAtLoc determines what character is currently inhabiting the space on the grid 
  *
  * @param Point p - is the coordinates of the char
  * @return - the current position of the char 
  */
  public char getCharAtLoc(Point p)
  {
    return map[p.x][p.y];
  }

  /** 
  * Outputs the 5x5 grid and sets each character on the grid as x except the starting Point
  *
  * @param Point p - is used to set each point on the grid, depending on whether they are revealed or not
  */
  public void displayMap(Point p)
  {
    for (int i = 0; i < 5; i++) 
    {
			for (int j = 0; j < 5; j++) 
      {
				if (!(i == p.x && j == p.y))
        {
					if (revealed[i][j]) 
						System.out.print(map[i][j]);
				  else
						System.out.print('x');
					
				} 
        else 
					System.out.print('*');
				System.out.print(" ");
			}
			System.out.println();
		}
  }

  /** 
  * Used to find the s character on the map and sets it as the starting point
  * 
  * @return - returns the coordinates of where the starting point is
  */
  public Point findStart()
  {
   Point position = new Point(0,0);
		for (int i = 0; i < 5; i++) 
    {
			for (int j = 0; j < 5; j++) 
      {
				if (map[i][j] == 's') 
        {
					position.x = i;
					position.y = j;
          revealed[i][j] = true;
				}
			}
		}
    return position;
  }

  /** 
  * Sets that location on the map to be revealed to the player
  *
  * @param Point p is set to true
  */
  public void reveal(Point p)
  {
    revealed[p.x][p.y] = true;
  }

  /** 
  * Removes the char that was previously there and replaces it with 'n' which would indicate an empty room
  *
  * @param Point p - the location of what is supposed to be changed to 'n'
  */
  public void removeCharAtLoc(Point p)
  {
    if(map[p.x][p.y] != 's')
      map[p.x][p.y] = 'n';
  }
}