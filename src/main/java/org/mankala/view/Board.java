package org.mankala.view;

import java.util.Arrays;

import org.mankala.entity.Player;

/**
 * This is a helper class containing method to show the result after each round and at the end of the game.
 */
public class Board {

    // Output methods used to show the result
    public void showBoard(Player playerOne, Player playerTwo) {
        System.out.println("Player one:" +
                Arrays.toString(playerOne.getPits().toArray()) +
                " manKala(" + playerOne.getId() + "):"+ playerOne.getManKala());
        System.out.println("Player two:" +
                Arrays.toString(playerTwo.getPits().toArray()) +
                " manKala(" + playerTwo.getId() + "):"+ playerTwo.getManKala());

    }

    public void printResult(Player playerOne, Player playerTwo) {
        if (playerOne.getManKala() > playerTwo.getManKala()) {
            System.out.println("Hooray! Player one is the winner");
            System.out.println("ManKala of the first player:" + playerOne.getManKala());
            System.out.println("ManKala of the second player:" + playerTwo.getManKala());

        } else if (playerOne.getManKala() < playerTwo.getManKala()) {
            System.out.println("Hooray! Player two is the winner");
            System.out.println("ManKala of the second player:" + playerTwo.getManKala());
            System.out.println("ManKala of the first player:" + playerOne.getManKala());
        } else {
            System.out.println("No winner For this game, try it again");
        }

    }
}
