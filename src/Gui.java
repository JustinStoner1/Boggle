import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Gui extends Application
{
  //Basic game settings
  private Game boggle;
  private int widthHeight = 5;//max is 10
  private int gameTimeLimit = 180;//In milliseconds 180000
  private Timeline timeline;

  //Gui
  private StackPane window;
  private BorderPane screen;
  private Text currTimeDisplay;
  private Text scoreBoard;
  private TextField userInputBar;
  private String backGroundStyle = "-fx-background: rgb(200,200,200);";
  //Dark: "-fx-background: rgb(80,80,80);";

  //Window size
  private final int tileSize = 80;
  private final String fontName = "monospaced";
  private final int fontSize = 20;
  private final int horiMarginTotalSize = 0;//fontSize*18;

  private final int windowSizeW = tileSize*widthHeight+horiMarginTotalSize;
  private final int windowSizeH = tileSize*widthHeight+100;

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

    window = new StackPane();
    screen = new BorderPane();
    currTimeDisplay = new Text();

    window.setStyle(backGroundStyle);

    primaryStage.setTitle("Boggle");

    BorderPane topBoard = new BorderPane();

    //Message Box
    Text messageBox = new Text(" Hit \"Enter\" to enter a word");
    messageBox.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    messageBox.setFill(Color.ORANGERED);

    //Score Board
    scoreBoard = new Text("Score: ");
    scoreBoard.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));
    scoreBoard.setFill(Color.ORANGERED);

    //Time Display
    currTimeDisplay.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*0.8));
    currTimeDisplay.setFill(Color.ORANGERED);

    //Info Display
    topBoard.setTop(currTimeDisplay);
    topBoard.setCenter(scoreBoard);
    topBoard.setBottom(messageBox);

    //Good words column
    VBox goodPlaysColumn = new VBox();
    Text goodPlays = new Text(" Accepted Plays: \n");
    goodPlays.setFill(Color.SEAGREEN);
    goodPlays.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    goodPlaysColumn.getChildren().add(goodPlays);
    ScrollPane rightBar = new ScrollPane();
    rightBar.setContent(goodPlaysColumn);
    rightBar.setStyle(backGroundStyle);

    //Bad words column
    VBox badPlaysColumn = new VBox();
    Text badPlays = new Text(" Invalid Plays: \n");
    badPlays.setFill(Color.ORANGERED);
    badPlays.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    badPlaysColumn.getChildren().add(badPlays);
    ScrollPane leftBar = new ScrollPane();
    leftBar.setContent(badPlaysColumn);
    leftBar.setStyle(backGroundStyle);

    //Text Field
    userInputBar = new TextField();

    //board
    boardDisplay = new GridPane();
    loadImages();
    updateBoard();

    //Loading components
    screen.setRight(rightBar);
    screen.setLeft(leftBar);
    screen.setCenter(boardDisplay);
    screen.setBottom(userInputBar);
    screen.setTop(topBoard);

    //BorderPane.setMargin(boardDisplay, new Insets(12,12,12,12));

    window.getChildren().add(screen);

    userInputBar.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER)
      {
        String userInput = userInputBar.getText();
        if (userInput.length() > 0)
        {
          boolean successFullTurn = boggle.takeTurn(userInput);
          userInputBar.setText("");
          if (successFullTurn)
          {
            messageBox.setText(" \"" + userInput + "\"" + " is a valid play, worth " + (userInput.length()-2) + " Point(s)");
            goodPlays.setText(" Accepted Plays: \n" + boggle.getGoodWords());
            scoreBoard.setText("Score: " + boggle.getScore());
          }
          else
          {
            int errorCode = boggle.getLastErrorCode();
            messageBox.setText(" \"" + userInput + "\" " + errorCodeToString(errorCode));
            if (errorCode != 2)
            {
              badPlays.setText(" Invalid Plays: \n" + boggle.getBadWords());
            }
            //badPlaysColumn.getChildren().add(new Text(userInput);
          }
        }
        else
        {
          messageBox.setText(" You must type a word");
        }
      }
    });

    primaryStage.setScene(new Scene(window, windowSizeW+badPlays.getBoundsInLocal().getWidth()+goodPlays.getBoundsInLocal().getWidth(), windowSizeH));
    primaryStage.show();

    GameTicker gameTicker = new GameTicker();
    gameTicker.start();
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

  private String errorCodeToString(int errorCode)
  {
    switch (errorCode)
    {
      case 1: return "was too small";
      case 2: return "was already played";
      case 3: return "is not in the board";
      case 4: return "is not a real word";
      default: return "failed for unknown reasons";
    }
  }

  private void endGame()
  {
    BorderPane gameOver = new BorderPane();

    Text gameOverCenter = new Text("Your Final Score:" + boggle.getScore());
    gameOverCenter.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*2));
    gameOverCenter.setFill(Color.ORANGERED);

    Text gameOverLeft = new Text("GAME\nOVER");
    gameOverLeft.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));
    gameOverLeft.setFill(Color.ORANGERED);

    Text gameOverRight = new Text("GAME\nOVER");
    gameOverRight.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));
    gameOverRight.setFill(Color.ORANGERED);

    Text gameOverBottom = new Text("You Played: " + boggle.getWordCount() + " Words");
    gameOverBottom.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));
    gameOverBottom.setFill(Color.ORANGERED);

    gameOver.setCenter(gameOverCenter);
    gameOver.setLeft(gameOverLeft);
    gameOver.setRight(gameOverRight);
    gameOver.setBottom(gameOverBottom);

    window.getChildren().remove(screen);
    window.getChildren().add(gameOver);
    //System.exit(1);
  }

  class GameTicker extends AnimationTimer
  {
    private final double nanoToSeconds = Math.pow(10,-9);

    boolean startUp = false;
    double initialTime;//In milliseconds
    double currTime;//In milliseconds
    double timeRemaining;//In seconds
    @Override
    public void handle(long now)
    {
      if (startUp)
      {
        currTime = (now * nanoToSeconds) - initialTime;
        //currTime = Math.floor(currTime*100)/100;
      }
      else
      {
        initialTime = now * Math.pow(10,-9);
        startUp = true;
      }
      timeRemaining = (float)(gameTimeLimit - currTime);
      timeRemaining = ((int)(timeRemaining*100))/100.0;
      currTimeDisplay.setText("Current Time: " + timeRemaining);
      if (timeRemaining <= 0)
      {
        endGame();
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