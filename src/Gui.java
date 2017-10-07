import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Gui extends Application
{
  //Basic game settings
  private Game boggle;
  private int widthHeight;//max is 10
  private int gameTimeLimit = 180;//In milliseconds 180000

  //Gui size parts
  private final int maxBoardWidthHeight = 400;
  private final int wordColumnWidth = 210;
  private final int textBoxHeight = 30;
  private final int infoBoardHeight = 100;

  //Gui
  private Stage primaryStage;
  private StackPane window;
  //StartScreenGui
  private BorderPane startScreen;
  //GameGui
  private BorderPane gameScreen;
  private Text currTimeDisplay;
  private Text scoreBoard;
  private TextField userInputBar;
  private Text messageBox;
  private Text badPlays;
  private Text goodPlays;
  private String backGroundStyle = "-fx-background: rgb(200,200,200);";
  //Dark: "-fx-background: rgb(80,80,80);";

  //Board
  private Group boardVisualStack = new Group();
  private Canvas boardDisplayCanvas;
  private ArrayList<ArrayList<String>> board;
  private ArrayList<String> letters = new ArrayList<>();

  //Window size
  private int tileSize;
  private final String fontName = "monospaced";
  private final int fontSize = 20;

  //Game Play
  private boolean isDrawing = false;
  private Point mousePos = new Point();
  private Point lastClickPos = new Point();
  private Group lineVisualStack = new Group();

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;

    primaryStage.setTitle("Boggle");
    window = new StackPane();

    int width = maxBoardWidthHeight+wordColumnWidth*2;
    int height = maxBoardWidthHeight+textBoxHeight+infoBoardHeight;
    primaryStage.setScene(new Scene(window,width,height));

    startGame();
  }

  public void initializeGame(int widthHeight)
  {
    this.widthHeight = widthHeight;
    tileSize = maxBoardWidthHeight/widthHeight;
    boggle = new Game("assets/OpenEnglishWordList.txt", widthHeight);
    displayGameGui();
  }

  private void startGame()
  {
    startScreen = new BorderPane();

    int height = 0;
    int width = 0;

    Text welcome = new Text("    Welcome to Boggle!\n    what do you want the board size to be?\n");
    welcome.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize*1.5));
    welcome.setFill(Color.ORANGERED);

    SetBoardSize pressHandler = new SetBoardSize();

    Button fourXfour = new Button("    4 x 4    ");
    fourXfour.setOnAction(pressHandler);
    fourXfour.setPrefHeight(wordColumnWidth*2);
    fourXfour.setPrefWidth(wordColumnWidth*2);
    Button fiveXfive = new Button("    5 x 5    ");
    fiveXfive.setOnAction(pressHandler);
    fiveXfive.setPrefHeight(wordColumnWidth*2);
    fiveXfive.setPrefWidth(wordColumnWidth*2);

    HBox buttons = new HBox();
    buttons.getChildren().add(fourXfour);
    buttons.getChildren().add(fiveXfive);

    startScreen.setTop(welcome);
    startScreen.setRight(fourXfour);
    startScreen.setLeft(fiveXfive);

    window.getChildren().add(startScreen);

    primaryStage.show();
  }

  private void displayGameGui()
  {
    gameScreen = new BorderPane();
    currTimeDisplay = new Text();

    window.setStyle(backGroundStyle);

    //TopBoard
    BorderPane infoBoard = new BorderPane();

    //Message Box
    messageBox = new Text(" Hit \"Enter\" to enter a word");
    messageBox.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    messageBox.setFill(Color.ORANGERED);
    //Score Board
    scoreBoard = new Text("Score: ");
    scoreBoard.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize * 1.5));
    scoreBoard.setFill(Color.ORANGERED);
    //Time Display
    currTimeDisplay.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize * 0.8));
    currTimeDisplay.setFill(Color.ORANGERED);
    //Info Display
    infoBoard.setTop(currTimeDisplay);
    infoBoard.setCenter(scoreBoard);
    infoBoard.setBottom(messageBox);
    infoBoard.setPrefHeight(infoBoardHeight);

    //Good words column
    VBox goodPlaysColumn = new VBox();

    Text goodPlaysText = new Text("  Valid Words:   ");
    goodPlaysText.setFill(Color.SEAGREEN);
    goodPlaysText.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    goodPlaysColumn.getChildren().add(goodPlaysText);

    goodPlays = new Text("");
    goodPlays.setFill(Color.SEAGREEN);
    goodPlays.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    goodPlaysColumn.getChildren().add(goodPlays);
    ScrollPane rightBar = new ScrollPane();
    rightBar.setContent(goodPlays);
    rightBar.setStyle(backGroundStyle);

    goodPlaysColumn.getChildren().add(rightBar);
    goodPlaysColumn.setPrefWidth(wordColumnWidth);

    //Bad words column
    VBox badPlaysColumn = new VBox();

    Text badPlaysText = new Text("  Invalid Words: ");
    badPlaysText.setFill(Color.ORANGERED);
    badPlaysText.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    badPlaysColumn.getChildren().add(badPlaysText);

    badPlays = new Text("");
    badPlays.setFill(Color.ORANGERED);
    badPlays.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    badPlaysColumn.getChildren().add(badPlays);
    ScrollPane leftBar = new ScrollPane();
    leftBar.setContent(badPlays);
    leftBar.setStyle(backGroundStyle);

    badPlaysColumn.getChildren().add(leftBar);
    badPlaysColumn.setPrefWidth(wordColumnWidth);

    //BottomBoard
    BorderPane bottomBoard = new BorderPane();

    //Text Field
    EnterWord enterWord = new EnterWord();
    userInputBar = new TextField();
    userInputBar.setOnKeyPressed(enterWord);
    userInputBar.setPrefHeight(textBoxHeight);

    //Enter Button
    Button enter =  new Button("  Enter  ");
    enter.setOnAction(enterWord);
    enter.setPrefHeight(textBoxHeight);

    bottomBoard.setCenter(userInputBar);
    bottomBoard.setLeft(enter);

    //Board
    boardDisplayCanvas = new Canvas(tileSize * widthHeight, tileSize * widthHeight);
    boardVisualStack.getChildren().add(boardDisplayCanvas);
    loadImages();
    updateBoard();

    //Loading components
    gameScreen.setRight(goodPlaysColumn);
    gameScreen.setLeft(badPlaysColumn);
    gameScreen.setCenter(boardVisualStack);
    gameScreen.setBottom(bottomBoard);
    gameScreen.setTop(infoBoard);

    window.getChildren().remove(startScreen);
    window.getChildren().add(gameScreen);

    primaryStage.show();

    GameTicker gameTicker = new GameTicker();
    gameTicker.start();
  }

  private void updateBoard()
  {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    board = boggle.getBoard();

    MouseClick clickFinder =  new MouseClick();

    int alphabetIndex;
    for (int r = 0; r < widthHeight; r++)
    {
      for (int c = 0; c < widthHeight; c++)
      {
        alphabetIndex = alphabet.indexOf(board.get(r).get(c).charAt(0));
        Image dice = new Image(letters.get(alphabetIndex), false);
        ImagePattern imagePattern = new ImagePattern(dice);
        Rectangle diceRectangle = new Rectangle(c * tileSize, r * tileSize, tileSize, tileSize);
        diceRectangle.setFill(Color.DARKOLIVEGREEN);
        diceRectangle.setFill(imagePattern);

        diceRectangle.setOnMousePressed(clickFinder);
        boardVisualStack.getChildren().addAll(diceRectangle);
      }
    }
    boardVisualStack.getChildren().add(lineVisualStack);
  }

  private String errorCodeToString(int errorCode)
  {
    switch (errorCode)
    {
      case 1:
        return "was too small";
      case 2:
        return "was already played";
      case 3:
        return "is not in the board";
      case 4:
        return "is not a real word";
      default:
        return "failed for unknown reasons";
    }
  }

  private void endGame()
  {
    BorderPane gameOver = new BorderPane();

    Text gameOverCenter = new Text("Your Final Score:" + boggle.getScore());
    gameOverCenter.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize * 2));
    gameOverCenter.setFill(Color.ORANGERED);

    Text gameOverLeft = new Text("GAME\nOVER");
    gameOverLeft.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize * 1.5));
    gameOverLeft.setFill(Color.ORANGERED);

    Text gameOverRight = new Text("GAME\nOVER");
    gameOverRight.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize * 1.5));
    gameOverRight.setFill(Color.ORANGERED);

    Text gameOverBottom = new Text("You Played: " + boggle.getWordCount() + " Words");
    gameOverBottom.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize * 1.5));
    gameOverBottom.setFill(Color.ORANGERED);

    gameOver.setCenter(gameOverCenter);
    gameOver.setLeft(gameOverLeft);
    gameOver.setRight(gameOverRight);
    gameOver.setBottom(gameOverBottom);

    window.getChildren().remove(gameScreen);
    window.getChildren().add(gameOver);

    //System.exit(1);
  }

  class SetBoardSize implements EventHandler
  {
    @Override
    public void handle(Event event)
    {
      String buttonText = ((Button)event.getSource()).getText();
      if (buttonText.equals("    5 x 5    ")) initializeGame(5);
      else initializeGame(4);
    }
  }

  class EnterWord implements EventHandler
  {
    @Override
    public void handle(Event event)
    {
      if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && ((KeyEvent) event).getCode() != KeyCode.ENTER) return;
      String userInput = userInputBar.getText().trim();
      isDrawing = false;
      lineVisualStack.getChildren().clear();
      if (userInput.length() > 0)
      {
        boolean successFullTurn = boggle.takeTurn(userInput);
        userInputBar.setText("");
        if (successFullTurn)
        {
          messageBox.setText(" \"" + userInput + "\"" + " is a valid play, worth " + (userInput.length() - 2) + " Point(s)");
          goodPlays.setText(boggle.getGoodWords());
          scoreBoard.setText("Score: " + boggle.getScore());
        }
        else
        {
          int errorCode = boggle.getLastErrorCode();
          messageBox.setText(" \"" + userInput + "\" " + errorCodeToString(errorCode));
          if (errorCode != 2)
          {
            badPlays.setText(boggle.getBadWords());
          }
        }
      }
      else messageBox.setText(" You must type a word");
    }
  }

  class MouseClick implements EventHandler
  {
    @Override
    public void handle(Event event)
    {
      Rectangle origin = (Rectangle)event.getSource();
      int oXPixel = (int)origin.getX()+tileSize/2;
      int oYPixel = (int)origin.getY()+tileSize/2;
      int oXBoard = oXPixel/tileSize;
      int oYBoard = oYPixel/tileSize;
      String letter = board.get(oYBoard).get(oXBoard);
      userInputBar.setText(userInputBar.getText()+letter);

      if (isDrawing)
      {
        mousePos = MouseInfo.getPointerInfo().getLocation();
        Line arrow = new Line(lastClickPos.getX(), lastClickPos.getY(), oXPixel, oYPixel);
        //Rectangle arrow = new Rectangle(lastClickPos.getX(), lastClickPos.getY(), oXPixel, oYPixel);
        //arrow.setFill(new Color(1,1,1,0.5));
        //arrow.setStrokeWidth(5);
        arrow.setFill(Color.TRANSPARENT);
        arrow.setFill(new Color(1,1,1,0.5));
        //arrow.setAl
        lineVisualStack.getChildren().add(arrow);
      }
      else
      {
        isDrawing = true;
      }
      lastClickPos.setLocation(oXPixel,oYPixel);
    }
  }

  class GameTicker extends AnimationTimer
  {
    private final double nanoToSeconds = Math.pow(10, -9);

    boolean startUp = false;
    double initialTime;//In milliseconds
    double currTime;//In milliseconds
    //double timeRemaining;//In seconds

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
        initialTime = now * Math.pow(10, -9);
        startUp = true;
      }
      double timeRemaining = gameTimeLimit - currTime;
      timeRemaining = ((int) (timeRemaining * 100)) / 100.0;
      currTimeDisplay.setText("\n  Current Time: " + timeRemaining);
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