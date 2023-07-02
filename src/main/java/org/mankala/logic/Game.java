package org.mankala.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mankala.entity.Player;
import org.mankala.view.Board;

/**
 * This class contains the logic of the game.
 */

public class Game {

    // Number of stones per pit in initial stage.
    private final int numberOfStonesForStart = 4;

    // Each player has 6 pit on his side.
    private final int numberOfPitsPerPlayer = 6;


    // Flag that determines if the game is finished.
    private boolean isFinished;

    // List of players in the game
    private List<Player> players;

    // Object containing information about the selected pit by player
    private PitSeLector pitSeLector = new PitSeLector();

    // Helper class to show the board after each round and final result
    private Board board = new Board();

    // Check if the player should be skipped for a round
    private boolean shouldBeSkipped = false;

    public void playGame() {

        initializePlayers();

        startGame(players.get(0), players.get(1));

    }

    // Game initialization
    public void initializePlayers() {

        players = new ArrayList<>(2);
        // Create an object for first player and initialize it.
        Player playerOne = new Player(1);
        initializePitForPlayer(playerOne);
        playerOne.setManKala(0);
        players.add(playerOne);

        // Create an object for second player and initialize it
        Player playerTwo = new Player(2);
        initializePitForPlayer(playerTwo);
        playerTwo.setManKala(0);
        players.add(playerTwo);

        board.showBoard(playerOne, playerTwo);

    }

    public void initializePitForPlayer(Player player) {
        List<Integer> pits = new ArrayList<>(Collections.nCopies(numberOfPitsPerPlayer, numberOfStonesForStart));
        player.setPits(pits);
    }

    // Play Game and Update the board based in selected pit
    public void startGame(Player playerOne, Player playerTwo) {

        // Play game by switching players till is finished.
        do {
            // First player plays if his turn shouldn't be skipped.
            selectPitAndUpdateBoard(playerOne, playerTwo);

            // Second player plays if his turn shouldn't be skipped
            selectPitAndUpdateBoard(playerTwo, playerOne);

        } while (!isFinished);

        board.printResult(playerOne, playerTwo);
    }

    public void selectPitAndUpdateBoard(Player currentPlayer, Player opponentPlayer) {

        // Check if we need to skip the current player
        if (!shouldBeSkipped) {
            int selectedPitOfPlayerOne = pitSeLector.selectPit(currentPlayer);

            if (!updateBoardBasedOnSelectedPit(selectedPitOfPlayerOne, currentPlayer, opponentPlayer)) {
                setShouldBeSkipped(true);
            }

            // Always show the board by showing the first player on top
            if (currentPlayer.getId() == 1) {
                board.showBoard(currentPlayer, opponentPlayer);
            } else {
                board.showBoard(opponentPlayer, currentPlayer);
            }

            // Check if we can continue or game is over
            if (isPitsEmpty(currentPlayer) || isPitsEmpty(opponentPlayer)) {
                setFinished(true);
                setShouldBeSkipped(true);

                //Add all stones of the one which is not empty to its manKala
                currentPlayer.setManKala(currentPlayer. getManKala() +
                        currentPlayer.getPits().stream().collect(Collectors.summingInt(Integer::intValue)));
                opponentPlayer.setManKala(opponentPlayer.getManKala() +
                        opponentPlayer.getPits().stream().collect(Collectors.summingInt(Integer::intValue)));

            }

        } else {
            // Reset the flag should be skipped that other player can play
            setShouldBeSkipped(false);
        }

    }

    public boolean updateBoardBasedOnSelectedPit(int selectedPit, Player currentPlayer, Player opponentPlayer) {
        int selectedPitIndex = selectedPit - 1;
        int startingPitIndex = selectedPit;
        int numberOfStones = currentPlayer.getPits().get(selectedPitIndex);
        currentPlayer.getPits().set(selectedPitIndex, 0);

        if (numberOfStones == 0) {
            System.out.println("Current pit is empty. Choose another one");
            setFinished(false);
            return false;
        }
        
        do {

            // Add stones to player itself
            for (int i = startingPitIndex; i < 6; i++) {
                int newNumberOfStones = currentPlayer.getPits().get(i) + 1;
                currentPlayer.getPits().set(i, newNumberOfStones);
                numberOfStones--;

                // Check if we should continue or the stones are finished already.
                if (numberOfStones == 0) {
                    // The player can take the opponents stone from the cross opposite pot and put it into the manKala
                    // if he ended up having one stone in his last pot in his side
                    if(currentPlayer.getPits().get(i) == 1) {
                        currentPlayer.setManKala(currentPlayer.getManKala() +
                                currentPlayer.getPits().get(i) +
                                opponentPlayer.getPits().get(5 - i));
                        currentPlayer.getPits().set(i, 0);
                        opponentPlayer.getPits().set(5 - i, 0);
                    }
                    return true;
                }
            }

            // Add stone to mankala of the player
            currentPlayer.setManKala(currentPlayer.getManKala() + 1);
            numberOfStones--;
            if (numberOfStones == 0) {
                // We should give another round to current player and skip the opponent
                setShouldBeSkipped(true);
                return false;
            }

            // Add stones to the opponent
            for (int i = 0; i < 6; i++) {
                int newNumberOfStones = opponentPlayer.getPits().get(i) + 1;
                opponentPlayer.getPits().set(i, newNumberOfStones);
                numberOfStones--;

                // Check if we should continue or the stones are finished already.
                if (numberOfStones == 0) {
                    return true;
                }
            }

            //Reset the starting position for current player
            startingPitIndex = 0;

        } while(numberOfStones > 0);

        return true;
    }

    // Other methods
    public boolean isPitsEmpty(Player player) {
        return player.getPits().stream().allMatch(pit -> pit.equals(0));
    }

    // Getter and Setters
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean getShouldBeSkipped() {
        return shouldBeSkipped;
    }

    public void setShouldBeSkipped(boolean shouldBeSkipped) {
        this.shouldBeSkipped = shouldBeSkipped;
    }
}
