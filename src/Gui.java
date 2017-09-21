import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gui extends Application
{
  static String dictionaryFilename = "assets/OpenEnglishWordList.txt";
  public static void main(String[] args)
  {
    Dictionary dictionary = new Dictionary(dictionaryFilename);
    System.out.println("Da");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage)
  {
    primaryStage.setTitle("Boggle");

    StackPane root = new StackPane();
    primaryStage.setScene(new Scene(root, 300, 250));
    primaryStage.show();
  }
}