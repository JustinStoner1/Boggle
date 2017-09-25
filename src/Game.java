import java.util.ArrayList;

public class Game
{
  //private String dictionaryFilename;
  private Dictionary dictionary;

  private Board board;
  private int widthHeight;

  public Game(String dictionaryFilename, int widthHeight)
  {
    //this.dictionaryFilename = dictionaryFilename;
    dictionary = new Dictionary(dictionaryFilename);
    this.widthHeight = widthHeight;
    board = new Board(widthHeight);
  }

  public boolean takeTurn(String userInput)
  {
    if (userInput.length() > 2)
    {
      boolean isInDictionary = dictionary.isInDictionary(userInput.toLowerCase());
      boolean isInBoard = board.containsWord(userInput);

      if (isInBoard)
      {
        //System.out.println("Is In Board");
        if (isInDictionary)
        {
          //System.out.println("Is In Dictionary");
          //tests();
          return true;
        }
      }
    }
    //tests();
    return false;
  }

  public ArrayList<ArrayList<String>> getBoard()
  {
    return board.getBoard();
  }

  public String toString()
  {
    return board + "";
  }
}
