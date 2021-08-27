package com.demo.dto;

public enum GameStatus {
    IN_PROGRESS("Roll a die! Your game continues."),
    WIN("Congratulations! You have just won a game. It was a pleasure to play with you."),
    LOSE("Almost done. Roll a die again.");

    private final String statusDescription;

    GameStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public String toString() {
        return statusDescription;
    }
}
