import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.util.ArrayList;

public class Gui extends Application
{
  //Basic game settings
  private Game boggle;
  private int widthHeight = 5;
  private int gameTime = 180000;//In milliseconds

  //Gui
  StackPane window = new StackPane();
  BorderPane screen = new BorderPane();
  private final int tileSize = 80;
  private final String fontName = "monospaced";
  private final int fontSize = 20;
  private final int horiMarginTotalSize = fontSize*18;

  private final int windowSizeW = tileSize*widthHeight+horiMarginTotalSize;
  private final int windowSizeH = tileSize*widthHeight+75;

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

    BorderPane topBoard = new BorderPane();
    Text messageBox = new Text("Hit \"Enter\" to enter a word");
    messageBox.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));

    Text scoreBoard = new Text("Score: ");
    scoreBoard.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));

    topBoard.setRight(new Text("<>---<0>"));
    topBoard.setLeft(new Text("<0>---<>"));
    topBoard.setCenter(scoreBoard);
    topBoard.setBottom(messageBox);

    TextField userInputBar = new TextField();

    BorderPane goodPlaysColumn = new BorderPane();
    Text goodPlays = new Text("Accepted Plays:\n");
    goodPlays.setFill(Color.GREEN);
    goodPlays.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    goodPlaysColumn.setRight(goodPlays);

    BorderPane badPlaysColumn = new BorderPane();
    Text badPlays = new Text("Invalid Plays:\n");
    badPlays.setFill(Color.RED);
    badPlays.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    badPlaysColumn.setRight(badPlays);

    boardDisplay = new GridPane();

    loadImages();
    updateBoard();

    screen.setRight(goodPlaysColumn);
    screen.setLeft(badPlaysColumn);
    screen.setCenter(boardDisplay);
    screen.setBottom(userInputBar);
    screen.setTop(topBoard);

    //BorderPane.setMargin(boardDisplay, new Insets(12,12,12,12));

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
          goodPlays.setText("Accepted Plays:\n" + boggle.getGoodWords());
          scoreBoard.setText("Score: " + boggle.getScore());
        }
        else
        {
          messageBox.setText(userInput + " is not a valid play");
          badPlays.setText("Invalid Plays:\n" + boggle.getBadWords());
        }
      }
    });

    primaryStage.setScene(new Scene(window, windowSizeW, windowSizeH));
    primaryStage.show();

    Timeline timeline = new Timeline(new KeyFrame(
      Duration.millis(gameTime),
      ae -> endGame()));
    timeline.play();

    //Game over Screen.
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

  public void endGame()
  {
    BorderPane gameOver = new BorderPane();
    Text gameOverCenter = new Text("Your Final Score:" + boggle.getScore());
    gameOverCenter.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*2));

    Text gameOverLeft = new Text("GAME\nOVER");
    gameOverLeft.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));

    Text gameOverRight = new Text("GAME\nOVER");
    gameOverRight.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));

    Text gameOverBottom = new Text("You Played: " + boggle.getWordCount() + " Words");
    gameOverBottom.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));

    gameOver.setCenter(gameOverCenter);
    gameOver.setLeft(gameOverLeft);
    gameOver.setRight(gameOverRight);
    gameOver.setBottom(gameOverBottom);

    window.getChildren().remove(screen);
    window.getChildren().add(gameOver);
    System.out.println("GameOrge!");

    //System.exit(1);
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