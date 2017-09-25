import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Gui extends Application
{
  private Game boggle;
  private int widthHeight = 5;
  private final int tileSize = 40;

  //private ArrayList<ImageView> letters = new ArrayList<>();
  private ArrayList<String> letters = new ArrayList<>();
  GridPane boardDisplay;

  public static void main(String[] args)
  {
    launch(args);
  }

  public void startAGame()
  {
    boggle = new Game("assets/OpenEnglishWordList.txt",widthHeight);
  }

  @Override
  public void start(Stage primaryStage)
  {
    startAGame();
    primaryStage.setTitle("Boggle");
    StackPane window = new StackPane();

    Text messageBox = new Text("Hit \"Enter\" to enter a word");
    BorderPane screen = new BorderPane();
    TextField userInputBar = new TextField();
    Text playedWords = new Text("Words Played: \n");
    boardDisplay = new GridPane();

    //screen.setRight(playedWords);

    loadImages();
    updateBoard();

    //TEMP
    //Text boardDisplay = new Text(boggle+"");
    for (int i = 0; i < 26; i++)
    {
      //boardDisplay.add(letters.get(i),1,i);
    }


    screen.setCenter(boardDisplay);
    screen.setBottom(userInputBar);
    screen.setTop(messageBox);
    window.getChildren().add(screen);

    userInputBar.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER)
      {
        String userInput = userInputBar.getText();
        boolean successFullTurn = boggle.takeTurn(userInput);
        userInputBar.setText("");
        if (successFullTurn)
        {
          messageBox.setText("\"" + userInput + "\"" + " is a valid play");
          playedWords.setText(playedWords.getText() + userInput + "\n");
        }
        else
        {
          messageBox.setText(userInput + " is not a valid play");
        }
      }
    });

    primaryStage.setScene(new Scene(window, tileSize*5, tileSize*5+40));
    primaryStage.show();
  }

  private void updateBoard()
  {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    ArrayList<ArrayList<String>> board = boggle.getBoard();

    int alphabetIndex;
    ImageView placeHolderImageView;
    for (int r = 0; r < widthHeight; r++)
    {
      for (int c = 0; c < widthHeight; c++)
      {
        alphabetIndex = alphabet.indexOf(board.get(r).get(c).charAt(0));
        placeHolderImageView = new ImageView(new Image(letters.get(alphabetIndex)));
        placeHolderImageView.setFitWidth(tileSize);
        placeHolderImageView.setFitHeight(tileSize);
        boardDisplay.add(placeHolderImageView,c,r);
      }
    }
  }

  private void loadImages()
  {
    letters.add("File:assets/image_part_001.jpg");
    letters.add("File:assets/image_part_002.jpg");
    letters.add("File:assets/image_part_003.jpg");
    letters.add("File:assets/image_part_004.jpg");
    letters.add("File:assets/image_part_005.jpg");
    letters.add("File:assets/image_part_006.jpg");
    letters.add("File:assets/image_part_007.jpg");
    letters.add("File:assets/image_part_008.jpg");
    letters.add("File:assets/image_part_009.jpg");
    letters.add("File:assets/image_part_010.jpg");
    letters.add("File:assets/image_part_011.jpg");
    letters.add("File:assets/image_part_012.jpg");
    letters.add("File:assets/image_part_013.jpg");
    letters.add("File:assets/image_part_014.jpg");
    letters.add("File:assets/image_part_015.jpg");
    letters.add("File:assets/image_part_016.jpg");
    letters.add("File:assets/image_part_017.jpg");
    letters.add("File:assets/image_part_018.jpg");
    letters.add("File:assets/image_part_019.jpg");
    letters.add("File:assets/image_part_020.jpg");
    letters.add("File:assets/image_part_021.jpg");
    letters.add("File:assets/image_part_022.jpg");
    letters.add("File:assets/image_part_023.jpg");
    letters.add("File:assets/image_part_024.jpg");
    letters.add("File:assets/image_part_025.jpg");
    letters.add("File:assets/image_part_026.jpg");
  }

}