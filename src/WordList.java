import java.util.ArrayList;

public class WordList
{
  private ArrayList<String> wordList;

  public WordList()
  {
    wordList = new ArrayList<>();
  }

  public void addWord(String word)
  {
    wordList.add(word);
  }

  public boolean isInList(String word)
  {
    return wordList.contains(word);
  }

  public String toString()
  {
    String outputString = "";
    for (String word: wordList)
    {
      outputString += word + "\n";
    }
    return outputString;
  }
}
