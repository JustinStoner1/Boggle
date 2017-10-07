import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board
{
  //Constants
  private final static String unVisitedSpot = "-";
  private final static String visitedSpot = "@";

  //Board
  private ArrayList<ArrayList<String>> board;
  private ArrayList<ArrayList<String>> visitationRecord;
  private int widthHeight;
  private String diceFaces = "abcdefghijklmnopqrstuvwxyz";//"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private int[] letterCounts = new int[diceFaces.length()];

  private int[] searchDirC = {0, 1, 1, 1, 0, -1, -1, -1};
  private int[] searchDirR = {-1, -1, 0, 1, 1, 1, 0, -1};

  public Board(int widthHeight)
  {
    this.widthHeight = widthHeight;
    char currDiceFace;
    int currLetterIndex;

    board = new ArrayList<>(widthHeight);
    visitationRecord = new ArrayList<>(widthHeight);

    Random randGen = new Random();//2:nan 12:dad
    for (int r = 0; r < widthHeight; r++)
    {
      ArrayList<String> row = new ArrayList<>(widthHeight);
      visitationRecord.add(new ArrayList<>(widthHeight));
      for (int c = 0; c < widthHeight; c++)
      {
        visitationRecord.get(r).add("-");
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
    resetVisted();
    String firstLetter = "" + word.charAt(0);
    for (int r = 0; r < widthHeight; r++)
    {
      for (int c = 0; c < widthHeight; c++)
      {
        if (firstLetter.equals(board.get(r).get(c)))
        {
          if (lookForACertainNeighbor(r, c, word, 0))
          {
            //showVisited();
            return true;
          }
        }
      }
    }
    //showVisited();
    return false;
  }

  public boolean lookForACertainNeighbor(int row, int col, String word, int letterNumber)
  {
    letterNumber++;
    visitationRecord.get(row).set(col,visitedSpot);
    if (letterNumber >= word.length())
    {
      return true;
    }

    String lookFor = "" + word.charAt(letterNumber);

    int nC, nR;
    for (int d = 0; d < 8; d++)
    {
      nC = col + searchDirC[d];
      nR = row + searchDirR[d];
      if (nC >= 0 && nR >= 0 && nR < widthHeight && nC < widthHeight)
      {
        if (board.get(nR).get(nC).equals(lookFor) && visitationRecord.get(nR).get(nC).equals(unVisitedSpot))
        {
          //System.out.println("["+(nC+1)+","+(nR+1)+"]:"+lookFor);
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
    return new ArrayList<ArrayList<String>>(board);
  }

  private void resetVisted()
  {
    for (int r = 0; r < widthHeight; r++)
    {
      visitationRecord.set(r,new ArrayList<>(widthHeight));
      for (int c = 0; c < widthHeight; c++)
      {
        visitationRecord.get(r).add(unVisitedSpot);
      }
    }
  }

  private void showVisited()
  {
    for (int r = 0; r < widthHeight; r++)
    {
      for (int c = 0; c < widthHeight; c++)
      {
        System.out.print(visitationRecord.get(r).get(c));
      }
      System.out.println("\n");
    }
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
}
