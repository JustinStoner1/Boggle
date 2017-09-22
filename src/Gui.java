import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application
{
  static String dictionaryFilename = "assets/OpenEnglishWordList.txt";
  static Dictionary dictionary = new Dictionary(dictionaryFilename);

  public static void main(String[] args)
  {

    //System.out.println(""+dictionary);
    //System.out.println(dictionary.isInDictionary("zymometers"));
    launch(args);
  }

  @Override
  public void start(Stage primaryStage)
  {
    primaryStage.setTitle("Boggle");
    StackPane window = new StackPane();

    Text messageBox = new Text("Enter a word to see if it is in our dictionary");
    BorderPane screen = new BorderPane();
    TextField userInputBar = new TextField();
    screen.setBottom(userInputBar);
    screen.setCenter(messageBox);
    window.getChildren().add(screen);

    userInputBar.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER)
      {
        String userInput = userInputBar.getText();
        boolean isInDictionary = dictionary.isInDictionary(userInput);
        if (isInDictionary)
        {
          messageBox.setText(userInput + " is in our dictionary");
        }
        else
        {
          messageBox.setText(userInput + " is not in our dictionary");
        }
        //System.out.println("Da");
        userInputBar.setText("");
      }
    });

    primaryStage.setScene(new Scene(window, 300, 100));
    primaryStage.show();
  }

}