//***********************************
//Justin Stoner
//
//Description:
// Stores words the player has played, good or bad
//***********************************

import java.util.ArrayList;

public class WordList
{
  private ArrayList<String> wordList;

  //***********************************
  //input: void
  //returns: NA
  //initializes the list
  //***********************************
  public WordList()
  {
    wordList = new ArrayList<>();
  }
  //***********************************
  //input: String word
  //returns: void
  //adds a word to the list
  //***********************************
  public void addWord(String word)
  {
    wordList.add(word);
  }
  //***********************************
  //input: String void
  //returns: void
  //checks if a word is in the list
  //***********************************
  public boolean isInList(String word)
  {
    return wordList.contains(word);
  }
  //***********************************
  //input: void
  //returns: String
  //returns a string rep of the list
  //***********************************
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
