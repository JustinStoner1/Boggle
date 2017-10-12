//***********************************
//Justin Stoner
//
//Description:
// Represents the game, keeps track of the data
// Processes user input and enforces the rules
//***********************************

import java.util.ArrayList;

public class Game
{
  private int widthHeight;
  private Dictionary dictionary;
  private Board board;
  private int score;
  private int wordCount;
  private int lastErrorCode;
  //***********************************
  //input: String dictionaryFilename, int widthHeight
  //returns: NA
  //initializes the list
  //***********************************
  public Game(String dictionaryFilename, int widthHeight)
  {
    this.widthHeight = widthHeight;

    dictionary = new Dictionary(dictionaryFilename);
    board = new Board(widthHeight);

    /*
    int numWords = 0;
    ArrayList<String> words = dictionary.getDictionary();
    for (String word : words)
    {
      if (board.containsWord(word))
      {
        System.out.println(word);
        numWords++;
      }
    }
    System.out.println(numWords);
    */
  }
  //***********************************
  //input: String dictionaryFilename, int widthHeight
  //returns: boolean
  //process the users input
  //decides if it is legal and updates the game data
  //updates the error codes
  //***********************************
  public boolean takeTurn(String userInput)
  {
    if (userInput.length() > 2)
    {
      wordCount++;
      userInput = userInput.toLowerCase();
      boolean isInDictionary = dictionary.isInDictionary(userInput);
      boolean isInBoard = board.containsWord(userInput);

      if (isInBoard)
      {
        if (isInDictionary)
        {
          score += userInput.length() - 2;
          return true;
        }
        else lastErrorCode = 4;//Not a word
      }
      else lastErrorCode = 3;//Not in Board
    }
    else lastErrorCode = 1;
    return false;//Too Small
  }
  //***********************************
  //input: void
  //returns: ArrayList<ArrayList<String>>
  //returns the board(a copy)
  //***********************************
  public ArrayList<ArrayList<String>> getBoard()
  {
    return board.getBoard();
  }
  //***********************************
  //input: void
  //returns: int
  //returns the score
  //***********************************
  public int getScore()
  {
    return score;
  }
  //***********************************
  //input: void
  //returns: int
  //returns the number of words played
  //***********************************
  public int getWordCount()
  {
    return wordCount;
  }
  //***********************************
  //input: void
  //returns: int
  //returns the last error code
  //***********************************
  public int getLastErrorCode()
  {
    return lastErrorCode;
  }
  //***********************************
  //input: void
  //returns: String
  //returns string rep of the board
  //***********************************
  public String toString()
  {
    return board + "";
  }
}
