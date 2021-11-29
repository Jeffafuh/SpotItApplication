# SpotItApplication

This is application was created as the final group project for the class CS-3443 (Application Prog.) at UTSA during the Fall 2021 semester.

Group Members:
- Jeff Dong
- Bhavya Gokana
- Anaya Hartfield
- Joy Kiprotich
- Vamshi Ponnala

## Description

As the title suggests, this application implements a functioning version of the card game, *Spot It!* (or more commonly known as [*Dobble*](https://www.dobblegame.com/en/games/) in Europe). In addition to playing the game, this application also supports deck creation and a leaderboard.

*Spot It!* is a card game with a deck that contains a myriad of symbols on each card such that each any two cards has one, and only one, symbol in common. To generate a *Spot It!* deck with *n* symbols on each card, this application generates a finite projective plane of order *n-1* to create the deck. More information on the mathematics for how *Spot It!* decks are generated (and initial inspiration for this project) can be found [here](https://www.youtube.com/watch?v=VTDKqW_GLkw).

## The Menu

From here, the user can navigate to the other menus and optionally change the color of the background and the text on all of the buttons!

## The Game

When the user selects the "Play" button, they will be taken to a configuration screen. Here, the user can modify the deck in two ways; how many cards they want in the deck and the number of symbols on each card in the deck. Additionally, the user can also select between two game modes: Burndown (single player) and Classic (multiplayer against bots). Once all of the desired settings have been selected, the user can start the game.

### Burndown

Try to burn through the deck as fast as you can! Displayed are the top two cards of the deck; find and click on their matching symbol to eliminate one of the cards. The timer stops when the deck is empty. Once the timer stops and the game ends, the user has the option to submit their time to the leaderboard!

### Classic

See who has the fastest reflexes! Everyone has one card to start with and a central deck of cards - the first person to click the matching symbol on their card and the deck claims that card for themselves! At the end whenever the deck is empty, the person who has the most cards in their deck is the winner. Can you beat 3 other bots that are just random inputs? Play a game and find out!

## Creating A Deck

Want to generate a deck for your own personal use? Once the user presses the "Create" button, you can generate and view an entire deck with any number* of symbols!
__*Only up yo 10 symbols per card__

## Leaderboard

See how you compare to other peoples' times! Select a number of symbols per card, and view the four fastest times for that symbol count.

## Running the application

This project was made in Eclipse using Javafx.

Once the directory has been cloned, please import the project into Eclipse to run the application. Ensure that the Java compiler is enabled to allow project specific settings with the Compiler compliance level as 1.8 for the application to compile and run successfully.
