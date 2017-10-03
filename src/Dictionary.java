import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Dictionary
{
  //final private String fileName;
  private ArrayList<String> dictionary = new ArrayList<>();

  public Dictionary(String fileName)
  {
    //this.fileName = fileName;

    // This will reference one line at a time
    String word;

    try
    {
      FileReader fileReader = new FileReader(fileName);
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
    catch (FileNotFoundException error)
    {
      System.out.println("Dictionary txt file not found");
      System.exit(1);
    }
    catch (IOException error)
    {
      System.out.println("An error was encountered while reading in the dictionary");
      System.exit(1);
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
