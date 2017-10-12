//***********************************
//Justin Stoner
//
//Description:
// Grabs words from the provided file
// And puts them into an arraylist
// Also checks if a word exists inside itself
//***********************************

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Dictionary
{
  //final private String fileName;
  private ArrayList<String> dictionary = new ArrayList<>(178695);

  //***********************************
  //input: String fileName
  //returns: NA
  //takes words from a text file and adds them to the dictionary
  //***********************************
  public Dictionary(String fileName)
  {
    //this.fileName = fileName;

    // This will reference one line at a time
    String word;

    try
    {
      // FileReader reads text files in the default encoding.
      FileReader fileReader = new FileReader(fileName);

      // Always wrap FileReader in BufferedReader.
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while (true)
      {
        word = bufferedReader.readLine();
        if (word != null)
        {
          dictionary.add(word);
        }
        else break;
      }
      //System.out.println(dictionary.size());
      bufferedReader.close();
    }
    catch (FileNotFoundException ex)
    {
      System.out.println("Dictionary txt file not found");
    }
    catch (IOException ex)
    {
      System.out.println("An error was encountered while reading in the dictionary");
    }
  }
  //***********************************
  //input: String fileName
  //returns: boolean
  //takes words from a text file and adds them to the dictionary
  //***********************************
  public boolean isInDictionary(String word)
  {
    return dictionary.contains(word);
  }
  //***********************************
  //input: void
  //returns: ArrayList<String>
  //returns a copy of the dictionary
  //***********************************
  public ArrayList<String> getDictionary()
  {
    return new ArrayList<String>(dictionary);
  }
  //***********************************
  //input: void
  //returns: String
  //returns the dictionary
  //***********************************
  public String toString()
  {
    String stringRepOfDictionary = "";
    Iterator<String> dictionaryIterator = dictionary.iterator();
    while (dictionaryIterator.hasNext())
    {
      System.out.println(dictionaryIterator.next());
    }
    return stringRepOfDictionary;
  }
}
