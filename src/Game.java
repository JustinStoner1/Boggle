import java.util.ArrayList;

public class Game
{
  private int widthHeight;
  private Dictionary dictionary;
  private Board board;
  private WordList goodWords;
  private WordList badWords;
  private int score;
  private int wordCount;
  private int lastErrorCode;

  public Game(String dictionaryFilename, int widthHeight)
  {
    this.widthHeight = widthHeight;

    dictionary = new Dictionary(dictionaryFilename);
    board = new Board(widthHeight);
    goodWords = new WordList();
    badWords = new WordList();

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

  public boolean takeTurn(String userInput)
  {
    if (userInput.length() > 2)
    {
      wordCount++;
      userInput = userInput.toLowerCase();
      boolean isInDictionary = dictionary.isInDictionary(userInput);
      boolean isInBoard = board.containsWord(userInput);

      if (!goodWords.isInList(userInput))
      {
        if (isInBoard)
        {
          if (isInDictionary)
          {
            goodWords.addWord(userInput);
            score += userInput.length() - 2;
            return true;
          }
          else lastErrorCode = 4;//Not a word
        }
        else lastErrorCode = 3;//Not in Board
      }
      else
      {
        lastErrorCode = 2;//Already Played
        return false;
      }
    }
    else lastErrorCode = 1;
    badWords.addWord(userInput);
    return false;//Too Small
  }

  public ArrayList<ArrayList<String>> getBoard()
  {
    return board.getBoard();
  }

  public String getGoodWords()
  {
    return goodWords+"";
  }
  public String getBadWords()
  {
    return badWords+"";
  }
  public int getScore()
  {
    return score;
  }
  public int getWordCount()
  {
    return wordCount;
  }
  public int getLastErrorCode()
  {
    return lastErrorCode;
  }

  public String toString()
  {
    return board + "";
  }
}
