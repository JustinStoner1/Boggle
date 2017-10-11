# Boggle
The Boggle Project

Starting The Game:
  Simply click run in inteliji or run the jar

Playing The Game:
  Plays very simply, type words into the text field that you want to play, or you can click the letter dice on the board and it will enter the letters for you.
  The game ends when the time runs out

How it works:
  Most of this is explained in the comments.
  User input is taken and check aganist a variety of checks of legallity
  Most of this is simply checking if a certain list contains a certain string (word)
  The board finds words in itself recursively by calling itselfs with different parts of the word
  So once it finds a letter, it will call itself with the next letter it is looking for and so on
  It looks for letters by checking its neighbors for matching letters
  
Known bugs:
  Only one, sometimes, for some reason, when the program is stopped in inteliji, it will crash for reasons unkown.
  This error has something to do with something called a "quantum renderer" and does not originate from within my code that i know of
