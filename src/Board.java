import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board
{
  private ArrayList<ArrayList<String>> board;
  private int widthHeight;
  private Random randGen = new Random();
  private String diceFaces = "abcdefghijklmnopqrstuvwxyz";//"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private int[] letterCounts = new int[diceFaces.length()];
  private ArrayList<ArrayList<String>> vistedSpots;

  private int[] searchDirC = {0, 1, 1, 1, 0, -1, -1, -1};
  private int[] searchDirR = {-1, -1, 0, 1, 1, 1, 0, -1};

  public Board(int widthHeight)
  {
    this.widthHeight = widthHeight;
    char currDiceFace;
    int currLetterIndex;

    board = new ArrayList<>();
    vistedSpots = new ArrayList<>();

    for (int r = 0; r < widthHeight; r++)
    {
      ArrayList<String> row = new ArrayList<>();
      vistedSpots.add(new ArrayList<>());
      for (int c = 0; c < widthHeight; c++)
      {
        vistedSpots.get(r).add("-");
        while (true)
        {
          currLetterIndex = randGen.nextInt(diceFaces.length());
          currDiceFace = diceFaces.charAt(currLetterIndex);
          if (letterCounts[currLetterIndex] > 3) continue;
          letterCounts[currLetterIndex]++;
          row.add("" + currDiceFace);
          break;
        }
      }
      board.add(row);
    }
    for (int r = 0; r < widthHeight; r++)
    {
      if (letterCounts[20] > 3) break;
      for (int c = 0; c < widthHeight; c++)
      {
        if (board.get(r).get(c).equals("q"))
        {
          int nC, nR;
          neighborLoop:
          for (int d = 0; d < 8; d++)
          {
            nC = c + searchDirC[d];
            nR = r + searchDirR[d];
            if (nC >= 0 && nR >= 0 && nR < widthHeight && nC < widthHeight)
            {
              int chanceToBeAU = randGen.nextInt(4);
              if (chanceToBeAU == 0)
              {
                board.get(nR).set(nC, "u");
                letterCounts[20]++;
                break neighborLoop;
              }
            }
          }
        }
      }
    }
  }

  public boolean containsWord(String word)
  {
    resetVisited();
    String firstLetter = "" + word.charAt(0);
    for (int r = 0; r < widthHeight; r++)
    {
      for (int c = 0; c < widthHeight; c++)
      {
        if (firstLetter.equals(board.get(r).get(c)))
        {
          vistedSpots.get(r).set(c, "@");
          if (lookForACertainNeighbor(r, c, word, 1))
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean lookForACertainNeighbor(int row, int col, String word, int letterNumber)
  {
    if (letterNumber >= word.length()) return true;

    String lookFor = "" + word.charAt(letterNumber);

    int nC, nR;
    for (int d = 0; d < 8; d++)
    {
      nC = col + searchDirC[d];
      nR = row + searchDirR[d];
      if (nC >= 0 && nR >= 0 && nR < widthHeight && nC < widthHeight)
      {
        if (board.get(nR).get(nC).equals(lookFor) && !vistedSpots.get(nR).get(nC).equals("@"))
        {
          vistedSpots.get(nR).set(nC, "@");
          letterNumber++;
          if (lookForACertainNeighbor(nR, nC, word, letterNumber))
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  public ArrayList<ArrayList<String>> getBoard()
  {
    return board;
  }

  public String toString()
  {
    String stringRepOfDictionary = "";
    Iterator<ArrayList<String>> boardIterator = board.iterator();
    while (boardIterator.hasNext())
    {
      stringRepOfDictionary += boardIterator.next() + "\n";
    }
    return stringRepOfDictionary;
  }

  public void resetVisited()
  {
    for (int r = 0; r < widthHeight; r++)
    {
      vistedSpots.set(r,new ArrayList<>());
      for (int c = 0; c < widthHeight; c++)
      {
        vistedSpots.get(r).add("-");
      }
    }
  }

  public void showVisted()
  {
    for (int i = 0;i < widthHeight; i++)
    {
      for (int j = 0;j < widthHeight; j++)
      {
        System.out.print(vistedSpots.get(i).get(j));
      }
      System.out.println("\n");
    }
  }
}
