import java.util.Scanner;

public class Game
{
  private String dictionaryFilename = "assets/OpenEnglishWordList.txt";
  private Dictionary dictionary = new Dictionary(dictionaryFilename);

  private Board board;
  private int widthHeight;

  public Game(int widthHeight)
  {
    this.widthHeight = widthHeight;
    board = new Board(widthHeight);
  }

  public boolean takeTurn(String userInput)
  {

    boolean isInDictionary = dictionary.isInDictionary(userInput.toLowerCase());
    boolean isInBoard = board.containsWord(userInput);

    if (isInBoard)
    {
      System.out.println("Is In Board");
      if (isInDictionary)
      {
        System.out.println("Is In Dictionary");
        tests();
        return true;
      }
    }
    tests();
    return false;
  }

  public void tests()
  {
    System.out.println(board);
  }

  public String toString()
  {
    return board + "";
  }
}
