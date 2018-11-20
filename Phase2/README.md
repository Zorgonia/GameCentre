## How to run our program:
1. Open Android Studio
2. From the welcome screen, click on "Check out project from version control" => git
3. Enter our group link on the url https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0585 and clone it.
4. When it asks if you want to create an Android Studio project select yes.
5. Select to import the project from an external model, and choose Gradle (not android gradle) and click next.
6. Under the Gradle Project line, add "\Phase1\GameCentre" so the project gradle is automatically selected.
It should look something like ...\AndroidStudioProjects\group_0585\Phase1\GameCentre
7. Now select to use the default gradle wrapper, and then click on finish.
8. After the project is finished indexing, you should be able to click on the run button and use our app.

## How to navigate the app:
1. You'll first be brought to a sign in/sign up screen. Make an account with a username and password at the bottom of the screen.
2. Enter your account info in the top two boxes and click sign in.
3. Now you'll be brought to the game home screen. Options include starting a new game, loading/saving a game,
viewing the leaderboards, changing the complexity of the game, and setting the amount of undos for a game.
4. If you click on a button that brings you to another screen, use the builtin android back button to return to the previous screen.
5. The personal leaderboard screen displays your level. If you forget your password, you can reset it by pressing "forgot password" on the app
starting screen and entering your username and level.

## Rules of the game:
- The goal of the game is to put the tiles in numerical order from top left corner to bottom right, with the blank tile in the bottom right
- For example, 1 2 3, 4 5 6, 7 8 blank is a victory in 3x3 (ordered row 1, row 2, row 3)
- Click on the tile you would like to move in order to move it. You may **ONLY** move tiles directly adjacent, not diagonal, to the blank tile.
- Aim for a low score, as each move (including each undo) increases your score by 1.
- When the game finishes, the board freezes, and you should use the android back button return to the main menu

## Notes:
- The commit names refer to the following utorids: Jimbo is xiejiab1, MD Tanvir Hyder is hydermd1, hartand4 is hartand4, Alexandre Nunes is nunesale, and Zorgonia is yangke16