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

Board extends abstract class. Will have lots of dupe code
Boardmanager implements interface.
Personalscoreboard/Scoreboard doesn't use additional interfaces/parents. Adjust to display current game's scores.
Gameactivity: After loading, terminate load screen and previous gameactivity.
Gameactivity: leave it mostly as it is and modify boardmanager to be used differently by gameactivity?
Keep move as it is, maybe extend for checkers
Tile: Abstract Class (Constructor empty)
Undostack: Leave as it is for all others. Extend for checkers.