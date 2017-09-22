import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Dictionary
{
  String fileName;
  ArrayList<String> dictionary = new ArrayList<String>();

  public Dictionary(String fileName)
  {
    this.fileName = fileName;

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
      // Always close files.
      bufferedReader.close();
    }
    catch (FileNotFoundException ex)
    {
      System.out.println("Dictionary txt file not found");
    }
    catch (IOException ex)
    {
      System.out.println("An error was encountered while reading in the dictionary");
      // Or we could just do this:
      // ex.printStackTrace();
    }
  }

  public boolean isInDictionary(String word)
  {
    return dictionary.contains(word);
  }

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
