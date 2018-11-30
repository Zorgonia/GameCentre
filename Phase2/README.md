## How to run our program:
1. Open Android Studio
2. From the welcome screen, click on "Check out project from version control" => git
3. Enter our group link on the url https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0610 and clone it.
4. When it asks if you want to create an Android Studio project select yes.
5. Select to import the project from an external model, and choose Gradle (not android gradle) and click next.
6. Under the Gradle Project line, add "\Phase2\GameCentre" so the project gradle is automatically selected.
It should look something like ...\AndroidStudioProjects\group_0610\Phase1\GameCentre
7. Now select to use the default gradle wrapper, and then click on finish.
8. After the project is finished indexing, you should be able to click on the run button and use our app.

## How to navigate the app:
1. You'll first be brought to a sign in screen. Click the button at the bottom to create an account.
2. After you've created an account, click on the android back button to return to the sign in screen to sign in.
2. Enter your account info in the top two boxes and click sign in
3. Now you'll be brought to a game select screen, where you can select from Sliding tiles, checkers, and 2048. Click on the game you would like to play.
4. When you click on a game, it brings you to that game's menu screen. Each one has a new game, load gmae, and leaderboard buttons.
The sliding tiles one also has an option for complexity, as well as number of desired undos.
5. You can click new game on any of the game menu screens to start a new game. You can manually save in sliding tiles and 2048, and can undo in sliding tiles and checkers
Note that all games have the autosave feature.
6. If you click on a button that brings you to another screen, use the builtin android back button to return to the previous screen.
7. The personal leaderboard screen displays your level. If you forget your password, you can reset it by pressing "forgot password" on the app
starting screen and entering your username and level.
8. Scoreboards are unique for each game.

## Rules of the game Sliding Tiles:
- The goal of the game is to put the tiles in numerical order from top left corner to bottom right, with the blank tile in the bottom right
- For example, 1 2 3, 4 5 6, 7 8 blank is a victory in 3x3 (ordered row 1, row 2, row 3)
- Click on the tile you would like to move in order to move it. You may **ONLY** move tiles directly adjacent, not diagonal, to the blank tile.
- Aim for a low score, as each move (including each undo) increases your score by 1.
- When the game finishes, the board freezes, and you should use the android back button return to the main menu

##Rules of checkers are in the app itself, look for the rules button

##Rules for 2048
- The goal of the game is to get the 2048 tile
- The board starts nearly empty, with one single 2 or 4 tile. You can slide the board in any of up, down, left, or right , as long as a non blank tile moves.
- In order to get higher value tiles, you need to combine tiles with the same value. 
- For example, if you have two 2s in the top left corner and the space to right of it, if you slide right they will combine to make a 4
- Similarly, two 4 tiles combine to make 8, two 8s to 16 and so on and so forth.
- You lose if you cannot make a move (i.e. no matter which way you swipe no tiles can combine and no tiles can move) 
- You win if you get the 2048 tile. In both cases, the game ends.
