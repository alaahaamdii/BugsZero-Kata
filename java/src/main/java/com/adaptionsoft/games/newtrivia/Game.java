package com.adaptionsoft.games.newtrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private final Questions questions;
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game(Questions questions) {
        this.questions = questions;
    }

    public boolean addPlayer(String playerName) {
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                movePlayerAndAskQuestion(currentPlayer, roll);
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            movePlayerAndAskQuestion(currentPlayer, roll);
        }
    }

    private void movePlayerAndAskQuestion(Player player, int roll) {
        player.setPlace((player.getPlace() + roll) % 12);
        System.out.println(player.getName() + "'s new location is " + player.getPlace());
        System.out.println("The category is " + currentCategory(player.getPlace()));
        askQuestion();
    }

    private void askQuestion() {
        Question question = questions.getNextQuestion(currentCategory(players.get(currentPlayerIndex).getPlace()));
        System.out.println(question.getText());
    }

    private Category currentCategory(int place) {
        switch (place) {
            case 0: case 4: case 8: return Category.POP;
            case 1: case 5: case 9: return Category.SCIENCE;
            case 2: case 6: case 10: return Category.SPORTS;
            default: return Category.ROCK;
        }
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                currentPlayer.addGoldCoin();
                System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coins.");

                boolean winner = didPlayerWin(currentPlayer);
                nextPlayer();
                return winner;
            } else {
                nextPlayer();
                return true;
            }
        } else {
            System.out.println("Answer was correct!!!!");
            currentPlayer.addGoldCoin();
            System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coins.");

            boolean winner = didPlayerWin(currentPlayer);
            nextPlayer();
            return winner;
        }
    }

    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);

        nextPlayer();
        return true;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }

    private boolean didPlayerWin(Player player) {
        return player.getPurse() < 6;
    }
}
