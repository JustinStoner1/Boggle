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

  public Game(String dictionaryFilename, int widthHeight)
  {
    this.widthHeight = widthHeight;

    dictionary = new Dictionary(dictionaryFilename);
    board = new Board(widthHeight);
    goodWords = new WordList();
    badWords = new WordList();
  }

  public boolean takeTurn(String userInput)
  {
    if (userInput.length() > 2)
    {
      wordCount++;
      boolean isInDictionary = dictionary.isInDictionary(userInput.toLowerCase());
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
        }
      }
    }
    badWords.addWord(userInput);
    return false;
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

  public String toString()
  {
    return board + "";
  }
}
