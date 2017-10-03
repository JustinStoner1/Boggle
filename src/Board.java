import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board
{
  private ArrayList<ArrayList<String>> board = new ArrayList<>();
  private int widthHeight;
  private Random randGen = new Random();
  private String diceFaces = "abcdefghijklmnopqrstuvwxyz";//"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private int[] letterCounts = new int[diceFaces.length()];

  private int[] searchDirC = {0,1,1,1,0,-1,-1,-1};
  private int[] searchDirR = {-1,-1,0,1,1,1,0,-1};
  private int[] dirIndexs = {0,1,2,3,4,5,6,7};

  public Board(int widthHeight)
  {
    this.widthHeight = widthHeight;
    char currDiceFace;
    int currLetterIndex;

    for (int r = 0; r < widthHeight; r++)
    {
      ArrayList<String> row = new ArrayList<>();
      for (int c = 0; c < widthHeight; c++)
      {
        while (true)
        {
          currLetterIndex = randGen.nextInt(diceFaces.length());
          currDiceFace = diceFaces.charAt(currLetterIndex);
          if (letterCounts[currLetterIndex] > 4) continue;
          letterCounts[currLetterIndex]++;
          row.add("" + currDiceFace);
          break;
        }
      }
      board.add(row);
    }
    for (int r = 0; r < widthHeight; r++)
    {
      if (letterCounts[20] > 4) break;
      for (int c = 0; c < widthHeight; c++)
      {
        if (board.get(r).get(c).equals("q"))
        {
          int nC, nR;
          neighborLoop:
          for (int d = 0; d < 8;d++)
          {
            nC = c + searchDirC[d];
            nR = r + searchDirR[d];
            if (nC >= 0 && nR >= 0 && nR < widthHeight && nC < widthHeight)
            {
              int chanceToBeAU = randGen.nextInt(8);
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
    String firstLetter = ""+word.charAt(0);
    for (int r = 0; r < widthHeight; r++)
    {
      for (int c = 0; c < widthHeight; c++)
      {
        if (firstLetter.equals(board.get(r).get(c)))
        {
          //board.get(r).set(c, "*");
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

    String lookFor = ""+word.charAt(letterNumber);
    //System.out.println("looking for a: "+lookFor);

    int nC, nR;
    for (int d = 0; d < 8; d++)
    {
      nC = col + searchDirC[d];
      nR = row + searchDirR[d];
      if (nC >= 0 && nR >= 0 && nR < widthHeight && nC < widthHeight)
      {
        //board.get(nR).set(nC, "*");
        if (board.get(nR).get(nC).equals(lookFor))
        {
          //System.out.println("Found a: "+lookFor);
          //board.get(nR).set(nC, "@");
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
}
