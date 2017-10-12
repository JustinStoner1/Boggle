//***********************************
//Justin Stoner
//
//Description:
// starts the game and builds the gui
//***********************************

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Gui extends Application
{
  //Basic game settings
  private Game boggle;
  private int widthHeight;//max is 10

  //Gui size parts in pixels
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
  private TextField userInputBar;
  private Text messageBox;
  private String backGroundStyle = "-fx-background: rgb(200,200,200);";
  //Board
  private Group boardVisualStack = new Group();
  private Canvas boardDisplayCanvas;
  private ArrayList<ArrayList<String>> board;
  private ArrayList<String> letters = new ArrayList<>();

  //Window size
  private int tileSize;
  private final String fontName = "monospaced";
  private final int fontSize = 20;

  public static void main(String[] args)
  {
    launch(args);
  }
  //***********************************
  //input: Stage primaryStage
  //returns: void
  //Called by javafx, used to start the gui and program
  //***********************************
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
  //***********************************
  //input: int widthHeight
  //returns: void
  //Makes a new game of boggle
  //tells the game to display the game screen
  //***********************************
  public void initializeGame(int widthHeight)
  {
    this.widthHeight = widthHeight;
    tileSize = maxBoardWidthHeight/widthHeight;
    boggle = new Game("assets/OpenEnglishWordList.txt", widthHeight);
    displayGameGui();
  }
  //***********************************
  //input: void
  //returns: void
  //Builds the start menu
  //sends the chosen widthHeight to initializeGame()
  //***********************************
  private void startGame()
  {
    startScreen = new BorderPane();

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
  //***********************************
  //input: void
  //returns: void
  //Builds the game gui and sets up the input sources
  //***********************************
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
    //Time Display
    currTimeDisplay.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
    currTimeDisplay.setFill(Color.ORANGERED);
    //Info Display
    infoBoard.setCenter(messageBox);
    infoBoard.setPrefHeight(infoBoardHeight);

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
    gameScreen.setCenter(boardVisualStack);
    gameScreen.setBottom(bottomBoard);
    gameScreen.setTop(infoBoard);

    window.getChildren().remove(startScreen);
    window.getChildren().add(gameScreen);

    primaryStage.show();
  }
  //***********************************
  //input: void
  //returns: void
  //Grabs the tray from the instance of boggle
  // and builds the board for the gui
  //***********************************
  private void updateBoard()
  {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    board = boggle.getBoard();

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

        boardVisualStack.getChildren().addAll(diceRectangle);
      }
    }
  }
  //***********************************
  //input: int errorCode
  //returns: void
  //Translates the last error code from the game to a readable string
  //***********************************
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
  //***********************************
  //The event handler that handles the start menus buttons
  //Calls initializeGame() with the chosen board size
  //***********************************
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
  //***********************************
  //The event handler that handles the enter key and button
  //Grabs the text from the text field and sends it to be processed in the game
  //Updates the score board area with the current game states
  //Tells the user if and why they input was illegal
  //***********************************
  class EnterWord implements EventHandler
  {
    @Override
    public void handle(Event event)
    {
      if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && ((KeyEvent) event).getCode() != KeyCode.ENTER) return;
      String userInput = userInputBar.getText().trim();
      if (userInput.length() > 0)
      {
        boolean successFullTurn = boggle.takeTurn(userInput);
        userInputBar.setText("");
        if (successFullTurn)
        {
          messageBox.setText(" \"" + userInput + "\"" + " is a valid play, worth " + (userInput.length() - 2) + " Point(s)");
        }
        else
        {
          int errorCode = boggle.getLastErrorCode();
          messageBox.setText(" \"" + userInput + "\" " + errorCodeToString(errorCode));
        }
      }
      else messageBox.setText(" You must type a word");
    }
  }
  //***********************************
  //input: void
  //returns: void
  //loads the dice pictures into a usable array
  //***********************************
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