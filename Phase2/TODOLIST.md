TODOLIST


Phase 1:

The plans are to finish implementing all the unfinished sections of Phase 1. This includes allowing players to choose
complexity, allowing scores to be displayed correctly. Code may also need to be refactored as progress is made. Most
other concepts have also been implemented, including basic undo features, saving and loading to different files,
accounts and related functions, as well as autosave.

things to finish
Mostly refactoring code and implementing functions more efficiently - Everyone
Any bonus things that there are time for/people interested in - Anyone

what has already been done
Account (sign up, sign in) - Andries/Jimbo
Save/Load - Jimbo/Kevin
Autosave- Jimbo
Undo - Alex
GameCentre homescreen - Jimbo/Andries
Scoreboard and Score related things - Tanvir
Complexity - Kevin
Scoreboard finish up - Tanvir (maybe other people if required)
Account and score relations - Andries/Jimbo

Phase 2:
Done:
The making of board into generic class GameBoard implementation for other games (Tanvir)
The making of BoardManager to interface (Jimbo and Alex)
Making tile abstract (Andries)
General implementations of 2048 (Tanvir and Kevin) and Checkers (Jimbo and Alex) are complete
Unit tests for sliding tiles board and tile generally complete (Andries)
ScoreBoard implementation over multiple games (Jimbo and Tanvir)
Autosaves work both for checkers and 2048, general saves also implemented in 2048. (Kevin 2048, Jimbo checkers)
Undo works for checkers, as well as a "draw" button (Alex)
Most of the UI elements (Tanvir)
Unit tests for 2048 (Kevin), checkers (Alex).


To work on currently:
- Make use of more design patterns if possible
- Refactor code as you go along a lot of this
- Fix other style issues

Board extends abstract class. Will have lots of dupe code
Boardmanager implements interface.
Personalscoreboard/Scoreboard doesn't use additional interfaces/parents. Adjust to display current game's scores.
Gameactivity: After loading, terminate load screen and previous gameactivity.
Gameactivity: leave it mostly as it is and modify boardmanager to be used differently by gameactivity?
Keep move as it is, maybe extend for checkers
Tile: Abstract Class (Constructor empty)
Undostack: Leave as it is for all others. Extend for checkers.

Kevin's thoughts on some of the remaining classes not discussed yet/things to think about
Save/Load Activity, only 1 and extracted out of the package or multiple instances for each separate game?
So for example SlidingSave CheckersSave ... vs all in one file (like right now) that switches depending on the game you're currently in
probably the second is better in my opinion
Account -> we're pulling this out probably? AccountActivity is like the home screen under my understanding
starting activity/game home screen -> abstract class because implementation should be relatively similar for each activity
the only things are specifically implementing certain things such as the complexity slider for slidingtiles (not in the parent) 
Pulling forgetactivity out because it's not tied to a specific game
The other classes like movement controller and custom adapter not too sure about as of now.