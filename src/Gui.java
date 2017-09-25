import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Scanner;

public class Gui extends Application
{
  private Game boggle;

  public static void main(String[] args)
  {
    launch(args);
  }

  public void startAGame()
  {
    boggle = new Game(5);
    //boggle.tests();
  }

  @Override
  public void start(Stage primaryStage)
  {
    startAGame();
    primaryStage.setTitle("Boggle");
    StackPane window = new StackPane();

    Text messageBox = new Text("Enter a word to see if it is in our dictionary");
    BorderPane screen = new BorderPane();
    TextField userInputBar = new TextField();
    Text playedWords = new Text("Played Words: \n");
    screen.setBottom(userInputBar);
    screen.setTop(messageBox);
    screen.setRight(playedWords);

    //TEMP
    Text boardDisplay = new Text(boggle+"");
    screen.setCenter(boardDisplay);

    window.getChildren().add(screen);

    userInputBar.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER)
      {
        String userInput = userInputBar.getText();
        boolean successFullTurn = boggle.takeTurn(userInput);
        userInputBar.setText("");
        if (successFullTurn)
        {
          messageBox.setText(userInput + " is a valid move");
          playedWords.setText(playedWords.getText() + userInput + "\n");
        }
        else
        {
          messageBox.setText(userInput + " is not a valid move");
        }
      }
    });

    primaryStage.setScene(new Scene(window, 300, 200));
    primaryStage.show();
  }

}