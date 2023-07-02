package org.mankala.logic;

import java.util.Scanner;

import org.mankala.entity.Player;

/**
 * This pit selector is responsible for reading an input from user for selected pit
 * and checks if it is valid or not.
 */
public class PitSeLector {

    // Pit number selected by player
    private int selectedPit;

    /**
     * Select the pit for the player on his/her own round.
     * @param {Player} player The player that can selects the pit.
     * @returns {int} selectedPit the list of actions that would be deleted.
     */
    public int selectPit(Player player) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Choose the pit for player[" + player.getId() + "]:");
        System.out.println("Please only provide integer number from 1 to 6.");

        do {
            while (wrongDataType(reader)) {
                reader.next();
            }
        } while(wrongPitNumberSelected(reader.nextInt()));

        System.out.println("The stones will be taken from pit number:" + selectedPit);
        return selectedPit;
    }

    //Validation methods
    /**
     * Checks if the wrong integer number is provided by user or not.
     * @param {int} pitNumber The player that can selects the pit.
     * @returns {boolean} true if the user provided wrong number and should provide another number.
     */
    public boolean wrongPitNumberSelected(int pitNumber) {
        if (pitNumber < 1 || pitNumber > 6) {
            System.out.println("This input is not allowed. Please provide integer number from 1 to 6");
            return true;
        }
        selectedPit = pitNumber;
        return false;

    }

    /**
     * Checks if the wrong datatype is provided by user or not.
     * @param {Scanner} reader text scanner that can read next input.
     * @returns {boolean} true if the user provided wrong dataType. Then user should provide another input with int data type.
     */
    public boolean wrongDataType(Scanner reader) {
        if (!reader.hasNextInt()) {
            System.out.println("Wrong data type. Please provide integer number from 1 to 6");
            return true;
        }
        return false;
    }

    public int getSelectedPit() {
        return selectedPit;
    }

    public void setSelectedPit(int selectedPit) {
        this.selectedPit = selectedPit;
    }
}
