package com.adaptionsoft.games.newtrivia;

public class QuestionImpl implements Question {
    private final String text;
    private final Category category;

    public QuestionImpl(String text, Category category) {
        this.text = text;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public Category getCategory() {
        return category;
    }
}
